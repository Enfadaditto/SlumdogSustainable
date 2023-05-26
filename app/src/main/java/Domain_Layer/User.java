package Domain_Layer;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.table.DatabaseTable;
import com.slumdogsustainable.MainActivity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Patron_Mediador.IMediadorLogros;
import Patron_Mediador.MediadorLogros;
import Patron_estado.EstadoNivelJugador;
import Persistence.ODS_URepository;
import Persistence.PartidaRepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

@DatabaseTable
public class User {
    @DatabaseField (id = true)
    private String nickname;
    @DatabaseField
    private String email;
    @DatabaseField
    private String password;
    @DatabaseField
    private int pointsAchieved;
    @DatabaseField
    private float timeSpent;
    @DatabaseField
    private int gamesAchieved;
    @DatabaseField
    private int gamesFailed;
    @DatabaseField
    private int gamesAbandoned;
    @DatabaseField
    private int icon;
    @DatabaseField
    private int pointsAchievedOnMonday;
    @DatabaseField
    private int gamesAchievedOnMonday;
    @DatabaseField
    private int timeSpentOnMonday;
    @DatabaseField
    private int metasSemanales;
    @DatabaseField
    private boolean logrosAñadidos;

    private List<ODS_has_User> ODSUser;
    private IMediadorLogros mediadorLogros = new MediadorLogros();
    private EstadoNivelJugador estado;



    User() {}
    public User(String nick, String email, String password, int icon) {
        this.nickname = nick;
        this.email = email;
        this.password = password;
        this.icon = icon;
        this.pointsAchieved = 0;
        this.timeSpent = 0;
        this.logrosAñadidos = false;
    }

    public static void updatePartidasandTime(UserRepository userRepository, Boolean hit, Boolean abadonada, int time, int Puntos) {
            try {
                    PartidaRepository p = new PartidaRepository(SingletonConnection.getSingletonInstance());
                    if(hit) {
                            MainActivity.user.setGamesAchieved(MainActivity.user.getGamesAchieved() + 1);
                            p.guardar(new Partida((int) p.getDao().countOf(), MainActivity.user.getNickname(), Puntos, new Date()));
                    }
                    else if(!abadonada){
                            MainActivity.user.setGamesFailed(MainActivity.user.getGamesFailed() + 1);
                    }
                    else {
                            MainActivity.user.setGamesAbandoned(MainActivity.user.getGamesAbandoned() + 1);
                            p.guardar(new Partida((int) p.getDao().countOf(), MainActivity.user.getNickname(), Puntos, new Date()));
                    }
                    MainActivity.user.setTimeSpent(MainActivity.user.getTimeSpent() + ((float) time) / 60000);
                    userRepository.getDao().update(MainActivity.user);
            } catch (SQLException e) {
                    throw new RuntimeException(e);
            }
    }

    public static boolean checkUsernameNotTaken(UserRepository userRepository, String username) {
            List<User> users = userRepository.obtenerTodos();
            List<String> userNicks = new ArrayList<>();
            for (User u : users) userNicks.add(u.getNickname());
            return !userNicks.contains(username);
    }

    public static User getUserByUsername(UserRepository userRepository, String username) {
            List<User> users = userRepository.obtenerTodos();
            for (User user : users) {
                    if (user.getNickname().equals(username)) return user;
            }
            return null;
    }

    public static boolean checkPassword(UserRepository userRepository, String username, String password) {
            if(getUserByUsername(userRepository, username) != null) {
                    return getUserByUsername(userRepository, username).getPassword().equals(password);
            }
            return false;
    }


    public int getGamesAbandoned() {
        return gamesAbandoned;
    }

    public void setGamesAbandoned(int gamesAbandoned) {
        this.gamesAbandoned = gamesAbandoned;
    }

    public int getGamesAchieved() {
        return gamesAchieved;
    }

    public int getGamesFailed() {
        return gamesFailed;
    }

    public void setGamesAchieved(int gamesAchieved) {
        this.gamesAchieved = gamesAchieved;
    }

    public void setGamesFailed(int gamesFailed) {
        this.gamesFailed = gamesFailed;
    }

    public boolean passwordIsSafe(String password) {
        return  password.matches("^(?=(.*[a-z]){1,})(?=(.*[A-Z]){1,})(?=(.*[0-9]){1,})(?=(.*[!@#$%^&*()\\-__+.]){1,}).{8,}$");
    }
    public void addODSKnowledge(ODS_has_User newODSKnown) {
        this.ODSUser.add(newODSKnown);
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPointsAchieved() {
        return pointsAchieved;
    }

    public void setPointsAchieved(int pointsAchieved) {
        this.pointsAchieved = pointsAchieved;
    }

    public float getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(float timeSpent) {
        this.timeSpent = timeSpent;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void desbloquearLogro(Logro logro) {
        mediadorLogros.notificarLogroDesbloqueado(this, logro);
    }

    public void addEnlaces() {
        mediadorLogros.addEnlaces(this);
    }

   public int  getNivelUsuario(){
       if(this.getPointsAchieved()>5000 && this.getPointsAchieved()<10000)  {
           return 1;
       }else if(this.getPointsAchieved()>=10000){
           return 2;
       }

       return 0;
   }

    public List<ODS_has_User> getAllODS_user(ODS_URepository ods_uRepository) {
        List <ODS_has_User> res = new ArrayList();
        List <ODS_has_User> ods = ods_uRepository.obtenerTodos();
        for(ODS_has_User o : ods) {
            if(o.getUserNickname().equals(getNickname())) {
                res.add(o);
            }
        }
        return res;
    }

    public void updateODS(Boolean hit, int idODS, ODS_URepository ods_uRepository) {
        List <ODS_has_User> ods = ods_uRepository.obtenerTodos();
        UpdateBuilder<ODS_has_User, Integer> updateBuilder = ods_uRepository.getDao().updateBuilder();
        for(ODS_has_User o : ods) {
            if(o.getUserNickname().equals(getNickname()) && idODS == o.getid_Ods()) {
                try {
                    updateBuilder.where().eq("username", getNickname()).and().eq("id_ODS", o.getid_Ods());
                    if(hit) {
                        o.oneRightGuess();
                        updateBuilder.updateColumnValue("hits", o.getRightGuesses());
                    }
                    else {
                        o.oneWrongGuess();
                        updateBuilder.updateColumnValue("fails", o.getWrongGuesses());
                    }
                    updateBuilder.update();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public List <ODS_has_User.tuplaStats> getPercentagebyUser(ODS_URepository ods_uRepository) {
        List <ODS_has_User> aux = new ArrayList();
        List <ODS_has_User> ods = ods_uRepository.obtenerTodos();
        List <ODS_has_User.tuplaStats> res = new ArrayList<>();
        for(ODS_has_User o : ods) {
            if(o.getUserNickname().equals(getNickname())) {
                aux.add(o);

            }
        }
        for(ODS_has_User o : aux) {
            res.add(new ODS_has_User.tuplaStats(o.getRightGuesssesPercent(), o.getid_Ods()));
        }

        return res;
    }

    public void cargarODSUser(String nickname) {
        ODS_URepository ODS = new ODS_URepository(SingletonConnection.getSingletonInstance());
        for(int i = 0; i < 18; i++) {
            ODS.guardar(new ODS_has_User(nickname, i, 0,0));
        }
    }


    public int getPointsAchievedOnMonday() {
        return pointsAchievedOnMonday;
    }

    public void setPointsAchievedOnMonday(int pointsAchievedOnMonday) {
        this.pointsAchievedOnMonday = pointsAchievedOnMonday;
    }

    public int getGamesAchievedOnMonday() {
        return gamesAchievedOnMonday;
    }

    public void setGamesAchievedOnMonday(int gamesAchievedOnMonday) {
        this.gamesAchievedOnMonday = gamesAchievedOnMonday;
    }

    public int getTimeSpentOnMonday() {
        return timeSpentOnMonday;
    }

    public void setTimeSpentOnMonday(int timeSpentOnMonday) {
        this.timeSpentOnMonday = timeSpentOnMonday;
    }

    public int getMetasSemanales() {
        return metasSemanales;
    }

    public void setMetasSemanales(int metasSemanales) {
        this.metasSemanales = metasSemanales;
    }

    public boolean isLogrosAñadidos() {
        return logrosAñadidos;
    }

    public void setLogrosAñadidos(boolean logrosAñadidos) {
        this.logrosAñadidos = logrosAñadidos;
    }
}
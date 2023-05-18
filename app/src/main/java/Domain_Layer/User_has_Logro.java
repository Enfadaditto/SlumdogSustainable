package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;
import com.slumdogsustainable.MainActivity;

import java.util.ArrayList;
import java.util.List;

import Persistence.LogroRepository;
import Persistence.SingletonConnection;
import Persistence.User_LRepository;

public class User_has_Logro implements Observador{

    @DatabaseField(id = true)
    private int id_userLogro;

    @DatabaseField
    private String id_user;
    @DatabaseField
    private int id_logro;
    @DatabaseField
    private int progreso;
    @DatabaseField
    private boolean completado;

    User_has_Logro() {}

    public User_has_Logro(String id_user, int id_logro) {
        this.id_user = id_user; this.id_logro = id_logro;
        this.progreso = 0; this.completado = false;
    }

    @Override
    public void actualizar(int id_logro) {
        if (this.id_logro != id_logro) return;

        Logro logro = new Logro(-1,"","");
        User_LRepository userlogroR = new User_LRepository(SingletonConnection.getSingletonInstance());

        MainActivity.logrosCompletados.add(logro.getLogroPorID(id_logro));
        this.setProgreso(100); this.setCompletado(true);
        userlogroR.actualizar(this);

        //Mostrar logro por pantalla
        // ESQUEMA DE COMO MOSTRARLO

        //  |----------|---------------------------|
        //  |          |   NOMBRE DEL LOGRO        |
        //  | ICONO    |---------------------------|
        //  |          |   DESCRIPCIÓN DEL LOGRO   |
        //  |          |   DESCRIPCIÓN DEL LOGRO   |
        //  |----------|---------------------------|

        // Poner logro a completado en user_has_logro
        // Para mostrar logros completados en el armario comprobar todos aquellos que fueron completados
    }

    public List<User_has_Logro> getAllUserLogros(User u) {
        List<User_has_Logro> ret = new ArrayList<>();
        List<Logro> logros = new LogroRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();

        for (Logro l : logros) {
            if (this.getEnlaceUsuarioLogro(u.getNickname(), l.getId_logro()) != null)
                ret.add(this.getEnlaceUsuarioLogro(u.getNickname(), l.getId_logro()));
            else
                ret.add(new User_has_Logro(u.getNickname(), l.getId_logro()));
        }

        return ret;
    }

    public User_has_Logro getEnlaceUsuarioLogro(String username, int id_logro) {
        List<User_has_Logro> listaEnlaces = new User_LRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
        for (User_has_Logro x : listaEnlaces)
            if (x.getId_user().equals(username) && x.getId_logro() == id_logro) return x;
        return null;
    }


    public User_has_Logro getUserLogroPorID(int id) {
        List<User_has_Logro> UHL = new User_LRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
        for (User_has_Logro x : UHL) if (x.id_logro == id) return x;
        return null;
    }

    public String getId_user() {
        return id_user;
    }

    public int getId_logro() {
        return id_logro;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}

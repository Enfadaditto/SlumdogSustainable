package Domain_Layer;

import android.graphics.Bitmap;
import android.media.Image;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.slumdogsustainable.MainActivity;

import java.util.List;

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

    private Bitmap icon;

    private List<ODS_has_User> ODSUser;

    private List<Logro> MedallasYTrofeos;

    User() {}
    public User(String nick, String email, String password, Bitmap icon) {
        this.nickname = nick;
        this.email = email;
        this.password = password;
        this.icon = icon;
        this.pointsAchieved = 0;
        this.timeSpent = 0;
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

    public void addLogro(Logro newLogro) { this.MedallasYTrofeos.add(newLogro);}

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

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
}
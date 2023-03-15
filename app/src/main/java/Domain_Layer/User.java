package Domain_Layer;

import android.media.Image;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

import Persistence.DBConnection;
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
    private Image icon;

    private List<ODS_has_User> ODSUser;

    public User(String nick, String email, String password) {
        this.nickname = nick;
        this.email = email;
        this.password = password;
        this.pointsAchieved = 0;
        this.timeSpent = 0;
    }

    public boolean checkPassword(String username, String password) {
        return password == new UserRepository(new DBConnection().getConnection()).GetUserByNickName(username).password;
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

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
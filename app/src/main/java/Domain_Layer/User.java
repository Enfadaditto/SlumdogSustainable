package Domain_Layer;

import android.media.Image;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    private Image icon;

    private List<ODS_has_User> ODSUser;

    User() {}
    public User(String nick, String email, String password) {
        this.nickname = nick;
        this.email = email;
        this.password = password;
        this.pointsAchieved = 0;
        this.timeSpent = 0;
    }

    public boolean checkUsernameNotTaken(String username) {
        return new UserRepository().getUserByUsername(username).equals(null);
    }

    public boolean checkPassword(String username, String password) {
        return new UserRepository().getUserByUsername(username).password.equals(password);
    }

    public boolean passwordIsSafe(String password) {
        return  password.matches("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z])");
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
package Domain_Layer;

import java.util.List;

public class User {

    private String nickname;
    private String email;
    private String password;
    private int pointsAchieved;
    private float timeSpent;

    private List<ODS_has_User> ODSUser;

    public User(String nick, String email, String password) {
        this.nickname = nick;
        this.email = email;
        this.password = password;
        this.pointsAchieved = 0;
        this.timeSpent = 0;
    }

    public String getNickname() {return this.nickname;}
    public void setNickname(String newNickname) {this.nickname = newNickname;}
    public String getEmail() {return this.email;}
    public void setEmail(String newEmail) {this.email = newEmail;}
    public String getPassword() {return this.password;}
    public void setPassword(String newPassword) {this.password = newPassword;}
    // TODO: Add more getters and setters for the remaining attributes
    public void addODSKnowledge(ODS_has_User newODSKnown) {this.ODSUser.add(newODSKnown);}
}
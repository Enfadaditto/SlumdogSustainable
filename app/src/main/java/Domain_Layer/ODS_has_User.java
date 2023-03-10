package Domain_Layer;

public class ODS_has_User {

    private String ODSTheme;
    private String userNickname;
    private int rightGuesses;
    private int wrongGuesses;
    private double rightGuessesPercent;

    private User user;
    private ODS ods;

    public ODS_has_User(User user, ODS ods){
        this.user = user;
        this.ods = ods;

        this.ODSTheme = ods.getTheme();
        this.userNickname = user.getNickname();
        this.rightGuesses = 0;
        this.wrongGuesses = 0;
    }

    public String getODSTheme() {
        return ODSTheme;
    }

    public void setODSTheme(String ODSTheme) {
        this.ODSTheme = ODSTheme;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public int getRightGuesses() {
        return rightGuesses;
    }

    public void oneRightGuess() {
        this.rightGuesses++;
    }

    public int getWrongGuesses() {
        return wrongGuesses;
    }

    public void oneWrongGuess() {
        this.wrongGuesses++;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ODS getOds() {
        return ods;
    }

    public void setOds(ODS ods) {
        this.ods = ods;
    }

    public double getRigthGuesssesPercent() {
        return this.rightGuesses/(this.rightGuesses + this.wrongGuesses) * 100;
    }
}

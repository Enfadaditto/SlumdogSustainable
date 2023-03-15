package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ODS_has_User {

    private String ODSTheme;
    @DatabaseField
    private int hits;
    @DatabaseField
    private int fails;

    private double rightGuessesPercent;
    @DatabaseField(id = true)
    private String username;
    @DatabaseField(id = true)
    private int id_ODS;

    public ODS_has_User(String user, int ods) {
        this.username = user;
        this.id_ODS = ods;
        this.hits = 0;
        this.fails = 0;
    }

    public String getODSTheme() {
        return ODSTheme;
    }

    public void setODSTheme(String ODSTheme) {
        this.ODSTheme = ODSTheme;
    }

    public String getUserNickname() {
        return username;
    }

    public void setUserNickname(String userNickname) {
        this.username = userNickname;
    }

    public int getRightGuesses() {
        return hits;
    }

    public void oneRightGuess() {
        this.hits++;
    }

    public int getWrongGuesses() {
        return fails;
    }

    public void oneWrongGuess() {
        this.fails++;
    }

    public int getid_Ods() {
        return id_ODS;
    }

    public void setid_Ods(int ods) {
        this.id_ODS = ods;
    }

    public double getRigthGuesssesPercent() {
        return this.hits / (this.hits + this.fails) * 100;
    }
}

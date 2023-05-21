package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.slumdogsustainable.MainActivity;

import java.util.ArrayList;
import java.util.List;

import Persistence.ODS_URepository;

@DatabaseTable
public class ODS_has_User {

    private String ODSTheme;
    @DatabaseField
    private int hits;
    @DatabaseField
    private int fails;

    private double rightGuessesPercent;
    @DatabaseField
    private String username;
    @DatabaseField
    private int id_ODS;

    ODS_has_User() {}
    public ODS_has_User(String user, int ods) {
        this.username = user;
        this.id_ODS = ods;
        this.hits = 0;
        this.fails = 0;
    }

    public ODS_has_User(String user, int ods, int hits, int fails) {
        this.username = user;
        this.id_ODS = ods;
        this.hits = hits;
        this.fails = fails;
    }

    public static List<Integer> getNumberHitsandFails(ODS_URepository ods_uRepository) {
        List<Integer> sol = new ArrayList(); sol.add(0); sol.add(0);
        List <ODS_has_User> aux = MainActivity.user.getAllODS_user(ods_uRepository);
        for(int i = 0; i < 18; i++) {
            sol.set(0, sol.get(0) + aux.get(i).getRightGuesses());
            sol.set(1, sol.get(1) + aux.get(i).getWrongGuesses());
        }
        return sol;
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

    public float getRightGuesssesPercent() {
        return (100 * (float) this.hits / (this.hits + this.fails));
    }

    public static class tuplaStats {
        public float percentage;
        public int idOds;

        public tuplaStats(float p, int i) {
            percentage = p;
            idOds = i;
        }
    }
}

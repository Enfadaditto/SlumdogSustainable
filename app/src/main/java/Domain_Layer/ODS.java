package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;
import java.util.Random;
@DatabaseTable
public class ODS {
    @DatabaseField
    private String theme;
    @DatabaseField(id = true)
    private int id_ODS;
    ODS() {}
    public ODS(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setId_ODS(int id_ODS) {
        this.id_ODS = id_ODS;
    }

    public int getId_ODS() {
        return id_ODS;
    }

}

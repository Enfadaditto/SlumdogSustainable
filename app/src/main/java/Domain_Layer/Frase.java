package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;

public class Frase {
    @DatabaseField(id = true)
    private int id_frase;

    @DatabaseField
    private String frase;
    @DatabaseField
    private int id_ODS;

    Frase() {}

    public Frase(String frase, int id_ODS) {
        this.frase = frase;
        this.id_ODS = id_ODS;
    }
    public int getId_ODS() {
        return id_ODS;
    }
    public void setId_ODS(int id_ODS) {
        this.id_ODS = id_ODS;
    }
    public int getId_frase() {
        return id_frase;
    }
    public String getFrase() {
        return frase;
    }
    public void setFrase(String frase) {
        this.frase = frase;
    }
    public void setId_frase(int id_frase) {
        this.id_frase = id_frase;
    }
}

package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;

public class Ahorcado {
    @DatabaseField(id = true)
    private int idAhorcado;

    @DatabaseField
    private String enunciado;

    @DatabaseField
    private String palabra;

    @DatabaseField
    private int id_ODS;

    @DatabaseField
    private String Dificultad;

    Ahorcado() {}

    public Ahorcado(String enunciado, String palabra, int id_ODS, String Dificultad) {
        this.palabra = palabra;
        this.enunciado = enunciado;
        this.id_ODS = id_ODS;
        this.Dificultad = Dificultad;
    }

    public String getDificultad() {
        return Dificultad;
    }

    public void setDificultad(String dificultad) {
        Dificultad = dificultad;
    }

    public int getId_ODS() {
        return id_ODS;
    }

    public void setId_ODS(int id_ODS) {
        this.id_ODS = id_ODS;
    }

    public int getIdAhorcado() {
        return idAhorcado;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setIdAhorcado(int idAhorcado) {
        this.idAhorcado = idAhorcado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }
}

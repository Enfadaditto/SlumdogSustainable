package Domain_Layer;

import androidx.annotation.NonNull;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Frase extends Partida {
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

    public List<Character> letrasDeLaFrase() {
        List<Character> listadoCaracteres = new ArrayList<>();
        char[] fraseCaracteres = this.frase.toCharArray();
        for(char letra : fraseCaracteres) {
            if (!listadoCaracteres.contains(letra)) listadoCaracteres.add(letra);
        }

        return listadoCaracteres;
    }
    public char[] fraseProblema() {
        char[] fraseResultado = this.frase.toCharArray();
        int modificadorDificultad = (int) Math.ceil((40 - this.getNivel()*10) * this.frase.replace(" ", "").length()) - 1;
        List<Integer> posicionesMostrarLetra = new ArrayList<>();
        int i = 0;
        while (i < modificadorDificultad) {
            int posicionMostrar = (int) Math.floor(Math.random()*(fraseResultado.length));
            if (!posicionesMostrarLetra.contains(posicionMostrar) && fraseResultado[posicionMostrar] != ' ') {
                posicionesMostrarLetra.add(posicionMostrar); i++;
            }
        }

        for (int j = 0; j < fraseResultado.length; j++) {
            if (!posicionesMostrarLetra.contains(j) && fraseResultado[j] != ' ') fraseResultado[j] = '_';
        }

        return fraseResultado;
    }
}

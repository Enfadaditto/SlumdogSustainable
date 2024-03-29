package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Persistence.FraseRepository;
import Persistence.SingletonConnection;

public class Frase extends Reto {
    @DatabaseField(id = true)
    private int id_frase;

    @DatabaseField
    private String frase;
    @DatabaseField
    private int id_ODS;

    private String Dificultad;

    @DatabaseField
    private String Descripcion;
    private char[] fraseProblema;

    Frase() {}

    public Frase(String frase, int id_ODS, String descripcion) {
        this.frase = frase;
        this.id_ODS = id_ODS;
        this.Descripcion = descripcion;
    }

    public static List<Frase> getListadoFrase(FraseRepository fraseRepository) throws SQLException{
        List<Frase> list = fraseRepository.getDao().queryForAll();
        List<Frase> resultlist = new ArrayList<>();
        for(Frase q : list) resultlist.add(q);
        return resultlist;
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
        char[] fraseCaracteres = sacarFraseParaBotones(this.frase.toCharArray());
        for(char letra : fraseCaracteres) {
            if ((letra+"").matches("[A-Z]*") || (letra+"").matches("[a-z]*")) listadoCaracteres.add(letra);
        }

        return listadoCaracteres;
    }
    public void fraseProblema(int diff) {
        char[] fraseResultado = this.frase.toCharArray();
        int modificadorDificultad = (int) Math.ceil((40 - diff*10)/100.0 * this.frase.replace(" ", "").length()) - 1;
        List<Integer> posicionesMostrarLetra = new ArrayList<>();
        int i = 0;
        while (i < modificadorDificultad) {
            int posicionMostrar = (int) Math.floor(Math.random()*(fraseResultado.length));
            if (!posicionesMostrarLetra.contains(posicionMostrar) &&
                    ((fraseResultado[posicionMostrar]+"").matches("[A-Z]*") ||
                    (fraseResultado[posicionMostrar]+"").matches("[a-z]*"))) {
                posicionesMostrarLetra.add(posicionMostrar); i++;
            }
        }

        for (int j = 0; j < fraseResultado.length; j++) {
            if (!posicionesMostrarLetra.contains(j) &&
                    ((fraseResultado[j]+"").matches("[A-Z]*") ||
               (fraseResultado[j]+"").matches("[a-z]*")))

                fraseResultado[j] = '_';
        }

        this.fraseProblema = fraseResultado;
    }

    public char[] sacarFraseParaBotones(char[] frase) {
        char[] returnFrase = frase;
        for (int i = 0; i < frase.length; i++){
            if (frase[i] == this.fraseProblema[i]) returnFrase[i]=' ';
        }
        return returnFrase;
    }

    public char[] getFraseProblema(int diff) {
        if (this.fraseProblema == null) fraseProblema(diff);
        return fraseProblema;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public Frase getFraseDB() {
        FraseRepository fr = new FraseRepository(SingletonConnection.getSingletonInstance());
        List<Frase> listFrase = new ArrayList<>();
        try {
            listFrase = new ArrayList<>(getListadoFrase(fr));
        } catch (SQLException e) {}

        Collections.shuffle(listFrase);
        return listFrase.get(0);
    }
}

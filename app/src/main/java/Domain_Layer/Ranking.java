package Domain_Layer;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.Comparator;
import java.util.List;

public class Ranking {
    int puntos;
    String nombre;

    public Ranking(int p, String n) {
        puntos = p;
        nombre = n;
    }

    public int getPuntos() {
        return puntos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public class RankingComparator implements Comparator<Ranking> {
        @Override
        public int compare(Ranking ranking, Ranking t1) {
            return Integer.compare(ranking.getPuntos(), t1.getPuntos());
        }
    }


}

package Patron_strategy;

import static Patron_Fachada.FachadaDeRetos.haConsolidado;
import static Patron_Fachada.FachadaDeRetos.juegoRetoPregunta;
import static Patron_Fachada.FachadaDeRetos.pistas;
import static Patron_Fachada.FachadaDeRetos.puntosConsolidados;
import static Patron_Fachada.FachadaDeRetos.puntosTotales;
import static Patron_Fachada.FachadaDeRetos.ronda;
import static Patron_Fachada.FachadaDeRetos.vidas;

import android.os.Bundle;

public class PlayPregunta implements JuegoStrategy{
    public Bundle crearReto() {
        Bundle b = new Bundle();
        b.putInt("idPregunta", juegoRetoPregunta.getPreguntaActual().getQuestionID());
        b.putInt("PuntosTotales", puntosTotales);
        b.putInt("PuntosConsolidados", puntosConsolidados);
        b.putInt("Vidas", vidas);
        b.putInt("Ronda", ronda);
        b.putBoolean("haConsolidado", haConsolidado);
        b.putInt("TiempoOpcion", juegoRetoPregunta.getTiempoOpcion());
        b.putInt("Tiempo", juegoRetoPregunta.getTiempo());
        b.putInt("Nivel", juegoRetoPregunta.getNivel());
        b.putInt("SonidoFallo", juegoRetoPregunta.getSonidofallo());
        b.putInt("SonidoAcierto", juegoRetoPregunta.getSonidoacierto());
        b.putInt("Pistas", pistas);
        return b;
    }
}

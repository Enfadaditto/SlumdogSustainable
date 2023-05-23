package Patron_strategy;

import static Patron_Fachada.FachadaDeRetos.haConsolidado;
import static Patron_Fachada.FachadaDeRetos.juegoRetoAhorcado;
import static Patron_Fachada.FachadaDeRetos.pistas;
import static Patron_Fachada.FachadaDeRetos.puntosConsolidados;
import static Patron_Fachada.FachadaDeRetos.puntosTotales;
import static Patron_Fachada.FachadaDeRetos.ronda;
import static Patron_Fachada.FachadaDeRetos.vidas;

import android.os.Bundle;

public class PlayAhorcado implements JuegoStrategy{
    public Bundle crearReto() {
        Bundle b = new Bundle();
        b.putString("palabraAhorcado", juegoRetoAhorcado.getAhorcado().getPalabra());
        b.putString("enunciadoAhorcado", juegoRetoAhorcado.getAhorcado().getEnunciado());
        b.putInt("PuntosTotales", puntosTotales);
        b.putInt("PuntosConsolidados", puntosConsolidados);
        b.putInt("Vidas", vidas);
        b.putInt("Ronda", ronda);
        b.putBoolean("haConsolidado", haConsolidado);
        b.putInt("TiempoOpcion", juegoRetoAhorcado.getTiempoOpcion());
        b.putInt("Tiempo", juegoRetoAhorcado.getTiempo());
        b.putInt("Nivel", juegoRetoAhorcado.getNivel());
        b.putInt("SonidoFallo", juegoRetoAhorcado.getSonidofallo());
        b.putInt("SonidoAcierto", juegoRetoAhorcado.getSonidoacierto());
        b.putInt("erroresRetoAhorcado", juegoRetoAhorcado.getErroresRetoAhorcado());
        b.putInt("odsAhorcado", juegoRetoAhorcado.getAhorcado().getId_ODS());
        b.putInt("Pistas", pistas);
        return b;
    }
}

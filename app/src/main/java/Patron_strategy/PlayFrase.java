package Patron_strategy;
import static Patron_Fachada.FachadaDeRetos.haConsolidado;
import static Patron_Fachada.FachadaDeRetos.juegoRetoDescubrirFrase;

import static Patron_Fachada.FachadaDeRetos.pistas;
import static Patron_Fachada.FachadaDeRetos.puntosConsolidados;
import static Patron_Fachada.FachadaDeRetos.puntosTotales;
import static Patron_Fachada.FachadaDeRetos.retoDescubrirFrase;
import static Patron_Fachada.FachadaDeRetos.ronda;
import static Patron_Fachada.FachadaDeRetos.vidas;

import android.os.Bundle;

import Builder.BuilderRetoDescubrirFrase;
import Builder.Director;


public class PlayFrase implements JuegoStrategy{

    public Bundle crearReto() {
        Bundle b = new Bundle();
        b.putString("fraseEnunciado", juegoRetoDescubrirFrase.getFraseActual().getFrase());
        b.putString("descripcionEnunciado", juegoRetoDescubrirFrase.getFraseActual().getDescripcion());
        b.putInt("PuntosTotales", puntosTotales);
        b.putInt("PuntosConsolidados", puntosConsolidados);
        b.putInt("Vidas", vidas);
        b.putInt("Ronda", ronda);
        b.putBoolean("haConsolidado", haConsolidado);
        b.putInt("TiempoOpcion", juegoRetoDescubrirFrase.getTiempoOpcion());
        b.putInt("Tiempo", juegoRetoDescubrirFrase.getTiempo());
        b.putInt("Nivel", juegoRetoDescubrirFrase.getNivel());
        b.putInt("SonidoFallo", juegoRetoDescubrirFrase.getSonidofallo());
        b.putInt("SonidoAcierto", juegoRetoDescubrirFrase.getSonidoacierto());
        b.putInt("odsFrase", juegoRetoDescubrirFrase.getFraseActual().getId_ODS());
        b.putInt("Pistas", pistas);
        return b;
    }

    public void obtenerRetos() {
        Director creadorDeJuego = new Director();
        retoDescubrirFrase = new BuilderRetoDescubrirFrase();
        creadorDeJuego.setJuegoBuilder(retoDescubrirFrase);
        creadorDeJuego.construirJuego();
        juegoRetoDescubrirFrase = retoDescubrirFrase.getJuego();
    }
}

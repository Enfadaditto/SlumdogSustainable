package Patron_strategy;

import static Patron_Fachada.FachadaDeRetos.retoAhorcado;
import static Patron_Fachada.FachadaDeRetos.retoDescubrirFrase;
import static Patron_Fachada.FachadaDeRetos.retoPregunta;
import static Patron_Fachada.FachadaDeRetos.juegoRetoDescubrirFrase;
import static Patron_Fachada.FachadaDeRetos.juegoRetoPregunta;
import static Patron_Fachada.FachadaDeRetos.juegoRetoAhorcado;



import android.os.Bundle;

import Builder.BuilderRetoAhorcado;
import Builder.BuilderRetoDescubrirFrase;
import Builder.BuilderRetoPregunta;
import Builder.Director;

public class PlayMixto implements JuegoStrategy{
    @Override
    public Bundle crearReto() {
        return null;
    }

    public void obtenerRetos() {
        Director creadorDeJuego = new Director();
        retoDescubrirFrase = new BuilderRetoDescubrirFrase();
        creadorDeJuego.setJuegoBuilder(retoDescubrirFrase);
        creadorDeJuego.construirJuego();
        juegoRetoDescubrirFrase = retoDescubrirFrase.getJuego();

        retoPregunta = new BuilderRetoPregunta();
        creadorDeJuego.setJuegoBuilder(retoPregunta);
        creadorDeJuego.construirJuego();
        juegoRetoPregunta = retoPregunta.getJuego();

        retoAhorcado = new BuilderRetoAhorcado();
        creadorDeJuego.setJuegoBuilder(retoAhorcado);
        creadorDeJuego.construirJuego();
        juegoRetoAhorcado = retoAhorcado.getJuego();
    }
}

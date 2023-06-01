package Domain_Layer.Patron_strategy;


import android.os.Bundle;

import Domain_Layer.Patron_Builder.BuilderRetoAhorcado;
import Domain_Layer.Patron_Builder.BuilderRetoDescubrirFrase;
import Domain_Layer.Patron_Builder.BuilderRetoPregunta;
import Domain_Layer.Patron_Builder.Director;
import Domain_Layer.Patron_Fachada.FachadaDeRetos;

public class MixtoStrategy implements JuegoStrategy{
    @Override
    public Bundle crearReto() {
        return null;
    }

    public void obtenerRetos() {
        Director creadorDeJuego = new Director();
        FachadaDeRetos.retoDescubrirFrase = new BuilderRetoDescubrirFrase();
        creadorDeJuego.setJuegoBuilder(FachadaDeRetos.retoDescubrirFrase);
        creadorDeJuego.construirJuego();
        FachadaDeRetos.juegoRetoDescubrirFrase = FachadaDeRetos.retoDescubrirFrase.getJuego();

        FachadaDeRetos.retoPregunta = new BuilderRetoPregunta();
        creadorDeJuego.setJuegoBuilder(FachadaDeRetos.retoPregunta);
        creadorDeJuego.construirJuego();
        FachadaDeRetos.juegoRetoPregunta = FachadaDeRetos.retoPregunta.getJuego();

        FachadaDeRetos.retoAhorcado = new BuilderRetoAhorcado();
        creadorDeJuego.setJuegoBuilder(FachadaDeRetos.retoAhorcado);
        creadorDeJuego.construirJuego();
        FachadaDeRetos.juegoRetoAhorcado = FachadaDeRetos.retoAhorcado.getJuego();
    }
}

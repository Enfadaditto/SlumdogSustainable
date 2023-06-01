package Domain_Layer.Patron_strategy;

import android.os.Bundle;

import Domain_Layer.Patron_Builder.BuilderRetoDescubrirFrase;
import Domain_Layer.Patron_Builder.Director;
import Domain_Layer.Patron_Fachada.FachadaDeRetos;


public class FraseStrategy implements JuegoStrategy{

    public Bundle crearReto() {
        Bundle b = new Bundle();
        b.putString("fraseEnunciado", FachadaDeRetos.juegoRetoDescubrirFrase.getFraseActual().getFrase());
        b.putString("descripcionEnunciado", FachadaDeRetos.juegoRetoDescubrirFrase.getFraseActual().getDescripcion());
        b.putInt("PuntosTotales", FachadaDeRetos.puntosTotales);
        b.putInt("PuntosConsolidados", FachadaDeRetos.puntosConsolidados);
        b.putInt("Vidas", FachadaDeRetos.vidas);
        b.putInt("Ronda", FachadaDeRetos.ronda);
        b.putBoolean("haConsolidado", FachadaDeRetos.haConsolidado);
        b.putInt("TiempoOpcion", FachadaDeRetos.juegoRetoDescubrirFrase.getTiempoOpcion());
        b.putInt("Tiempo", FachadaDeRetos.juegoRetoDescubrirFrase.getTiempo());
        b.putInt("Nivel", FachadaDeRetos.juegoRetoDescubrirFrase.getNivel());
        b.putInt("SonidoFallo", FachadaDeRetos.juegoRetoDescubrirFrase.getSonidofallo());
        b.putInt("SonidoAcierto", FachadaDeRetos.juegoRetoDescubrirFrase.getSonidoacierto());
        b.putInt("odsFrase", FachadaDeRetos.juegoRetoDescubrirFrase.getFraseActual().getId_ODS());
        b.putInt("Pistas", FachadaDeRetos.pistas);
        return b;
    }

    public void obtenerRetos() {
        Director creadorDeJuego = new Director();
        FachadaDeRetos.retoDescubrirFrase = new BuilderRetoDescubrirFrase();
        creadorDeJuego.setJuegoBuilder(FachadaDeRetos.retoDescubrirFrase);
        creadorDeJuego.construirJuego();
        FachadaDeRetos.juegoRetoDescubrirFrase = FachadaDeRetos.retoDescubrirFrase.getJuego();
    }
}

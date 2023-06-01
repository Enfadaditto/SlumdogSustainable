package Domain_Layer.Patron_strategy;

import android.os.Bundle;

import Domain_Layer.Patron_Builder.BuilderRetoAhorcado;
import Domain_Layer.Patron_Builder.Director;
import Domain_Layer.Patron_Fachada.FachadaDeRetos;

public class AhorcadoStrategy implements JuegoStrategy{
    public Bundle crearReto() {
        Bundle b = new Bundle();
        b.putString("palabraAhorcado", FachadaDeRetos.juegoRetoAhorcado.getAhorcado().getPalabra());
        b.putString("enunciadoAhorcado", FachadaDeRetos.juegoRetoAhorcado.getAhorcado().getEnunciado());
        b.putInt("PuntosTotales", FachadaDeRetos.puntosTotales);
        b.putInt("PuntosConsolidados", FachadaDeRetos.puntosConsolidados);
        b.putInt("Vidas", FachadaDeRetos.vidas);
        b.putInt("Ronda", FachadaDeRetos.ronda);
        b.putBoolean("haConsolidado", FachadaDeRetos.haConsolidado);
        b.putInt("TiempoOpcion", FachadaDeRetos.juegoRetoAhorcado.getTiempoOpcion());
        b.putInt("Tiempo", FachadaDeRetos.juegoRetoAhorcado.getTiempo());
        b.putInt("Nivel", FachadaDeRetos.juegoRetoAhorcado.getNivel());
        b.putInt("SonidoFallo", FachadaDeRetos.juegoRetoAhorcado.getSonidofallo());
        b.putInt("SonidoAcierto", FachadaDeRetos.juegoRetoAhorcado.getSonidoacierto());
        b.putInt("erroresRetoAhorcado", FachadaDeRetos.juegoRetoAhorcado.getErroresRetoAhorcado());
        b.putInt("odsAhorcado", FachadaDeRetos.juegoRetoAhorcado.getAhorcado().getId_ODS());
        b.putInt("Pistas", FachadaDeRetos.pistas);
        return b;
    }

    @Override
    public void obtenerRetos() {
        Director creadorDeJuego = new Director();
        FachadaDeRetos.retoAhorcado = new BuilderRetoAhorcado();
        creadorDeJuego.setJuegoBuilder(FachadaDeRetos.retoAhorcado);
        creadorDeJuego.construirJuego();
        FachadaDeRetos.juegoRetoAhorcado = FachadaDeRetos.retoAhorcado.getJuego();
    }
}

package Domain_Layer.Patron_strategy;

import android.os.Bundle;

import Domain_Layer.Patron_Builder.BuilderRetoPregunta;
import Domain_Layer.Patron_Builder.Director;
import Domain_Layer.Patron_Fachada.FachadaDeRetos;

public class PreguntaStrategy implements JuegoStrategy{
    public Bundle crearReto() {
        Bundle b = new Bundle();
        b.putInt("idPregunta", FachadaDeRetos.juegoRetoPregunta.getPreguntaActual().getQuestionID());
        b.putInt("PuntosTotales", FachadaDeRetos.puntosTotales);
        b.putInt("PuntosConsolidados", FachadaDeRetos.puntosConsolidados);
        b.putInt("Vidas", FachadaDeRetos.vidas);
        b.putInt("Ronda", FachadaDeRetos.ronda);
        b.putBoolean("haConsolidado", FachadaDeRetos.haConsolidado);
        b.putInt("TiempoOpcion", FachadaDeRetos.juegoRetoPregunta.getTiempoOpcion());
        b.putInt("Tiempo", FachadaDeRetos.juegoRetoPregunta.getTiempo());
        b.putInt("Nivel", FachadaDeRetos.juegoRetoPregunta.getNivel());
        b.putInt("SonidoFallo", FachadaDeRetos.juegoRetoPregunta.getSonidofallo());
        b.putInt("SonidoAcierto", FachadaDeRetos.juegoRetoPregunta.getSonidoacierto());
        b.putInt("Pistas", FachadaDeRetos.pistas);
        return b;
    }

    public void obtenerRetos() {
        Director creadorDeJuego = new Director();
        FachadaDeRetos.retoPregunta = new BuilderRetoPregunta();
        creadorDeJuego.setJuegoBuilder(FachadaDeRetos.retoPregunta);
        creadorDeJuego.construirJuego();
        FachadaDeRetos.juegoRetoPregunta = FachadaDeRetos.retoPregunta.getJuego();
    }
}

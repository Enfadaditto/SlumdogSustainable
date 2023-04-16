package Builder;

import Domain_Layer.Partida;

public class CreadorDePartida {

    private PartidaBuilder juegoBuilder;

    public void setJuegoBuilder(PartidaBuilder jb) {
        juegoBuilder = jb;
    }

    public void construirJuego() {

        juegoBuilder.buildNivel();
        juegoBuilder.buildPuntos();
        juegoBuilder.buildSonidoAcierto();
        juegoBuilder.buildSonidoFallo();
        juegoBuilder.buildTiempo();
        juegoBuilder.buildTiempoOpcion();
        juegoBuilder.buildRetosNivel1();
        juegoBuilder.buildRetosNivel2();
        juegoBuilder.buildRetosNivel3();
    }

}

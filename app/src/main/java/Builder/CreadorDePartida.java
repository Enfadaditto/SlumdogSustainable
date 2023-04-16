package Builder;

public class CreadorDePartida {

    private BuilderPartida juegoBuilder;

    public void setJuegoBuilder(BuilderPartida jb) {
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

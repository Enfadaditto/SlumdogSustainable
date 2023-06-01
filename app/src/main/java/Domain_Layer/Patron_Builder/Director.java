package Domain_Layer.Patron_Builder;

public class Director {

    private BuilderReto juegoBuilder;

    public void setJuegoBuilder(BuilderReto jb) {
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

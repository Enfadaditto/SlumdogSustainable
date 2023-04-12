package Builder;

public class CreadorDeJuego {

    private JuegoBuilder juegoBuilder;

    public void setJuegoBuilder(JuegoBuilder jb){

        juegoBuilder = jb;
    }

   public Juego getJuego(){
        return juegoBuilder.getJuego();
    }

    public void construirJuego(){

        juegoBuilder.buildDificultad();
        juegoBuilder.buildNivel();
        juegoBuilder.buildPuntos();
        juegoBuilder.buildSonidoAcierto();
        juegoBuilder.buildSonidoFallo();
        juegoBuilder.buildTiempo();
        juegoBuilder.buildTiempoOpcion();
    }

}

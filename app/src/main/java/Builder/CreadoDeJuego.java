package Builder;

public class CreadoDeJuego {

    private JuegoBuilder juegoBuilder;

    public void setJuegoBuilder(JuegoBuilder jb){

        juegoBuilder = jb;
    }

    public Juego getJuego(){
        return juegoBuilder.getJuego();
    }

    public void contruirJuego(){

        juegoBuilder.buildDificultad();
        juegoBuilder.buildNivel();
        juegoBuilder.buildPuntos();
        juegoBuilder.buildSonidoAcierto();
        juegoBuilder.buildSonidoFallo();
        juegoBuilder.buildTiempo();
    }

}

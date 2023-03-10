package Builder;

public abstract class GameBuilder {
    protected Juego juego;

    public Juego getJuego() {
        return juego;
    }

    public void crearNuevoJuego() {
        juego = new Juego();
    }


    public abstract void buildDificultad();
    public abstract void buildNivel();
    public abstract void buildTiempo();
    public abstract void buildSonidoAcierto();
    public abstract void buildSonidoFallo();
    public abstract void buildPuntos();
}

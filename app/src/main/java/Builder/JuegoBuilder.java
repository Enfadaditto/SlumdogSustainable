package Builder;

public abstract class JuegoBuilder {
  protected Juego juego;

  public void crearNuevoJuego() {
        juego = new Juego();
  }
  public Juego getJuego(){
      return juego;
  }

   public abstract void buildDificultad();
    public abstract void buildNivel();
    public abstract void buildTiempo();

    public abstract void buildTiempoOpcion();
    public abstract void buildSonidoAcierto();
    public abstract void buildSonidoFallo();
    public abstract void buildPuntos();


}

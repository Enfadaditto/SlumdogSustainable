package Builder;

public abstract class JuegoBuilder {
    protected Juego juego;
public interface  JuegoBuilder {
  //  protected Juego juego;

    public Juego getJuego() {
        return juego;
    }
  //  public void crearNuevoJuego() {
      //  juego = new Juego();
  //  }
  public Juego getJuego() ;

    public void crearNuevoJuego() {
        juego = new Juego();
    }


    public abstract void buildDificultad();
    public abstract void buildNivel();
    public abstract void buildTiempo();
    public abstract void buildSonidoAcierto();
    public abstract void buildSonidoFallo();
    public abstract void buildPuntos();
*/
    public  void buildDificultad();
    public  void buildNivel();
    public  void buildTiempo();
    public  void buildSonidoAcierto();
    public void buildSonidoFallo();
    public  void buildPuntos();
}

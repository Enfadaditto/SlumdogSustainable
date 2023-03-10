package Builder;

public class Juego {
    private String dificultad;
    private int nivel;
    private int tiempo;
    private String sonidoacierto;
    private String sonidofallo;
    private int puntos;


    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public int getNivel() { return nivel; }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setSonidoacierto(String sonidoacierto) {
        this.sonidoacierto = sonidoacierto;
    }

    public void setSonidofallo(String sonidofallo) {
        this.sonidofallo = sonidofallo;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}

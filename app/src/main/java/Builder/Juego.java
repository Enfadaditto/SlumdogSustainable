package Builder;

public class Juego {
    private String dificultad;
    private int nivel;
    private int tiempo;

    private int tiempoOpcion;
    private int sonidoacierto;
    private int sonidofallo;
    private int puntos;

    public String getDificultad() {
        return dificultad;
    }

    public int getTiempo() {
        return tiempo;
    }

    public int getSonidoacierto() {
        return sonidoacierto;
    }

    public int getSonidofallo() {
        return sonidofallo;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getNivel() {
        return nivel;
    }


    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public void setTiempoOpcion(int tiempo) {this.tiempoOpcion = tiempo;}

    public int getTiempoOpcion() {return tiempoOpcion;}

    public void setSonidoacierto(int sonidoacierto) {
        this.sonidoacierto = sonidoacierto;
    }

    public void setSonidofallo(int sonidofallo) {
        this.sonidofallo = sonidofallo;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}

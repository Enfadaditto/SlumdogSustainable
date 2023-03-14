package Builder;

import java.util.List;

import Domain_Layer.Question;

public class JuegoRetoPregunta extends JuegoBuilder {
public class JuegoRetoPregunta implements JuegoBuilder {
    private Juego juego;
    public JuegoRetoPregunta(){ juego = new Juego(); }




    public JuegoRetoPregunta(){ super.juego = new Juego(); }
    @Override
    public Juego getJuego(){
        return juego;
    }

    @Override
    public void buildDificultad() {
        juego.setDificultad("facil");
    }

    @Override
    public void buildNivel() {
        juego.setNivel(1);
    }

    @Override
    public void buildTiempo() {
        juego.setTiempo(10000); //10000ms = 10seg
    }

    @Override
    public void buildSonidoAcierto() {
        juego.setSonidoacierto("rutaSonidoAcierto");
    }

    @Override
    public void buildSonidoFallo() {
        juego.setSonidofallo("rutaSonidoFallo");
    }

    @Override
    public void buildPuntos() {
        juego.setPuntos(100 * juego.getNivel());
    }


    public void ejemplo(){}
    public int suma(){
        return 2;
    }

}

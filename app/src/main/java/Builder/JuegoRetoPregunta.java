package Builder;

import java.util.List;

import Domain_Layer.Question;

public class JuegoRetoPregunta extends JuegoBuilder {

    //public JuegoRetoPregunta(){ juego = new Juego(); }

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




}

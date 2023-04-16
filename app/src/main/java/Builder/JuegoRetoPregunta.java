package Builder;

import com.slumdogsustainable.R;

import java.util.List;

import Domain_Layer.Question;

public class JuegoRetoPregunta extends JuegoBuilder {

    public JuegoRetoPregunta(){ juego = new Juego(); }

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
        juego.setTiempo(30000); //10000ms = 10seg
    }

    @Override
    public void buildTiempoOpcion() {juego.setTiempoOpcion(15000);}

    @Override
    public void buildSonidoAcierto() {
        juego.setSonidoacierto(R.raw.correctanswer);
    }

    @Override
    public void buildSonidoFallo() {
        juego.setSonidofallo(R.raw.wronganswer);
    }

    @Override
    public void buildPuntos() {
        juego.setPuntos(100 * juego.getNivel());
    }




}

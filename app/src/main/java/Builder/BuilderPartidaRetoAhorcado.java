package Builder;

import Domain_Layer.Partida;
import Domain_Layer.PartidaRetoAhorcado;
import Domain_Layer.PartidaRetoPregunta;

public class BuilderPartidaRetoAhorcado extends BuilderPartida{

    protected PartidaRetoAhorcado juego;

    public PartidaRetoAhorcado getJuego() {
        return juego;
    }

    public BuilderPartidaRetoAhorcado() {
        juego = new PartidaRetoAhorcado();
    }

    public void buildNivel(){

    }

    public void buildRetosNivel1(){

    }

    public void buildRetosNivel2(){

    }

    public void buildRetosNivel3(){

    }
    public void buildTiempo(){

    }

    public void buildTiempoOpcion(){

    }

    public void buildSonidoAcierto(){

    }

    public void buildSonidoFallo(){

    }

    public void buildPuntos(){

    }


}

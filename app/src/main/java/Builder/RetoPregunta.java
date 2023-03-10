package Builder;

public class RetoPregunta extends GameBuilder {

    public RetoPregunta(){ super.juego = new Juego(); }

    @Override
    public void buildDificultad() {
        juego.setDificultad("facil");
    }

    @Override
    public void buildNivel() {

    }

    @Override
    public void buildTiempo() {

    }

    @Override
    public void buildSonidoAcierto() {

    }

    @Override
    public void buildSonidoFallo() {

    }

    @Override
    public void buildPuntos() {

    }
}

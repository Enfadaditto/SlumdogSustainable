package Domain_Layer.Patron_Builder;

import Domain_Layer.Reto;

public abstract class BuilderReto {

    public abstract Reto getJuego();

    public abstract void buildNivel();

    public abstract void buildRetosNivel1();

    public abstract void buildRetosNivel2();

    public abstract void buildRetosNivel3();
    public abstract void buildTiempo();

    public abstract void buildTiempoOpcion();

    public abstract void buildSonidoAcierto();

    public abstract void buildSonidoFallo();

    public abstract void buildPuntos();


}

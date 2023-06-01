package Domain_Layer.Patron_strategy;

import android.os.Bundle;

public class Context {
    private JuegoStrategy estrategia;

    public void setEstrategia(JuegoStrategy estrategia) {
        this.estrategia = estrategia;
    }

    public Bundle crearReto() {
        return estrategia.crearReto();
    }

    public void obtenerRetos() {
        estrategia.obtenerRetos();
    }
}

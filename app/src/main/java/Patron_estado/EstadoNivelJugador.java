package Patron_estado;

import com.slumdogsustainable.MainActivity;

import Domain_Layer.User;
import Persistence.UserRepository;
import Presentacion_layer.FachadaDeRetos;

abstract public class EstadoNivelJugador {

    protected FachadaDeRetos fachadaDeRetos;

    User usuarioActual;

    public EstadoNivelJugador(){

        usuarioActual = MainActivity.user;
    }

    public abstract int subirNivel1();
    abstract int subirNivel2();
    abstract int subirNivel3();
    public abstract int getNivelActual();



}

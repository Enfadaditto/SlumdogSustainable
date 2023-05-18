package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Logro;
import Domain_Layer.User;
import Domain_Layer.User_has_Logro;

public class User_LRepository extends Repository<User_has_Logro> {
    List<Logro> listaLogros;

    public User_LRepository(ConnectionSource c) {
        init(User_has_Logro.class, c);
        listaLogros = new LogroRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
    }


    public Logro getLogroPorID(int id) {
        for (Logro l : listaLogros) if (l.getId_logro() == id) return l;
        return new Logro(-1, "No se encontró el logro", "Culpa de Héctor");
    }
}

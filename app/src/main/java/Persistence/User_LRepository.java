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
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                listaLogros = new LogroRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
            }
        });
        hilo.start();
        try {
            hilo.join();
        } catch(Exception e) {}
    }

    public List<User_has_Logro> getLogrosUsuario(User u) {
        List<User_has_Logro> ret = new ArrayList<>();
        for (Logro l : listaLogros) {
            if (this.getEnlaceUsuarioLogro(u.getNickname(), l.getId_logro()) != null)
                ret.add(this.getEnlaceUsuarioLogro(u.getNickname(), l.getId_logro()));
            else
                ret.add(new User_has_Logro(u.getNickname(), l.getId_logro()));
        }

        return ret;
    }

    public Logro getLogroPorID(int id) {
        for (Logro l : listaLogros) if (l.getId_logro() == id) return l;
        return new Logro(-1, "No se encontró el logro", "Culpa de Héctor");
    }

    public User_has_Logro getEnlaceUsuarioLogro(String username, int id_logro) {
        for (User_has_Logro x : this.obtenerTodos())
            if (x.getId_user().equals(username) && x.getId_logro() == id_logro) return x;
        return null;
    }
}

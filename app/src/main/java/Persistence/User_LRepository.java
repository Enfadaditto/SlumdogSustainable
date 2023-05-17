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

    public List<User_has_Logro> getLogrosUsuario(User u) {
        List<User_has_Logro> ret = new ArrayList<>();
        System.out.println(listaLogros);
        for (Logro l : listaLogros) {
            if(this.obtener(l.getId_logro()) == null)
                //Si no existe el enlace logro - usuario
                ret.add(new User_has_Logro(u.getNickname(), l.getId_logro()));
            else
                //Si existiese ese enlace
                ret.add(this.obtener(l.getId_logro()));
        }

        return ret;
    }
}

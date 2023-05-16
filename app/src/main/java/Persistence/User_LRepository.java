package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Logro;
import Domain_Layer.User;
import Domain_Layer.User_has_Logro;

public class User_LRepository extends Repository{

    public User_LRepository(ConnectionSource c) { init(User_has_Logro.class, c); }

    public List<User_has_Logro> getLogrosUsuario(User u) {
        List<User_has_Logro> ret = new ArrayList();
        List<Logro> logros = new LogroRepository(SingletonConnection.getSingletonInstance()).getAllLogros();

        for (Logro l : logros) {
            ret.add(new User_has_Logro(u.getNickname(), l.getId_logro()));
        }

        return ret;
    }
}

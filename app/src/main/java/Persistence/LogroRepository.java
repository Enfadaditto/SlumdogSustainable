package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import java.util.ArrayList;
import java.util.List;
import Domain_Layer.Logro;
import Domain_Layer.User;

public class LogroRepository extends Repository<Logro> {

    public LogroRepository(ConnectionSource c) { init(Logro.class, c); }

    public List<Logro> getLogroUsuario(User u, Logro.TipoLogro tipo) {
        List<Logro> ret = new ArrayList();
        List<Logro> logros = obtenerTodos();

        for (Logro logro: logros)
            if (logro.getId_usuario().equals(u.getNickname()))
                if (logro.getTipo().equals(tipo))
                    ret.add(logro);

        return ret;
    }
}

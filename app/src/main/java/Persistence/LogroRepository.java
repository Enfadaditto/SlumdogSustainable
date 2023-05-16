package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import java.util.ArrayList;
import java.util.List;
import Domain_Layer.Logro;
import Domain_Layer.User;

public class LogroRepository extends Repository<Logro> {

    public LogroRepository(ConnectionSource c) { init(Logro.class, c); }

    public List<Logro> getAllLogros() {
        return obtenerTodos();
    }
}

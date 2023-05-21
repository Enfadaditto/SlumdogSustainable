package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import Domain_Layer.Logro;

public class LogroRepository extends Repository<Logro> {

    public LogroRepository(ConnectionSource c) { init(Logro.class, c); }

}

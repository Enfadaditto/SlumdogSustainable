package Persistence;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.Answer;
import Domain_Layer.Partida;
import Domain_Layer.Question;

public class PartidaRepository extends Repository<Partida> {

    public PartidaRepository(ConnectionSource c){
        init(Partida.class, c);
    }
}
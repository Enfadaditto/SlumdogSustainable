package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Ahorcado;
import Domain_Layer.Answer;
import Domain_Layer.Question;

public class AhorcadoRepository extends Repository<Ahorcado>{

    public AhorcadoRepository() {
        init(Ahorcado.class);
    }

    public AhorcadoRepository(ConnectionSource c) {
        init(Ahorcado.class,c);
    }

    public List<String> getAhorcadoListByDifficulty(String difficulty) throws SQLException {
        List<Ahorcado> list = this.getDao().queryForAll();
        List<String> resultlist = new ArrayList<>();
        for(Ahorcado q : list) {
            if(q.getDifficulty().equals(difficulty)) {
                resultlist.add(q.getPalabra());
            }
        }
        return resultlist;
    }
}

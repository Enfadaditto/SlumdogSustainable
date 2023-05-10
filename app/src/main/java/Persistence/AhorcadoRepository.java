package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Ahorcado;
import Domain_Layer.Answer;
import Domain_Layer.Question;

public class AhorcadoRepository extends Repository<Ahorcado>{

    public AhorcadoRepository(ConnectionSource c) {
        init(Ahorcado.class,c);
    }
}

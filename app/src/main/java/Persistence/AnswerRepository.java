package Persistence;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.Answer;
import Domain_Layer.Question;

public class AnswerRepository extends Repository<Answer> {

    public AnswerRepository(ConnectionSource c){
        init(Answer.class, c);
    }
}


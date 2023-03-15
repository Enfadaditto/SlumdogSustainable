package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.Answer;
public class AnswerRepository extends Repository<Answer> {

    public AnswerRepository(){
        init(Answer.class);
    }
}


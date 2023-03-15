package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.Question;
public class QuestionRepository extends Repository<Question> {

    public QuestionRepository(){
        init(Question.class);
    }
}

package Persistence;
import com.j256.ormlite.support.ConnectionSource;

import Domain_Layer.Question;

public class QuestionRepository extends Repository<Question> {

    public QuestionRepository(ConnectionSource c){
        init(Question.class, c);
    }
}

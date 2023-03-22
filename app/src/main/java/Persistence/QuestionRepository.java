package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Question;
import Domain_Layer.User;

public class QuestionRepository extends Repository<Question> {

    public QuestionRepository(){
        init(Question.class);
    }

    public List<Question> getQuestionListByDifficulty(String difficulty) throws SQLException {
        List<Question> list = this.getDao().queryForAll();
        List<Question> resultlist = new ArrayList<>();
        for(Question q : list) {
            if(q.getDifficulty().equals(difficulty)) {
                resultlist.add(q);
            }
        }
        return resultlist;
    }
}

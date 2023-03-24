package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Question;
import Domain_Layer.Answer;

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

    public List<Answer> getAnswers(Question q) throws SQLException {
        AnswerRepository AnswerRep = new AnswerRepository();
        List<Answer> list = AnswerRep.obtenerTodos();
        System.out.println("Prueba" + list);
        List<Answer> resultlist = new ArrayList<>();
        for(Answer a : list) {
            if(a.getQuestionID() == q.getQuestionID()) {
                resultlist.add(a);
            }
        }
        return resultlist;
    }
}

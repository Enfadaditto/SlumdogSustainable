package Persistence;
import com.j256.ormlite.support.ConnectionSource;
import com.slumdogsustainable.MainActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.Question;
import Domain_Layer.Answer;

public class QuestionRepository extends Repository<Question> {
    List<Answer> listaRespuestas;

    public QuestionRepository(ConnectionSource c){
        init(Question.class, c);
        listaRespuestas = new AnswerRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
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

    public List<Answer> getRespuestasPregunta(Question q) {
        List<Answer> resultlist = new ArrayList<>();
        for (Answer a : listaRespuestas) {
            if (a.getQuestionID() == q.getQuestionID()) {
                resultlist.add(a);
            }
        }
        return resultlist;
    }
}

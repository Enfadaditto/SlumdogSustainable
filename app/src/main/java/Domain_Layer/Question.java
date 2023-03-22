package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable
public class Question {
    @DatabaseField
    private String statement;
    @DatabaseField
    private int timeToAnswer;
    @DatabaseField
    private int points;
    @DatabaseField
    private String difficulty;
    @DatabaseField
    private int id_ODS;
    @DatabaseField (id = true)
    private int QuestionID;
    private List<Answer> answers;

    Question preguntaActual;

   public Question(){
       this.statement = "pregunta por defecto";
       this.timeToAnswer = 10;
       this.points= 100;
       this.difficulty = "1";
       // ODS odsPorDefecto = new ODS("tema1");
       this.id_ODS = 1;
       Answer respuesta1 = new Answer("respuesta1", true);
       Answer respuesta2 = new Answer("respuesta2", false);
       Answer respuesta3 = new Answer("respuesta3", false);
       Answer respuesta4 = new Answer("respuesta4", false);
       List<Answer> listaRespuestas= new ArrayList<Answer>();

       listaRespuestas.add(0,respuesta1);
       listaRespuestas.add(1,respuesta2);
       listaRespuestas.add(2,respuesta3);
       listaRespuestas.add(3,respuesta4);

       this.answers = listaRespuestas;
   }

    public Question(String statement, int time, int points, String difficulty, int ods) {
        this.statement = statement;
        this.timeToAnswer = time;
        this.points = points;
        this.difficulty = difficulty;
        this.id_ODS = ods;
    }

    public void addAnswer(Answer answer) {
        if (answers.size() != 4) {
            answers.add(answer);
        }
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public int getTimeToAnswer() {
        return timeToAnswer;
    }

    public void setTimeToAnswer(int timeToAnswer) {
        this.timeToAnswer = timeToAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getOds() {
        return id_ODS;
    }

    public void setOds(int ods) {
        this.id_ODS = ods;
    }

    public Question getQuestion(){
        return preguntaActual;
    }

    public List<Answer> getAnswers (){
       return answers;
    }

    public List<Question> getQuestionListByDifficulty(String dificultadFacil) {
       return null;
    }

   /* public int numeroAleatorioDeLista(List<Question> lista){

       if(lista.isEmpty()){
           return 0;
       }

       return (int)(Math.random()*lista.size());
    }*/

}

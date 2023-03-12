package Domain_Layer;

import java.util.List;


public class Question {

    private String statement;
    private int timeToAnswer;
    private int points;
    private String difficulty;

    private ODS ods;
    private List<Answer> answers;

    Question preguntaActual = new Question();

   public Question(){
       this.statement = "pregunta por defecto";
       this.timeToAnswer = 10;
       this.points= 100;
       this.difficulty = "1";
       ODS odsPorDefecto = new ODS("tema1");
       this.ods = odsPorDefecto;
       Answer respuesta1 = new Answer("respuesta1", true);
       Answer respuesta2 = new Answer("respuesta2", false);
       Answer respuesta3 = new Answer("respuesta3", false);
       Answer respuesta4 = new Answer("respuesta4", false);
       List<Answer> listaRespuestas = null;
       listaRespuestas.add(respuesta1);
       listaRespuestas.add(respuesta2);
       listaRespuestas.add(respuesta3);
       listaRespuestas.add(respuesta4);

       this.answers = listaRespuestas;
   }

    public Question(String statement, int time, int points, String difficulty, ODS ods) {
        this.statement = statement;
        this.timeToAnswer = time;
        this.points = points;
        this.difficulty = difficulty;
        this.ods = ods;
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

    public ODS getOds() {
        return ods;
    }

    public void setOds(ODS ods) {
        this.ods = ods;
    }

    public Question getQuestion(){
        return preguntaActual;
    }

    public List<Question> getQuestionListByDifficulty(String difficulty){

        //implementar con la base de datos


        List<Question> list = null;


        return list;

    }

    public int numeroAleatorioDeLista(List<Question> lista){

       return (int)(Math.random()*lista.size());
    }

}

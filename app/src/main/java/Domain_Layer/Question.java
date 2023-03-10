package Domain_Layer;

import java.util.List;


public class Question {

    private String statement;
    private int timeToAnswer;
    private int points;
    private String difficulty;

    private ODS ods;
    private List<Answer> answers;

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
}

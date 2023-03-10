package Domain_Layer;

import java.util.List;


public class Question {

    private int ID;
    private String statement;
    private int timeToAnswer;
    private int points;
    private String difficulty;

    private ODS ods;
    private List<Answer> answers;

    public Question(String statement, int time, int points, String difficulty, ODS ods) {
        // ver como asignar el ID
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
    public int getID() {return this.ID;}
}

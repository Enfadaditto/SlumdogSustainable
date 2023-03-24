package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Answer {
    @DatabaseField
    private String text;
    @DatabaseField
    private boolean hit;
    @DatabaseField(id = true)
    private int questionID;


    Answer() {}
    public Answer(String text, boolean hit) {
        this.text = text;
        this.hit = hit;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }


}

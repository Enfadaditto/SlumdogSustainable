package Domain_Layer;

public class Answer {

    private String text;
    private boolean hit;
    private String questionID;

    private Question question;

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

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}

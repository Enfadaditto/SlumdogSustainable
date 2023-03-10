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

    public boolean getHit() {return this.hit;}
}

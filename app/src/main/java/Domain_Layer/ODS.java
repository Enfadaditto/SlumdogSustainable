package Domain_Layer;

public class ODS {

    private String theme;
    private int questionID;

    private Question question;

    public ODS(String theme, Question question) {
        this.question = question;
        this.theme = theme;
        this.questionID = question.getID();
    }

    public String getTheme() {return this.theme;}
}

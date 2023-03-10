package Domain_Layer;

import java.util.List;
import java.util.Random;

public class ODS {

    private String theme;

    private List<Question> questions;
    private ODS_has_User ODSUser;

    public ODS(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Question getOneQuestion() { return questions.get(new Random().nextInt(questions.size())); }

    public void addQuestions(Question questions) {
        this.questions.add(questions);
    }

    public ODS_has_User getODSUser() {
        return ODSUser;
    }

    public void setODSUser(ODS_has_User ODSUser) {
        this.ODSUser = ODSUser;
    }
}

package Domain_Layer;

public class ODS_has_User {

    private String ODSTheme;
    private String userNickname;
    private int rightGuesses;

    private User user;
    private ODS ods;

    public ODS_has_User(User user, ODS ods){
        this.user = user;
        this.ods = ods;

        this.ODSTheme = ods.getTheme();
        this.userNickname = user.getNickname();
        this.rightGuesses = 0;
    }

    //TODO: Add getters and setters
}

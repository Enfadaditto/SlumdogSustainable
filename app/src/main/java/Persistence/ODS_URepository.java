package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.ODS_has_User;

public class ODS_URepository extends Repository<ODS_has_User> {

    public ODS_URepository(){
        init(ODS_has_User.class);
    }
}

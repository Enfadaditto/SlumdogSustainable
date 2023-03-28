package Persistence;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.ODS_has_User;
import Domain_Layer.Question;

public class ODS_URepository extends Repository<ODS_has_User> {

    public ODS_URepository(){
        init(ODS_has_User.class);
    }

    public ODS_URepository(ConnectionSource c){init(ODS_has_User.class, c);
    }
}

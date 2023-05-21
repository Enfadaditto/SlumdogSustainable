package Persistence;
import com.j256.ormlite.support.ConnectionSource;

import Domain_Layer.ODS_has_User;

public class ODS_URepository extends Repository<ODS_has_User> {

    public ODS_URepository(ConnectionSource c){init(ODS_has_User.class, c);
    }

}

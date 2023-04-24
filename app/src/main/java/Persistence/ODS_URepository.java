package Persistence;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Domain_Layer.ODS_has_User;
import Domain_Layer.Question;
import Domain_Layer.User;

public class ODS_URepository extends Repository<ODS_has_User> {

    public ODS_URepository(){
        init(ODS_has_User.class);
    }

    public ODS_URepository(ConnectionSource c){init(ODS_has_User.class, c);
    }

    public List <ODS_has_User.tuplaStats> getPercentagebyUser(User u) {
        List <ODS_has_User> aux = new ArrayList();
        List <ODS_has_User> ods = obtenerTodos();
        List <ODS_has_User.tuplaStats> res = new ArrayList<>();
        for(ODS_has_User o : ods) {
            if(o.getUserNickname().equals(u.getNickname())) {
                aux.add(o);

            }
        }
        for(ODS_has_User o : aux) {
            res.add(new ODS_has_User.tuplaStats(o.getRightGuesssesPercent(), o.getid_Ods()));
        }

        return res;
    }
}

package Persistence;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<ODS_has_User> getAllhits(User u) {
        List <ODS_has_User> res = new ArrayList();
        List <ODS_has_User> ods = obtenerTodos();
        for(ODS_has_User o : ods) {
            if(o.getUserNickname().equals(u.getNickname())) {
                res.add(o);
            }
        }
        return res;
    }

    public void updateODS(Boolean hit, int idODS, User u) {
        List <ODS_has_User> ods = obtenerTodos();
        UpdateBuilder<ODS_has_User, Integer> updateBuilder = getDao().updateBuilder();
        for(ODS_has_User o : ods) {
            if(o.getUserNickname().equals(u.getNickname()) && idODS == o.getid_Ods()) {
                try {
                    updateBuilder.where().eq("username", u.getNickname()).and().eq("id_ODS", o.getid_Ods());
                    if(hit) {
                        o.oneRightGuess();
                        updateBuilder.updateColumnValue("hits", o.getRightGuesses());
                    }
                    else {
                        o.oneWrongGuess();
                        updateBuilder.updateColumnValue("fails", o.getWrongGuesses());
                    }
                    updateBuilder.update();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
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

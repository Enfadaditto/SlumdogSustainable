package Persistence;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.ODS;
import Domain_Layer.Question;

public class ODSRepository extends Repository<ODS> {

    public ODSRepository(){
        init(ODS.class);
    }
    public ODSRepository(ConnectionSource c){
        init(ODS.class, c);
    }
}

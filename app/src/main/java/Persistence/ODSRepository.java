package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.ODS;
public class ODSRepository extends Repository<ODS> {

    public ODSRepository(){
        init(ODS.class);
    }
}

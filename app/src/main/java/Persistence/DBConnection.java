package Persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class DBConnection {
    private Connection connection;

    public DBConnection ()
    {
        try

        {

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://raimondb.mysql.database.azure.com:3306/mydb?useSSL=true", "inazuma", "Raimon123");
        }

        catch(Exception e)

        {

            System.out.println(e);

        }

    }

    public Connection getConnection() {
        return connection;
    }
}

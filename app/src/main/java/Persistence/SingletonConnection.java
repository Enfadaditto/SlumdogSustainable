package Persistence;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

public class SingletonConnection {

    private static JdbcConnectionSource connectionSource;
    public SingletonConnection() {
        try {
            String url = "jdbc:mysql://raimondb.mysql.database.azure.com:3306/mydb?useSSL=true";
            String username = "inazuma";
            String password = "Raimon123";
            connectionSource = new JdbcConnectionSource(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static JdbcConnectionSource getSingletonInstance() {
        if(connectionSource == null) {
            new SingletonConnection();
        }
        return  connectionSource;
    }
}

package Persistence;

import com.j256.ormlite.jdbc.JdbcConnectionSource;

import java.sql.SQLException;

public class SingletonConnection {
    private static SingletonConnection uniqueConnection;
    private JdbcConnectionSource connectionSource;
    public SingletonConnection() {
        try {
            connectionSource = new JdbcConnectionSource(
                    "jdbc:mysql://raimondb.mysql.database.azure.com:3306/mydb?useSSL=true", "inazuma", "Raimon123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static JdbcConnectionSource getSingletonInstance() {
        if(uniqueConnection == null) {
            uniqueConnection = new SingletonConnection();
        }
        return  uniqueConnection.connectionSource;
    }
}

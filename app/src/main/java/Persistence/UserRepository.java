package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.User;
public class UserRepository {

    private Connection connection;

    public UserRepository(Connection c)
    {

        this.connection = c;

    }

    public User GetUserByNickName(String nickname)
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM mydb.usuario WHERE Nickname= '" + nickname + "'");

            String nick = resultSet.getString(1);
            String email = resultSet.getString(2);
            String password = resultSet.getString(3);
            return new User(nick, email, password);
        }

        catch (Exception e)
        {
            System.out.println(e);
        }
        return null;
    }

    public void AddUser(String nickname, String email, String password)
    {
        try
        {
            Statement statement = connection.createStatement();
            statement.executeQuery("INSERT INTO mydb.usuario (Nickname, Email, Password) VALUES ('" + nickname + "', '" + email + "', '" + password+ "');");
            connection.commit();
        }

        catch (Exception e)
        {
            System.out.println(e.toString());
        }

    }

    public void DeleteUser(String nickname)
    {
        try
        {
            Statement statement = connection.createStatement();
            statement.executeQuery("DELETE FROM mydb.usuario WHERE Nickname=" + nickname + "'");
            connection.commit();
        }

        catch (Exception e)
        {
            System.out.println(e.toString());
        }

    }
}

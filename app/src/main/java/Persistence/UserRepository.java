package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import Domain_Layer.Question;
import Domain_Layer.User;
public class UserRepository extends Repository<User> {

        public UserRepository(){
                init(User.class);
        }

        public UserRepository(ConnectionSource c){
                init(User.class, c);
        }

        public boolean checkPassword(String username, String password) {
                return this.getUserByUsername(username).getPassword().equals(password);
        }
        public User getUserByUsername(String username) {
                List<User> users = this.obtenerTodos();
                for (User user : users) {
                        if (user.getNickname().equals(username)) return user;
                }
                return null;
        }
}


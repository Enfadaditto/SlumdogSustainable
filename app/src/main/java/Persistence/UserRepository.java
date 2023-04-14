package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                if(this.getUserByUsername(username) != null) {
                        return this.getUserByUsername(username).getPassword().equals(password);
                }
                return false;
        }
        public User getUserByUsername(String username) {
                List<User> users = null;
                try {
                        users = this.getDao().queryForAll();
                } catch (SQLException e) { }

                for (User user : users) {
                        if (user.getNickname().equals(username)) return user;
                }
                return null;
        }

        public void addPointsToTotal(String username, int points) {
            User thisUser = getUserByUsername(username);
            thisUser.setPointsAchieved(thisUser.getPointsAchieved() + points);
            this.guardar(thisUser);
        }
}


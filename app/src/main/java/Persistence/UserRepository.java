package Persistence;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.slumdogsustainable.MainActivity;

import Domain_Layer.Question;
import Domain_Layer.User;
public class UserRepository extends Repository<User> {

        public UserRepository(){
                init(User.class);
        }

        public UserRepository(ConnectionSource c){
                init(User.class, c);
        }

        public void updateGamesandTime(Boolean b, int time) {
                if(b) {
                        MainActivity.user.setGamesAchieved(MainActivity.user.getGamesAchieved() + 1);
                }
                else {
                        MainActivity.user.setGamesAchieved(MainActivity.user.getGamesAchieved() - 1);
                }
                try {
                        MainActivity.user.setTimeSpent(MainActivity.user.getTimeSpent() + time);
                        this.getDao().update(MainActivity.user);
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        public boolean checkPassword(String username, String password) {
                if(this.getUserByUsername(username) != null) {
                        return this.getUserByUsername(username).getPassword().equals(password);
                }
                return false;
        }
        public User getUserByUsername(String username) {
                List<User> users = this.obtenerTodos();
                for (User user : users) {
                        if (user.getNickname().equals(username)) return user;
                }
                return null;
        }

        public boolean checkUsernameNotTaken(String username) {
                List<User> users = this.obtenerTodos();
                List<String> userNicks = new ArrayList<>();
                for (User u : users) userNicks.add(u.getNickname());
                return !userNicks.contains(username);
        }
        public void saveUser(User user) {
                this.guardar(user);
        }

}


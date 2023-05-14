package Persistence;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.slumdogsustainable.MainActivity;

import Domain_Layer.Partida;
import Domain_Layer.Question;
import Domain_Layer.User;
public class UserRepository extends Repository<User> {

        public UserRepository(ConnectionSource c){
                init(User.class, c);
        }

        public void updateGamesandTime(Boolean b, int time) {
                try {
                        if(b) {
                                MainActivity.user.setGamesAchieved(MainActivity.user.getGamesAchieved() + 1);
                        }
                        else {
                                MainActivity.user.setGamesFailed(MainActivity.user.getGamesFailed() + 1);
                        }
                        MainActivity.user.setTimeSpent(MainActivity.user.getTimeSpent() + ((float) time) / 60000);
                        this.getDao().update(MainActivity.user);
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }

        public void updatePartidasandTime(Boolean hit, Boolean abadonada, int time, int Puntos) {
                try {
                        PartidaRepository p = new PartidaRepository(SingletonConnection.getSingletonInstance());
                        if(hit) {
                                MainActivity.user.setGamesAchieved(MainActivity.user.getGamesAchieved() + 1);
                                p.guardar(new Partida((int) p.getDao().countOf(), MainActivity.user.getNickname(), Puntos, new Date()));
                        }
                        else if(!abadonada){
                                MainActivity.user.setGamesFailed(MainActivity.user.getGamesFailed() + 1);
                        }
                        else {
                                MainActivity.user.setGamesAbandoned(MainActivity.user.getGamesAbandoned() + 1);
                                p.guardar(new Partida((int) p.getDao().countOf(), MainActivity.user.getNickname(), Puntos, new Date()));
                        }
                        MainActivity.user.setTimeSpent(MainActivity.user.getTimeSpent() + ((float) time) / 60000);
                        this.getDao().update(MainActivity.user);
                } catch (SQLException e) {
                        throw new RuntimeException(e);
                }
        }
        public void updateGamesAbandonedandTime(int time) {
                try {
                        MainActivity.user.setGamesAbandoned(MainActivity.user.getGamesAbandoned() + 1);
                        MainActivity.user.setTimeSpent(MainActivity.user.getTimeSpent() + ((float) time) / 60000);
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
}


package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import Domain_Layer.User;
public class UserRepository extends Repository<User> {

        public UserRepository(){
                init(User.class);
        }

        public User getUserByUsername(String username) {
                List<User> users = this.obtenerTodos();
                for (User user : users) {
                        if (user.getNickname().equals(username)) return user;
                }
                return null;
        }
}


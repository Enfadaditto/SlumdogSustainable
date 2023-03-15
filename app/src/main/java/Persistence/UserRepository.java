package Persistence;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import Domain_Layer.User;
public class UserRepository extends Repository<User> {

        public UserRepository(){
                init(User.class);
        }
}


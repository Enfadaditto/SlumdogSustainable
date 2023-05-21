package Persistence;

import com.j256.ormlite.support.ConnectionSource;

import Domain_Layer.User;
public class UserRepository extends Repository<User> {

        public UserRepository(ConnectionSource c){
                init(User.class, c);
        }
}


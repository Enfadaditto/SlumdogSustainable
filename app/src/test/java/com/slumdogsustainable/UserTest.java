package com.slumdogsustainable;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Random;

import Domain_Layer.User;
import Persistence.SingletonConnection;
import Persistence.UserRepository;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest {
    User testUser;
    @Test
    public void testCrearUsuario() {
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    UserRepository uRepo = new UserRepository(SingletonConnection.getSingletonInstance());
                    String nicknameTest = "prueba" + new Random().nextInt(1000);
                    uRepo.guardar(new User(nicknameTest, "email@email.com", "psswd123.", 0));
                    testUser = User.getUserByUsername(uRepo, nicknameTest);
                }
            });
            t.start();
            t.join();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        assertNotNull(testUser);
    }

    @Test
    public void testUsernamenotTaken() {
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    UserRepository uRepo = new UserRepository(SingletonConnection.getSingletonInstance());
                    String nicknameTest = "prueba" + new Random().nextInt(1000);
                    testUser = new User(nicknameTest, "email@email.com", "psswd123.", 0);
                    uRepo.guardar(testUser);
                    assertTrue(!User.checkUsernameNotTaken(uRepo, nicknameTest));
                }
            });
            t.start();
            t.join();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testUserLevel() {
        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String nicknameTest = "prueba" + new Random().nextInt(1000);
                    testUser = new User(nicknameTest, "email@email.com", "psswd123.", 0);
                    testUser.setPointsAchieved(15000);
                }
            });
            t.start();
            t.join();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        assertEquals(2, testUser.getNivelUsuario());
    }
}
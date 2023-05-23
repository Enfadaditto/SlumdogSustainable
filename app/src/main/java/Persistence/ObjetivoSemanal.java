package Persistence;

import com.slumdogsustainable.MainActivity;

public class ObjetivoSemanal {


    public static void sumarMetaSemanal() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UserRepository u = new UserRepository(SingletonConnection.getSingletonInstance());
                MainActivity.user.setMetasSemanales(MainActivity.user.getMetasSemanales()+1);
                u.actualizar(MainActivity.user);
            }
        }).start();

    }
}

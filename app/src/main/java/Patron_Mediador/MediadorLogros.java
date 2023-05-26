package Patron_Mediador;

import android.widget.Toast;

import com.slumdogsustainable.MainActivity;

import java.util.List;

import Domain_Layer.Logro;
import Domain_Layer.User;
import Domain_Layer.User_has_Logro;
import Persistence.LogroRepository;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
import Persistence.User_LRepository;

public class MediadorLogros implements IMediadorLogros {    //MEDIA ENTRE ENLACES USER_lOGRO, USUARIO Y LOGROS

    private User_LRepository conexionEnlacesBD;
    private LogroRepository conexionLogroBD;

    @Override
    public void notificarLogroDesbloqueado(User usuario, Logro logro) {
        conexionEnlacesBD = new User_LRepository(SingletonConnection.getSingletonInstance());
        List<User_has_Logro> listaEnlaces = conexionEnlacesBD.obtenerTodos();

        if ((new User_has_Logro().getEnlaceUsuarioLogro(usuario.getNickname(), logro.getId_logro(), listaEnlaces)).isCompletado()) return;
        MainActivity.logrosCompletados.offer(logro);
        System.out.println("Logro desbloqueado");
        User_has_Logro uhl = new User_has_Logro().getEnlaceUsuarioLogro(usuario.getNickname(), logro.getId_logro(), listaEnlaces);
        uhl.setCompletado(true);
        conexionEnlacesBD.actualizar(uhl);
    }

    public void addEnlaces(User usuario) {
        conexionEnlacesBD = new User_LRepository(SingletonConnection.getSingletonInstance());
        conexionLogroBD = new LogroRepository(SingletonConnection.getSingletonInstance());
        User_has_Logro x = new User_has_Logro("",-1);
        for (Logro l : conexionLogroBD.obtenerTodos()) {
            if (x.getEnlaceUsuarioLogro(usuario.getNickname(), l.getId_logro(), conexionEnlacesBD.obtenerTodos()) == null) {
                User_has_Logro nuevoEnlace = new User_has_Logro(usuario.getNickname(), l.getId_logro());
                nuevoEnlace.setCompletado(false); nuevoEnlace.setProgreso(0);
                conexionEnlacesBD.guardar(nuevoEnlace);
            }
        }

        usuario.setLogrosAñadidos(true);
        new UserRepository(SingletonConnection.getSingletonInstance()).actualizar(usuario);
    }
}

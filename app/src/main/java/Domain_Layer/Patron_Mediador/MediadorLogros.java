package Domain_Layer.Patron_Mediador;

import com.slumdogsustainable.MainActivity;

import java.util.List;

import Domain_Layer.Logro;
import Domain_Layer.User;
import Domain_Layer.User_has_Logro;
import Persistence.SingletonConnection;
import Persistence.UserRepository;
import Persistence.User_LRepository;

public class MediadorLogros implements IMediadorLogros {    //MEDIA ENTRE ENLACES USER_lOGRO, USUARIO Y LOGROS

    private User usuario;
    private List<Logro> logros;

    private User_LRepository conexionEnlacesBD;
    private UserRepository conexionUsuarioBD;


    public void registrarUsuario(User u) {
        System.out.println("REGISTRADO USUARIO");
        usuario = u;
        usuario.setMediador(this);
    }
    public void registrarLogros(List<Logro> logro) {
        System.out.println("REGISTRADA LISTA DE LOGROS");
        logros = logro;
        for (Logro l : logros) {
            l.setMediador(this);
        }
    }
    @Override
    public void notificarLogroDesbloqueado(Logro logro) {
        conexionEnlacesBD = new User_LRepository(SingletonConnection.getSingletonInstance());
        List<User_has_Logro> listaEnlaces = conexionEnlacesBD.obtenerTodos();

        if ((new User_has_Logro().getEnlaceUsuarioLogro(usuario.getNickname(), logro.getId_logro(), listaEnlaces)).isCompletado()) return;
        MainActivity.logrosCompletados.offer(logro);
        System.out.println("Logro desbloqueado");
        User_has_Logro uhl = new User_has_Logro().getEnlaceUsuarioLogro(usuario.getNickname(), logro.getId_logro(), listaEnlaces);
        uhl.setCompletado(true);
        conexionEnlacesBD.actualizar(uhl);
    }

    @Override
    public void addEnlacesToUser() {
        conexionEnlacesBD = new User_LRepository(SingletonConnection.getSingletonInstance());
        conexionUsuarioBD = new UserRepository(SingletonConnection.getSingletonInstance());
        User_has_Logro x = new User_has_Logro("",-1);
        for (Logro l : logros) {
            if (x.getEnlaceUsuarioLogro(usuario.getNickname(), l.getId_logro(), conexionEnlacesBD.obtenerTodos()) == null) {
                User_has_Logro nuevoEnlace = new User_has_Logro(usuario.getNickname(), l.getId_logro());
                nuevoEnlace.setCompletado(false); nuevoEnlace.setProgreso(0);
                conexionEnlacesBD.guardar(nuevoEnlace);
            }
        }

        usuario.setLogrosAñadidos(true);
        conexionUsuarioBD.actualizar(usuario);
    }
}

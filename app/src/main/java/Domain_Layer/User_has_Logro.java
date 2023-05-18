package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;

import java.util.List;

import Persistence.LogroRepository;
import Persistence.SingletonConnection;
import Persistence.User_LRepository;

public class User_has_Logro implements Observador{

    @DatabaseField(id = true)
    private int id_userLogro;

    @DatabaseField
    private String id_user;
    @DatabaseField
    private int id_logro;
    @DatabaseField
    private int progreso;
    @DatabaseField
    private boolean completado;

    User_has_Logro() {}

    public User_has_Logro(String id_user, int id_logro) {
        this.id_user = id_user; this.id_logro = id_logro;
        this.progreso = 0; this.completado = false;
    }

    public void completarLogro(User_has_Logro logro){
        if (logro.progreso == 100) logro.completado = true;
    }

    @Override
    public void actualizar(int id_logro) {
        if (this.id_logro != id_logro) return;
        System.out.println(id_user + " ha desbloqueado el logro " + id_logro);

        //Mostrar logro por pantalla
        // ESQUEMA DE COMO MOSTRARLO

        //  |----------|---------------------------|
        //  |          |   NOMBRE DEL LOGRO        |
        //  | ICONO    |---------------------------|
        //  |          |   DESCRIPCIÓN DEL LOGRO   |
        //  |          |   DESCRIPCIÓN DEL LOGRO   |
        //  |----------|---------------------------|

        // Poner logro a completado en user_has_logro
        // Para mostrar logros completados en el armario comprobar todos aquellos que fueron completados
    }

    public List<User_has_Logro> getAllLogros(User u) {
        User_LRepository logros = new User_LRepository(SingletonConnection.getSingletonInstance());
        List<User_has_Logro> aux = logros.getLogrosUsuario(u);
        return aux;
    }

    public User_has_Logro getLogroPorID(int id) {
        List<User_has_Logro> UHL = new User_LRepository(SingletonConnection.getSingletonInstance()).obtenerTodos();
        for (User_has_Logro x : UHL) if (x.id_logro == id) return x;
        return null;
    }

    public String getId_user() {
        return id_user;
    }

    public int getId_logro() {
        return id_logro;
    }

    public int getProgreso() {
        return progreso;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }
}

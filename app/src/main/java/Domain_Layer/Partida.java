package Domain_Layer;

import android.provider.Telephony;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class Partida {
    @DatabaseField(id = true)
    int idPartida;

    @DatabaseField
    Date Fecha;

    @DatabaseField
    int Puntos;

    @DatabaseField
    String Usuario;


    public Partida() {}

    public Partida(int id, String usuario, int Puntos, Date Fecha) {
        this.idPartida = id;
        this.Usuario = usuario;
        this.Puntos = Puntos;
        this.Fecha = Fecha;
    }


    public Date getFecha() {
        return Fecha;
    }

    public int getPuntos() {
        return Puntos;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }


    public void setPuntos(int puntos) {
        Puntos = puntos;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}

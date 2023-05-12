package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class Partida {
    @DatabaseField
    int idPartida;

    @DatabaseField
    Date Fecha;

    @DatabaseField
    int Puntos;

    @DatabaseField
    String Usuario;

    @DatabaseField
    Boolean ganada;

    @DatabaseField
    Boolean abandonada;

    Partida() {}

    public Boolean getAbandonada() {
        return abandonada;
    }

    public Boolean getGanada() {
        return ganada;
    }

    public int getIdPartida() {
        return idPartida;
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

    public void setAbandonada(Boolean abandonada) {
        this.abandonada = abandonada;
    }

    public void setFecha(Date fecha) {
        Fecha = fecha;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public void setGanada(Boolean ganada) {
        this.ganada = ganada;
    }

    public void setPuntos(int puntos) {
        Puntos = puntos;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }
}

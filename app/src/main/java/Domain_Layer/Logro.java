package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;

import Persistence.LogroRepository;
import Persistence.SingletonConnection;

public class Logro {
    @DatabaseField(id = true)
    private int id_logro;
    @DatabaseField
    private TipoLogro tipo;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String descripcion;

    public enum TipoLogro { Medalla, Trofeo }

    Logro() {}

    public Logro(int id_logro, String nombre, String descripcion) {
        this.id_logro = id_logro; this.nombre = nombre; this.descripcion = descripcion;
    }

    public Logro getLogroPorID(int id_logro) {
        LogroRepository lr = new LogroRepository(SingletonConnection.getSingletonInstance());
        return lr.obtener(id_logro);
    }

    public int getId_logro() {
        return id_logro;
    }

    public TipoLogro getTipo() {
        return tipo;
    }

    public void setTipo(TipoLogro tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

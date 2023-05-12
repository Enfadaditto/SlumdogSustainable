package Domain_Layer;

import com.j256.ormlite.field.DatabaseField;

public class Logro {
    @DatabaseField(id = true)
    private int id_logro;
    @DatabaseField
    private TipoLogro tipo;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String descripcion;
    @DatabaseField
    private String id_usuario;
    @DatabaseField
    private int progreso;

    public enum TipoLogro { Medalla, Trofeo }

    Logro() {}

    public Logro(int id_logro, String id_usuario) {
        this.id_logro = id_logro; this.id_usuario = id_usuario;
    }

    public Logro(int id_logro, String id_usuario, String nombre, String descripcion) {
        this(id_logro,id_usuario);
        this.nombre = nombre; this.descripcion = descripcion;
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

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public int getProgreso() { return progreso; }

    public void setProgreso(int progreso) { this.progreso = progreso; }
}

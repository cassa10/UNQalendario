package gradle.cucumber;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    @MongoId
    @MongoObjectId
    private String id;
    private String nombre;
    private String password;
    private List<Tarea> notificaciones;

    public Usuario() {
        this.setUp();
    }

    public Usuario(String nombre, String password){
        this.nombre = nombre;
        this.password = password;
        this.setUp();
    }

    private void setUp() {
        this.notificaciones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public void agregarNotificacion(Tarea tarea) {
        this.notificaciones.add(tarea);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Usuario that = (Usuario) obj;
        if (! this.getId().equals(that.getId())) return false;
        return true;
    }

}

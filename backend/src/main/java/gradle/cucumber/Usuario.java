package gradle.cucumber;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    @MongoId
    @MongoObjectId
    private String id;
    private String nombreUsuario;
    private String password;
    private String nombrePersona;
    private String apellido;
    private List<Tarea> notificaciones;

    public Usuario() {
        this.setUp();
    }

    public Usuario(String nombreUsuario, String password, String nombrePersona, String apellido){
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombrePersona = nombrePersona;
        this.apellido = apellido;
        this.setUp();
    }

    public Usuario(String nombreUsuario, String password){
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombrePersona = "";
        this.apellido = "";
        this.setUp();
    }

    private void setUp() {
        this.notificaciones = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
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

    public String getNombrePersona(){
        return this.nombrePersona;
    }

    public String getApellido(){
        return this.apellido;
    }

    public String getPassword() {return this.password;}

    public String getNombreYApellido(){
        if(this.getNombrePersona().isEmpty() || this.getApellido().isEmpty()){
            return this.getNombrePersona() + this.getApellido();
        }
        return this.getNombrePersona() +", "+this.getApellido();
    }

    public List<Tarea> getNotificaciones(){
        return this.notificaciones;
    }
}

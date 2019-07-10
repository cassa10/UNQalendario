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
    private int nuevasNotificaciones;
    private List<Notificacion> notificaciones;

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
        this.nuevasNotificaciones = 0;
    }

    public String getId() {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void agregarNotificacion(Notificacion notificacion) {
        this.notificaciones.add(0, notificacion);
        this.nuevasNotificaciones++;
    }

    public void seVieronTodasLasNotifiaciones() {
        this.nuevasNotificaciones = 0;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        Usuario that = (Usuario) obj;
        if (! this.getNombreUsuario().equals(that.getNombreUsuario())) return false;
        if( ! this.getId().equals(that.getId())) return false;
        return true;
    }

    public String getNombrePersona(){
        return this.nombrePersona;
    }

    public void setNuevasNotificaciones(int nuevasNotificaciones) {
        this.nuevasNotificaciones = nuevasNotificaciones;
    }

    public int getNuevasNotificaciones() {
        return nuevasNotificaciones;
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

    public List<Notificacion> getNotificaciones(){
        return this.notificaciones;
    }

    public void eliminarNotificacion(String idTarea) {
        this.notificaciones.removeIf(notificacion -> notificacion.getTarea().getId().contains(idTarea));
    }

    public void setId(String newId){
        this.id = newId;
    }
}

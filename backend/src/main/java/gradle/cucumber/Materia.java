package gradle.cucumber;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Materia {

    @MongoId
    @MongoObjectId
    private String id;
    private String nombre;
    private List<Usuario> suscriptores;
    private List<Tarea> tareas;
    private List<Usuario> administradores;

    public Materia(){
        this.setUp();
    }

    public Materia(String nombre){
        this.nombre = nombre;
        this.setUp();
    }

    private void setUp() {
        this.tareas = new ArrayList<>();
        this.suscriptores = new ArrayList<>();
        this.administradores =new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Usuario> getSuscriptores() {
        return suscriptores;
    }

    public void agregarSuscriptor(Usuario usuario) {
        if(this.suscriptores.stream().noneMatch(u -> usuario.getId().equals(u.getId()))) {this.suscriptores.add(usuario);}
    }

    public boolean esAdministrador(Usuario usuario){
        return this.administradores.contains(usuario);
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
        this.suscriptores.forEach(usuario -> usuario.agregarNotificacion(tarea));
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void agregarAdministrador(Usuario nuevoAdmin) {
        if(! this.getAdministradores().contains(nuevoAdmin)){
            this.administradores.add(nuevoAdmin);
        }
    }

    public List<Usuario> getAdministradores() {
        return this.administradores;
    }
    public String getNombreDelDocente(){
        if(this.getAdministradores().isEmpty()){
            return "";
        }
        return this.getAdministradores().get(0).getNombreYApellido();
    }

    public List<Tarea> mostrarTareas(Usuario usuario) {
        if(suscriptores.contains(usuario)){
            return tareas;
        }
        return new ArrayList<>();
    }
}

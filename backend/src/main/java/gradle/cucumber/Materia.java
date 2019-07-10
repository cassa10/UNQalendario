package gradle.cucumber;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Tarea> getTareas() {
        return tareas;
    }

    public List<Usuario> getAdministradores() {
        return this.administradores;
    }

    public void agregarSuscriptor(Usuario usuario) {
        //TODO
        // Query en DB
        if(!this.suscriptores.contains(usuario)){
            this.suscriptores.add(usuario);
        }
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    public void agregarAdministrador(Usuario nuevoAdmin) {
        if(! this.administradores.contains(nuevoAdmin)){
            this.administradores.add(nuevoAdmin);
        }
    }

    public String getNombreDelDocente(){
        if(this.getAdministradores().isEmpty()){
            return "";
        }
        return this.getAdministradores().get(0).getNombreYApellido();
    }

    @Override
    public boolean equals(Object obj){
        Materia that = (Materia) obj;
        if (! this.getId().equals(that.getId())) return false;
        return true;
    }
    public void eliminarTarea(Tarea tarea)  {
        this.tareas.removeIf(tarea1 -> tarea1.getId().equals(tarea.getId()));
    }


    public void eliminarSuscriptor(Usuario usuarioRecuperado) {
        this.getSuscriptores().remove(usuarioRecuperado);
    }
}

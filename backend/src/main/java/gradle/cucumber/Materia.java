package gradle.cucumber;

import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Materia {
    @MongoId
    @MongoObjectId
    private String id;
    private String nombre;
    private List<Usuario> suscriptores;
    private List<Tarea> tareas;

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
        if(this.suscriptores.stream().allMatch(u -> !usuario.getId().equals(u.getId()))) {this.suscriptores.add(usuario);}
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
        this.suscriptores.forEach(usuario -> usuario.agregarNotificacion(tarea));
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}

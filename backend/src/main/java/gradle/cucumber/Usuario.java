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
    private List<Materia> materiasSuscritas;

    public Usuario() {
        this.notificaciones = new ArrayList<>();
        this.materiasSuscritas = new ArrayList<>();
    }

    public Usuario(String nombre, String password){
        this.nombre = nombre;
        this.password = password;
        this.notificaciones = new ArrayList<>();
        this.materiasSuscritas = new ArrayList<>();
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

    public void agregarMateriaSubscrita(Materia materia) {
        if(this.materiasSuscritas.stream().allMatch(m -> !materia.getId().equals(m.getId()))) {this.materiasSuscritas.add(materia);}
    }

    public List<Materia> getMateriasSuscritas() {
        return materiasSuscritas;
    }
}

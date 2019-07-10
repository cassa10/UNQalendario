package gradle.cucumber;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.Date;

public class Tarea {
    private String id;
    private String nombreTarea;
    private Date fechaTarea;

    public Tarea(){

    }

    public Tarea(String nombre, LocalDate fecha) {
        this.nombreTarea=nombre;
        this.fechaTarea = Date.from(fecha.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String getNombre() {
        return this.nombreTarea;
    }

    public LocalDate getFecha() {

        return this.fechaTarea.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Tarea that = (Tarea) obj;
        if (! (this.getNombre().equals(that.getNombre()) && this.getFecha().equals(that.getFecha()))) return false;
        return true;
    }

}

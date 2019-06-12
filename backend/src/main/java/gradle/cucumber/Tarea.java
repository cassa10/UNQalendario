package gradle.cucumber;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.Date;

public class Tarea {
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

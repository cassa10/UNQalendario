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

}

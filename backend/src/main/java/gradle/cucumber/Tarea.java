package gradle.cucumber;

import java.time.LocalDate;
import java.util.Date;

public class Tarea {
    private String nombreTarea;
    private LocalDate fechaTarea;

    public Tarea(String nombre, LocalDate fecha) {
        this.nombreTarea=nombre;
        this.fechaTarea=fecha;
    }

    public String getNombre() {
        return this.nombreTarea;
    }

    public LocalDate getFecha() {
        return this.fechaTarea;
    }

}

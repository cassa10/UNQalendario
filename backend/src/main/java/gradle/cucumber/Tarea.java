package gradle.cucumber;

import java.time.LocalDate;

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

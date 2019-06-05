package gradle.cucumber;

import java.util.Date;

public class Tarea {
    private String nombreTarea;
    private Date fechaTarea;

    public Tarea(String nombre, Date fecha) {
        this.nombreTarea=nombre;
        this.fechaTarea=fecha;
    }

    public String getNombre() {
        return this.nombreTarea;
    }

    public Date getFecha() {
        return this.fechaTarea;
    }

}

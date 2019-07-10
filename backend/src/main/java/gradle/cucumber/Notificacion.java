package gradle.cucumber;

public class Notificacion {
    private String idMateria;
    private Tarea tarea;
    private String titulo;

    public Notificacion(String idMateria, Tarea tarea, String titulo) {
        this.idMateria = idMateria;
        this.tarea = tarea;
        this.titulo = titulo;
    }

    public Notificacion(){}

    public String getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(String idMateria) {
        this.idMateria = idMateria;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}

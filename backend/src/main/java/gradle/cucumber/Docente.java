package gradle.cucumber;

import java.util.ArrayList;
import java.util.List;

public class Docente {
    private String nombre;
    private List<Materia> materiasAdministradas;

    public Docente(){}
    public Docente(String nombre) {
        this.nombre = nombre;
        this.materiasAdministradas = new ArrayList<Materia>();
    }


    public void administrarMateria(Materia materia) {
        this.materiasAdministradas.add(materia);
    }

    public void agregarTarea(Materia materia, Tarea tarea) {
        if (this.tieneLaMateriaEnSuLista(materia)){
            materia.agregarTarea(tarea);
        }
    }

    private boolean tieneLaMateriaEnSuLista(Materia materia) {
    return this.materiasAdministradas.contains(materia);
    }
}

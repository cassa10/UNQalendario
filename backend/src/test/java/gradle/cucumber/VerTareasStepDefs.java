package gradle.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import java.time.LocalDate;

public class VerTareasStepDefs {

    Docente docente;
    Materia materia;
    Tarea tarea1, tarea2;
    UsuarioController usuarioController;

    @Given("^Una materia con tareas")
    public void inicializarMateriaConTareas(){
        docente = new Docente("Fede");
        materia = new Materia();
        tarea1 = new Tarea("Heap",LocalDate.of(2019,6,30));
        tarea2 = new Tarea("ArbolBinario",LocalDate.of(2019,8, 15));
        materia.agregarAdministrador(docente);
        docente.agregarTarea(materia,tarea1);
        docente.agregarTarea(materia,tarea2);
    }

    @And("^Un usuario suscrito a esa materia")
    public void suscribirAMateria(){

    }
}

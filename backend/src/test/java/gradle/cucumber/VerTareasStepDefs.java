package gradle.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class VerTareasStepDefs {

    private Docente docente;
    private Materia materia;
    private Tarea tarea1, tarea2;
    private Usuario usuario;
    private List<Tarea> tareasList;

    @Given("^Una materia con tareas$")
    public void inicializarMateriaConTareas(){
        docente = new Docente("Fede");
        materia = new Materia("Estructuras","Fede");
        tarea1 = new Tarea("Heap",LocalDate.of(2019,6,30));
        tarea2 = new Tarea("ArbolBinario",LocalDate.of(2019,8, 15));
        materia.agregarAdministrador(docente);
        docente.agregarTarea(materia,tarea1);
        docente.agregarTarea(materia,tarea2);
    }

    @And("^Un usuario suscrito a esa materia$")
    public void suscribirAMateria(){
        usuario = new Usuario("user","pass");
        materia.agregarSuscriptor(usuario);
    }

    @When("^El usuario quiere ver las tareas$")
    public void verTareasEnMateria(){
        System.out.println("materia :" + materia.getNombre() + " usuario: " + usuario.getNombre() );
        tareasList = usuario.verTareas(materia);
    }

    @Then("^Le aparece una lista de tareas para esa materia$")
    public void assertTareasEstanEnLaMateria(){
        assertEquals(2,tareasList.size());
        assertEquals(tarea1.getNombre(),tareasList.get(0).getNombre());
        assertEquals(tarea2.getNombre(),tareasList.get(1).getNombre());
    }

    @Given("^Una materia con un profesor$")
    public void unaMateriaConUnProfesor() {
        docente = new Docente("Fede");
        materia = new Materia ("Estructuras","Fede");
        materia.agregarAdministrador(docente);
        tarea1 = new Tarea("Heap",LocalDate.of(2019,7,30));
    }

    @And("^Agrego una tarea a la materia$")
    public void agregoUnaTareaALaMateria() {
        docente.agregarTarea(materia,tarea1);
    }

    @Then("^La materia tiene una tarea$")
    public void laMateriaTieneUnaTarea() {
        assertEquals(1,tareasList.size());
    }

    @And("^Usuario no suscrito a esa materia$")
    public void usuarioNoSuscritoAEsaMateria() {
        usuario = new Usuario("Juan", "1234");
    }

    @Then("^La lista no tiene tareas$")
    public void laListaNoTieneTareas() {
        assertEquals(0,tareasList.size());
    }
}

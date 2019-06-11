package gradle.cucumber;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.time.LocalDate;

public class UNQalendarioDocenteAgregaUnaTareaEnUnaMateriaQueAdministraStepdefs {

    private Materia materia;
    private Usuario docenteAdmin = new Usuario("Jano","123","Jano","");
    private Tarea tarea;

    @And("^El docente \"([^\"]*)\" administrador de la Materia \"([^\"]*)\"$")
    public void elDocenteAdministradorDeLaMateria(String nombreDocente, String nombremateria){
            this.materia = new Materia(nombremateria);
            this.materia.agregarAdministrador(this.docenteAdmin);
    }


    @When("^Este Docente agrega una Tarea en la Materia en la fecha <(\\d+)-(\\d+)-(\\d+)> con el nombre \"([^\"]*)\"$")
    public void esteDocenteAgregaUnaTareaEnLaMateriaEnLaFechaConElNombre(int anio, int mes, int dia, String nombreTarea)  {
        LocalDate fecha = LocalDate.of(anio,mes,dia);
        this.tarea= new Tarea(nombreTarea,fecha);
        this.materia.agregarTarea(tarea);
    }


    @Then("^La Materia posee una Tarea \"([^\"]*)\" la Materia \"([^\"]*)\" en la fecha <(\\d+)-(\\d+)-(\\d+)>$")
    public void laMateriaPoseeUnaTareaLaMateriaEnLaFecha(String nombreTarea, String nombreMateria, int anio, int mes, int dia) {
        LocalDate fecha = LocalDate.of(anio,mes,dia);

        Assert.assertEquals(this.tarea.getNombre(),nombreTarea);
        Assert.assertEquals(this.tarea.getFecha(),fecha);
        Assert.assertTrue(this.materia.getTareas().contains(tarea));
    }
}

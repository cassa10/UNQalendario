package gradle.cucumber;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static junit.framework.TestCase.assertTrue;

public class UNQalendarioStepDefs{
    private Materia materia;
    private Usuario docenteAdmin = new Usuario("Cesar","123","Cesar","Lopez");

    @Given("^Una materia sin administradores")
    public void inicializarMateria(){
        this.materia = new Materia();
    }

    @When("^Agrego a un nuevo docente como adminstrador")
    public void agregarDocenteAMateria(){
        this.materia.agregarAdministrador(this.docenteAdmin);
        }

    @Then("^un nuevo docente es ahora adminstrador de esa materia$")
    public void unNuevoDocenteEsAhoraAdminstradorDeEsaMateria() {
        assertTrue(materia.getAdministradores().contains(docenteAdmin));
    }
}
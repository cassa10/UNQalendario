package gradle.cucumber;

import gradle.cucumber.Service.UNQalendarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UNQalendarioObtenerMaterias {

    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private MateriaController materiaController;
    private List<Materia> materias = new ArrayList<>();
    private List<Materia> materiasFromController = new ArrayList<>();
    private Materia materiaFromController;


    @After
    public void cleanUpDataBase(){
        this.materias = new ArrayList<>();
        this.materiasFromController = new ArrayList<>();
        this.materiasFromController = null;
        unqalendarioService.destroy();
    }

    @Given("^Una Materia Controller$")
    public void crearMateriaControllerYUsuarioService(){
        this.materiaController = new MateriaController();
    }

    @And("^Materia \"([^\"]*)\" en el sistema$")
    public void crearMateriaYAgregarlaALaLista(String materiaNombre){
        this.materias.add(this.materiaController.save(new Materia(materiaNombre)));
    }

    @When("^Se solicita todas las materias a la Materia Controller$")
    public void getTodasLasMateriasDeMateriaController(){
        //COMO DEVUELVE ITERABLE LO CONVIERTO A LIST

        this.materiaController.todasLasMaterias().forEach(m -> this.materiasFromController.add(m));
    }

    @Then("^Se retornan las \"([^\"]*)\" materias anteriores$")
    public void assertCantidadMateriasNYLasMateriasAnteriores(String intStr){
        int nroMaterias = Integer.parseInt(intStr);

        assertEquals(this.materiasFromController.size(),nroMaterias);
        assertTrue(this.materiasFromController.containsAll(this.materias));
    }

    @Then("^Se retorna una lista vacia osea ninguna materia$")
    public void assertTodasLasMateriasControllerEstaVacia(){
        assertTrue(this.materiasFromController.isEmpty());
    }

    @When("^Se solicita get materia con el id de la materia \"([^\"]*)\" dada a la Materia Controller$")
    public void solicitarIdDeLaPrimeraMateriaDadaYControllerMateriaHaceGetConEseId(String indexIncStr){
        int index = Integer.parseInt(indexIncStr) - 1;
        String idPrimerMateria = this.materias.get(index).getId();
        this.materiaFromController = this.materiaController.getMateria(idPrimerMateria);
    }

    @Then("^Se retorna la materia \"([^\"]*)\" ya que existe$")
    public void assertPrimeraMateriaDadaFueRetornada(String indexIncStr){
        int index = Integer.parseInt(indexIncStr) - 1;
        assertEquals(this.materiaFromController,this.materias.get(index));
    }

    @When("^Se solicita buscarMateria con el string de \"([^\"]*)\" a la Materia Controller$")
    public void materiaControllerBuscaMateriaConString(String strSearch){
        this.materiaController.buscarMaterias(strSearch).forEach(m->this.materiasFromController.add(m));
    }

    @Then("^Se retornan las materias que contengan \"([^\"]*)\" en su nombre$")
    public void assertMateriasFromMateriaControllerEqualsMateriasConNombreContenidoConString(String strSearched){
        this.materias = this.materias.stream().filter(m-> m.getNombre().contains(strSearched)).collect(Collectors.toList());
        assertTrue(this.materias.containsAll(this.materiasFromController));
    }
}

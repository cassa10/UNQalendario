package gradle.cucumber;

import Service.MateriaService;
import Service.UNQalendarioService;
import Service.UsuarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UNQalendarioSuscripcionAMaterias {

    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private UsuarioController usuarioController;
    private UsuarioService usuarioService;
    private MateriaController materiaController;
    private MateriaService materiaService;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private Materia materia;
    private List<Materia> materiasSuscritas;
    private ResponseEntity responseTest;
    private ObjectId fakeId;

    @After
    public void cleanUpDataBase(){
        this.materiasSuscritas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        unqalendarioService.destroy();
    }

    @Given("^Un setUp de UNQalendario$")
    public void setUpDeUNQalendario(){
        usuarios = new ArrayList<>();
        materiasSuscritas = new ArrayList<>();
        usuarioController = new UsuarioController();
        usuarioService = new UsuarioService();
        materiaController = new MateriaController();
        materiaService = new MateriaService();
    }

    @And("^El Usuario llamado \"([^\"]*)\" con password \"([^\"]*)\"$")
    public void crearUsuario(String nombre, String password) throws UsuarioYaExiste{
        this.usuario =this.usuarioController.guardarUsuario(new Usuario(nombre,password));
    }

    @And("^Unos de los Usuarios llamado \"([^\"]*)\" con password \"([^\"]*)\"$")
    public void crearUsuarioYPonerloEnLaListaDeUsuarios(String nombre, String password) throws UsuarioYaExiste{
        usuarios.add(this.usuarioController.guardarUsuario(new Usuario(nombre,password)));
    }

    @And("^Una Materia \"([^\"]*)\" sin suscriptores$")
    public void crearMateria(String nombreMateria){
        this.materia =  this.materiaController.save(new Materia(nombreMateria,""));
    }

    @When("^Este Usuario se suscribe a la anterior materia$")
    public void usuarioSeSuscribeAMateria(){
        this.usuarioService.suscribirA(usuario.getId(),materia.getId());
    }

    @Then("^La materia posee un suscriptor cuyo usuario es el anteriormente suscrito$")
    public void assertSuscriptoresDeMateria(){
        assertTrue(this.materiaService.tieneSuscriptor(materia,usuario));
        assertEquals(this.materiaService.cantidadSuscriptores(materia),1);
    }


    @When("^Estos Usuarios se suscriben a la anterior materia$")
    public void suscribirListaDeUsuariosALaMateria(){
        this.usuarios.forEach(u -> this.usuarioService.suscribirA(u.getId(),materia.getId()));
    }

    @Then("^La materia posee \"([^\"]*)\" suscriptores cuyos usuarios son los anteriormente suscritos$")
    public void assertSuscriptoresDeMateria(String intStr){
        int cantSuscriptores = Integer.parseInt(intStr);

        this.usuarios.forEach(u -> assertTrue(this.materiaService.tieneSuscriptor(materia,u)));
        assertEquals(this.materiaService.cantidadSuscriptores(materia),cantSuscriptores);
    }

    @And("^Una de las Materias \"([^\"]*)\" donde esta suscrito el usuario anterior$")
    public void crearUnaDeLasMateriasYSuscribirAUsuario(String materiaNombre){

        Materia tmpMateria = this.materiaController.save(new Materia(materiaNombre,""));
        this.usuarioService.suscribirA(usuario.getId(),tmpMateria.getId());

        tmpMateria = this.materiaService.get(tmpMateria.getId());
        this.materiasSuscritas.add(tmpMateria);

    }

    @When("^UsuarioController obtiene todas las materias suscritas del usuario anterior$")
    public void obtenerTodasLasMateriasSuscritasDeUsuario(){

        this.responseTest = this.usuarioController.getMaterias(this.usuario.getId());
    }

    @Then("^UsuarioController responde un ResponseEntity Ok con todas las materias suscritas del usuario anterior en su body$")
    public void usuarioControllerRespondeResponseEntityOkConTodasLasMateriasSuscritasDelUsuarioAnterior(){

        List<Materia> materiasSuscritas = (List<Materia>) this.responseTest.getBody();
        List<String> nombresMateriasSuscritas = materiasSuscritas.stream().map(m->m.getNombre()).collect(Collectors.toList());

        List<String> nombresMateriasSuscritasTest = this.materiasSuscritas.stream().map(m-> m.getNombre()).collect(Collectors.toList());

        assertTrue(nombresMateriasSuscritas.containsAll(nombresMateriasSuscritasTest));

        //200 = Ok
        assertEquals(this.responseTest.getStatusCodeValue(),200);
    }

    @When("^UsuarioController intenta obtener las materias suscritas de un usuario que no existe$")
    public void usuarioControllerGetMateriasDeUnIdUsuarioQueNoExiste(){
        this.fakeId = new ObjectId();
        this.responseTest = this.usuarioController.getMaterias(fakeId.toString());
    }

    @Then("^UsuarioController responde un ResponseEntity Not Found una descripcion de El Usuario con el Id no existe en su body$")
    public void assertResponseEntityNotFoundConUsuarioConElIdNoExisteEnElBody(){
        assertEquals(this.responseTest.getStatusCodeValue(),404);

        assertEquals(this.responseTest.getBody(),"El Usuario con el id " + fakeId + " no existe");
    }

    @Then("^UsuarioController responde un ResponseEntity Ok con ninguna materia en su body$")
    public void assertResponseEntityOkSinMateriasEnElBody(){
        assertEquals(this.responseTest.getStatusCodeValue(),200);

        List<Materia> materiasSuscritas = (List<Materia>) this.responseTest.getBody();
        assertTrue(materiasSuscritas.isEmpty());
    }
}

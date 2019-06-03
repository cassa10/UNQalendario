package gradle.cucumber;

import Service.MateriaService;
import Service.UNQalendarioService;
import Service.UsuarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

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

    @After
    public void cleanUpDataBase(){
        unqalendarioService.destroy();
    }

    @Given("^Un setUp de UNQalendario$")
    public void setUpDeUNQalendario(){
        usuarios = new ArrayList<>();
        usuarioController = new UsuarioController();
        usuarioService = new UsuarioService();
        materiaController = new MateriaController();
        materiaService = new MateriaService();
    }

    @And("^El Usuario llamado \"([^\"]*)\" con password \"([^\"]*)\"$")
    public void crearUsuario(String nombre, String password){
        usuario =this.usuarioController.guardarUsuario(new Usuario(nombre,password));
    }

    @And("^Unos de los Usuarios llamado \"([^\"]*)\" con password \"([^\"]*)\"$")
    public void crearUsuarioYPonerloEnLaListaDeUsuarios(String nombre, String password){
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

}

package gradle.cucumber;

import Service.UNQalendarioService;
import Service.UsuarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UNQalendarioUserLogInAndSignUpStepDefs {

    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private UsuarioController usuarioController;
    private UsuarioService usuarioService;
    private Usuario usuario;
    private Usuario usuarioGetFromUsuarioController;

    @After
    public void cleanUpDataBase(){
        unqalendarioService.destroy();
    }

    @Given("^Un UsuarioController y un UsuarioService$")
    public void setUpUsuarioController(){
        this.usuarioController = new UsuarioController();
        this.usuarioService = new UsuarioService();
    }

    @And("^Un Usuario llamado \"([^\"]*)\" con password \"([^\"]*)\"$")
    public void crearUsuario(String nombre, String password){
        usuario = new Usuario(nombre,password);
    }

    @When("^El UsuarioController guarda a este usuario$")
    public void controllerUsuarioRecibeUsuario(){
        this.usuarioController.guardarUsuario(this.usuario);

    }

    @Then("^El Usuario aparece en la coleccion de Usuarios de la base de datos$")
    public void estaUsuarioEnLaColleccionUsuarios(){
        assertNotNull(this.usuarioService.getUsuarioPorNombre(usuario.getNombre()));
    }

    @And("^El Usuario \"([^\"]*)\" con password \"([^\"]*)\" en la Base de Datos$")
    public void crearYGuardarUsuarioConPasswordENBaseDeDatos(String nombre, String password){
        this.usuario = new Usuario(nombre,password);
        this.usuarioController.guardarUsuario(usuario);
    }

    @When("^El UsuarioController se le hace un get del id de este usuario$")
    public void usuarioControllerGetConIdUsuario(){
        this.usuarioGetFromUsuarioController = this.usuarioController.get(this.usuario.getId());
    }

    @Then("^Este metodo del UsuarioController devuelve al Usuario con ese id$")
    public void assertGetUsuarioControllerConUsuario(){
        assertEquals(this.usuarioGetFromUsuarioController,this.usuario);
    }
}

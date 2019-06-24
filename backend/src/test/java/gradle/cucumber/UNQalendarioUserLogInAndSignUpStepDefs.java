package gradle.cucumber;

import gradle.cucumber.Service.UNQalendarioService;
import gradle.cucumber.Service.UsuarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UNQalendarioUserLogInAndSignUpStepDefs {

    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private UsuarioController usuarioController;
    private UsuarioService usuarioService;
    private Usuario usuario;
    private Usuario usuarioGetFromUsuarioController;
    private UsuarioYaExiste exceptionTest;
    private ResponseEntity responseTest;

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
    public void controllerUsuarioRecibeUsuario() throws UsuarioYaExiste{
        this.usuarioController.guardarUsuario(this.usuario);

    }

    @Then("^El Usuario aparece en la coleccion de Usuarios de la base de datos$")
    public void estaUsuarioEnLaColleccionUsuarios(){
        assertNotNull(this.usuarioService.getUsuarioPorNombre(usuario.getNombreUsuario()));
    }

    @And("^El Usuario \"([^\"]*)\" con password \"([^\"]*)\" en la Base de Datos$")
    public void crearYGuardarUsuarioConPasswordENBaseDeDatos(String nombre, String password) throws UsuarioYaExiste{
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

    @When("^El UsuarioController intenta guardar a este usuario$")
    public void intentarGuardarUsuario(){
        try{
            this.usuarioController.guardarUsuario(this.usuario);
        }catch (UsuarioYaExiste e){
            this.exceptionTest = e;
        }

    }

    @Then("^El UsuarioController lanza excepcion que ya existe ese nombre Usuario$")
    public void assertExceptionYaExisteEseNombreUsuario(){
        assertNotNull(this.exceptionTest);
        assertEquals(this.exceptionTest.getClass(),UsuarioYaExiste.class);
    }

    @When("^El UsuarioController verifica si el Usuario \"([^\"]*)\" con password \"([^\"]*)\" existe$")
    public void usuarioControllerVerificaSiElUsuarioConUnPasswordEspecificoExiste(String usuario,String password){
        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",usuario);
        data.put("password",password);
        this.responseTest = this.usuarioController.checkerSiExisteUsuarioYPassword(data);
    }

    @Then("^El UsuarioController responde un ResponseEntity Ok con el id del Usuario$")
    public void assertResponseEntityOkConElIdDelUsuario(){
        assertEquals(this.responseTest.getBody(),this.usuario.getId());
        //200 = Ok
        assertEquals(this.responseTest.getStatusCodeValue(),200);
    }

    @Then("^El UsuarioController responde un ResponseEntity Not Found con descripcion Datos Invalidos$")
    public void assertResponseEntityNotFoundConDescripcionDatosInvalidos(){
        assertEquals(this.responseTest.getBody(),"Datos Invalidos");
        //404 = Not Found
        assertEquals(this.responseTest.getStatusCodeValue(),404);
    }


}

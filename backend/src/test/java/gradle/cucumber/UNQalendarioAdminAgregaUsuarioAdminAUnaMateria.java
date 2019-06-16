package gradle.cucumber;

import Service.UNQalendarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UNQalendarioAdminAgregaUsuarioAdminAUnaMateria {

    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private MateriaController materiaController;
    private UsuarioController usuarioController;
    private List<Materia> materias;
    private List<Usuario> usuarios;
    private Usuario usuario;
    private Materia materia;
    private ResponseEntity responseTest;

    @After
    public void cleanUpDataBase(){
        this.materias = new ArrayList<>();

        unqalendarioService.destroy();
    }

    @Given("^Un setUp MateriaController y UsuarioController mas utilidades$")
    public void setUpMasUtilidades(){
        usuarioController = new UsuarioController();
        materiaController = new MateriaController();
        this.materias = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.materia = null;
    }

    @And("^Materia \"([^\"]*)\" existente$")
    public void agregarMateria(String materiaNombre){
        this.materias.add(this.materiaController.save(new Materia(materiaNombre)));
    }

    @And("^Usuario con usuario \"([^\"]*)\" password \"([^\"]*)\" nombre \"([^\"]*)\" y apellido \"([^\"]*)\"$")
    public void agregarUsuario(String user,String password,String nombre, String apellido) throws UsuarioYaExiste{
        Usuario usuario = new Usuario(user,password,nombre,apellido);
        this.usuarios.add(usuario);
        this.usuarioController.guardarUsuario(usuario);
    }

    @And("^Un Usuario con usuario \"([^\"]*)\" password \"([^\"]*)\" nombre \"([^\"]*)\" y apellido \"([^\"]*)\"$")
    public void agregarUnUsuario(String user,String password,String nombre, String apellido) throws UsuarioYaExiste{
        Usuario usuario = new Usuario(user,password,nombre,apellido);
        this.usuario = usuario;
        this.usuarioController.guardarUsuario(usuario);
    }

    @When("^MateriaController agrega administrador a \"([^\"]*)\" en la materia \"([^\"]*)\"$")
    public void materiaControllerAgregaAdminEnLaMateriaDada(String username,String indexIncMateria){
        int index = Integer.parseInt(indexIncMateria) - 1;
        String idMateria = this.materias.get(index).getId();
        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",username);
        responseTest = materiaController.agregarAdministradorAUnaMateria(idMateria,data);

        //actualizo la materia en el test
        this.materias.set(index,this.materiaController.getMateria(idMateria));
    }

    @Then("^Se retorna un ResponseEntity ok con body Materia Updated y la materia \"([^\"]*)\" posee un admin que es este usuario$")
    public void assertResponseEntityOkYLaMateriaPoseeUnAdminQueEsEsteUsuario(String indexIncMateria){
        int index = Integer.parseInt(indexIncMateria) - 1;

        //200 = ok
        assertEquals(this.responseTest.getStatusCodeValue(),200);
        assertEquals(this.responseTest.getBody(),"Materia Updated");

        assertEquals(this.materias.get(index).getAdministradores().size(),1);
        assertTrue(this.materias.get(index).getAdministradores().contains(this.usuario));
    }

    @When("^MateriaController agrega administrador a \"([^\"]*)\" en una materia que no existe$")
    public void materiaControllerAgregaAdminAUnaMateriaQueNoExiste(String usuario){
        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",usuario);
        responseTest = materiaController.agregarAdministradorAUnaMateria(ObjectId.get().toString(),data);
    }

    @Then("^Se retorna un ResponseEntity not found con body Datos Invalidos$")
    public void assertResponseEntityNotFoundConBodyDatosInvalidos(){
        assertEquals(responseTest.getBody(),"Datos Invalidos");
        assertEquals(responseTest.getStatusCodeValue(),404);
    }

    @And("^El \"([^\"]*)\" es admin de la materia \"([^\"]*)\"$")
    public void hacerUsuarioAdminDeLaMateriaDelIndexPrevio(String usuario,String indexIncStr) throws UsuarioYaExiste{
        int index = Integer.parseInt(indexIncStr)-1;
        String idMateria = materias.get(index).getId();

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",usuario);

        this.materiaController.agregarAdministradorAUnaMateria(idMateria,data);
    }

    @And("^Se crea \"([^\"]*)\" siendo admin de la materia \"([^\"]*)\"$")
    public void crearUsuarioYHacerloAdminDeLaMateriaDelIndexPrevio(String usuario,String indexIncStr) throws UsuarioYaExiste{
        int index = Integer.parseInt(indexIncStr)-1;
        String idMateria = materias.get(index).getId();

        Usuario user = new Usuario(usuario,"passwordX","NombreX","ApellidoX");
        this.usuarios.add(this.usuarioController.guardarUsuario(user));

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",usuario);

        this.materiaController.agregarAdministradorAUnaMateria(idMateria,data);
    }

    @Then("^Se retorna un ResponseEntity not found con body Datos Invalidos y la materia \"([^\"]*)\" no posee admins$")
    public void assertResponseEntityNotFoundYBodyDatosInvalidosYMateriaNoPoseeAdmins(String indexIncStr){
        assertEquals(responseTest.getBody(),"Datos Invalidos");
        assertEquals(responseTest.getStatusCodeValue(),404);

        int index = Integer.parseInt(indexIncStr) - 1;
        assertTrue(this.materias.get(index).getAdministradores().isEmpty());
    }

    @Then("^Se retorna un ResponseEntity ok con body Materia Updated y la materia \"([^\"]*)\" posee \"([^\"]*)\" admins incluyendo a este usuario y a los anteriores$")
    public void assertResponseEntityOkConBodyMateriaUpdatedYLaMateriaEnIndexNPoseeKAdmines(String indexIncStr,String sizeAdminsStr){
        int sizeAdmins = Integer.parseInt(sizeAdminsStr);
        int index = Integer.parseInt(indexIncStr) - 1;

        assertEquals(this.responseTest.getStatusCodeValue(),200);
        assertEquals(this.responseTest.getBody(),"Materia Updated");

        assertEquals(this.materias.get(index).getAdministradores().size(),sizeAdmins);
        assertTrue(this.materias.get(index).getAdministradores().containsAll(this.usuarios));
        assertTrue(this.materias.get(index).getAdministradores().contains(this.usuario));
    }
}

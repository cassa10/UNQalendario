package gradle.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gradle.cucumber.Service.MateriaService;
import gradle.cucumber.Service.UNQalendarioService;
import gradle.cucumber.Service.UsuarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class UNQalendarioDesuscripcionAMaterias {

    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private UsuarioController usuarioController;
    private MateriaController materiaController = new MateriaController();
    private Usuario usuario;
    private List<Usuario> usuarios;
    private Materia materia;

    @After
    public void cleanUpDataBase(){
        this.usuarios = new ArrayList<>();
        unqalendarioService.destroy();
    }

    @Given("^Un setUp nuevo de UNQalendario$")
    public void setUpNuevoDeUNQalendario(){
        usuarios = new ArrayList<>();
        usuarioController = new UsuarioController();
        materiaController = new MateriaController();
    }

    @And("^Un Nuevo Usuario \"([^\"]*)\" y password \"([^\"]*)\"$")
    public void unUsuarioKConPasswordP(String nombre, String password) throws UsuarioYaExiste{
        this.usuario = this.usuarioController.guardarUsuario(new Usuario(nombre,password));
        usuarios.add(usuario);
    }

    @And("^Una nueva Materia con el nombre \"([^\"]*)\"$")
    public void unaNuevaMateriaconNombre(String nombreMateria){
        this.materia =  this.materiaController.save(new Materia(nombreMateria));
    }

    @And("^Este Usuario suscrito a la materia anterior$")
    public void suscribirUsuarioALaMateria(){
        HashMap<String,String> datosUsuario = new HashMap<String,String>();
        datosUsuario.put("idUsuario",usuario.getId());
        this.usuarioController.suscribirAMateria(materia.getId(),datosUsuario);
    }

    @When("^Este Usuario se desuscribe de esta materia$")
    public void desuscribirUsuarioDeLaMateria(){
        HashMap<String,String> datosUsuario = new HashMap<String,String>();
        datosUsuario.put("idUsuario",usuario.getId());
        this.usuarioController.desuscribirDeMateria(materia.getId(),datosUsuario);
    }

    @Then("^Este Usuario ya no esta suscrito a esta materia$")
    public void assertSiElUsuarioEstaSuscritoEnLaMateria(){
        usuario = usuarioController.get(usuario.getId());
        materia = materiaController.getMateria(materia.getId());

        List<Usuario> suscriptoresDeMateria = materia.getSuscriptores();
        assertFalse(suscriptoresDeMateria.contains(usuario));
        assertTrue(suscriptoresDeMateria.isEmpty());
    }

    @And("^Otro nuevo Usuario \"([^\"]*)\" y password \"([^\"]*)\"$")
    public void otroUsuarioKConPasswordP(String nombre, String password) throws UsuarioYaExiste{
        Usuario nuevoUsuario = this.usuarioController.guardarUsuario(new Usuario(nombre,password));
        usuarios.add(nuevoUsuario);
    }

    @And("^Todos los Usuarios anteriores suscritos a la materia anterior$")
    public void suscribirTodosLosUsuariosALaMateria(){
        HashMap<String,String> datosUser;
        for (Usuario u : usuarios) {
            datosUser = new HashMap<>();
            datosUser.put("idUsuario",u.getId());
            usuarioController.suscribirAMateria(materia.getId(),datosUser);
        }
    }

    @When("^El primer Usuario se desuscribe de esta materia$")
    public void desuscribirDeLaMateriaAlPrimerUsuario(){
        HashMap<String,String> datosUsuario = new HashMap<String,String>();
        usuario = usuarios.get(0);
        datosUsuario.put("idUsuario",usuario.getId());

        usuarioController.desuscribirDeMateria(materia.getId(),datosUsuario);
    }

    @Then("^El primer Usuario ya no esta suscrito a esta materia y los demas si$")
    public void assertElPrimerUsuarioNoEstaSuscritoAEstaMateriaYLosDemasSi(){
        usuario = usuarioController.get(usuario.getId());
        materia = materiaController.getMateria(materia.getId());

        List<Usuario> suscriptoresDeMateria = materia.getSuscriptores();
        assertFalse(suscriptoresDeMateria.contains(usuario));
        assertFalse(suscriptoresDeMateria.isEmpty());

        //Elimino de la lista de usuarios al que ya no pertenece
        usuarios.remove(usuario);

        assertTrue(suscriptoresDeMateria.containsAll(usuarios));
        assertEquals(suscriptoresDeMateria.size(),2);
    }
}

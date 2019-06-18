package gradle.cucumber;

import Service.UNQalendarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UNQalendarioNotificacionPorPost {
    private Usuario usuario;
    private UsuarioController usuarioController;
    private Materia materia;
    private Usuario docente;
    private MateriaController materiaController;
    private UNQalendarioService unqalendarioService = new UNQalendarioService();

    @After
    public void cleanUpDataBase(){
        unqalendarioService.destroy();
    }

    @Given("^Un UsuarioController$")
    public void setUpUsuarioController() throws UsuarioYaExiste {
        this.usuarioController = new UsuarioController();
        this.materiaController = new MateriaController();
        this.materia =  this.materiaController.save(new Materia("Intro"));
        this.docente = new Usuario("dios","1234");
        this.docente = this.usuarioController.guardarUsuario(docente);
    }


    @And("^Un Usuario suscrito a una materia")
    public void inicializarUsuarioYsuscribir() throws UsuarioYaExiste {
        usuario =  new Usuario("duki", "1221");
        usuario = this.usuarioController.guardarUsuario(usuario);
        HashMap<String,String> data = new HashMap<>();
        data.put("idUsuario",this.usuario.getId());
        this.usuarioController.suscribirAMateria(materia.getId(),data);

    }

    @And("^Un docente es administrador de esa materia$")
    public void hacerUsuarioAdminDeMateria(){
        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.docente.getNombreUsuario());
        this.materiaController.agregarAdministradorAUnaMateria(materia.getId(),data);
    }

    @When("^un docente postea una tarea")
    public void docentePosteaTarea(){

        HashMap<String,String> data = new HashMap<>();
        data.put("fechaEntrega","19/07/2019");
        data.put("nombreTarea","Una nueva tarea");
        data.put("usuario",docente.getId());
        this.materiaController.agregarTareaEnMateria(materia.getId(),data);
    }

    @Then("^El Usuario recibe la notificacion$")
    public void elUsuarioRecibeLaNotificacion() {
        Usuario usuarioRecuperado = usuarioController.get(usuario.getId());
        assertEquals(1,usuarioRecuperado.getNotificaciones().size());
    }

    @And("^Una tarea posteada en esa materia$")
    public void unaTareaPosteadaEnEsaMateria() {
        HashMap<String,String> data = new HashMap<>();
        data.put("fechaEntrega","19/07/2019");
        data.put("nombreTarea","Primer tarea");
        data.put("usuario",docente.getId());
        this.materiaController.agregarTareaEnMateria(materia.getId(),data);
    }

    @When("^un docente postea dos tareas$")
    public void unDocentePosteaDosTareas() {
        HashMap<String,String> data = new HashMap<>();
        data.put("fechaEntrega","20/07/2019");
        data.put("nombreTarea","Segunda tarea");
        data.put("usuario",docente.getId());
        this.materiaController.agregarTareaEnMateria(materia.getId(),data);
        HashMap<String,String> data2 = new HashMap<>();
        data2.put("fechaEntrega","21/07/2019");
        data2.put("nombreTarea","Tercer tarea");
        data2.put("usuario",docente.getId());
        this.materiaController.agregarTareaEnMateria(materia.getId(),data2);

    }

    @Then("^El Usuario tiene tres notificaciones de tarea$")
    public void elUsuarioTieneTresNotificacionesDeTarea() {
        Usuario usuarioRecuperado = usuarioController.get(usuario.getId());
        assertEquals(3,usuarioRecuperado.getNotificaciones().size());
    }

    @When("^Se intenta postear una tarea erroneamente$")
    public void seIntentaPostearUnaTareaErroneamente(){
        HashMap<String,String> data = new HashMap<>();
        data.put("fechaEntrega","25/07/2019");
        data.put("nombreTarea","Tarea Erronea");
        data.put("usuario","DOCENTE QUE NO EXISTE");
        this.materiaController.agregarTareaEnMateria(materia.getId(),data);
    }

    @Then("^El Usuario solo tiene una notificacion$")
    public void assertUsuarioTieneUnaNotificacion(){
        Usuario usuarioRecuperado = usuarioController.get(usuario.getId());
        assertEquals(1,usuarioRecuperado.getNotificaciones().size());
    }

}

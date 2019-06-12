package gradle.cucumber;

import Service.UNQalendarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.junit.Assert.*;


public class UNQalendarioAdminMateriaAgregaTarea {
    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private MateriaController materiaController;
    private UsuarioController usuarioController;
    private Usuario usuario;
    private Materia materia;
    private ResponseEntity responseTest;
    private Tarea tarea;

    @After
    public void cleanUpDataBase(){

        unqalendarioService.destroy();
    }

    @Given("^Un setup una materiaController y usuarioController$")
    public void setUp(){
        usuarioController = new UsuarioController();
        materiaController = new MateriaController();
        materia = null;
        usuario = null;
        tarea = null;
    }

    @And("^Un usuario y una materia$")
    public void crearUnUsuarioYUnaMateria() throws UsuarioYaExiste{
        this.usuario = new Usuario("pepe","123","Pepe","Grillo");
        this.materia = new Materia("Ser un Grillo");
        usuarioController.guardarUsuario(usuario);
        materiaController.save(materia);
    }

    @And("^Ese usuario es administrador de esa materia$")
    public void hacerUsuarioAdminDeMateria(){
        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuario.getNombreUsuario());
        this.materiaController.agregarAdministradorAUnaMateria(materia.getId(),data);
    }

    @And("^Una tarea \"([^\"]*)\" con fecha \"([^\"]*)\"$")
    public void crearDataRequestTarea(String nombre, String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        this.tarea = new Tarea(nombre,LocalDate.parse(fecha,formatter));
    }

    @When("^MateriaController se le pide agregar esta tarea en esa Materia$")
    public void materiaControllerAgregarTareaEnEsaMateria(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);
        this.responseTest = materiaController.agregarTareaEnMateria(materia.getId(),data);

        materia = this.materiaController.getMateria(materia.getId());
    }

    @Then("^Se retorna un response ok con esta Materia que posee solamente esta tarea en el body$")
    public void assertResponseOkConEstaMateriaConLaTareaAgregada(){
        assertFalse(this.materia.getAdministradores().isEmpty());

        //200 = ok
        assertEquals(this.responseTest.getStatusCodeValue(),200);

        assertEquals(this.materia.getTareas().size(),1);

        assertEquals(this.materia.getTareas().get(0),this.tarea);
    }
}

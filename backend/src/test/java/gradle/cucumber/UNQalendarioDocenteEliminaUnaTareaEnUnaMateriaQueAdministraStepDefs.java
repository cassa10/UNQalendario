package gradle.cucumber;

import Service.MateriaService;
import Service.UNQalendarioService;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.types.ObjectId;
import org.junit.Assert;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class UNQalendarioDocenteEliminaUnaTareaEnUnaMateriaQueAdministraStepDefs {
    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private MateriaController materiaController;
    private UsuarioController usuarioController;
    private Usuario usuario;
    private Usuario otroUsuario;
    private Materia materia;
    private ResponseEntity responseTest;
    private Tarea tarea;
    private List<Tarea> tareas;
    private MateriaService materiaService;
    private List<Usuario> usuarios;

    @After
    public void cleanUpDataBase(){

        unqalendarioService.destroy();
        tareas = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    @Given("^Un setup una materiaController y usuarioControllerr$")
    public void setUp() {
        usuarioController = new UsuarioController();
        materiaController = new MateriaController();
        materiaService = new MateriaService();
        materia = null;
        usuario = null;
        otroUsuario = null;
        tarea = null;
        tareas = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    @And("^Un usuario y una materiaa$")
    public void crearUnUsuarioYUnaMateria() throws UsuarioYaExiste{
        this.usuario = new Usuario("pepe","123","Pepe","Grillo");
        this.materia = new Materia("Ser un Grillo");

        this.usuario = usuarioController.guardarUsuario(usuario);
        this.materia = materiaController.save(materia);
    }

    @And("^Ese usuario es administrador de esa materiaa$")
    public void setAdminDeMateriaAUsuario() {
        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuario.getNombreUsuario());
        this.materiaController.agregarAdministradorAUnaMateria(materia.getId(),data);

    }

    @And("^Una tareaa \"([^\"]*)\" con fecha \"([^\"]*)\"$")
    public void createDataRequestTarea(String nombre, String fecha) throws Throwable {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.tarea = new Tarea(nombre,LocalDate.parse(fecha,formatter));
    }

    @And("^MateriaController se le pide agregar esta tarea en esa Materiaa$")
    public void materiaControllerAgregarTareaEnEsaMateria(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuario.getId());
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);
        this.responseTest = materiaController.agregarTareaEnMateria(materia.getId(),data);

        materia = this.materiaController.getMateria(materia.getId());
    }

    @When("^MateriaController se le pide eliminar esta tarea en esa Materia$")
    public void materiacontrollerSeLePideEliminarEstaTareaEnEsaMateria() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuario.getId());
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);
        this.responseTest = materiaController.eliminarTareaEnMateria(materia.getId(),data);

        materia = this.materiaController.getMateria(materia.getId());
    }

    @Then("^Se retorna un response ok con esta Materia que no posee ninguna materia en el body$")
    public void seRetornaUnResponseOkConEstaMateriaQueNoPoseeNingunaMateriaEnElBody() {
        Materia materiaBody = (Materia) this.responseTest.getBody();
        assertEquals(materiaBody.getTareas().size(),0);
    }

    @And("^Una lista de tareas en esa materiaa$")
    public void unaListaDeTareasEnEsaMateriaa() {
        this.tareas.add(new Tarea("Tarea re loca",LocalDate.of(2030,1,2)));
        this.tareas.add(new Tarea("Tarea hogar",LocalDate.of(2032,2,4)));
        this.tareas.add(new Tarea("Tarea en clase",LocalDate.of(2035,6,6)));

        this.tareas.forEach(t->this.materiaService.agregarTarea(this.materia.getId(),t));
    }

    @Then("^Se retorna un response ok con esta Materia que contiene todas las tareas anteriores sin la ultima tarea en el body$")
    public void seRetornaUnResponseOkConEstaMateriaQueContieneTodasLasTareasAnterioresSinLaUltimaTareaEnElBody() {
        Materia materiaBody = (Materia) this.responseTest.getBody();

        assertEquals(this.responseTest.getStatusCodeValue(),200);

        assertEquals(materiaBody.getTareas().size(),this.tareas.size());
        assertTrue(materiaBody.getTareas().containsAll(this.tareas));
    }

    @And("^Tres usuarios y una materiaa$")
    public void tresUsuariosYUnaMateriaa() throws UsuarioYaExiste {
        usuario = new Usuario("pepe","123","Pepe","Grillo");


        Usuario usuario2 = new Usuario("lelo","1223","Lelo","Gege");
        Usuario usuario3 = new Usuario("fedekpo","xd","Federico","Kapo");

        usuario = usuarioController.guardarUsuario(usuario);
        usuario2 = usuarioController.guardarUsuario(usuario2);
        usuario3 = usuarioController.guardarUsuario(usuario3);

        usuarios.add(usuario);
        usuarios.add(usuario2);
        usuarios.add(usuario3);

        materia = new Materia("Ser un Grillo");

        materia = materiaController.save(materia);
    }

    @And("^Estos usuarios son administradores de esa materiaa$")
    public void estosUsuariosSonAdministradoresDeEsaMateriaa() {
        for (Usuario u:usuarios){
            HashMap<String,String> data = new HashMap<>();
            data.put("usuario",u.getNombreUsuario());
            this.materiaController.agregarAdministradorAUnaMateria(materia.getId(),data);
        }
    }

    @When("^El segundo Usuario pide a la MateriaController eliminar esta tarea en esa Materia$")
    public void elSegundoUsuarioPideALaMateriaControllerEliminarEstaTareaEnEsaMateria() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuarios.get(1).getId());
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);
        this.responseTest = materiaController.eliminarTareaEnMateria(materia.getId(),data);

        materia = this.materiaController.getMateria(materia.getId());
    }

    @Then("^Se retorna un response not found con \"([^\"]*)\" en el body y la materia contiene la tarea anterior$")
    public void seRetornaUnResponseNotFoundConEnElBodyYLaMateriaContieneLaTareaAnterior(String mensajeBody) {
        assertEquals(this.responseTest.getStatusCodeValue(),404);

        assertEquals(this.responseTest.getBody(),mensajeBody);

        assertTrue(this.materia.getTareas().contains(this.tarea));
    }

    @And("^Otro usuario no es administrador de esa materia$")
    public void otroUsuarioNoEsAdministradorDeEsaMateria() throws UsuarioYaExiste {
        this.otroUsuario = new Usuario("noAdmin","123","NoAdmin","DeMateria");
        this.otroUsuario = usuarioController.guardarUsuario(otroUsuario);

    }

    @When("^MateriaController se le pide eliminar esta tarea en esa Materia con el otro usuario$")
    public void materiacontrollerSeLePideEliminarEstaTareaEnEsaMateriaConElOtroUsuario() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.otroUsuario.getId());
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);
        this.responseTest = materiaController.eliminarTareaEnMateria(materia.getId(),data);

        materia = this.materiaController.getMateria(materia.getId());
    }

    @And("^Un usuario nuevoo$")
    public void unUsuarioNuevoo() throws UsuarioYaExiste {
        this.usuario = new Usuario("pepe","123","Pepe","Grillo");
        this.usuario = usuarioController.guardarUsuario(usuario);

    }

    @When("^MateriaController se le pide eliminar esta tarea en una Materia que no existe$")
    public void materiacontrollerSeLePideEliminarEstaTareaEnUnaMateriaQueNoExiste() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuario.getId());
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);

        //No hay materias asique este id siempre va a concluir en un id inexistente.
        this.responseTest = materiaController.eliminarTareaEnMateria(ObjectId.get().toString(),data);
    }

    @Then("^Se retornaa un response not found con \"([^\"]*)\" en el body$")
    public void seRetornaaUnResponseNotFoundConEnElBody(String mensajeBody) {
        assertEquals(this.responseTest.getStatusCodeValue(),404);

        assertEquals(this.responseTest.getBody(),mensajeBody);
    }
}

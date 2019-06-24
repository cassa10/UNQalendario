package gradle.cucumber;

import gradle.cucumber.Service.MateriaService;
import gradle.cucumber.Service.UNQalendarioService;
import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;


public class UNQalendarioAdminMateriaAgregaTarea {
    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private MateriaController materiaController;
    private UsuarioController usuarioController;
    private Usuario usuario;
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

    @Given("^Un setup una materiaController y usuarioController$")
    public void setUp(){
        usuarioController = new UsuarioController();
        materiaController = new MateriaController();
        materiaService = new MateriaService();
        materia = null;
        usuario = null;
        tarea = null;
        tareas = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    @And("^Un usuario y una materia$")
    public void crearUnUsuarioYUnaMateria() throws UsuarioYaExiste{
        this.usuario = new Usuario("pepe","123","Pepe","Grillo");
        this.materia = new Materia("Ser un Grillo");

        this.usuario = usuarioController.guardarUsuario(usuario);
        this.materia = materiaController.save(materia);
    }

    @And("^Ese usuario es administrador de esa materia$")
    public void hacerUsuarioAdminDeMateria(){
        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuario.getNombreUsuario());
        this.materiaController.agregarAdministradorAUnaMateria(materia.getId(),data);
    }

    @And("^Una tarea \"([^\"]*)\" con fecha \"([^\"]*)\"$")
    public void crearDataRequestTarea(String nombre, String fecha){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        this.tarea = new Tarea(nombre,LocalDate.parse(fecha,formatter));
    }

    @When("^MateriaController se le pide agregar esta tarea en esa Materia$")
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

    @Then("^Se retorna un response ok con esta Materia que posee solamente esta tarea en el body$")
    public void assertResponseOkConEstaMateriaConLaTareaAgregada(){

        Materia materiaBody = (Materia) this.responseTest.getBody();

        //200 = ok
        assertEquals(this.responseTest.getStatusCodeValue(),200);

        assertEquals(materiaBody.getTareas().size(),1);

        assertTrue(materiaBody.getTareas().contains(this.tarea));
    }

    @Then("^Se retorna un response not found con \"([^\"]*)\" en el body y la materia no contiene la tarea anterior$")
    public void assertResponseNotFoundConBodyYLaMateriaNoAgregoTarea(String mensajeBody){


        assertEquals(this.responseTest.getStatusCodeValue(),404);

        assertEquals(this.responseTest.getBody(),mensajeBody);

        assertFalse(this.materia.getTareas().contains(this.tarea));
    }

    @And("^Un usuario nuevo$")
    public void crearUnicamenteUsuario() throws UsuarioYaExiste{
        this.usuario = new Usuario("pepe","123","Pepe","Grillo");
        this.usuario = usuarioController.guardarUsuario(usuario);
    }

    @When("^MateriaController se le pide agregar esta tarea en una Materia que no existe$")
    public void materiaControllerAgregaTareaEnMateriaQueNoExiste(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuario.getId());
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);

        //No hay materias asique este id siempre va a concluir en un id inexistente.
        this.responseTest = materiaController.agregarTareaEnMateria(ObjectId.get().toString(),data);
    }

    @Then("^Se retorna un response not found con \"([^\"]*)\" en el body$")
    public void assertResponseNotFoundConBody(String mensajeBody){

        assertEquals(this.responseTest.getStatusCodeValue(),404);

        assertEquals(this.responseTest.getBody(),mensajeBody);

    }

    @And("^Una lista de tareas en esa materia$")
    public void agregarListaDeTareasEnEsaMateria() {
        this.tareas.add(new Tarea("Tarea re loca",LocalDate.of(2030,1,2)));
        this.tareas.add(new Tarea("Tarea hogar",LocalDate.of(2032,2,4)));
        this.tareas.add(new Tarea("Tarea en clase",LocalDate.of(2035,6,6)));

        this.tareas.forEach(t->this.materiaService.agregarTarea(this.materia.getId(),t));
    }

    @Then("^Se retorna un response ok con esta Materia que contiene todas las tareas anteriores y esta tarea en el body$")
    public void assertResponseOkConMateriaConteniendoTareasAnterioresYLaNueva(){
        Materia materiaBody = (Materia) this.responseTest.getBody();

        assertEquals(this.responseTest.getStatusCodeValue(),200);

        assertTrue(materiaBody.getTareas().contains(this.tarea));
        assertEquals(materiaBody.getTareas().size(),this.tareas.size()+1);
        assertTrue(materiaBody.getTareas().containsAll(this.tareas));
    }

    @And("^Tres usuarios y una materia$")
    public void crearTresUsuariosYUnaMateria() throws UsuarioYaExiste{
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
    @And("^Estos usuarios son administradores de esa materia$")
    public void hacerEstosUsuariosAdminDeEstaMateria(){
        for (Usuario u:usuarios){
            HashMap<String,String> data = new HashMap<>();
            data.put("usuario",u.getNombreUsuario());
            this.materiaController.agregarAdministradorAUnaMateria(materia.getId(),data);
        }
    }

    @When("^El segundo Usuario pide a la MateriaController agregar esta tarea en esa Materia$")
    public void segundoUsuarioPideAMateriaControllerAgregarTareaEnEsaMateria(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String fechaStr = tarea.getFecha().format(formatter);

        HashMap<String,String> data = new HashMap<>();
        data.put("usuario",this.usuarios.get(1).getId());
        data.put("nombreTarea",tarea.getNombre());
        data.put("fechaEntrega",fechaStr);
        this.responseTest = materiaController.agregarTareaEnMateria(materia.getId(),data);

        materia = this.materiaController.getMateria(materia.getId());
    }

    @And("^Existe una tarea identica a la anterior$")
    public void existeMateriaIdenticaALaAnterior(){
        this.materiaService.agregarTarea(this.materia.getId(),this.tarea);
    }

    @Then("^Se retorna un response Bad Request \"([^\"]*)\" en el body y la materia sigue intacta$")
    public void assertResponseBadRequestConBody(String body){

        //400 is bad request.
        assertEquals(this.responseTest.getStatusCodeValue(),400);
        assertEquals(this.responseTest.getBody(),body);

        List<Tarea> tareasDeMateria = this.materiaService.get(materia.getId()).getTareas();
        assertEquals(tareasDeMateria.size(),1);
        assertTrue(tareasDeMateria.contains(tarea));
    }
}

/*
package gradle.cucumber;

import Persistence.DummyObject;
import Service.UsuarioService;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.HashMap;

public class UNQalendarioNotificacionPorPost {
    private Usuario usuario;
    private UsuarioController usuarioController;
    private UsuarioService usuarioService;
    private Materia materia;
    private Usuario docente;
    private MateriaController materiaController;

    @Given("^Un UsuarioController y un UsuarioServicee$")
    public void setUpUsuarioController(){
        this.usuarioController = new UsuarioController();
        this.usuarioService = new UsuarioService();
        this.materiaController = new MateriaController();
    }


    @And("^Un Usuario suscrito a una materia")
    public void inicializarUsuarioYsuscribir(){
        usuario =  new Usuario();
        HashMap<String,String> data = new HashMap<>();
        data.put("idUsuario",this.usuario.getId());
        this.usuarioController.suscribirAMateria(materia.getId(),data);
    }

    @When("^un docente postea una tarea")
    public void docentePosteaTarea(){
        HashMap<String,String> data = new HashMap<>();
        data.put("idUsuario",this.usuario.getId());
        materiaController.agregarTareaEnMateria(materia.getId(),)
    }
}
*/

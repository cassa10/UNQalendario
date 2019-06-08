package gradle.cucumber;

import Service.MateriaService;
import Service.UNQalendarioService;
import Service.UsuarioService;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class UNQalendarioDocenteAgregaUnaTareaEnUnaMateriaQueAdministraStepdefs {


    private UNQalendarioService unqalendarioService = new UNQalendarioService();
    private UsuarioController usuarioController;
    private UsuarioService usuarioService;
    private MateriaController materiaController;
    private MateriaService materiaService;
    private Usuario usuario;
    private List<Usuario> usuarios;
    private Materia materia;
    private Docente docenteAdmin = new Docente("Jano de Mate2");
    private Tarea tarea;

    @And("^El docente \"([^\"]*)\" administrador de la Materia \"([^\"]*)\"$")
    public void elDocenteAdministradorDeLaMateria(String nombreDocente, String nombremateria){
            this.materia = new Materia(nombremateria,nombreDocente);
            this.materia.agregarAdministrador(this.docenteAdmin);
    }


    @When("^Este Docente agrega una Tarea en la Materia en la fecha <(\\d+)-(\\d+)-(\\d+)> con el nombre \"([^\"]*)\"$")
    public void esteDocenteAgregaUnaTareaEnLaMateriaEnLaFechaConElNombre(int anio, int mes, int dia, String nombreTarea)  {
        LocalDate fecha = LocalDate.of(anio,mes,dia);
        this.tarea= new Tarea(nombreTarea,fecha);
        this.docenteAdmin.agregarTarea(this.materia,tarea);

    }


    @Then("^La Materia posee una Tarea \"([^\"]*)\" la Materia \"([^\"]*)\" en la fecha <(\\d+)-(\\d+)-(\\d+)>$")
    public void laMateriaPoseeUnaTareaLaMateriaEnLaFecha(String nombreTarea, String nombreMateria, int anio, int mes, int dia) {
        Date fecha;
        fecha = new Date();
        fecha.setYear(anio);
        fecha.setMonth(mes);

        Assert.assertEquals(this.tarea.getNombre(),nombreTarea);
        Assert.assertEquals(this.tarea.getFecha(),fecha);
        Assert.assertTrue(this.materia.getTareas().contains(tarea));
    }
}

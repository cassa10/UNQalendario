import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Home;
import pageobjects.Login;
import pageobjects.Materia;

import java.util.List;

public class MateriaTest extends BaseTest {

    private Login loginPage;
    private String userDocente = "teacher";
    private String passDocente = "teacher";
    private String nombreMateria = "DummyMateria";

    @BeforeClass
    private void rompePepeRompe(){
        connection.deleteAllSubjects();
        connection.deleteAllUsers();
    }

    @BeforeMethod
    private void createNewLogin(){
        loginPage = new Login(driver);
    }

    @AfterClass
    private void deleteEverything(){
        connection.deleteAllUsers();
        connection.deleteAllSubjects();
    }

    @Test
    public void cuandoAgregoUnaMateriaNuevaNoTieneTareas(){
        registerNewUser(userDocente,passDocente, "Teach", "Er");
        agregarDocenteAMateriaNueva(userDocente, nombreMateria);
        Login newLoginPage = new Login(driver);
        Home homePage = newLoginPage.loginWith(userDocente, passDocente);
        Materia materiaPage = homePage.irAVerMateria(nombreMateria);
        Assert.assertEquals(materiaPage.getListOfTareas().size(),0,"La materia tiene tareas, ");
    }

    @Test(dependsOnMethods = "cuandoAgregoUnaMateriaNuevaNoTieneTareas")
    public void cuandoAgregoUnaTareaAUnaMateriaLaMateriaTieneUnaTarea(){
        Home homePage = loginPage.loginWith(userDocente, passDocente);
        Materia materiaPage = homePage.irAVerMateria(nombreMateria);
        materiaPage.crearTarea("DummyTarea", "07093019");
        List<WebElement> tareasList = materiaPage.getListOfTareas();
        Assert.assertEquals(tareasList.size(), 1, "La tarea no fue agregada, ");
        Assert.assertTrue(tareasList.get(0).findElement(By.xpath("/descendant::div[text()='DummyTarea']")).isDisplayed(),"No se muestra la tarea, ");
    }
}

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Home;
import pageobjects.Login;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class HomeTest extends BaseTest{

    Login loginPage;

    @BeforeMethod
    public void createNewLogin(){
        loginPage = new Login(driver);
    }

    @AfterMethod
    public void deleteDatabase(){
        connection.deleteAllSubjects();
        connection.deleteAllUsers();
    }

    @Test
    public void cuandoInicioSesionYNoTengoMateriasLaSeccionTusMateriasApareceVacia(){
        String userAlumno = "uriel";
        String passAlumno = "uriel";
        String nombreMateria = "Elementos";
        crearMateriaConProfesor(nombreMateria);
        registerNewUser(userAlumno,passAlumno, "Uriel","Piñeyro");
        Home homePage = loginPage.loginWith(userAlumno,passAlumno);
        //Primero busco a la tarjeta de la materia, que haya sido agregada.
        WebElement materiaCard = homePage.getMateriaCard(nombreMateria);
        WebElement tituloSeccion = homePage.getTitleOfSection(materiaCard);
        Assert.assertEquals( tituloSeccion.getText(),"Materias", "La materia no es o no se encuentra, ");
    }

    @Test
    public void cuandoMeSuscriboAUnaMateriaApareceEnSeccionTusMaterias(){
        String userAlumno = "uriel";
        String passAlumno = "uriel";
        String nombreMateria = "Elementos";
        crearMateriaConProfesor(nombreMateria);
        registerNewUser(userAlumno,passAlumno, "Uriel","Piñeyro");
        Home homePage = loginPage.loginWith(userAlumno,passAlumno);
        homePage.suscribirAMateria(nombreMateria);
        //Primero busco a la tarjeta de la materia, que haya sido agregada.
        WebElement materiaCard = homePage.getMateriaCard(nombreMateria);
        WebElement tituloSeccion = homePage.getTitleOfSection(materiaCard);
        Assert.assertEquals( tituloSeccion.getText(),"Tus Materias", "La materia no es o no se encuentra, ");
    }
}
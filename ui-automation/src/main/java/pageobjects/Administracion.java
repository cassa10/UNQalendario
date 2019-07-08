package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Administracion extends BasePage {

    private By materiaNameBy = By.id("inputPassword2");
    private By crearMateriaBtnBy = By.xpath("//button[contains(text(),'Crear')]");
    private By materiasDropdownBy = By.xpath("//select[@class='form-control']");

    public Administracion(WebDriver driver) {
        super(driver);
        driver.get("http://localhost:3000/administracion");
        wait.until(visibilityOfElementLocated(materiaNameBy));
    }

    public void crearMateria(String nombre){
        driver.findElement(materiaNameBy).sendKeys(nombre);
        driver.findElement(crearMateriaBtnBy).click();
    }
    //TODO test de crear materia y agregar un docente a esa materia.
    //TODO crear un teardown similar al que hicimos para lo de usuario.
    //TODO testear lo de la US
}

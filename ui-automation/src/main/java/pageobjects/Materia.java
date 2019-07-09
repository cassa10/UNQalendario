package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Collection;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Materia extends BasePage {

    private By tituloMateriaBy = By.xpath("//h1");
    private By nuevaTareaBtnBy = By.xpath("//button[contains(text(), 'Nueva Tarea')]");
    private By tituloNuevaTareaInputBy = By.xpath("//input[@type='text' and @class='form-control']");
    private By fechaNuevaTareaInputBy = By.xpath("//input[@type='date']");
    private By nuevaTareaModalBy = By.xpath("//div[@class='modal-content']");
    private By crearTareaBtnBy = By.xpath("//button[text()='Crear']");
    private By confirmarBtnBy = By.xpath("//button[text()='Confirmar!']");
    private By listOfTareasBy = By.xpath("//div[@class='alert alert-info col-6']");

    public Materia(WebDriver driver) {
        super(driver);
        wait.until(visibilityOfElementLocated(tituloMateriaBy));
    }

    public void irACrearTarea(){
        driver.findElement(nuevaTareaBtnBy).click();
        wait.until(visibilityOfElementLocated(nuevaTareaModalBy));
    }

    public void crearTarea(String titulo, String mmddAAAA){
        irACrearTarea();
        driver.findElement(tituloNuevaTareaInputBy).sendKeys(titulo);
        driver.findElement(fechaNuevaTareaInputBy).sendKeys(mmddAAAA);
        driver.findElement(crearTareaBtnBy).click();
        wait.until(visibilityOfElementLocated(confirmarBtnBy)).click();
        wait.until(visibilityOfElementLocated(By.xpath("//div[@role='alert']/descendant::div[text()='"+titulo+"']")));
    }

    public List<WebElement> getListOfTareas() {
        return driver.findElements(listOfTareasBy);
    }
}

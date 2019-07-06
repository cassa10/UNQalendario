package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Register extends BasePage{
    private By loginContainerBy = By.cssSelector("div.logIn-containter");
    private By usernameInputBy = By.id("username");
    private By passwordInputBy = By.id("password");
    private By nombreInputBy = By.id("nombre");
    private By apellidoInputBy = By.id("apellido");
    private By submitBtnBy = By.cssSelector("button[type=submit]");

    private WebDriver driver;

    public Register(WebDriver driver){
        super(driver);
        wait.until(visibilityOfElementLocated(loginContainerBy));
    }

    private void inputDataWith(String username, String password, String nombre, String apellido){
        driver.findElement(usernameInputBy).sendKeys(username);
        driver.findElement(passwordInputBy).sendKeys(password);
        driver.findElement(nombreInputBy).sendKeys(nombre);
        driver.findElement(apellidoInputBy).sendKeys(apellido);
    }
}

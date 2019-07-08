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

    public Register(WebDriver driver){
        super(driver);
        wait.until(visibilityOfElementLocated(loginContainerBy));
    }

    public Login registerWith(String username, String password, String nombre, String apellido){
        inputDataWith(username, password, nombre, apellido);
        submit();
        return new Login(driver);
    }

    private void submit(){
        driver.findElement(submitBtnBy).click();
    }

    private void inputDataWith(String username, String password, String nombre, String apellido){
        driver.findElement(usernameInputBy).sendKeys(username);
        driver.findElement(passwordInputBy).sendKeys(password);
        driver.findElement(nombreInputBy).sendKeys(nombre);
        driver.findElement(apellidoInputBy).sendKeys(apellido);
    }


    public By getLoginContainerBy() {
        return loginContainerBy;
    }

    public By getUsernameInputBy() {
        return usernameInputBy;
    }

    public By getPasswordInputBy() {
        return passwordInputBy;
    }

    public By getNombreInputBy() {
        return nombreInputBy;
    }

    public By getApellidoInputBy() {
        return apellidoInputBy;
    }

    public By getSubmitBtnBy() {
        return submitBtnBy;
    }
}

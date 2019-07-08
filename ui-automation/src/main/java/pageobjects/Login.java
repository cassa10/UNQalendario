package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Login extends BasePage {
    private By loginContainerBy = By.cssSelector("div.logIn-containter");
    private By pageTitleBy = By.xpath("//h1[contains(text(),'UNQalendario')]");
    private By usernameInputFieldBy = By.id("username");
    private By submitBtnBy = By.cssSelector("button[type=submit]");
    private By passwordInputFieldBy = By.id("password");
    private By errorMessageBy = By.cssSelector("button[type=submit]");
    private By registrateBtnBy = By.xpath("//a[@href='/crearCuenta']");

    public Login(WebDriver driver){
        super(driver);
        driver.get("http://localhost:3000/");
        wait.until(visibilityOfElementLocated(pageTitleBy));
    }

    public By getPageTitleBy() {
        return pageTitleBy;
    }

    public By getUsernameInputFieldBy() {
        return usernameInputFieldBy;
    }

    public By getPasswordInputFieldBy() {
        return passwordInputFieldBy;
    }

    public By getErrorMessageBy() {
        return errorMessageBy;
    }

    public boolean isTitleDisplayed() {
        return driver.findElement(pageTitleBy).isDisplayed();
    }

    public Home loginWith(String username, String password){
        driver.findElement(usernameInputFieldBy).sendKeys(username);
        driver.findElement(passwordInputFieldBy).sendKeys(password);
        submit();
        if (isElementVisible(errorMessageBy)){
            return null;
        } else {
            return new Home(driver);
        }
    }

    private void submit() {
        driver.findElement(submitBtnBy).click();
    }

    public Register goToRegister() {
        driver.findElement(registrateBtnBy).click();
        return new Register(driver);
    }

    public By getLoginContainerBy() {
        return loginContainerBy;
    }
}

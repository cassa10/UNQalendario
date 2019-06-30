package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class LandingPage {

    private By pageTitleBy = By.xpath("//h1[contains(text(),'UNQalendario')]");
    private WebDriver driver;
    private WebDriverWait wait;

    public LandingPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver,2);
        driver.get("http://localhost:3000/");
        wait.until(visibilityOfElementLocated(pageTitleBy));
    }

    public boolean isTitleDisplayed() {
        return driver.findElement(pageTitleBy).isDisplayed();
    }
}

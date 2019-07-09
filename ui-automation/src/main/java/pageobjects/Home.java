package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Home extends BasePage {

    private By navBarBy = By.cssSelector("div[class=header-navbar]");
    private By eresAdministradorBadgeBy = By.cssSelector("span[class*=adminInfo]");

    public Home(WebDriver driver) {
        super(driver);
        wait.until(visibilityOfElementLocated(navBarBy));
    }

    public By getNavBarBy() {
        return navBarBy;
    }

    public boolean isAdministradorBadgeVisible() {
        try{
            return driver.findElement(eresAdministradorBadgeBy).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}

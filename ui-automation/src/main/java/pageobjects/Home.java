package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Home extends BasePage {

    private By navBarBy = By.cssSelector("div[class=header-navbar]");

    public Home(WebDriver driver) {
        super(driver);
        wait.until(visibilityOfElementLocated(navBarBy));
    }

    public By getNavBarBy() {
        return navBarBy;
    }
}

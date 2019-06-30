import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.LandingPage;

public class LandingPageTest {

    @Test
    public void whenOpeningANewPageTitleIsDisplayed(){
        WebDriver driver = new ChromeDriver();
        LandingPage landingPage = new LandingPage(driver);
        Assert.assertTrue(landingPage.isTitleDisplayed());
        driver.quit();
    }
}

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Home;
import pageobjects.Login;
import pageobjects.Register;

public class LoginTest extends BaseTest {

    @Test
    public void whenOpeningANewPageTitleIsDisplayed(){
        Login loginPage = new Login(driver);
        Assert.assertTrue(loginPage.isTitleDisplayed());
    }

    @Test
    public void whenInsertingWrongDataErrorMessageAppears(){
        Login loginPage = new Login(driver);
        loginPage.loginWith("juan","perez");
        Assert.assertTrue(driver.findElement(loginPage.getErrorMessageBy()).isDisplayed(), "Error message not displayed");
    }

    @Test
    public void whenLoginWithRightDataHomePageIsDisplayed(){
        String username  = "uriel";
        String password = "uriel";
        String nombre = "Uriel";
        String apellido = "Piñeyro";
        Login loginPage = registerNewUser(username, password, nombre, apellido);
        Home homePage = loginPage.loginWith(username, password);
        Assert.assertTrue(homePage.isElementVisible(homePage.getNavBarBy()));
    }

    @Test
    public void whenClickingOnRegisterNowRegisterPageOpens(){
        Login loginPage = new Login(driver);
        Register registerPage = loginPage.goToRegister();
        Assert.assertTrue(registerPage.isElementVisible(registerPage.getLoginContainerBy()));
    }
}

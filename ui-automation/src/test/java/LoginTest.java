import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Home;
import pageobjects.Login;
import pageobjects.Register;

public class LoginTest extends BaseTest {

    private Login loginPage;

    @BeforeMethod
    public void newLoginPage(){
        loginPage = new Login(driver);
    }

    @Test
    public void whenOpeningANewPageTitleIsDisplayed(){
        Assert.assertTrue(loginPage.isTitleDisplayed());
    }

    @Test
    public void whenInsertingWrongDataErrorMessageAppears(){
        loginPage.loginWith("juan","perez");
        Assert.assertTrue(driver.findElement(loginPage.getErrorMessageBy()).isDisplayed(), "Error message not displayed");
    }

    @Test
    public void whenLoginWithRightDataHomePageIsDisplayed(){
        Home homePage = loginPage.loginWith("uriel","uriel");
        Assert.assertTrue(driver.findElement(homePage.getNavBarBy()).isDisplayed());
    }

    @Test
    public void whenClickingOnRegisterNowRegisterPageOpens(){
        Register registerPage = loginPage.goToRegister();
        Assert.assertTrue(registerPage.isElementVisible(registerPage.getLoginContainerBy()));
    }
}

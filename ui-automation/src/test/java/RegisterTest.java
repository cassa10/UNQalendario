import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Login;
import pageobjects.Register;

public class RegisterTest extends BaseTest {

    private Register registerPage;

    @BeforeMethod
    public void setup(){
        driver.get("http://localhost:3000/crearCuenta");
        registerPage = new Register(driver);
    }

    @AfterClass
    public void deleteAllUsers(){
        MongoConnection connection = new MongoConnection();
        connection.deleteAllUsers();
    }

    @Test
    public void whenRegisterWithRightDataLoginPageOpens(){
        Login loginPage = registerPage.registerWith("uriel","uriel","Uriel", "Piñeyro");
        Assert.assertTrue(loginPage.isElementVisible(loginPage.getLoginContainerBy()));
    }
}

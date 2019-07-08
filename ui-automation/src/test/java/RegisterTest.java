import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobjects.Register;

public class RegisterTest extends BaseTest {

    private Register registerPage;

    @BeforeMethod
    public void setup(){
        registerPage = new Register(driver);
    }

    @Test
    public void whenRegisterWithRightDataLoginPageOpens(){
        driver.get("");
    }
}

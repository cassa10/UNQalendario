import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import pageobjects.Login;
import pageobjects.Register;

public abstract class BaseTest {

    protected static WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public static void setUp(){
        driver=new ChromeDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void cleanAndRefresh(){
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

    public Login registerNewUser(String username, String password, String nombre, String apellido){
        driver.get("http://localhost:3000/crearCuenta");
        Register register = new Register(driver);
        return register.registerWith(username,password, nombre, apellido);
    }
}

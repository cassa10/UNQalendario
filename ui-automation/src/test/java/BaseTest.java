import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

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
}

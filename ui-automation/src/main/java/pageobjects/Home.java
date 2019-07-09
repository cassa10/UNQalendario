package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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

    public void suscribirAMateria(String nombreMateria) {
        getMateriaCard(nombreMateria).
                findElement(By.xpath("descendant::p[@class='card-name-bottom suscribir']")).
                click();
    }

    public WebElement getMateriaCard(String nombreMateria){
        return driver.findElement(By.xpath("//h5[contains(text(), '"+nombreMateria+"')]/ancestor::div[@class='card-materia']"));
    }

    public WebElement getTitleOfSection(WebElement materiaCard){
        return materiaCard.findElement(By.xpath("ancestor::div[@class='row']/child::div/child::h4"));
    }

    public Materia irAVerMateria(String nombreMateria) {
        WebElement materiaCard = getMateriaCard(nombreMateria);
        materiaCard.findElement(By.xpath("//p[contains(text(), 'Ver')]")).click();
        return new Materia(driver);
    }
}


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Authorization {

    private WebDriver driver;
    private String emailLogin = "klishk060@gmail.com";
    private String passwordLogin = "klisheyko123";
    private static final Logger log = LogManager.getRootLogger();

//    testing credit card: 4242424242424242
//cv = 543
//    klishk060@gmail.com


    //   LOCATORS
    By emailLoginLocator = By.xpath("//input[@id='email']");
    By passwordLoginLocator = By.xpath("//input[@id='password']");
    By buttonLoginLocator = By.xpath("//button[@type='submit']");
    By nameOfCurrentUserLocator = By.xpath("//h2[contains(text(),'Hi KLISHK KLISHK!')]");


    //Constructors and Getters
    public  Authorization(WebDriver driver) {
        this.driver = driver;
    }
    public String getEmailLogin() {
        return emailLogin;
    }
    public String getPasswordLogin() {
        return passwordLogin;
    }

    public void emailAuthorization(String emailLogin){
        WebElement loginUser = driver.findElement(emailLoginLocator);
        loginUser.click();
        log.info("make click on label Login");
        loginUser.clear();
        log.info("clean label Login");
        loginUser.sendKeys(emailLogin);
        log.info("insert test login");
    }
    public void passwordAuthorization(String passwordLogin){
        WebElement passwordUser = driver.findElement(passwordLoginLocator);
        passwordUser.click();
        log.info("make click on label Password");
        passwordUser.clear();
        log.info("clean label Password");
        passwordUser.sendKeys(passwordLogin);
        log.info("insert test Password");
    }
    public void buttonAuthorization(){
        driver.findElement(buttonLoginLocator).click();
        log.info("make click on button Login");
    }

    public void checkAuthorization(){
        Assert.assertEquals("Hi KLISHK KLISHK!", driver.findElement(nameOfCurrentUserLocator).getText());
    }
}
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

public class Authorization {

    private WebDriver driver;
    private String emailLogin = "klishk060@gmail.com";
    private String passwordLogin = "klisheyko123";

//    testing credit card: 4242424242424242
//cv = 543
//    klishk060@gmail.com


//   LOCATORS
    By emailLoginLocator = By.xpath("//input[@id='email']");
    By passwordLoginLocator = By.xpath("//input[@id='password']");
    By buttonLoginLocator = By.xpath("//button[@type='submit']");
    By nameOfCurrentUser = By.xpath("//h2[contains(text(),'Hi KLISHK KLISHK!')]");

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
        WebElement loginUser = driver.findElement(emailLoginLocator);loginUser.click();
        loginUser.clear();
        loginUser.sendKeys(emailLogin);
    }
    public void passwordAuthorization(String passwordLogin){
        WebElement passwordUser = driver.findElement(passwordLoginLocator);
        passwordUser.click();
        passwordUser.clear();
        passwordUser.sendKeys(passwordLogin);
    }
    public void buttonAuthorization(){
        driver.findElement(buttonLoginLocator).click(); }

    public void checkAuthorization(){
        Assert.assertEquals("Hi KLISHK KLISHK!", driver.findElement(nameOfCurrentUser).getText());
    }
}


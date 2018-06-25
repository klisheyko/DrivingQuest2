import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;


public class SettingsTest {

    public WebDriver driver;

    @Before
    public void setUpTest(){
        System.out.println("Set Up Method ");
        System.setProperty("webdriver.chrome.driver","/home/ytka/Desktop/drivingquest/src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.get("http://gym.web-102.net/public/api/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }
    @After
    public void tearDownTest(){
//        if (driver != null){
//           System.out.println("TearDown Method");
//           driver.close();
//        }
    }
}

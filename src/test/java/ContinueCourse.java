
import org.apache.log4j.spi.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.log4testng.Logger;

import java.util.List;


public class ContinueCourse {

    private static Logger log = Logger.getLogger(SettingsTest.class);
    //   LOCATORS
    By continueCourseLocator = By.cssSelector(".btn-course span");
    By nextSlideLocator = By.cssSelector(".course-next");
    By backgroundGreenLocator = By.cssSelector("div[style*='background:green']");
    By submitQuizLocator = By.xpath("//button[contains(text(),'Submit Quiz')]");
    By returnToDashboardLocator = By.xpath("//a[contains(text(),'Return to dashboard')]");
    By validationLastAnswerLocator = By.cssSelector("div[class*='answer-item']");
    By submitValidationLocator = By.xpath("//button[contains(text(),'Submit')]");
    By submitVideoQuestLocator = By.xpath("//button[contains(text(),'Submit')]");
    By coursePassedLocator = By.xpath("//p[contains(text(),'You have completed your course.')]");
    By LogOutLocator = By.xpath("//a[contains(text(),'Sign Out')]");

    protected WebDriver driver;

    public ContinueCourse(WebDriver driver) {
        this.driver = driver;
    }

    public void continueCourse(WebDriver driver) {
//        WebElement continueCourseElement = driver.findElement(continueCourseLocator);
//        WebElement logOut = driver.findElement(LogOutLocator);
//        if (continueCourseElement==null) {
//            logOut.click();
//        }else continueCourseElement.click();

        WebElement continueCourseElement = driver.findElement(continueCourseLocator);
        continueCourseElement.click();
        log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
    public boolean retryingFindClick(WebDriver driver) {
        boolean result = false;
        int attempts = 0;
        while (attempts < 2) {
            try {
                if (nextSlideLocator != null) {
                    this.driver.get(this.driver.getCurrentUrl() + "?notimer=1");
                    this.driver.findElement(nextSlideLocator).click();
                    result = true;
                    break;
                }
            } catch ( RuntimeException e ) {
         }
            attempts++;
     }
        return result;
    }
    public boolean autoClickNextSlide() {
        PageType pageType = GetPageType();
        ContinueCourse continueCourse = new ContinueCourse(driver);
        switch (pageType){
            case Quiz:
                driver.get(driver.getCurrentUrl() + "?notimer=1");
                driver.navigate().forward();
                List<WebElement> myList=driver.findElements(backgroundGreenLocator);
                for(int i=0; i<myList.size(); i++){
                    myList.get(i).click();
                }
                driver.findElement(submitQuizLocator).click();
                driver.findElement(returnToDashboardLocator).click();
                continueCourse.continueCourse(driver);
                log.info("+++++++++++++++++++++++++++++++++++!!!!!!!!!!++++++++++++++++++++++++++++++++++++++");
                break;
            case Slide:
                //
                if (driver.findElement(nextSlideLocator) != null) {
                    retryingFindClick(driver);
                }
                break;
            case Validation:
                driver.navigate().refresh();
                List<WebElement> myList1=driver.findElements(validationLastAnswerLocator);
                myList1.get(myList1.size() - 1).click();
                driver.findElement(submitValidationLocator).click();
                break;
            case Video:
                driver.get(driver.getCurrentUrl() + "?notimer=1");
                driver.navigate().forward();
                List<WebElement> myList2=driver.findElements(backgroundGreenLocator);
                for(int i=0; i<myList2.size();i++){
                    myList2.get(i).click();
                }
                driver.findElement(submitVideoQuestLocator).click();
                break;
            case Dashboard:
                driver.findElement(LogOutLocator).click();
                break;
            case Unknown:
                // write assert false test has failed
                // log this error to file (log4J)
                break;
                default:
                    break;
        }
        return
                autoClickNextSlide();
    }
    public PageType GetPageType(){
        try {
            if(driver.getCurrentUrl().contains("slide")){
                return PageType.Slide;
            }
            else if(driver.getCurrentUrl().contains("quiz")){
                return PageType.Quiz;
            }
            else if(driver.getCurrentUrl().contains("validation")){
                return PageType.Validation;
            }
            else if (driver.getCurrentUrl().contains("videoquest")){
                return PageType.Video;
            }
            else if(driver.getCurrentUrl().contains("dashboard")&(coursePassedLocator!=null)){
                return PageType.Dashboard;
            }
            else return PageType.Unknown;
        }
        catch(Exception ex){
            // write exception to log file
            return PageType.Unknown;
        }
    }
 }



import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class ContinueCourse {
    private static final Logger log = LogManager.getRootLogger();

    //   LOCATORS
    By continueCourseLocator = By.cssSelector(".btn-course span");
    By nextSlideLocator = By.cssSelector(".course-next");
    By slideNumberLocator = By.xpath("//span[contains(text(),'Slide #')]");
    By timeRemainingLocator = By.xpath("//span[contains(text(),'Time Remaining ')]");
    By questionLocator = By.xpath("//div[contains(text(),'Question')]");
    By backgroundGreenLocator = By.cssSelector("div[style*='background:green']");
    By submitQuizLocator = By.xpath("//button[contains(text(),'Submit Quiz')]");
    By returnToDashboardLocator = By.xpath("//a[contains(text(),'Return to dashboard')]");
    By validationLastAnswerLocator = By.cssSelector("div[class*='answer-item']");
    By submitValidationLocator = By.xpath("//button[contains(text(),'Submit')]");
    By submitVideoQuestLocator = By.xpath("//button[contains(text(),'Submit')]");
    By coursePassedLocator = By.xpath("//p[contains(text(),'You have completed your course.')]");
    By LogOutLocator = By.xpath("//a[contains(text(),'Sign Out')]");
    By parentTaughtLocator = By.xpath("//button[contains(text(),'Parent-Taught')]");
    By localDrivingSchoolLocator = By.xpath("//button[contains(text(),'Local Driving School')]");
    By textChouseWayLocator = By.xpath("//p[contains(text(),'Texas requires teen students to complete 14 Hours of Behind-the-Wheel training with a Parent-Instructor or with a Driving School. How do you wish to complete these hours?')]");

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
        Assert.assertNotNull(continueCourseLocator);
        log.info("Next Slide");
        log.isInfoEnabled();

    }
    public boolean retryingFindClick(WebDriver driver) {

        boolean result = false;
        int attempts = 0;
        while (attempts < 3) {
            try {
                if (nextSlideLocator != null) {
                    this.driver.get(this.driver.getCurrentUrl() + "?notimer=1");
                    this.driver.findElement(nextSlideLocator).click();
                    result = true;
                    log.debug("Next Slide element FOUND on the Page");
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
                for (WebElement aMyList : myList) {
                    aMyList.click();
                    log.debug("List of answer choices in the test Quiz");
                }
                driver.findElement(submitQuizLocator).click();
                driver.findElement(returnToDashboardLocator).click();
                continueCourse.continueCourse(driver);
                break;
            case Slide:
                //
                if (driver.findElement(nextSlideLocator) != null) {
//                    Assert.assertEquals("Slide #", driver.findElement(slideNumberLocator).getText());
                    Assert.assertEquals("Time Remaining", driver.findElement(timeRemainingLocator).getText());
                    retryingFindClick(driver);
                }else if(driver.getCurrentUrl().contains("slide")){
                    retryingFindClick(driver);
                }
                break;
            case Validation:
                driver.navigate().refresh();
                List<WebElement> myList1=driver.findElements(validationLastAnswerLocator);
                log.debug("A list of answers to the question of Validation is collected");
                myList1.get(myList1.size() - 1).click();
                driver.findElement(submitValidationLocator).click();
                log.debug("The last answer option is selected in the list and clicked on it - test Validation");
                break;
            case Video:
                driver.get(driver.getCurrentUrl() + "?notimer=1");
                driver.navigate().forward();
                log.debug("Collected a list of answers in the test Video");
                List<WebElement> myList2=driver.findElements(backgroundGreenLocator);
                for (WebElement aMyList2 : myList2) {
                    aMyList2.click();
                    log.debug("Made a click on the correct answer in the test Video");
                }
                Assert.assertEquals("Time Remaining", driver.findElement(timeRemainingLocator).getText());
                Assert.assertEquals("Question", driver.findElement(questionLocator).getText());
                driver.findElement(submitVideoQuestLocator).click();
                log.debug("Pressed the Submit button after passing the Video test");
                break;
            case Dashboard:
                driver.findElement(LogOutLocator).click();
                log.debug("If we get to the main menu but the Continue Course item is not active. This means that the course is fully completed. Press LogOut");
                break;
            case SaveQuiz:
                WebElement textChouseWay = driver.findElement(textChouseWayLocator);
                if(textChouseWay.isEnabled()){
                    Assert.assertEquals("Texas requires teen students to complete 14 Hours of Behind-the-Wheel training with a Parent-Instructor or with a Driving School. How do you wish to complete these hours?",textChouseWay.getText());
                    driver.findElement(parentTaughtLocator).click();
                }
            case Unknown:
               //Asser ?
                log.error("LogOut button pressed. AutoTest Was Stopped");
                break;
            default:
                break;
        }
        return
                autoClickNextSlide();
    }
    private PageType GetPageType(){
        try {
            if(driver.getCurrentUrl().contains("slide")){
                return PageType.Slide;
            }if(driver.getCurrentUrl().contains("savequiz")){
                return PageType.SaveQuiz;
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
            else if(driver.getCurrentUrl().contains("dashboard")&&(coursePassedLocator!=null)){
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
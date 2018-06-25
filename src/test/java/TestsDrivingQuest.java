import org.junit.Test;
import org.testng.log4testng.Logger;


public class TestsDrivingQuest extends SettingsTest {


        @Test
        public void authorizationTest() {
            Authorization authorization = new Authorization(driver);
            authorization.emailAuthorization(authorization.getEmailLogin());
            authorization.passwordAuthorization(authorization.getPasswordLogin());
            authorization.buttonAuthorization();
            authorization.checkAuthorization();
        }
        @Test
        public void continueCourse() {
            ContinueCourse continueCourse = new ContinueCourse(driver);
            authorizationTest();
            continueCourse.continueCourse(driver);
        }
        @Test
        public void nextSlide() {
            ContinueCourse continueCourse = new ContinueCourse(driver);
            continueCourse();
            continueCourse.autoClickNextSlide();
        }
    }

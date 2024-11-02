import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestContactsAppAndroid {
  AppiumDriver driver;

  @BeforeClass
  public void setup() throws MalformedURLException {
    UiAutomator2Options options =
        new UiAutomator2Options()
            .setUdid("emulator-5554")
            .setNewCommandTimeout(Duration.ofSeconds(30))
            .setAppPackage("com.google.android.contacts")
            .setAppActivity("com.android.contacts.activities.PeopleActivity");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
  }

  @Test
  public void test() {
    driver.findElement(AppiumBy.accessibilityId("Create contact")).click();
    driver
        .findElement(AppiumBy.xpath("//android.widget.EditText[@text=\"First name\"]"))
        .sendKeys("Test First Name");
    driver
        .findElement(AppiumBy.xpath("//android.widget.EditText[@text=\"Last name\"]"))
        .sendKeys("Test Last Name");
    driver
        .findElement(AppiumBy.xpath("//android.widget.EditText[@text=\"Phone\"]"))
        .sendKeys("0123456789");
    driver
        .findElement(
            AppiumBy.xpath(
                "//android.widget.Button[@resource-id=\"com.google.android.contacts:id/toolbar_button\"]"))
        .click();
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestShoppingSiteAndroid {
  AppiumDriver driver;

  @BeforeClass
  public void setup() throws MalformedURLException {
    UiAutomator2Options options =
        new UiAutomator2Options()
            .setUdid("emulator-5554")
            .setNewCommandTimeout(Duration.ofSeconds(30))
            .withBrowserName("Chrome");

    driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  }

  @Test
  public void test() throws InterruptedException {
    driver.get("https://magento.softwaretestingboard.com/push-it-messenger-bag.html");
    //    Thread.sleep(10000);
    //    WebDriverWait webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(15));
    //    webDriverWait.until(ExpectedCondition)
    WebElement productTitle = driver.findElement(By.cssSelector("h1.page-title"));
    driver.executeScript("arguments[0].scrollIntoView(true);", productTitle);
    driver.findElement(By.id("product-addtocart-button")).click();
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }
}

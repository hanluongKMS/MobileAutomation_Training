import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestContactsMobileApp {
  AndroidDriver driver;

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
  public void testContacts() throws InterruptedException {
    System.out.println("Click create contact");
    driver.findElement(AppiumBy.accessibilityId("Create contact")).click();

    System.out.println("Input first name");
    driver.findElement(AppiumBy.xpath(Constants.firstNameTextBox)).sendKeys(Constants.firstName);

    System.out.println("Input last name");
    driver.findElement(AppiumBy.xpath(Constants.lastNameTextBox)).sendKeys(Constants.lastName);

    System.out.println("Click Significant date dropdown");
    driver.findElement(AppiumBy.xpath(Constants.dateDropdown)).click();

    System.out.println("Select date");
    WebElement date = driver.findElement(AppiumBy.xpath(Constants.dayElement));
    date.clear();
    date.sendKeys(Constants.dateText);
    date.click();

    System.out.println("Select month");
    WebElement month = driver.findElement(AppiumBy.xpath(Constants.monthElement));
    month.clear();
    month.sendKeys(Constants.monthText);
    month.click();

    System.out.println("Select year");
    WebElement year = driver.findElement(AppiumBy.xpath(Constants.yearElement));
    year.clear();
    year.sendKeys(Constants.yearText);
    year.click();

    System.out.println("Click set button");
    driver.findElement(AppiumBy.id(Constants.setBtn)).click();

    System.out.println("Input phone number");
    driver
        .findElement(AppiumBy.xpath(Constants.phoneNumberTextBox))
        .sendKeys(Constants.phoneNumber);

    System.out.println("Click on Create");
    driver.findElement(AppiumBy.xpath(Constants.createBtn)).click();

    System.out.println("Close popup window");
    Thread.sleep(5000); // wait for the contact is loaded
    driver.findElement(AppiumBy.id(Constants.closePopupBtn)).click();

    System.out.println("Verify the first name and last name");
    String contactName = driver.findElement(AppiumBy.id(Constants.contactNameText)).getText();
    Assert.assertEquals(
        contactName,
        String.format("%s %s", Constants.firstName, Constants.lastName),
        "Incorrect the contact name");

    System.out.println("Verify the phone number");
    String phone = driver.findElement(AppiumBy.id(Constants.contactPhoneText)).getText();
    Assert.assertEquals(phone, Constants.phoneNumber, "Incorrect phone number");

    System.out.println("Verify the birthday");
    String birthday = driver.findElement(AppiumBy.xpath(Constants.birthdayText)).getText();
    Assert.assertEquals(birthday, Constants.birthday, "Incorrect birthday");

    System.out.println("Click on three dot");
    driver.findElement(AppiumBy.id(Constants.threeDotBtn)).click();

    System.out.println("Click on delete button");
    driver.findElement(AppiumBy.xpath(Constants.deleteBtn)).click();

    System.out.println("Click on delete button on popup");
    driver.findElement(AppiumBy.id(Constants.deletePopupBtn)).click();

    System.out.println("Verify the contact is deleted");
    Assert.assertTrue(
        driver.findElement(AppiumBy.id(Constants.noContactsYetText)).isDisplayed(),
        "The contact is appearing");
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  public static class Constants {
    static final String firstName = "Automation";
    static final String lastName = "Mobile";
    static final String phoneNumber = "0123456789";
    static final String monthText = "Jan";
    static final String dateText = "01";
    static final String yearText = "1999";
    static final String birthday = "January 1, 1999";
    static final String firstNameTextBox = "//android.widget.EditText[@text='First name']";
    static final String lastNameTextBox = "//android.widget.EditText[@text='Last name']";
    static final String phoneNumberTextBox = "//android.widget.EditText[@text='Phone']";
    static final String dateDropdown =
        "//android.widget.ImageButton[@content-desc='Show date picker']";
    static final String monthElement =
        "//android.widget.NumberPicker[1]/*[@resource-id='android:id/numberpicker_input']";
    static final String dayElement =
        "//android.widget.NumberPicker[2]/*[@resource-id='android:id/numberpicker_input']";
    static final String yearElement =
        "//android.widget.NumberPicker[3]/*[@resource-id='android:id/numberpicker_input']";
    static final String setBtn = "android:id/button1";
    static final String createBtn =
        "//android.widget.Button[@resource-id='com.google.android.contacts:id/toolbar_button']";
    static final String closePopupBtn = "android:id/closeButton";
    static final String contactNameText = "com.google.android.contacts:id/large_title";
    static final String contactPhoneText = "com.google.android.contacts:id/header";
    static final String birthdayText =
        "//android.widget.TextView[@text='Birthday']/preceding-sibling::android.widget.TextView";
    static final String threeDotBtn = "com.google.android.contacts:id/action_bar_overflow_menu";
    static final String deleteBtn =
        "//android.widget.TextView[@resource-id='com.google.android.contacts:id/title' and @text='Delete']";
    static final String deletePopupBtn = "android:id/button1";
    static final String noContactsYetText = "android:id/text1";
  }
}

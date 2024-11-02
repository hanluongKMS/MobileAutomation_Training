import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestShoppingMobileWeb {
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
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

    System.out.println("Go to the website");
    driver.get("https://magento.softwaretestingboard.com/");

    System.out.println("Click on navigation menu");
    wait.until(ExpectedConditions.presenceOfElementLocated(Constants.navigationMenu));
    driver.findElement(Constants.navigationMenu).click();

    System.out.println("Click Women category");
    driver.findElement(Constants.womenCategory).click();

    System.out.println("Select the Hoodies option");
    driver.findElement(Constants.hoodiesOption).click();

    System.out.println("Click add to cart");
    Thread.sleep(3000);
    WebElement productReview = driver.findElement(Constants.productReview);
    driver.executeScript("arguments[0].scrollIntoView(true);", productReview);
    driver.findElement(Constants.addToCartBtn).click();

    System.out.println("Wait for loading is completed");
    Thread.sleep(5000);

    System.out.println("Scroll to element");
    driver.executeScript("window.scrollTo(0, 0.25*document.body.scrollHeight)");

    System.out.println("Select size");
    driver.findElement(Constants.productSize).click();

    System.out.println("Select color");
    driver.findElement(Constants.productColor).click();

    System.out.println("Click add to cart of product");
    driver.findElement(Constants.productAddToCartBtn).click();

    System.out.println("Get the product name");
    String productName = driver.findElement(Constants.productNameText).getText();

    System.out.println("Click on My Cart");
    driver.executeScript("window.scrollTo(0, 0.1*document.body.scrollHeight)");
    driver.findElement(Constants.myCartBtn).click();

    System.out.println("Verify the product is added");
    String addedProductName = driver.findElement(Constants.addedProductName).getText();
    Assert.assertEquals(productName, addedProductName, "Incorrect the added product");
    Assert.assertEquals(
        driver.findElement(Constants.numberOfProducts).getText(),
        "1",
        "More than 1 product is added");

    System.out.println("Click on Proceed to Checkout");
    driver.findElement(Constants.proceedToCheckoutBtn).click();

    System.out.println("Wait for loading is completed");
    wait.until(ExpectedConditions.elementToBeClickable(Constants.firstNameTextBox));

    System.out.println("Input the required field");
    driver.findElement(Constants.emailTextBox).sendKeys(Constants.email);
    driver.findElement(Constants.firstNameTextBox).sendKeys(Constants.firstName);
    driver.findElement(Constants.lastNameTextBox).sendKeys(Constants.lastName);
    driver.findElement(Constants.streetTextBox).sendKeys(Constants.street);
    driver.findElement(Constants.cityTextBox).sendKeys(Constants.city);
    Select select = new Select(driver.findElement(Constants.regionDropdown));
    select.selectByVisibleText(Constants.regionOption);
    driver.findElement(Constants.zipCodeTextBox).sendKeys(Constants.zipCode);
    driver.findElement(Constants.phoneNumberTextBox).sendKeys(Constants.phoneNumber);

    System.out.println("Select shipping method with best way");
    driver.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    driver.findElement(Constants.shippingMethodOption).click();

    System.out.println("Click on Next button");
    driver.findElement(Constants.nextBtn).click();

    System.out.println("Wait for loading is completed");
    wait.until(ExpectedConditions.elementToBeClickable(Constants.placeHolderBtn));

    System.out.println("Click on Place Order");
    driver.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    driver.findElement(Constants.placeHolderBtn).click();

    System.out.println("Verify place order successfully");
    wait.until(ExpectedConditions.presenceOfElementLocated(Constants.continueShoppingBtn));
    String message = driver.findElement(Constants.headerMessageText).getText();
    Assert.assertEquals(message, Constants.successfulMessage, "Place order unsuccessfully");
  }

  @AfterClass
  public void tearDown() {
    if (driver != null) {
      driver.quit();
    }
  }

  public static class Constants {
    static final By navigationMenu = By.cssSelector(".action.nav-toggle");
    static final By womenCategory = By.xpath("//a[./span[text()='Women']]");
    static final By hoodiesOption =
        By.xpath("//a[./span[contains(text(),'Hoodies')] and contains(@href,'women')]");
    static final By productReview =
        By.xpath("//ol[contains(@class,'product-items')]/li[last()]//div[@class='rating-result']");
    static final By addToCartBtn =
        By.xpath("//ol[contains(@class,'product-items')]/li[last()]//button");
    static final By productSize =
        By.xpath("//div[contains(@id,'option-label-size') and @option-label = 'L']");
    static final By productColor = By.xpath("//div[contains(@id,'option-label-color')]");
    static final By productAddToCartBtn = By.xpath("//button[@title='Add to Cart']");
    static final By productNameText = By.cssSelector(".page-title span");
    static final By myCartBtn = By.xpath("//a[./span[text()='My Cart']]");
    static final By addedProductName = By.cssSelector("#mini-cart .product-item-name");
    static final By numberOfProducts = By.cssSelector(".items-total .count");
    static final By proceedToCheckoutBtn = By.cssSelector("#top-cart-btn-checkout");
    static final By emailTextBox = By.cssSelector("fieldset #customer-email");
    static final By firstNameTextBox = By.name("firstname");
    static final By lastNameTextBox = By.name("lastname");
    static final By streetTextBox = By.name("street[0]");
    static final By cityTextBox = By.name("city");
    static final By zipCodeTextBox = By.name("postcode");
    static final By phoneNumberTextBox = By.name("telephone");
    static final By regionDropdown = By.cssSelector("[name='region_id']");
    static final By shippingMethodOption = By.xpath("//td[text()='Best Way']");
    static final By nextBtn = By.cssSelector("[id^='shipping-method-buttons'] button");
    static final By placeHolderBtn = By.cssSelector("button[title='Place Order']");
    static final By continueShoppingBtn = By.cssSelector("[class='action primary continue']");
    static final By headerMessageText = By.cssSelector(".page-title span");
    static final String email = "automation@gmail.com";
    static final String firstName = "automation";
    static final String lastName = "test";
    static final String street = "123 Main Street";
    static final String city = "New York";
    static final String zipCode = "50000";
    static final String phoneNumber = "0123456789";
    static final String regionOption = "Alaska";
    static final String successfulMessage = "Thank you for your purchase!";
  }
}

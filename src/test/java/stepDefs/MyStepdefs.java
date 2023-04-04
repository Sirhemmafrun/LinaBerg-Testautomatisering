package stepDefs;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


import java.time.LocalTime;

import static org.junit.Assert.assertEquals;


public class MyStepdefs {
    private WebDriver driver;





    @Given("I have chosen a {string}")
    public void iHaveChosenA(String browser) {
        if (browser.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.get("https://login.mailchimp.com/signup/");
            clickWait(By.cssSelector("#onetrust-accept-btn-handler")); //Cookies
        } else if (browser.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\Selenium\\msedgedriver.exe");
            driver = new EdgeDriver();
            driver.get("https://login.mailchimp.com/signup/");
            clickWait(By.cssSelector("#onetrust-accept-btn-handler"));//Cookies

        }
    }

    @And("I have entered Email {string}")
    public void iHaveEnteredEmail(String Email) {
        //Lägg till minut plus sekunden och random nummer i Email
        LocalTime randomEmail = java.time.LocalTime.now();
        int second = randomEmail.getSecond();
        int minute = randomEmail.getMinute();

        if (Email.equalsIgnoreCase("hejsan@hello.com")) {
            sendKeys(By.cssSelector("#email"), (second + minute + Math.random() + Email));
        }


    }

    @And("I have entered Username {string}")
    public void iHaveEnteredUsername(String Username) {

        if (Username.equalsIgnoreCase("LinaHej")) {
            clickWait(By.cssSelector("#new_username"));
        }

        if (Username.equalsIgnoreCase("Long")) {
            clickWait(By.cssSelector("#new_username"));
            driver.findElement(By.cssSelector("#new_username")).clear(); //Tar bort email som automatiskt kommer in
            Username = "";

            for (int i = 0; i < 100; i++) { //Lägg till något på username
                Username += "re";

            }
            sendKeys(By.cssSelector("#new_username"), (Username)); //Skickar till Explicit wait metod

        }
        if (Username.equalsIgnoreCase("Lina22")) {
            clickWait(By.cssSelector("#new_username")); //Skickar till Explicit wait metod
            driver.findElement(By.cssSelector("#new_username")).clear(); //Tar bort email som automatiskt kommer in

            sendKeys(By.cssSelector("#new_username"), (Username)); //Skickar till Explicit wait metod

        }
        if (Username.equalsIgnoreCase("Lina223")) {
            clickWait(By.cssSelector("#new_username")); //Skickar till Explicit wait metod
            driver.findElement(By.cssSelector("#new_username")).clear(); //Tar bort email som automatiskt kommer in

            sendKeys(By.cssSelector("#new_username"), (Username)); //Skickar till Explicit wait metod
        }

    }


    @And("I have entered Password {string}")
    public void iHaveEnteredPassword(String Password) {
        sendKeys(By.cssSelector("#new_password"), (Password));//Skickar till Explicit wait metod
    }

    //Explicit wait för allt som ska skickas in
    private void sendKeys(By by, String text) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
        element.sendKeys(text);
    }


    @When("I click on Sign Up")
    public void iClickOnSignUp() {
        clickWait(By.cssSelector("#marketing_newsletter")); //Checkbox nyhetsbrev
        clickWait(By.cssSelector("#create-account-enabled"));//Skickar till Explicit wait metod
    }

    //Explicit wait för allt som ska klickas på
    private void clickWait(By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        element.click();
    }


    @Then("I {string} an account")
    public void iAnAccount(String create) {

        String result;
        if (create.equalsIgnoreCase("Yes")) {
            try { //Hittar recaptcha och det blir godkänt
                WebElement reCaptcha = driver.findElement(By.cssSelector("[type='text/javascript']"));
                String expectedr = "script";

                assertEquals(expectedr, reCaptcha.getTagName());
            } catch (Exception NoSuchElementException) {

                //Result väntar på ny sida
                result = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-content > div > div.content.line.section > section > div > h1"))).getText();
                String expected = "Check your email";
                String actual = result;
                assertEquals(expected, actual);
            }
        }
        if (create.equalsIgnoreCase("UserTooLong")) {
            //Result väntar på att sidan laddas
            result = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span"))).getText();
            String expected = "Enter a value less than 100 characters long";
            String actual = result;

            assertEquals(expected, actual);
        }
        if (create.equalsIgnoreCase("UserExists")) {
            //Result väntar på att sidan laddas
            result = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(2) > div > span"))).getText();
            String expected = "Great minds think alike - someone already has this username. If it's you, log in.";
            String actual = result;

            assertEquals(expected, actual);

        }
        if (create.equalsIgnoreCase("MissingEmail")) {
            //Result väntar på att sidan laddas
            result = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#signup-form > fieldset > div:nth-child(1) > div > span"))).getText();
            String expected = "An email address must contain a single @.";
            String actual = result;

            assertEquals(expected, actual);
        }

    }

    @After
    public void tearDown() {
        driver.close();
        driver.quit();
    }
}

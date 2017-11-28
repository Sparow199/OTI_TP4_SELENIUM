import org.apache.commons.logging.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.sql.DriverManager;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;


public class ExampleTest {

    private WebDriver driver;

    @Before
    public void createDriver() {
        System.setProperty("webdriver.chrome.driver", "/local/aoudia/Gitlab/OTI/OTI_TP4_SELENIUM/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void freeDriver() {
        driver.quit();
    }

    @Test
    public void test() throws java.io.IOException {

        driver.get("http://localhost:8080/src/index.html");

        WebElement element = driver.findElement(By.name("v1"));
        element.sendKeys("12");

        element = driver.findElement(By.name("c1"));
        element.sendKeys("EUR");

        element = driver.findElement(By.name("v2"));
        element.sendKeys("22");

        element = driver.findElement(By.name("c2"));
        element.sendKeys("EUR");

        element = driver.findElement(By.id("button"));
        element.click();


        // Wait for the page to load, timeout after 10 seconds, stop when results present
        new WebDriverWait(driver, 10).until(new ExpectedCondition < Boolean > () {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("res")).isDisplayed();
            }
        });

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("/tmp/screenshot_test.jpg"));

        assertEquals(driver.findElement(By.id("res")).getText(), "Result : 34 (EUR)");
        new File("/tmp/screenshot_test.jpg").delete();
    }

    @Test
    public void testNOK() throws java.io.IOException {

        driver.get("http://localhost:8080/src/index.html");


        WebElement element = driver.findElement(By.name("v1"));
        element.sendKeys("12");

        element = driver.findElement(By.name("c1"));
        element.sendKeys("EUR");

        element = driver.findElement(By.name("v2"));
        element.sendKeys("22");

        element = driver.findElement(By.name("c2"));
        element.sendKeys("EUR");

        element = driver.findElement(By.id("button"));
        element.click();

        // Wait for the page to load, timeout after 10 seconds, stop when results present
        new WebDriverWait(driver, 10).until(new ExpectedCondition < Boolean > () {

            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("res")) != null;
            }

        });

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("/tmp/screenshot_testnok.jpg"));

        assertEquals(driver.findElement(By.id("res")).getText(), "Result : 34 (EUR)");
        new File("/tmp/screenshot_testnok.jpg").delete();
    }

    @Test
    public void testOK2() throws java.io.IOException {
        driver.get("http://localhost:8080/src/index.html");

        WebElement element = driver.findElement(By.name("v1"));
        element.sendKeys("12");

        element = driver.findElement(By.name("c1"));
        element.sendKeys("EUR");

        element = driver.findElement(By.name("v2"));
        element.sendKeys("22");

        element = driver.findElement(By.name("c2"));
        element.sendKeys("eUR");

        element = driver.findElement(By.id("button"));
        element.click();

        // Wait for the page to load, timeout after 10 seconds, stop when results present
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.id("res")));
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("/tmp/screenshot_testok2.jpg"));

        assertEquals(driver.findElement(By.id("res")).getText(), "Result : 34 (EUR)");
        new File("/tmp/screenshot_testok2.jpg").delete();
    }

}
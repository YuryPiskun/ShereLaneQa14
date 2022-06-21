package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class SingUpTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        //driver.manage().window().setSize(new Dimension(1680, 740));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
    }

    @Test
    public void checkSearchField() {
        WebElement searchField = driver.findElement(By.name("keyword"));
        searchField.sendKeys("Charles Dickens");
        WebElement searchButton = driver.findElement(By.cssSelector("input[value='Search']"));
        searchButton.click();
        String messageText = driver.findElement(By.className("confirmation_message")).getText();
        Assert.assertEquals("Nothing is found :(", messageText);
    }

    @Test
    public void checkSingUpButton() {
        WebElement singUpButton = driver.findElement(By.xpath("//a[text()='Sign up']"));
        singUpButton.click();
        Assert.assertTrue(driver.findElement(By.name("zip_code")).isDisplayed());
    }

    @Test
    public void zipCodeShouldBeValid() {
        WebElement singUpButton = driver.findElement(By.xpath("//a[text()='Sign up']"));
        singUpButton.click();
        WebElement zipCodeField = driver.findElement(By.name("zip_code"));
        zipCodeField.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("input[value='Register']")).isDisplayed());
    }

    @Test
    public void zipCodeFieldLessLength() {
        WebElement singUpButton = driver.findElement(By.xpath("//a[text()='Sign up']"));
        singUpButton.click();
        WebElement zipCodeField = driver.findElement(By.name("zip_code"));
        zipCodeField.sendKeys("1234");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        Assert.assertTrue(driver.findElement(By.xpath
                        ("//*[contains(text(),'Oops, error on page. ZIP code should have 5 digits')]")).
                isDisplayed());
    }

    @Test
    public void userShouldBeRegisteredWithValidInfo() {
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        driver.findElement(By.name("first_name")).sendKeys("Yury");
        driver.findElement(By.name("last_name")).sendKeys("Piskun");
        driver.findElement(By.name("email")).sendKeys("yury1111@mail.ru");
        driver.findElement(By.name("password1")).sendKeys("qwert");
        driver.findElement(By.name("password2")).sendKeys("qwert");
        driver.findElement(By.cssSelector("input[value='Register']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector(".confirmation_message")).isDisplayed());
    }

    @Test
    public void checkDiscountFromZeroToTwenty() {
        WebElement singUpButton = driver.findElement(By.xpath("//a[text()='Sign up']"));
        singUpButton.click();
        WebElement zipCodeField = driver.findElement(By.name("zip_code"));
        zipCodeField.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        driver.findElement(By.name("first_name")).sendKeys("Yury");
        driver.findElement(By.name("last_name")).sendKeys("Piskun");
        driver.findElement(By.name("email")).sendKeys("yury1111@mail.ru");
        driver.findElement(By.name("password1")).sendKeys("qwert");
        driver.findElement(By.name("password2")).sendKeys("qwert");
        driver.findElement(By.cssSelector("input[value='Register']")).click();
        String userLogin = driver.findElement(By.xpath
                        ("//td[contains(text(),'Email')]/following::b"))
                .getText();
        driver.get("https://www.sharelane.com/cgi-bin/main.py");
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.sendKeys(userLogin);
        WebElement passField = driver.findElement(By.name("password"));
        passField.sendKeys("1111");
        driver.findElement(By.xpath("//input[@value='Login']")).click();
        WebElement firstBookOnPage = driver.findElement(By.xpath("(//tr[@align='center']//td//a)[2]"));
        firstBookOnPage.click();
        WebElement addToCartButton = driver.findElement(
                By.xpath("//table[@align='center']//p//a"));
        addToCartButton.click();
        WebElement goToCartButton = driver.findElement(By.xpath("//td[@align='right']//a"));
        goToCartButton.click();
        WebElement quantityField = driver.findElement(By.xpath("//tr[2]//td[3]//input"));
        quantityField.clear();
        quantityField.sendKeys("19");
        WebElement updateButton = driver.findElement(By.xpath("//input[@value='Update']"));
        updateButton.click();
        Assert.assertEquals(driver.findElement(By.xpath("//tr[2]//td[5]//p//b")).getText(), "2");
/*
        quantityField.clear();
        quantityField.sendKeys("20");
        updateButton.click();
        Assert.assertEquals(driver.findElement(By.xpath("//tr[2]//td[5]//p//b")).getText(), "2");
*/
    }

    @AfterMethod(alwaysRun = true)
    public void closeThis() {
        driver.quit();
    }
}

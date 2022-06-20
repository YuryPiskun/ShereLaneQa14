package org.example;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SingUpTest {
    WebDriver driver;
    @BeforeClass
    public void setPathToWebDriver(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }
    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://www.sharelane.com/cgi-bin/register.py");    }

    @Test
    public void zipCodeShouldBeValid(){
        WebElement zipCodeField = driver.findElement(By.name("zip_code"));
        zipCodeField.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value='Continue']")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("input[value='Register']")).isDisplayed());
    }

    @Test
    public void userShouldBeRegesteredWithValidInfo(){
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        driver.findElement(By.name("first_name")).sendKeys("Yury");
        driver.findElement(By.name("last_name")).sendKeys("Piskun");
        driver.findElement(By.name("email")).sendKeys("yury1111@mail.ru");
        driver.findElement(By.name("first_name")).sendKeys("Yury");
        driver.findElement(By.name("password1")).sendKeys("qwert");
        driver.findElement(By.name("password2")).sendKeys("qwert");
        driver.findElement(By.cssSelector("input[value='Register']")).click();
        Assert.assertTrue(driver.findElement(By.className("confirmation_massage")).isDisplayed());
    }


}

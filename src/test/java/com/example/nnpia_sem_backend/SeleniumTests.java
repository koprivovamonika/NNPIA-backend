package com.example.nnpia_sem_backend;

import com.example.nnpia_sem_backend.entity.BeautyProcedure;
import com.example.nnpia_sem_backend.service.ProcedureService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SeleniumTests {

    @Autowired
    ProcedureService procedureService;

    private static WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }

    @AfterAll
    static void afterAll() {
        driver.quit();
    }

    @Test
    void successfulLogin() throws InterruptedException {
        driver.get("http://localhost:3000/login");
        Thread.sleep(2000);
        WebElement input = driver.findElement(By.tagName("input"));

        driver.findElement(By.name("username")).sendKeys("administrator");
        driver.findElement(By.name("password")).sendKeys("admin1234");
        driver.findElement(By.className("btn-primary")).click();

        WebDriverWait wait = new WebDriverWait(driver, 3);

        wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id=\"root\"]/div/div/div/h1"), "Administration"));
    }

    @Test
    void unsuccessfulLogin() throws InterruptedException {
        driver.get("http://localhost:3000/login");
        Thread.sleep(2000);
        WebElement input = driver.findElement(By.tagName("input"));

        driver.findElement(By.name("username")).sendKeys("badUser");
        driver.findElement(By.name("password")).sendKeys("badPassword");
        driver.findElement(By.className("btn-primary")).click();

        WebDriverWait wait = new WebDriverWait(driver, 3);

        wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id=\"root\"]/div/div/form/h2"), "Bad credentials"));
    }


    @Test
    void addProcedure() throws InterruptedException {
        driver.get("http://localhost:3000/login");
        Thread.sleep(2000);

        driver.findElement(By.name("username")).sendKeys("administrator");
        driver.findElement(By.name("password")).sendKeys("admin1234");
        driver.findElement(By.className("btn-primary")).click();
        Thread.sleep(1000);

        driver.get("http://localhost:3000/create_update_procedure");

        driver.findElement(By.xpath("//input[@type='text']")).sendKeys("Test Procedure");
        driver.findElement(By.tagName("textarea")).sendKeys("testovaci procedura");
        driver.findElement(By.xpath("//input[@type='number']")).sendKeys("500");
        driver.findElement(By.className("btn-primary")).click();


        WebDriverWait wait = new WebDriverWait(driver, 1);
        wait.until(ExpectedConditions.textToBe(By.xpath("//*[@id=\"root\"]/div/div/h1"), "Procedures"));
        BeautyProcedure test_procedure = procedureService.findProcedureByName("Test Procedure");
        procedureService.deleteProcedure(test_procedure.getId());
    }
}

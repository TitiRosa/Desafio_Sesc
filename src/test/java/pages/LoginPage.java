package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By usernameField = By.id("Usuario");
    private By passwordField = By.id("Senha");
    private By loginButton = By.id("btLogin");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Abrir URL da página de login
    public void open(String url) {
        System.out.println("Abrindo URL: " + url);
        driver.get(url);
        takeScreenshot("openLoginPage");
    }

    // Realizar login
    public void login(String username, String password) {
        try {
            System.out.println("Preenchendo usuário e senha...");
            WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
            userField.sendKeys(username);

            WebElement passField = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
            passField.sendKeys(password);

            takeScreenshot("fillLoginFields");

            WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
            loginBtn.click();

            System.out.println("Login realizado com sucesso!");
            takeScreenshot("afterLogin");
        } catch (Exception e) {
            System.err.println("Erro durante login: " + e.getMessage());
            takeScreenshot("loginError");
            throw e;
        }
    }

    // Tirar screenshot
    private void takeScreenshot(String actionName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            String path = "screenshots/" + actionName + "_" + System.currentTimeMillis() + ".png";
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(srcFile.toPath(), Paths.get(path));
            System.out.println("Screenshot salvo: " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


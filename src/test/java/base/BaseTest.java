package base;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;

import java.time.Duration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public Faker faker;
    public DadosUsuario dadosUsuario;

    // Configuração padrão (parametrizável via -DusuarioPadrao, -DsenhaPadrao, -DbaseUrl)
    private final String usuarioPadrao = System.getProperty("usuarioPadrao", "lauraVieira");
    private final String senhaPadrao = System.getProperty("senhaPadrao", "EuTeste");
    private final String baseUrl = System.getProperty("baseUrl", "https://conectaread-development.senacrs.com.br/Login");

    public BaseTest() {
        faker = new Faker();
    }

    protected void initDriver() {
        ChromeOptions options = new ChromeOptions();

        String userDataDir = null;
        try {
            Path tempProfile = Files.createTempDirectory("selenium-profile-");
            userDataDir = tempProfile.toAbsolutePath().toString();
        } catch (IOException ignored) { }

        options.addArguments("--headless=new",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-gpu",
                "--window-size=1920,1080",
                "--ignore-certificate-errors",
                "--allow-insecure-localhost",
                "--disable-notifications",
                "--disable-extensions",
                "--disable-infobars",
                "--disable-features=IsolateOrigins,site-per-process,PrivacySandboxSettings4,CookieDeprecationMessages",
                "--disable-blink-features=AutomationControlled");
        options.setAcceptInsecureCerts(true);
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0 Safari/537.36");
        if (userDataDir != null) {
            options.addArguments("--user-data-dir=" + userDataDir);
        }
        options.setBinary("/usr/bin/google-chrome");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Login genérico
     */
    public void login(String usuario, String senha) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(baseUrl);
        loginPage.login(usuario, senha);
    }

    /**
     * Login com usuário padrão
     */
    public void loginPadrao() {
        login(usuarioPadrao, senhaPadrao);
    }

    /**
     * Gera dados de usuário aleatórios
     */
    public void gerarDadosUsuario() {
        String nomeCompleto = faker.name().fullName();
        String email = faker.internet().emailAddress();
        String cpf = faker.number().digits(11);
        String nomeUsuario = faker.name().username();
        String senha = faker.internet().password(5, 10, true, true, true);
        String empresa = "SENAC";
        String uf = "Rio Grande do Sul (RS)";
        String perfil = "Gerenciar - Teste prático QA";
        String polo = "TESTE CANDIDATO 2";
        String poloValue = "6993";

        dadosUsuario = new DadosUsuario(nomeCompleto, email, cpf, nomeUsuario, senha, empresa, uf, perfil, polo, poloValue);
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        initDriver();

        String usarLoginPadrao = System.getProperty("usarLoginPadrao", "true");
        if (Boolean.parseBoolean(usarLoginPadrao)) {
            loginPadrao();
        }
        gerarDadosUsuario();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

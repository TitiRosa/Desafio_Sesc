package base;

import com.github.javafaker.Faker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    public Faker faker;
    public DadosUsuario dadosUsuario;

    // Usuário padrão
    private final String usuarioPadrao = "lauraVieira";
    private final String senhaPadrao = "EuTeste";

    public BaseTest() {
        faker = new Faker();
    }

    protected void initDriver() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /**
     * Login genérico
     */
    public void login(String usuario, String senha) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open("https://conectaread-development.senacrs.com.br/Login");
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

        loginPadrao();
        gerarDadosUsuario();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

package base;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import base.DadosUsuario;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public Faker faker;
    public DadosUsuario dadosUsuario;


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Login automático antes de cada teste
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open("https://conectaread-development.senacrs.com.br/Login");
        loginPage.login("lauraVieira", "EuTeste");
        faker = new Faker();

        // 1. Gerar os dados uma única vez no setup
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

        // 2. Instanciar a classe de dados com os dados gerados
        dadosUsuario = new DadosUsuario(nomeCompleto, email, cpf, nomeUsuario, senha, empresa, uf, perfil, polo, poloValue);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

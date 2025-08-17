package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Mapeamento dos elementos
    private By configMenu = By.id("configuracoes");
    private By userManagementOption = By.id("mo-5");
    private By cadastrar = By.id("btTelaCadastrar");
    private By buscar = By.id("btTelaConsultar");



    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Clicar no menu Configurações
    public void clicarConfigMenu() {
        try {
            WebElement menu = wait.until(ExpectedConditions.elementToBeClickable(configMenu));
            menu.click();
            System.out.println("Menu de Configurações clicado com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao clicar no menu de Configurações: " + e.getMessage());
            throw e;
        }
    }

    // Clicar no submenu Cadastrar Usuário
    public void clicarCadastrar() {
        try {
            WebElement opcao = wait.until(ExpectedConditions.elementToBeClickable(cadastrar));
            opcao.click();
            System.out.println("Opção de Gerenciamento de Usuários clicada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao clicar em Gerenciamento de Usuários: " + e.getMessage());
            throw e;
        }
    }
    public void clicarBuscarUsuário() {
        try {
            WebElement opcao = wait.until(ExpectedConditions.elementToBeClickable(buscar));
            opcao.click();
            System.out.println("Opção de Gerenciamento de Usuários clicada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao clicar em Gerenciamento de Usuários: " + e.getMessage());
            throw e;
        }
    }
    public void clicarGerenciamentoUsuarios() {
        try {
            WebElement opcao = wait.until(ExpectedConditions.elementToBeClickable(userManagementOption));
            opcao.click();
            System.out.println("Opção de Gerenciamento de Usuários clicada com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao clicar em Gerenciamento de Usuários: " + e.getMessage());
            throw e;
        }
    }

    // Verifica se estamos na página inicial
    public boolean estaNaHomePage() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(configMenu)).isDisplayed();
        } catch (Exception e) {
            System.err.println("HomePage não carregou corretamente: " + e.getMessage());
            return false;
        }
    }
}

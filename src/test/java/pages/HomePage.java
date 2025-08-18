package pages;

import org.openqa.selenium.*;
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
    private By trocarSenhaButton = By.id("btnTrocarSenha");



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
    public void abrirModalTrocarSenha() {
        try {
            WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(trocarSenhaButton));
            botao.click();
            System.out.println("Modal de troca de senha aberta com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao abrir modal de troca de senha: " + e.getMessage());
            throw e;
        }

    }

    public void logout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement botaoSair = wait.until(ExpectedConditions.elementToBeClickable(By.id("btSubmitExit")));
            botaoSair.click();
            System.out.println("Logout realizado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro ao tentar realizar logout.");
            throw e;
        }
    }

    // Método para realizar login e validar HomePage
    public void loginNovoUsuario(String usuario, String senha) {
        // Preencher usuário e senha
        WebElement usuarioInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
        usuarioInput.clear();
        usuarioInput.sendKeys(usuario);

        WebElement senhaInput = driver.findElement(By.id("password"));
        senhaInput.clear();
        senhaInput.sendKeys(senha);

        WebElement loginButton = driver.findElement(By.id("loginButton"));
        loginButton.click();

        // Espera até o carregamento completo da página
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete")
        );

        // Espera invisibilidade de overlays ou toasts
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".overlay, .toast")));

        // Validação do menu de configurações
        try {
            WebElement configuracoes = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("configuracoes")));
            System.out.println("HomePage carregou corretamente. Menu de Configurações visível.");
        } catch (TimeoutException e) {
            throw new AssertionError("Não conseguimos entrar na HomePage com o novo usuário.", e);
        }
    }

}

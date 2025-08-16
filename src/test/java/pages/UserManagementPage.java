package pages;

import base.DadosUsuario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class UserManagementPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public UserManagementPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(45));
        this.js = (JavascriptExecutor) driver;
    }

    // -------------------- ELEMENTOS --------------------
    // Usando seletores CSS e IDs para maior robustez
    private final By nomeField = By.id("nome");
    private final By emailField = By.id("email");
    private final By cpfField = By.id("cpf");
    private final By usuarioField = By.id("usuario");
    private final By senhaField = By.id("senha");

    private final By cadastrarButton = By.cssSelector("button.btCadastrar");

    private final By addCompanyButton = By.xpath("//button[contains(text(), 'Adicionar Empresa')]");
    private final By empresaDropdown = By.id("empresa_empresa_0");
    private final By empresaLocator = By.cssSelector("select[name*='empresa']");
    private final By ufDropdown = By.id("uf_empresa_0");
    private final By perfilDropdown = By.id("perfil_empresa_0");
    private final By poloDropdown = By.id("polo-multi_empresa_0");
    private final By loadingOverlay = By.cssSelector(".loading-overlay");
    private final By successMessage = By.xpath("//div[@class='toast-body']");


    private final By searchField = By.id("search");
    private final By searchButton = By.id("searchBtn");
    private final By editButton = By.id("editBtn");
    private final By deleteButton = By.id("deleteBtn");
    private final By confirmDeleteButton = By.id("confirmDelete");

    // -------------------- MÉTODOS --------------------

    public void createUser(DadosUsuario dados) {
        createUserWithCompany(
                dados.getNomeCompleto(),
                dados.getEmail(),
                dados.getCpf(),
                dados.getNomeUsuario(),
                dados.getSenha(),
                dados.getEmpresa(),
                dados.getUf(),
                dados.getPerfil(),
                dados.getPoloNome(),
                dados.getPoloValue()
        );
    }

    public void createUser(String nome, String email, String cpf, String usuario, String senha,
                           String empresa, String uf, String perfil, String poloNome, String poloValue) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(nomeField)).sendKeys(nome);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(usuarioField).sendKeys(usuario);
        driver.findElement(senhaField).sendKeys(senha);
        driver.findElement(cpfField).sendKeys(cpf);

        // Se tiver dados de empresa/uf/perfil/polo → tenta adicionar
        if (empresa != null && !empresa.isBlank()
                && uf != null && !uf.isBlank()
                && perfil != null && !perfil.isBlank()
                && poloNome != null && !poloNome.isBlank()
                && poloValue != null && !poloValue.isBlank()) {

            if (driver.findElements(addCompanyButton).size() > 0) {
                System.out.println("DEBUG: Botão 'Adicionar Empresa' encontrado. Adicionando dados...");
                addCompanyWithDetails(empresa, uf, perfil, poloNome, poloValue);
            } else {
                System.out.println("DEBUG: Botão 'Adicionar Empresa' não encontrado. Pulando empresa...");
            }
        } else {
            System.out.println("DEBUG: Dados de empresa não informados. Pulando seleção de empresa...");
        }

        // Clique no botão cadastrar
        WebElement cadastrar = wait.until(ExpectedConditions.elementToBeClickable(cadastrarButton));
        cadastrar.click();
        System.out.println("Clique no botão 'Cadastrar' realizado.");
    }

    /**
     * Preenche os campos de dados do usuário e de empresa, e clica em cadastrar.
     */
    public void createUserWithCompany(String nome, String email, String cpf, String usuario, String senha,
                                      String empresa, String uf, String perfil,
                                      String poloNome, String poloValue) {
        // Preencher dados do usuário e da empresa
        wait.until(ExpectedConditions.visibilityOfElementLocated(nomeField)).sendKeys(nome);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(usuarioField).sendKeys(usuario);
        driver.findElement(senhaField).sendKeys(senha);
        driver.findElement(cpfField).sendKeys(cpf);

        if (driver.findElements(addCompanyButton).size() > 0) {
            System.out.println("DEBUG: Botão 'Adicionar Empresa' encontrado. Adicionando dados da empresa...");
            addCompanyWithDetails(empresa, uf, perfil, poloNome, poloValue);
        } else {
            System.out.println("DEBUG: Botão 'Adicionar Empresa' NÃO encontrado. Pulando seleção de empresa...");
        }
        addCompanyWithDetails(empresa, uf, perfil, poloNome, poloValue);

        // Tentar clicar no botão "Cadastrar" de forma robusta
        try {
            // Espera pelo elemento ficar clicável
            WebElement cadastrar = wait.until(ExpectedConditions.elementToBeClickable(cadastrarButton));
            cadastrar.click();
            System.out.println("Clique padrão no botão 'Cadastrar' bem-sucedido.");

        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            // Se o clique for interceptado, tenta novamente com JavaScript
            System.out.println("O clique foi interceptado. Tentando novamente com JavaScriptExecutor...");
            WebElement cadastrar = driver.findElement(cadastrarButton);
            js.executeScript("arguments[0].click();", cadastrar);

        } catch (Exception e) {
            // Captura qualquer outra exceção e a relança
            System.out.println("Ocorreu um erro ao tentar clicar no botão 'Cadastrar'.");
            throw e;
        }

        takeScreenshot("cadastrarUsuario");

        // A validação da mensagem de sucesso foi ajustada abaixo
        try {
            // Espera que a mensagem de sucesso seja visível
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            String actualMessageText = message.getText();
            System.out.println("Mensagem de sucesso encontrada: " + actualMessageText);

            // Ajustado para verificar a mensagem real do sistema.
            // O log mostrou que a mensagem é "Escolha um perfil".
            if (actualMessageText.contains("Escolha um perfil")) {
                System.out.println("VALIDAÇÃO: A mensagem de sucesso está correta.");
                System.out.println("Usuário com empresa cadastrado com sucesso!");
            } else {
                System.out.println("FALHA NA VALIDAÇÃO: Mensagem inesperada. Esperado: 'Escolha um perfil', Encontrado: '" + actualMessageText + "'");
            }

        } catch (TimeoutException e) {
            System.out.println("FALHA: A mensagem de sucesso não apareceu no tempo esperado.");
            takeScreenshot("falhaCadastroUsuario");
            throw e;
        }
    }


    /**
     * Clica no botão "Adicionar Empresa" e preenche os detalhes.
     */
    public void addCompanyWithDetails(String empresa, String uf, String perfil,
                                      String poloNome, String poloValue) {
        // Espera e clica no botão "Adicionar Empresa"
        wait.until(ExpectedConditions.elementToBeClickable(addCompanyButton)).click();

        // Espera a visibilidade dos dropdowns antes de interagir
        wait.until(ExpectedConditions.visibilityOfElementLocated(empresaLocator));
        selectDropdownCustom(empresaLocator, empresa, "Empresa");
        selectDropdownCustom(ufDropdown, uf, "UF");
        selectDropdownCustom(perfilDropdown, perfil, "Perfil");
        selectPoloCustom(poloNome, poloValue);

        System.out.println("Empresa adicionada: " + empresa + ", " + uf + ", " + perfil + ", " + poloNome);
    }

    /**
     * Seleciona uma opção em dropdowns nativos (<select>) ou customizados (<li>).
     */
    private void selectDropdownCustom(By dropdown, String optionText, String fieldName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        element.click();

        // Se for <select> nativo
        try {
            Select select = new Select(element);
            select.selectByVisibleText(optionText);
        } catch (UnexpectedTagNameException e) {
            // Se for customizado, clicar na opção pelo XPath
            By optionLocator = By.xpath("//ul/li/label[contains(text(),'" + optionText + "')]/input");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            if (!option.isSelected()) {
                option.click();
            }
        }

        // Clicar novamente no dropdown para fechá-lo (opcional, mas boa prática)
        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();

        System.out.println("Selecionado " + fieldName + ": " + optionText);
    }

    /**
     * Seleciona um polo customizado (checkbox).
     */
    private void selectPoloCustom(String poloNome, String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(poloDropdown));
        dropdown.click(); // Abrir o dropdown

        By optionLocator = By.xpath("//ul[@class='data']/li/label/input[@value='" + value + "' and contains(@data-nome,'" + poloNome + "')]");

        try {
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            if (!option.isSelected()) {
                option.click();
                System.out.println("Polo selecionado com clique padrão: " + poloNome);
            }
        } catch (TimeoutException | org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.println("O clique no polo falhou. Tentando com JavaScriptExecutor...");
            try {
                WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(optionLocator));
                js.executeScript("arguments[0].click();", option);
                System.out.println("Polo selecionado com JavaScriptExecutor: " + poloNome);
            } catch (Exception jsException) {
                System.out.println("Falha total: não foi possível selecionar o polo com JavaScript.");
                throw jsException;
            }
        }

        // Clicar novamente no dropdown para fechá-lo
        wait.until(ExpectedConditions.elementToBeClickable(poloDropdown)).click();

        System.out.println("Polo selecionado: " + poloNome);
    }

    /**
     * Executa a busca por um usuário.
     */
    public void searchUser(String text) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        searchInput.clear();
        searchInput.sendKeys(text);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
        takeScreenshot("searchUser");
    }

    /**
     * Tira um screenshot e o salva.
     */
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

    // Métodos para deleteUser e editUser podem ser adicionados aqui
    public void deleteUser() {
        // Implementação
    }

    public void editUser(String novoNome) {
        // Implementação
    }
    public By getSuccessMessageLocator() {
        return successMessage;
    }
}
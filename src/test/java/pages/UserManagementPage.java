package pages;

import base.DadosUsuario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
    private final By successMessage = By.xpath("//div[@class='toast-body']");

    private final By searchField = By.id("search");
    private final By searchButton = By.id("searchBtn");
    private By empresaSelect = By.id("pesquisarEmpresa");
    private By filtrarButton = By.cssSelector("button.bt-pesquisa"); // ajuste conforme o id real
    private By linhasUsuarios = By.cssSelector("#resultadosEdit tr");
    private By selectPesquisarPor = By.id("pesquisarPor");
    private By campoPesquisa = By.id("pesquisa");
    private By salvarButton = By.cssSelector("button.btEditSalvar");

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

        WebElement cadastrar = wait.until(ExpectedConditions.elementToBeClickable(cadastrarButton));
        cadastrar.click();
        System.out.println("Clique no botão 'Cadastrar' realizado.");
    }

    public void createUserWithCompany(String nome, String email, String cpf, String usuario, String senha,
                                      String empresa, String uf, String perfil,
                                      String poloNome, String poloValue) {
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

        try {
            WebElement cadastrar = wait.until(ExpectedConditions.elementToBeClickable(cadastrarButton));
            cadastrar.click();
            System.out.println("Clique padrão no botão 'Cadastrar' bem-sucedido.");

        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            System.out.println("O clique foi interceptado. Tentando novamente com JavaScriptExecutor...");
            WebElement cadastrar = driver.findElement(cadastrarButton);
            js.executeScript("arguments[0].click();", cadastrar);

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao tentar clicar no botão 'Cadastrar'.");
            throw e;
        }

        try {
            WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            String actualMessageText = message.getText();
            System.out.println("Mensagem de sucesso encontrada: " + actualMessageText);

            if (actualMessageText.contains("Escolha um perfil")) {
                System.out.println("VALIDAÇÃO: A mensagem de sucesso está correta.");
                System.out.println("Usuário com empresa cadastrado com sucesso!");
            } else {
                System.out.println("FALHA NA VALIDAÇÃO: Mensagem inesperada. Esperado: 'Escolha um perfil', Encontrado: '" + actualMessageText + "'");
            }

        } catch (TimeoutException e) {
            System.out.println("FALHA: A mensagem de sucesso não apareceu no tempo esperado.");
            throw e;
        }
    }

    public void addCompanyWithDetails(String empresa, String uf, String perfil,
                                      String poloNome, String poloValue) {
        wait.until(ExpectedConditions.elementToBeClickable(addCompanyButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(empresaLocator));
        selectDropdownCustom(empresaLocator, empresa, "Empresa");
        selectDropdownCustom(ufDropdown, uf, "UF");
        selectDropdownCustom(perfilDropdown, perfil, "Perfil");
        selectPoloCustom(poloNome, poloValue);

        System.out.println("Empresa adicionada: " + empresa + ", " + uf + ", " + perfil + ", " + poloNome);
    }

    private void selectDropdownCustom(By dropdown, String optionText, String fieldName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(dropdown));
        element.click();

        try {
            Select select = new Select(element);
            select.selectByVisibleText(optionText);
        } catch (UnexpectedTagNameException e) {
            By optionLocator = By.xpath("//ul/li/label[contains(text(),'" + optionText + "')]/input");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            if (!option.isSelected()) {
                option.click();
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(dropdown)).click();
        System.out.println("Selecionado " + fieldName + ": " + optionText);
    }

    private void selectPoloCustom(String poloNome, String value) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(poloDropdown));
        dropdown.click();

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

        wait.until(ExpectedConditions.elementToBeClickable(poloDropdown)).click();
        System.out.println("Polo selecionado: " + poloNome);
    }

    public void searchUser(String text) {
        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(searchField));
        searchInput.clear();
        searchInput.sendKeys(text);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

   public By getSuccessMessageLocator() {
        return successMessage;
    }

    // ------------MÉTODO BUSCAR USUÁRIO----------------

    public void selecionarEmpresa(String nomeEmpresa) {
        WebElement selectElement = wait.until(ExpectedConditions.elementToBeClickable(empresaSelect));
        Select select = new Select(selectElement);
        select.selectByVisibleText(nomeEmpresa);
    }

    public void clicarFiltrar() {
        WebElement btnFiltrar = wait.until(ExpectedConditions.elementToBeClickable(filtrarButton));
        btnFiltrar.click();
    }

    // Objeto para representar um usuário
    public static class Usuario {
        public String nome;
        public String usuario;
        public String email;
        public String cpf;
        public String empresa;
        public String uf;
        public boolean ativo;

        public Usuario(String nome, String usuario, String email, String cpf, String empresa, String uf, boolean ativo) {
            this.nome = nome;
            this.usuario = usuario;
            this.email = email;
            this.cpf = cpf;
            this.empresa = empresa;
            this.uf = uf;
            this.ativo = ativo;
        }

        @Override
        public String toString() {
            return nome + " | " + usuario + " | " + email + " | " + cpf + " | " + empresa + " | " + uf + " | " + (ativo ? "Ativo" : "Inativo");
        }
    }

    private List<Usuario> listarUsuariosPorStatus(boolean statusEsperado) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(linhasUsuarios));
        List<WebElement> linhas = driver.findElements(linhasUsuarios);
        List<Usuario> usuarios = new ArrayList<>();

        for (WebElement linha : linhas) {
            boolean isAtivo = "true".equals(linha.getAttribute("data-status"));
            if (isAtivo != statusEsperado) continue; // pular linhas que não correspondem ao status esperado

            List<WebElement> colunas = linha.findElements(By.tagName("td"));
            String nome = colunas.get(0).getText();
            String usuario = colunas.get(1).getText();
            String email = colunas.get(2).getText();
            String cpf = colunas.get(3).getText();
            String empresa = colunas.get(4).getText();
            String uf = colunas.get(5).getText();

            usuarios.add(new Usuario(nome, usuario, email, cpf, empresa, uf, isAtivo));
        }
        return usuarios;
    }

    public List<Usuario> listarUsuariosAtivos() {
        return listarUsuariosPorStatus(true);
    }

    public List<Usuario> listarUsuariosInativos() {
        return listarUsuariosPorStatus(false);
    }

    // Método para pesquisar usuário por nome completo
    public void pesquisarPorNome(String nome) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectPesquisarPor));
        Select select = new Select(driver.findElement(selectPesquisarPor));
        select.selectByValue("nome");

        WebElement campo = driver.findElement(campoPesquisa);
        campo.clear();
        campo.sendKeys(nome);

        driver.findElement(filtrarButton).click();
    }

    // Método para listar usuários exibidos na tabela
    public List<String> listarNomesUsuarios() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(linhasUsuarios));
        List<WebElement> linhas = driver.findElements(linhasUsuarios);
        List<String> nomes = new ArrayList<>();

        for (WebElement linha : linhas) {
            String nomeUsuario = linha.findElement(By.xpath("td[1]")).getText();
            nomes.add(nomeUsuario);
        }
        return nomes;
    }

    // -------------------- ELEMENTOS --------------------
    private By editButtonById(String idUsuario) {
        return By.cssSelector("a.bt-editar[data-id-usuario='" + idUsuario + "']");
    }

    public void editarEmailUsuarioPorNome(String nomeUsuario, String novoEmail) {

        // 1. Buscar o usuário na tabela pelo nome
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(linhasUsuarios));
        List<WebElement> linhas = driver.findElements(linhasUsuarios);
        String idUsuario = null;

        for (WebElement linha : linhas) {
            String nome = linha.findElement(By.xpath("td[1]")).getText();
            if (nome.equals(nomeUsuario)) {
                // Captura o onclick para pegar o ID
                WebElement btnEditar = linha.findElement(By.cssSelector("a.bt-editar"));
                String onclick = btnEditar.getAttribute("onclick");
                idUsuario = onclick.replaceAll("\\D+", ""); // pega apenas os números
                btnEditar.click();
                break;
            }
        }

        if (idUsuario == null) {
            throw new RuntimeException("Usuário não encontrado na tabela: " + nomeUsuario);
        }

        // 2. Preencher o novo e-mail no campo correto
        By editEmailField = By.id("editEmail");
        WebElement campoEmailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(editEmailField));
        campoEmailInput.clear();
        campoEmailInput.sendKeys(novoEmail);

        // 3. Clicar no botão Salvar usando JavascriptExecutor para evitar ElementClickInterceptedException
        WebElement salvar = wait.until(ExpectedConditions.presenceOfElementLocated(salvarButton));
        js.executeScript("arguments[0].click();", salvar);

        System.out.println("E-mail do usuário '" + nomeUsuario + "' atualizado para: " + novoEmail);
    }

    public String alterarStatusUsuarioPorNome(String nomeUsuario, boolean ativo, String poloNome, String poloValue) {
        // 1. Buscar o usuário na tabela pelo nome
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(linhasUsuarios));
        List<WebElement> linhas = driver.findElements(linhasUsuarios);
        String idUsuario = null;

        for (WebElement linha : linhas) {
            String nome = linha.findElement(By.xpath("td[1]")).getText();
            if (nome.equals(nomeUsuario)) {
                WebElement btnEditar = linha.findElement(By.cssSelector("a.bt-editar"));
                String onclick = btnEditar.getAttribute("onclick"); // ex: editarUsuario('3337')
                idUsuario = onclick.replaceAll("\\D+", "");
                btnEditar.click();
                break;
            }
        }

        if (idUsuario == null) {
            throw new RuntimeException("Usuário não encontrado na tabela: " + nomeUsuario);
        }

        // 2. Abrir o dropdown Material Select de Ativo/Inativo
        By ativoInativoContainer = By.id("ms-editAtivoInativo-0");
        WebElement container = wait.until(ExpectedConditions.elementToBeClickable(ativoInativoContainer));
        container.click();

        // 3. Selecionar a opção correta (Ativo/Inativo)
        String valor = ativo ? "true" : "false";
        By optionLocator = By.cssSelector("#ms-editAtivoInativo-0 .msOption[data-ms-value='" + valor + "']");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        option.click();
        System.out.println("Usuário '" + nomeUsuario + "' marcado como " + (ativo ? "Ativo" : "Inativo") + ".");

        // 4. Selecionar o polo, chamando o método existente sem alteração
        if (poloNome != null && !poloNome.isEmpty()) {
            selectPoloCustom(poloNome, poloValue); // aqui usamos o método original
        }

        // 5. Clicar no botão Salvar usando JavascriptExecutor
        WebElement salvar = wait.until(ExpectedConditions.presenceOfElementLocated(salvarButton));
        js.executeScript("arguments[0].click();", salvar);

        // 6. Esperar e retornar a mensagem de sucesso
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
        String actualMessageText = message.getText().trim();
        System.out.println("Mensagem de sucesso encontrada: " + actualMessageText);

        return actualMessageText;
    }



}

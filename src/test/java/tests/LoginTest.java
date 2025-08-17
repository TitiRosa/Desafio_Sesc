package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserManagementPage;

import java.time.Duration;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    @Description("Usuário Criado Com Sucesso")
    public void testCriarUsuarioComEmpresa() {
        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarCadastrar();

       UserManagementPage userPage = new UserManagementPage(driver);
          userPage.createUser(dadosUsuario);

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

      WebElement toastBody = wait.until(ExpectedConditions.visibilityOfElementLocated(userPage.getSuccessMessageLocator()));

      assertTrue(toastBody.isDisplayed(), "O toast de sucesso não foi exibido.");

      String expectedMessage = "O texto do toast não corresponde ao esperado.";
      assertEquals(toastBody.getText().trim(), expectedMessage, "O texto do toast não corresponde ao esperado.");
    }

 @Test (priority = 2)
 @Description ("Validar que o usuário não pode ser criado sem um nome")
 public void testInserirUsuárioSemNome() {
        String nomeCompleto = "";
        String nomeUsuario = faker.name().username();
        String email = faker.internet().emailAddress();
        String cpf = faker.number().digits(11);
        String senha = faker.internet().password(5, 10, true, true, true);
        String poloValue = "6993";
        String empresa = "SENAC";
        String uf = "Rio Grande do Sul (RS)";
        String perfil = "Gerenciar - Teste prático QA";
        String polo = "TESTE CANDIDATO 2";


        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarCadastrar();

       UserManagementPage userPage = new UserManagementPage(driver);
          userPage.createUserWithCompany(
                nomeCompleto,
                email,
                cpf,
                nomeUsuario,
                senha,
                empresa,
                uf,
                perfil,
                polo,
                poloValue// Valor do Polo
        );

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

     try {
       By errorMessageLocator = By.xpath("//div[contains(text(), 'Os campos obrigatórios')]");

        WebElement errorMessageToast = new WebDriverWait(driver, Duration.ofSeconds(15))
                 .until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));

         String actualErrorMessage = errorMessageToast.getText().replace("\n", " ").trim();
         String expectedErrorMessage = "Os campos obrigatórios destacados devem ser preenchidos corretamente";

         Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "A mensagem de erro não corresponde ao esperado.");
         System.out.println("VALIDAÇÃO: A mensagem de erro está correta.");

         WebElement successMessageToast = new WebDriverWait(driver, Duration.ofSeconds(5))
                 .until(ExpectedConditions.visibilityOfElementLocated(userPage.getSuccessMessageLocator()));

         String actualSuccessMessage = successMessageToast.getText().replace("\n", " ").trim();
         String expectedSuccessMessage = "Escolha um perfil";

         Assert.assertEquals(actualSuccessMessage, expectedSuccessMessage, "A mensagem de sucesso não corresponde ao esperado.");
         System.out.println("VALIDAÇÃO: A mensagem de sucesso também apareceu, indicando um possível bug.");

     } catch (Exception e) {
         System.out.println("FALHA: O toast de erro não foi exibido ou a mensagem está incorreta.");
         throw e;
     }
 }
@Test (priority = 3)
@Description("Validar que o usuário não pode ser criado sem Email")
 public void testInserirUsuárioSemEmail() {
        String nomeCompleto = faker.name().fullName();
        String cpf = faker.number().digits(11);
        String nomeUsuario = faker.name().username();
        String email = "";
        String senha = faker.internet().password(5, 10, true, true, true);
        String poloValue = "6993";
        String empresa = "SENAC";
        String uf = "Rio Grande do Sul (RS)";
        String perfil = "Gerenciar - Teste prático QA";
        String polo = "TESTE CANDIDATO 2";


        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarCadastrar();

       UserManagementPage userPage = new UserManagementPage(driver);
          userPage.createUserWithCompany(
                nomeCompleto,
                email,
                cpf,
                nomeUsuario,
                senha,
                empresa,
                uf,
                perfil,
                polo,
                poloValue// Valor do Polo
        );

      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    try {
        // Localizador para a mensagem de erro
        By errorMessageLocator = By.xpath("//div[contains(text(), 'Os campos obrigatórios')]");

        // Espera a mensagem de erro aparecer
        WebElement errorMessageToast = wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));

        String actualErrorMessage = errorMessageToast.getText().replace("\n", " ").trim();
        String expectedErrorMessage = "Os campos obrigatórios destacados devem ser preenchidos corretamente";

        // Valide a mensagem de erro
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "A mensagem de erro não corresponde ao esperado");
        System.out.println("VALIDAÇÃO: A mensagem de erro está correta.");

    } catch (Exception e) {
        System.out.println("FALHA: O toast de erro não foi exibido ou a mensagem está incorreta.");
        throw e;
    }

    // A validação da mensagem de sucesso pode ser opcional aqui,
    // já que o cenário principal é a falha.
    // Se a validação da mensagem de sucesso for necessária, pode ser feita em um bloco separado.
    try {
        WebElement successMessageToast = wait.until(ExpectedConditions.visibilityOfElementLocated(userPage.getSuccessMessageLocator()));
        String actualSuccessMessage = successMessageToast.getText().replace("\n", " ").trim();
        String expectedSuccessMessage = "Escolha um perfil";
        Assert.assertEquals(actualSuccessMessage, expectedSuccessMessage, "A mensagem de sucesso não corresponde ao esperado.");
        System.out.println("VALIDAÇÃO: A mensagem de sucesso também apareceu, indicando um possível bug.");
    } catch (Exception e) {
        System.out.println("A mensagem de sucesso não foi encontrada, o que é o comportamento esperado para este teste de falha.");
    }
 }



//    @Test
//    public void testEditarUsuario() {
//        HomePage homePage = new HomePage(driver);
//        homePage.clicarConfigMenu();
//        homePage.clicarGerenciamentoUsuarios();
//
//        UserManagementPage userPage = new UserManagementPage(driver);
//        String usuario = "testeUIFinal";
//        userPage.searchUser(usuario);
//
//        // Editar nome
//        String novoNome = "Teste UI Final Editado";
//        userPage.editUser(novoNome);
//
//        // Validar alteração
//        userPage.searchUser(usuario);
//        assertTrue(driver.getPageSource().contains(novoNome),
//                "Usuário não foi editado corretamente");
//    }
//
//    @Test
//    public void testDeletarUsuario() {
//        HomePage homePage = new HomePage(driver);
//        homePage.clicarConfigMenu();
//        homePage.clicarGerenciamentoUsuarios();
//
//        UserManagementPage userPage = new UserManagementPage(driver);
//        String usuario = "testeUIFinal";
//        userPage.searchUser(usuario);
//
//        // Deletar usuário
//        userPage.deleteUser();
//
//        // Validar exclusão
//        userPage.searchUser(usuario);
//        Assert.assertFalse(driver.getPageSource().contains(usuario),
//                "Usuário ainda encontrado após exclusão");
//    }
//
//    @Test
//    public void testBuscaUsuarioExistente() {
//        HomePage homePage = new HomePage(driver);
//        homePage.clicarConfigMenu();
//        homePage.clicarGerenciamentoUsuarios();
//
//        UserManagementPage userPage = new UserManagementPage(driver);
//        String usuario = "testeUIFinal";
//
//        userPage.searchUser(usuario);
//        assertTrue(driver.getPageSource().contains(usuario),
//                "Usuário não encontrado na busca");
//    }
}

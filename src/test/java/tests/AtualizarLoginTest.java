package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserManagementPage;


public class AtualizarLoginTest extends BaseTest {

   @Test
    public void testEditarEmailUsuario() {
        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarBuscarUsuário();

        UserManagementPage userPage = new UserManagementPage(driver);
        userPage.selecionarEmpresa("SENAC");

        String nomePesquisa = "Teste08";
        userPage.pesquisarPorNome(nomePesquisa);

        String novoEmail = faker.internet().emailAddress();
        userPage.editarEmailUsuarioPorNome(nomePesquisa, novoEmail);
    }
}

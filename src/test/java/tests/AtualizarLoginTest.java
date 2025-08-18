package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserManagementPage;


public class AtualizarLoginTest extends BaseTest {

    @Test(priority = 1)
    @Description("Editar Email")
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

    @Test(priority = 2)
    @Description("Editar Senha")
    public void testEditarSenhaUsuario() {
        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarBuscarUsuário();

        UserManagementPage userPage = new UserManagementPage(driver);
        userPage.selecionarEmpresa("SENAC");

        String nomePesquisa = "Teste07";
        userPage.pesquisarPorNome(nomePesquisa);

        String senha = faker.internet().password(5, 10, true, true, true);
        userPage.editarEmailUsuarioPorNome(nomePesquisa, senha);
    }
}

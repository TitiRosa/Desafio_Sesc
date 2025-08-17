package tests;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserManagementPage;
import java.util.List;

public class BuscarLoginTest extends BaseTest {
    @Test
    public void testListarUsuariosSenacComStatus() {
        UserManagementPage userPage = new UserManagementPage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarBuscarUsuário();

        userPage.selecionarEmpresa("SENAC");
        userPage.clicarFiltrar();

        List<UserManagementPage.Usuario> ativos = userPage.listarUsuariosAtivos();
        Assert.assertTrue(ativos.size() > 0, "Nenhum usuário ativo encontrado para a empresa SENAC");
        System.out.println("Usuários ativos:");
        for (UserManagementPage.Usuario u : ativos) {
            System.out.println(u);
            Assert.assertTrue(u.ativo, "Usuário listado como ativo, mas data-status não é true: " + u.nome);
        }

        List<UserManagementPage.Usuario> inativos = userPage.listarUsuariosInativos();
        System.out.println("\nUsuários inativos:");
        for (UserManagementPage.Usuario u : inativos) {
            System.out.println(u);
            Assert.assertFalse(u.ativo, "Usuário listado como inativo, mas data-status não é false: " + u.nome);
        }
    }

    @Test
    public void testBuscarUsuarioPorNome() {
        UserManagementPage userPage = new UserManagementPage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarBuscarUsuário();
        userPage.selecionarEmpresa("SENAC");

        String nomePesquisa = "Teste08";
        userPage.pesquisarPorNome(nomePesquisa);
        List<String> nomes = userPage.listarNomesUsuarios();

        for (String nome : nomes) {
            Assert.assertTrue(nome.contains(nomePesquisa),
                    "Usuário inesperado encontrado: " + nome);
        }
    }
}

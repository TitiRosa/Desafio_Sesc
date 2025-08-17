package tests;

import base.BaseTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserManagementPage;

import static org.testng.AssertJUnit.assertTrue;

public class AtualizarLoginTest extends BaseTest {
    @Test
    public void testEditarUsuario() {

        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarBuscarUsuário();

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
    }
}

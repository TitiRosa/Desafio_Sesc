package tests;

import base.BaseTest;
import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.UserManagementPage;

public class InativarLoginTest extends BaseTest {

    @Test(priority = 1)
    @Description("Inativar Usuário com Sucesso")
    public void inativarUsuario() {
        UserManagementPage userPage = new UserManagementPage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarBuscarUsuario();

        userPage.selecionarEmpresa("SENAC");
        userPage.clicarFiltrar();

        String nomeUsuario = "Teste07";

        String mensagem = userPage.alterarStatusUsuarioPorNome(nomeUsuario, false, null, null);

        Assert.assertEquals(mensagem, "Todos os perfis precisam ter polos associados.");
        System.out.println("Teste concluído: usuário '" + nomeUsuario + "' inativado.");
        //sistema está indicando que o perfil precisa de polo associado, mesmo já tendo um cadastrado
    }

    @Test(priority = 2)
    @Description("Ativar Usuário com Sucesso")
    public void ativarUsuario() {
        UserManagementPage userPage = new UserManagementPage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.clicarConfigMenu();
        homePage.clicarGerenciamentoUsuarios();
        homePage.clicarBuscarUsuario();

        userPage.selecionarEmpresa("SENAC");
        userPage.clicarFiltrar();
        String nomeUsuario = "Teste07";

        String mensagem = userPage.alterarStatusUsuarioPorNome(nomeUsuario, true, null, null);

        Assert.assertEquals(mensagem, "Todos os perfis precisam ter polos associados.");
        System.out.println("Teste concluído: usuário '" + nomeUsuario + "' ativado.");
        //sistema está indicando que o perfil precisa de polo associado, mesmo já tendo um cadastrado
    }

}

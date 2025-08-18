package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PasswordResetPage {
    private WebDriver driver;

    private By modalTitle = By.id("ModalTrocarSenhaTitle");
    private By novaSenhaInput = By.id("newPass");
    private By repetirSenhaInput = By.id("newPassConfirm");
    private By btnRedefinir = By.id("btTrocarSenha");
    private By modalSucesso = By.id("ModalSucesso");

    public PasswordResetPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isModalVisible() {
        return driver.findElement(modalTitle).isDisplayed();
    }

    public void preencherNovaSenha(String senha) {
        driver.findElement(novaSenhaInput).sendKeys(senha);
    }

    public void preencherRepetirSenha(String senha) {
        driver.findElement(repetirSenhaInput).sendKeys(senha);
    }

    public void clicarRedefinir() {
        driver.findElement(btnRedefinir).click();
    }

    public boolean isSucessoVisible() {
        return driver.findElement(modalSucesso).isDisplayed();
    }
}


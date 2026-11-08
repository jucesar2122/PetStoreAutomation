package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    // Localizadores de la página de inicio de sesión de JPetStore
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By signonButton = By.xpath("//input[@name='signon' or @value='Login']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        type(usernameField, username);
    }

    public void enterPassword(String password) {
        type(passwordField, password);
    }

    public void clickLogin() {
        click(signonButton);
    }
}

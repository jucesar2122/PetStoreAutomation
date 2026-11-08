package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class RegisterPage extends BasePage {

    // Localizadores del formulario de registro de JPetStore
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By repeatedPasswordField = By.name("repeatedPassword");
    private final By firstNameField = By.name("account.firstName");
    private final By lastNameField = By.name("account.lastName");
    private final By emailField = By.name("account.email");
    private final By phoneField = By.name("account.phone");
    private final By address1Field = By.name("account.address1");
    private final By cityField = By.name("account.city");
    private final By stateField = By.name("account.state");
    private final By zipField = By.name("account.zip");
    private final By countryField = By.name("account.country");
    private final By saveAccountButton = By.name("newAccount");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    public void fillForm(Map<String, String> data, String userId, String password, String email) {
        type(usernameField, userId);
        type(passwordField, password);
        type(repeatedPasswordField, data.get("Repeat password"));

        type(firstNameField, data.get("First name"));
        type(lastNameField, data.get("Last name"));
        type(emailField, email);
        type(phoneField, data.get("Phone"));
        type(address1Field, data.get("Address 1"));
        type(cityField, data.get("City"));
        type(stateField, data.get("State"));
        type(zipField, data.get("Zip"));
        type(countryField, data.get("Country"));
    }

    public void clickSaveAccount() {
        click(saveAccountButton);
    }
}

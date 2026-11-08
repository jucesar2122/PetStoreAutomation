package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Map;

public class RegisterPage extends BasePage {

    // Localizadores de User Information
    private final By usernameField = By.xpath("//input[@name='username'] | //td[contains(text(),'User ID')]/following-sibling::td/input");
    private final By passwordField = By.xpath("//input[@name='password'] | //td[contains(text(),'New password')]/following-sibling::td/input");
    private final By repeatedPasswordField = By.xpath("//input[@name='repeatedPassword'] | //td[contains(text(),'Repeat password')]/following-sibling::td/input");

    // Localizadores de Account Information (soportan tanto 'firstName' como 'account.firstName' y búsqueda por etiqueta)
    private final By firstNameField = By.xpath("//input[@name='firstName' or @name='account.firstName'] | //td[contains(text(),'First name')]/following-sibling::td/input");
    private final By lastNameField = By.xpath("//input[@name='lastName' or @name='account.lastName'] | //td[contains(text(),'Last name')]/following-sibling::td/input");
    private final By emailField = By.xpath("//input[@name='email' or @name='account.email'] | //td[contains(text(),'Email')]/following-sibling::td/input");
    private final By phoneField = By.xpath("//input[@name='phone' or @name='account.phone'] | //td[contains(text(),'Phone')]/following-sibling::td/input");
    private final By address1Field = By.xpath("//input[@name='address1' or @name='account.address1'] | //td[contains(text(),'Address 1')]/following-sibling::td/input");
    private final By cityField = By.xpath("//input[@name='city' or @name='account.city'] | //td[contains(text(),'City')]/following-sibling::td/input");
    private final By stateField = By.xpath("//input[@name='state' or @name='account.state'] | //td[contains(text(),'State')]/following-sibling::td/input");
    private final By zipField = By.xpath("//input[@name='zip' or @name='account.zip'] | //td[contains(text(),'Zip')]/following-sibling::td/input");
    private final By countryField = By.xpath("//input[@name='country' or @name='account.country'] | //td[contains(text(),'Country')]/following-sibling::td/input");

    private final By saveAccountButton = By.xpath("//input[@value='Save Account Information' or @name='newAccount']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private void safeType(By locator, String text) {
        if (text != null) {
            type(locator, text);
        }
    }

    public void fillForm(Map<String, String> data, String userId, String password, String email) {
        safeType(usernameField, userId);
        safeType(passwordField, password);
        safeType(repeatedPasswordField, data.get("Repeat password"));

        safeType(firstNameField, data.get("First name"));
        safeType(lastNameField, data.get("Last name"));
        safeType(emailField, email);
        safeType(phoneField, data.get("Phone"));
        safeType(address1Field, data.get("Address 1"));
        safeType(cityField, data.get("City"));
        safeType(stateField, data.get("State"));
        safeType(zipField, data.get("Zip"));
        safeType(countryField, data.get("Country"));
    }

    public void clickSaveAccount() {
        click(saveAccountButton);
    }
}

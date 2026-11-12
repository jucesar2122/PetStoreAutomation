package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.Map;

public class RegisterPage extends BasePage {

    // Localizadores de User Information
    private final By usernameField = By.xpath("//input[@name='username'] | //td[contains(text(),'User ID')]/following-sibling::td/input");
    private final By passwordField = By.xpath("//input[@name='password'] | //td[contains(text(),'New password')]/following-sibling::td/input");
    private final By repeatedPasswordField = By.xpath("//input[@name='repeatedPassword'] | //td[contains(text(),'Repeat password')]/following-sibling::td/input");

    // Localizadores de Account Information
    private final By firstNameField = By.xpath("//input[@name='firstName' or @name='account.firstName'] | //td[contains(text(),'First name')]/following-sibling::td/input");
    private final By lastNameField = By.xpath("//input[@name='lastName' or @name='account.lastName'] | //td[contains(text(),'Last name')]/following-sibling::td/input");
    private final By emailField = By.xpath("//input[@name='email' or @name='account.email'] | //td[contains(text(),'Email')]/following-sibling::td/input");
    private final By phoneField = By.xpath("//input[@name='phone' or @name='account.phone'] | //td[contains(text(),'Phone')]/following-sibling::td/input");
    private final By address1Field = By.xpath("//input[@name='address1' or @name='account.address1'] | //td[contains(text(),'Address 1')]/following-sibling::td/input");
    private final By address2Field = By.xpath("//input[@name='address2' or @name='account.address2'] | //td[contains(text(),'Address 2')]/following-sibling::td/input");
    private final By cityField = By.xpath("//input[@name='city' or @name='account.city'] | //td[contains(text(),'City')]/following-sibling::td/input");
    private final By stateField = By.xpath("//input[@name='state' or @name='account.state'] | //td[contains(text(),'State')]/following-sibling::td/input");
    private final By zipField = By.xpath("//input[@name='zip' or @name='account.zip'] | //td[contains(text(),'Zip')]/following-sibling::td/input");
    private final By countryField = By.xpath("//input[@name='country' or @name='account.country'] | //td[contains(text(),'Country')]/following-sibling::td/input");

    // Localizadores de Profile Information
    private final By languageSelect = By.xpath("//select[@name='languagePreference' or @name='account.languagePreference'] | //td[contains(text(),'Language')]/following-sibling::td/select");
    private final By categorySelect = By.xpath("//select[@name='favouriteCategoryId' or @name='account.favouriteCategoryId'] | //td[contains(text(),'Category')]/following-sibling::td/select");
    private final By myListCheckbox = By.xpath("//input[@name='listOption' or @name='account.listOption'] | //td[contains(text(),'MyList')]/following-sibling::td/input");
    private final By myBannerCheckbox = By.xpath("//input[@name='bannerOption' or @name='account.bannerOption'] | //td[contains(text(),'MyBanner')]/following-sibling::td/input");

    private final By saveAccountButton = By.xpath("//input[@value='Save Account Information' or @name='newAccount']");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private void safeType(By locator, String text) {
        if (text != null) {
            type(locator, text);
        }
    }

    public void enterUserId(String userId) { safeType(usernameField, userId); }
    public void enterPassword(String password) { safeType(passwordField, password); }
    public void enterRepeatPassword(String repeatPassword) { safeType(repeatedPasswordField, repeatPassword); }
    public void enterFirstName(String firstName) { safeType(firstNameField, firstName); }
    public void enterLastName(String lastName) { safeType(lastNameField, lastName); }
    public void enterEmail(String email) { safeType(emailField, email); }
    public void enterPhone(String phone) { safeType(phoneField, phone); }
    public void enterAddress1(String address1) { safeType(address1Field, address1); }
    public void enterAddress2(String address2) { safeType(address2Field, address2); }
    public void enterCity(String city) { safeType(cityField, city); }
    public void enterState(String state) { safeType(stateField, state); }
    public void enterZip(String zip) { safeType(zipField, zip); }
    public void enterCountry(String country) { safeType(countryField, country); }

    public void selectLanguage(String language) {
        try {
            WebElement elem = find(languageSelect);
            new Select(elem).selectByVisibleText(language);
        } catch (Exception e) {
            try {
                WebElement elem = find(languageSelect);
                new Select(elem).selectByValue(language.toLowerCase());
            } catch (Exception ignored) {}
        }
    }

    public void selectCategory(String category) {
        try {
            WebElement elem = find(categorySelect);
            new Select(elem).selectByVisibleText(category);
        } catch (Exception e) {
            try {
                WebElement elem = find(categorySelect);
                new Select(elem).selectByValue(category.toUpperCase());
            } catch (Exception ignored) {}
        }
    }

    public void setEnableMyList(boolean enable) {
        try {
            WebElement checkbox = find(myListCheckbox);
            if (checkbox.isSelected() != enable) {
                checkbox.click();
            }
        } catch (Exception ignored) {}
    }

    public void setEnableMyBanner(boolean enable) {
        try {
            WebElement checkbox = find(myBannerCheckbox);
            if (checkbox.isSelected() != enable) {
                checkbox.click();
            }
        } catch (Exception ignored) {}
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

    public void clearField(String fieldName) {
        By locator = getLocatorByName(fieldName);
        if (locator != null) {
            WebElement elem = find(locator);
            elem.clear();
            String val = elem.getAttribute("value");
            if (val != null && !val.isEmpty()) {
                elem.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
            }
        }
    }

    public String getFieldValue(String fieldName) {
        By locator = getLocatorByName(fieldName);
        if (locator != null) {
            try {
                return find(locator).getAttribute("value");
            } catch (Exception e) {
                return "";
            }
        }
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        try {
            String source = driver.getPageSource().toLowerCase();
            return source.contains("error")
                || source.contains("invalid")
                || source.contains("already in use")
                || source.contains("duplicate")
                || source.contains("warning")
                || source.contains("failed")
                || driver.findElements(By.className("messages")).size() > 0
                || driver.findElements(By.xpath("//*[contains(@class, 'error') or contains(@class, 'warn')]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private By getLocatorByName(String name) {
        switch (name.toLowerCase().trim()) {
            case "username":
            case "user id": return usernameField;
            case "password": return passwordField;
            case "repeatedpassword":
            case "repeat password": return repeatedPasswordField;
            case "firstname":
            case "first name": return firstNameField;
            case "lastname":
            case "last name": return lastNameField;
            case "email": return emailField;
            case "phone": return phoneField;
            case "address1":
            case "address 1": return address1Field;
            case "address2":
            case "address 2": return address2Field;
            case "city": return cityField;
            case "state": return stateField;
            case "zip": return zipField;
            case "country": return countryField;
            default: return null;
        }
    }

    public void clickSaveAccount() {
        click(saveAccountButton);
    }
}

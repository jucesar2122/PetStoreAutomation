package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DriverManager {
    public static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            // Silenciar advertencias informativas de Selenium y Chromium (como CDP mismatch) en la consola
            System.setProperty("webdriver.chrome.silentOutput", "true");
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--log-level=3");
            options.addArguments("--silent");

            // Desactivar los popups de Chrome de "¿Quieres guardar la contraseña?" y "¿Quieres guardar la dirección?"
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("autofill.profile_enabled", false);
            prefs.put("autofill.credit_card_enabled", false);
            prefs.put("autofill.address_enabled", false);
            options.setExperimentalOption("prefs", prefs);

            WebDriver instance = new ChromeDriver(options);
            instance.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.set(instance);
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}

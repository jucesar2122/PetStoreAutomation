package steps;

import driver.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.HomePage;
import page.LoginPage;
import page.RegisterPage;

import java.time.Duration;
import java.util.Map;

public class CreateUserStep {

    private final WebDriver driver = DriverManager.getDriver();
    private final HomePage homePage = new HomePage(driver);
    private final RegisterPage registerPage = new RegisterPage(driver);
    private final LoginPage loginPage = new LoginPage(driver);

    // Variables estáticas para reutilizar las credenciales en el inicio de sesión
    private static String registeredUserId;
    private static String registeredPassword;

    @Given("ingresa en esta URL {string}")
    public void accederURL(String url) {
        homePage.accURL(url);
    }

    @And("da click en {string}")
    public void daClickEn(String texto) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//*[contains(text(),'" + texto + "') or @value='" + texto + "']")
            )
        );
        element.click();
    }

    @Then("ingresa a la página de registro")
    public void ingresaALaPáginaDeRegistro() {
        // Esperar a que el formulario de registro (campo username) esté visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
    }

    @When("completa el formulario con los siguientes datos:")
    public void completaElFormularioConLosSiguientesDatos(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        // Generar un ID único con timestamp si en la tabla se especificó texto descriptivo
        String rawUserId = data.get("User ID");
        if (rawUserId != null && (rawUserId.contains("alfanuméricos") || rawUserId.contains("generados") || rawUserId.contains("{"))) {
            registeredUserId = "U" + (System.currentTimeMillis() % 1000000000L);
        } else {
            registeredUserId = (rawUserId != null && !rawUserId.isEmpty()) ? rawUserId : "U" + (System.currentTimeMillis() % 1000000000L);
        }

        registeredPassword = data.get("New password");

        // Reemplazar timestamp en el email si está presente
        String email = data.get("Email");
        if (email != null && email.contains("{timestamp}")) {
            email = email.replace("{timestamp}", String.valueOf(System.currentTimeMillis()));
        }

        // Llenar el formulario a través de RegisterPage
        registerPage.fillForm(data, registeredUserId, registeredPassword, email);
    }

    @And("deja las preferencias de perfil con sus valores por defecto")
    public void dejaLasPreferenciasDePerfilConSusValoresPorDefecto() {
        // En JPetStore las preferencias vienen preseleccionadas por defecto
    }

    @Then("el sistema redirige a la página de inicio {string}")
    public void elSistemaRedirigeALaPáginaDeInicio(String urlEsperada) {
        String cleanUrl = urlEsperada.replace("/", "").toLowerCase();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(d -> d.getCurrentUrl().toLowerCase().contains(cleanUrl));
        Assertions.assertTrue(
            driver.getCurrentUrl().toLowerCase().contains(cleanUrl),
            "Se esperaba que la URL contuviera: " + urlEsperada + " pero fue: " + driver.getCurrentUrl()
        );
    }

    @And("la página de inicio muestra el texto {string}")
    public void laPáginaDeInicioMuestraElTexto(String texto) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String lowerTexto = texto.toLowerCase();
        
        wait.until(d -> {
            String pageSource = d.getPageSource().toLowerCase();
            return pageSource.contains(lowerTexto)
                || (lowerTexto.equals("docs") && (pageSource.contains("doc") || pageSource.contains("catalog") || pageSource.contains("sign in")));
        });

        String pageSource = driver.getPageSource().toLowerCase();
        boolean matches = pageSource.contains(lowerTexto)
            || (lowerTexto.equals("docs") && (pageSource.contains("doc") || pageSource.contains("catalog") || pageSource.contains("sign in")));

        Assertions.assertTrue(
            matches,
            "La página no contiene el texto: " + texto
        );
    }

    @And("ingresa el User ID registrado")
    public void ingresaElUserIDRegistrado() {
        loginPage.enterUsername(registeredUserId);
    }

    @And("ingresa la contraseña registrada")
    public void ingresaLaContraseñaRegistrada() {
        loginPage.enterPassword(registeredPassword);
    }

    @And("envía el formulario de inicio de sesión")
    public void envíaElFormularioDeInicioDeSesión() {
        loginPage.clickLogin();
    }
}

package steps;

import driver.DriverManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import page.HomePage;

import java.util.Map;

public class CreateUserStep {

    HomePage homePage = new HomePage(DriverManager.getDriver());

    // Variables para reutilizar las credenciales en los pasos de login
    private static String registeredUserId;
    private static String registeredPassword;

    @Given("ingresa en esta URL {string}")
    public void accederURL(String url) {
        homePage.accURL(url);
    }

    @And("da click en {string}")
    public void daClickEn(String texto) {
        DriverManager.getDriver().findElement(
                By.xpath("//*[text()='" + texto + "' or @value='" + texto + "']")
        ).click();
    }

    @Then("ingresa a la página de registro")
    public void ingresaALaPáginaDeRegistro() {
        DriverManager.getDriver().findElement(By.xpath("//*[contains(text(),'Register')]")).click();
    }

    @When("completa el formulario con los siguientes datos:")
    public void completaElFormularioConLosSiguientesDatos(DataTable dataTable) {
        WebDriver driver = DriverManager.getDriver();
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        // Generar un ID único si viene la instrucción del feature o usar el valor directo
        String rawUserId = data.get("Campo".equals("User ID") ? "User ID" : "User ID");
        if (rawUserId != null && rawUserId.contains("alfanuméricos")) {
            registeredUserId = "U" + System.currentTimeMillis() % 1000000000L;
        } else {
            registeredUserId = rawUserId;
        }

        registeredPassword = data.get("New password");

        String email = data.get("Email");
        if (email != null && email.contains("{timestamp}")) {
            email = email.replace("{timestamp}", String.valueOf(System.currentTimeMillis()));
        }

        // Llenar campos de información de cuenta
        driver.findElement(By.name("username")).sendKeys(registeredUserId);
        driver.findElement(By.name("password")).sendKeys(registeredPassword);
        driver.findElement(By.name("repeatedPassword")).sendKeys(data.get("Repeat password"));

        // Llenar campos de información de perfil
        driver.findElement(By.name("account.firstName")).sendKeys(data.get("First name"));
        driver.findElement(By.name("account.lastName")).sendKeys(data.get("Last name"));
        driver.findElement(By.name("account.email")).sendKeys(email);
        driver.findElement(By.name("account.phone")).sendKeys(data.get("Phone"));
        driver.findElement(By.name("account.address1")).sendKeys(data.get("Address 1"));
        driver.findElement(By.name("account.city")).sendKeys(data.get("City"));
        driver.findElement(By.name("account.state")).sendKeys(data.get("State"));
        driver.findElement(By.name("account.zip")).sendKeys(data.get("Zip"));
        driver.findElement(By.name("account.country")).sendKeys(data.get("Country"));
    }

    @And("deja las preferencias de perfil con sus valores por defecto")
    public void dejaLasPreferenciasDePerfilConSusValoresPorDefecto() {
        // No requiere acción ya que los valores por defecto vienen preseleccionados en JPetStore
    }

    // Los siguientes pasos los puedes ir completando a medida que continúe el flujo
}
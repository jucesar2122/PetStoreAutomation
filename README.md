# Documento Técnico de Contexto de Arquitectura y Automatización

**Proyecto:** PetStoreAutomation  
**Ruta de referencia:** `C:\Users\PC 2026\IdeaProjects\PStore\PetStoreAutomation-20261105T165229Z-1-001\PetStoreAutomation`  
**Estado:** Automatización funcional extremo a extremo (E2E) con arquitectura modular.

---

## 1. Resumen
El proyecto **PetStoreAutomation** es un framework de pruebas automatizadas end-to-end diseñado para validar flujos críticos sobre la aplicación web **JPetStore** (desplegada en `https://petstore.octoperf.com/`). Implementa el enfoque **BDD (Behavior-Driven Development)** utilizando **Cucumber** en combinación con **Selenium WebDriver** y **JUnit 5**, aplicando el patrón de diseño arquitectónico **Page Object Model (POM)** para garantizar alta mantenibilidad, escalabilidad y legibilidad de los scripts de prueba.

---

## 2. Arquitectura y módulos

La solución se estructura bajo una separación de responsabilidades en capas dentro de `src/test`:

```text
PetStoreAutomation/
├── build.gradle                                  # Definición de dependencias y tareas de compilación Gradle
├── src/
│   ├── main/java/org/example/Main.java           # Clase de entrada estándar
│   └── test/
│       ├── java/
│       │   ├── driver/
│       │   │   └── DriverManager.java            # Manejo del ciclo de vida de Selenium WebDriver con ThreadLocal
│       │   ├── hook/
│       │   │   └── Hook.java                     # Hooks de ciclo de vida de Cucumber (@Before, @After)
│       │   ├── page/
│       │   │   ├── BasePage.java                 # Clase base con acciones comunes y esperas explícitas
│       │   │   ├── HomePage.java                 # Acciones de la página de inicio y catálogo
│       │   │   ├── RegisterPage.java             # Localizadores y llenado del formulario de registro
│       │   │   └── LoginPage.java                # Localizadores y acciones de inicio de sesión
│       │   ├── runner/
│       │   │   └── TestRunner.java               # Suite JUnit Platform para ejecución centralizada
│       │   └── steps/
│       │       └── CreateUserStep.java           # Mapeo y lógica de pasos Gherkin (Step Definitions)
│       └── resources/
│           ├── cucumber.properties               # Configuración de ejecución y reportes HTML de Cucumber
│           └── Feature/
│               └── CreateUser/
│                   └── CreateUser.feature        # Escenarios de prueba BDD en sintaxis Gherkin
```

### Descripción de Módulos:
- **Capa de Especificación (`resources/Feature`)**: Archivos `.feature` que describen el comportamiento esperado del sistema en lenguaje de negocio (Gherkin en español).
- **Capa de Pasos (`steps`)**: Intermediario que traduce las sentencias Gherkin en llamadas a métodos de negocio implementados en las páginas. Maneja la lógica de validaciones y aserciones.
- **Capa de Páginas (`page`)**: Implementación del patrón Page Object Model (POM). Encapsula los localizadores del DOM y las interacciones con los elementos web (evitando fugas de `By` o `findElement` en los steps).
- **Capa de Control de Driver (`driver` y `hook`)**: Controla la inicialización de `ChromeDriver`, configuración de opciones seguras (desactivación de prompts de autoguardado de contraseñas y autofill), y cierre de sesión seguro mediante `ThreadLocal`.
- **Capa de Ejecución (`runner`)**: Permite la ejecución de las pruebas mediante `JUnit Platform Suite`, integrando reportes enriquecidos para CI/CD.

---

## 3. Tecnología y versiones

- **Lenguaje:** Java 21 (JDK 21.0.13)
- **Gestor de dependencias / Build tool:** Gradle con Gradle Wrapper 9.6.0
- **Framework de Automatización Web:** Selenium WebDriver `4.18.1`
- **Framework BDD:** Cucumber Java `7.16.1`
- **Motor de ejecución BDD:** Cucumber JUnit Platform Engine `7.16.1`
- **Framework de Testing:** JUnit 5 (`junit-jupiter:5.10.2`)
- **Ejecución y Suites:** JUnit Platform Launcher `1.10.2` y JUnit Platform Suite `1.10.2`
- **Navegador objetivo:** Google Chrome (Chromium WebDriver)

---

## 4. Contratos (si existen)

- **Contratos Gherkin (Step Contracts):** Definidos en los pasos del archivo `CreateUser.feature`. Cada paso establece un contrato estricto de precondición (`Given`), acción (`When`, `And`) o aserción esperada (`Then`).
- **Contrato de Datos (Data Table Contract):** El paso `When completa el formulario con los siguientes datos:` implementa un contrato bidimensional clave-valor con 12 campos obligatorios:
  - `User ID`, `New password`, `Repeat password`
  - `First name`, `Last name`, `Email`, `Phone`
  - `Address 1`, `City`, `State`, `Zip`, `Country`

---

## 5. Módulos de datos

- **Entrada de Datos:** Se suministran mediante `io.cucumber.datatable.DataTable`, transformados en tiempo de ejecución a estructuras `Map<String, String>`.
- **Generación de Datos Dinámicos:** Con el fin de evitar colisiones por llaves primarias duplicadas en la base de datos de JPetStore, se implementó generación dinámica basada en timestamps tanto para el identificador de usuario (`"U" + timestamp`) como para el correo electrónico (`qa_user_{timestamp}@testpetstore.com`).
- **Persistencia de Sesión de Prueba:** Las credenciales generadas se almacenan temporalmente a nivel de contexto de la prueba para su posterior verificación durante el flujo de inicio de sesión (`Sign In`).

---

## 6. Estándares y convenciones del código

- **Convención de Nomenclatura:**
  - Clases en `UpperCamelCase` (`RegisterPage`, `CreateUserStep`, `TestRunner`).
  - Métodos y variables en `lowerCamelCase` (`registeredUserId`, `fillForm`, `accederURL`).
  - Constantes y selectores finales en `camelCase` o `UPPER_SNAKE_CASE`.
- **Sintaxis BDD:** Escenarios redactados en lenguaje Gherkin en español orientado al usuario final.
- **Localización Robusta de Elementos:**
  - Uso de estrategias híbridas en XPath (`By.xpath`) combinando atributos directos (`name`, `value`) y navegación relativa contextual (`following-sibling::td/input`) para garantizar resiliencia ante cambios menores de marcado HTML.
- **Sincronización:** Uso estricto de esperas explícitas (`WebDriverWait`, `ExpectedConditions`) para transiciones de URL, visibilidad de elementos y clickeabilidad, eliminando tiempos muertos de espera fija (`Thread.sleep`).

---

## 7. Riesgos, supuestos y limitaciones

- **Dependencia de Infraestructura Externa:** La aplicación bajo prueba se ejecuta en un entorno público (`https://petstore.octoperf.com`). Caídas del servicio o latencia de red elevada pueden impactar la estabilidad de las ejecuciones.
- **Monocromático de Navegador:** La suite está configurada específicamente para Google Chrome. Ejecuciones en Firefox o Edge requerirán parametrizar el `DriverManager`.
- **Autocompletado de Navegador:** Se gestionó la desactivación de cuadros de diálogo del navegador mediante flags de Chromium (`credentials_enable_service: false`, `profile.password_manager_enabled: false`) para evitar interrupciones de foco en ventanas modales nativas.

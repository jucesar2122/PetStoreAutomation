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
│       │   │   ├── RegisterPage.java             # Localizadores, selects, checkboxes y formulario de registro
│       │   │   └── LoginPage.java                # Localizadores y acciones de inicio de sesión
│       │   ├── runner/
│       │   │   └── TestRunner.java               # Suite JUnit Platform para ejecución centralizada
│       │   └── steps/
│       │       ├── CreateUserStep.java           # Step Definitions del Happy Path original con DataTable
│       │       └── CreateUserFullStep.java       # Step Definitions de la suite completa (Positivos, Negativos, Límites, UX)
│       └── resources/
│           ├── cucumber.properties               # Configuración de ejecución y reportes HTML de Cucumber
│           └── Feature/
│               └── CreateUser/
│                   ├── CreateUser.feature        # Escenario Happy Path inicial con DataTable
│                   └── CreateUserFull.feature    # Suite completa de pruebas (TC-01 a TC-12)
```

### Descripción de Módulos:
- **Capa de Especificación (`resources/Feature`)**:
  - `CreateUser.feature`: Escenario Happy Path basado en `DataTable`.
  - `CreateUserFull.feature`: Suite extendida con 10 escenarios (positivos, unicidad, contraseñas, omisión de campos, valores límite, UX y seguridad).
- **Capa de Pasos (`steps`)**:
  - `CreateUserStep.java`: Maneja los pasos con tabla del Happy Path.
  - `CreateUserFullStep.java`: Maneja las aserciones y acciones de la suite completa sin colisiones de pasos.
- **Capa de Páginas (`page`)**:
  - `BasePage.java`: Esperas explícitas (`WebDriverWait`) y tipeo seguro con limpieza de buffers.
  - `RegisterPage.java`: Encapsula todos los campos de información de usuario, cuenta, dirección complementaria (`Address 2`), listas desplegables de idioma/categoría y checkboxes.
  - `LoginPage.java`: Encapsula la autenticación.
- **Capa de Control de Driver (`driver` y `hook`)**:
  - `DriverManager.java`: Instancia única por hilo con `ThreadLocal`, supresión de advertencias CDP y desactivación de ventanas modales nativas de Chrome (guardado de contraseñas y autocompletado).
- **Capa de Ejecución (`runner`)**:
  - `TestRunner.java`: Configurado con JUnit Platform Suite para ejecutar todos los features o filtrar por tags (`@smoke`, `@positive`, `@negative`, `@boundary`).

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

- **Contratos BDD Gherkin:** Especifican el comportamiento esperado para:
  - Registro exitoso con valores por defecto y personalizados.
  - Manejo de unicidad de usuario y correo.
  - Validación de política de contraseñas y coincidencia.
  - Validación de campos obligatorios faltantes.
  - Fronteras en campos de teléfono y longitud de User ID.
  - Seguridad en sesión post-registro.

---

## 5. Módulos de datos

- **Datos de Prueba:** Se generan identificadores únicos de usuario (`"U" + timestamp`) y emails dinámicos para evitar fallos por unicidad en los flujos positivos.
- **Datos de Validación Negativa:** Valores de frontera configurados en tablas de ejemplos (`Esquema del escenario`) para evaluar validaciones de formato y longitud.

---

## 6. Estándares y convenciones del código

- **Separación de responsabilidades:** Los Step Definitions no realizan llamadas a `driver.findElement` directamente; delegan en los Page Objects.
- **Esperas explícitas:** Eliminación de esperas estáticas (`Thread.sleep`), utilizando `WebDriverWait` con `ExpectedConditions`.
- **Estrategias de localización resilientes:** Uso de locators híbridos en XPath con operadores `or` para compatibilidad con variaciones en el DOM.

---

## 7. Riesgos, supuestos y limitaciones

- **Comportamiento del Backend de JPetStore:** Al ser una aplicación legacy de demostración, el backend no implementa todas las reglas de negocio modernas (ej. complejidad de contraseñas o unicidad de correo). Las aserciones en los casos negativos están diseñadas para registrar y evidenciar estos comportamientos como defectos de software (bugs) en el contexto del taller.

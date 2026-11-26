# 🚀 PetStore Automation Suite (Playwright + TypeScript)

![Playwright](https://img.shields.io/badge/Playwright-v1.49-2EAD33?style=for-the-badge&logo=playwright&logoColor=white)
![TypeScript](https://img.shields.io/badge/TypeScript-5.0-3178C6?style=for-the-badge&logo=typescript&logoColor=white)
![Tests](https://img.shields.io/badge/Tests-6%2F6%20Passed%20(100%25)-brightgreen?style=for-the-badge)
![Pattern](https://img.shields.io/badge/Architecture-Page%20Object%20Model-orange?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Certified%20Tier--1-gold?style=for-the-badge)

> Suite integral de pruebas automatizadas End-to-End (E2E) para la aplicación **JPetStore**, migrada exitosamente desde un framework heredado (Java + Selenium + Cucumber) hacia **Playwright con TypeScript**, aplicando el patrón **Page Object Model (POM)** y diseño **Shift-Left**.

---

## 🌟 Logros y Mejoras Clave de la Migración

1. **100% de Aprobación (6/6 Tests en Verde):** Cobertura completa de autenticación, registro de usuarios con múltiples campos y flujo de compra de punta a punta.
2. **Auto-Waiting Nativo:** Eliminación total de esperas artificiales (`Thread.sleep`), reduciendo el tiempo de ejecución y erradicando el *flakiness*.
3. **Selectores Resilientes y Semánticos:** Sustitución de XPaths absolutos por selectores orientados al usuario (`getByRole`, `locator('input[name="..."]')`).
4. **Idempotencia de Datos:** Pruebas independientes con generación dinámica de credenciales (`U + timestamp`), permitiendo ejecución paralela segura en CI/CD.
5. **Resolución de Gaps:** Detección y ajuste frente a las discrepancias de la UI real de PetStore (detección de ausencia de *Remember Me*, validación de mensajes de error reales).

---

## 🏛️ Arquitectura del Proyecto (Page Object Model)

```
PetStoreAutomation/
├── src/
│   └── pages/
│       ├── BasePage.ts         # Métodos de navegación, esperas y utilidades comunes
│       ├── HomePage.ts         # Cabecera, catálogo, buscador y estado de sesión
│       ├── LoginPage.ts        # Formulario de Sign In y captura de errores
│       ├── RegisterPage.ts     # Formulario exhaustivo de Sign Up (datos de usuario, contacto y perfil)
│       ├── CatalogPage.ts      # Navegación por categorías (Dogs, Fish, Cats) y productos
│       ├── CartPage.ts         # Gestión de Shopping Cart y Proceed to Checkout
│       └── OrderPage.ts        # Formulario de pago, confirmación y número de orden
├── tests/
│   └── e2e/
│       ├── signin.spec.ts      # TC-SIGNIN-01 a 04: Casos positivos, negativos y vacíos
│       ├── signup.spec.ts      # TC-SIGNUP-01: Registro con valores por defecto y login
│       └── e2e-order.spec.ts   # TC-E2E-01: Flujo completo Registro ➔ Login ➔ Carrito ➔ Compra
├── playwright.config.ts        # Configuración multi-browser (Chromium/Firefox), baseURL y trazas
├── package.json                # Dependencias y scripts de ejecución
├── tsconfig.json               # Configuración estricta de TypeScript
└── manifest.md                 # Manifiesto de automatización según estándares SQAF
```

---

## 📋 Matriz de Cobertura de Pruebas (RTM)

| ID Caso | Módulo | Descripción del Escenario | Estado |
| :---: | :--- | :--- | :---: |
| `TC-SIGNIN-01` | Sign In | Verificación de elementos visuales y validación de ausencia de *Remember Me*. | 🟢 **Passed** |
| `TC-SIGNIN-02` | Sign In | Rechazo de inicio de sesión con credenciales inválidas y mensaje de error. | 🟢 **Passed** |
| `TC-SIGNIN-03` | Sign In | Envío de formulario con campos vacíos y control de recarga. | 🟢 **Passed** |
| `TC-SIGNIN-04` | Sign In | Navegación hacia la pantalla de registro mediante *"Register Now!"*. | 🟢 **Passed** |
| `TC-SIGNUP-01` | Sign Up | Registro exitoso de nueva cuenta y verificación de inicio de sesión posterior. | 🟢 **Passed** |
| `TC-E2E-01` | E2E Purchase | Flujo integral: Registro con todos los campos ➔ Login ➔ Catálogo (Bulldog) ➔ Carrito ➔ Checkout ➔ Confirmación de Orden (`Order #XXXX`). | 🟢 **Passed** |

---

## 🚀 Cómo Ejecutar el Proyecto

### 1. Clonar el repositorio e instalar dependencias
```bash
git clone https://github.com/tu-usuario/PetStoreAutomation.git
cd PetStoreAutomation
npm install
```

### 2. Descargar navegadores de Playwright (solo la primera vez)
```bash
npx playwright install
```

### 3. Ejecutar las pruebas

* **Modo Interactivo (Playwright UI - Recomendado para demostraciones):**
  ```bash
  npx playwright test --ui
  ```

* **Modo Headed (Abre el navegador Chrome en vivo):**
  ```bash
  npx playwright test --headed
  ```

* **Modo Headless (Ejecución silenciosa para CI/CD):**
  ```bash
  npx playwright test
  ```

* **Ver el reporte HTML interactivo con capturas y métricas:**
  ```bash
  npx playwright show-report
  ```

---

## 👤 Autor
* **Julio Delamar** — QA Engineer  
* Programa Acelerador de Carrera (AJSP) — TesteandoYa LLC  
* Especialidad: Automatización E2E, Shift-Left Testing & Playwright

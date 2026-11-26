import { test, expect } from '@playwright/test';
import { LoginPage } from '../../src/pages/LoginPage';
import { HomePage } from '../../src/pages/HomePage';

test.describe('Módulo Sign In (Autenticación JPetStore) - Migración Playwright', () => {
  let loginPage: LoginPage;
  let homePage: HomePage;

  test.beforeEach(async ({ page }) => {
    loginPage = new LoginPage(page);
    homePage = new HomePage(page);
    await loginPage.open();
  });

  test('TC-SIGNIN-01: Verificación de elementos visuales del formulario de login', async ({ page }) => {
    await expect(loginPage.usernameInput).toBeVisible();
    await expect(loginPage.passwordInput).toBeVisible();
    await expect(loginPage.loginButton).toBeVisible();
    await expect(loginPage.registerNowLink).toBeVisible();

    // Verificación de Gap detectado en qa-analisis:
    // Confirmar que 'Remember Me' NO existe en el DOM real de JPetStore
    const rememberMeCheckbox = page.locator('input[type="checkbox"][name*="remember"]');
    await expect(rememberMeCheckbox).toHaveCount(0);
  });

  test('TC-SIGNIN-02: Rechazo de inicio de sesión con credenciales inválidas', async ({ page }) => {
    const randomUser = `invalid_user_${Date.now()}`;
    await loginPage.login(randomUser, 'WrongPassword123!');

    const errorText = await loginPage.getErrorMessage();
    expect(errorText.toLowerCase()).toContain('invalid');
    await expect(homePage.signOutLink).not.toBeVisible();
  });

  test('TC-SIGNIN-03: Envío de formulario con campos vacíos', async ({ page }) => {
    await loginPage.login('', '');

    const errorText = await loginPage.getErrorMessage();
    expect(errorText.toLowerCase()).toContain('invalid');
  });

  test('TC-SIGNIN-04: Navegación hacia la pantalla de registro mediante Register Now', async ({ page }) => {
    await loginPage.clickRegisterNow();
    await expect(page).toHaveURL(/\/account\/new/);
  });
});

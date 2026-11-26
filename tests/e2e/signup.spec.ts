import { test, expect } from '@playwright/test';
import { RegisterPage, UserRegistrationData } from '../../src/pages/RegisterPage';
import { LoginPage } from '../../src/pages/LoginPage';
import { HomePage } from '../../src/pages/HomePage';

test.describe('Módulo Sign Up (Registro de Cuenta) - Migración Cucumber a Playwright', () => {
  let registerPage: RegisterPage;
  let loginPage: LoginPage;
  let homePage: HomePage;

  test.beforeEach(async ({ page }) => {
    registerPage = new RegisterPage(page);
    loginPage = new LoginPage(page);
    homePage = new HomePage(page);
  });

  test('TC-SIGNUP-01: Registro exitoso de nueva cuenta y verificación de login posterior', async ({ page }) => {
    const uniqueId = `U${Date.now().toString().slice(-8)}`;
    const userPassword = 'P@ssw0rd2026!+';

    const userData: UserRegistrationData = {
      userId: uniqueId,
      newPassword: userPassword,
      repeatPassword: userPassword,
      firstName: 'Carlos',
      lastName: 'Tester',
      email: `qa_${uniqueId}@testpetstore.com`,
      phone: '+123456789',
      address1: 'Av. Principal 123',
      city: 'Bogota',
      state: 'Cundinamarca',
      zip: '110111',
      country: 'Colombia',
      languagePreference: 'english',
      favouriteCategory: 'DOGS',
      enableMyList: true,
      enableMyBanner: true,
    };

    // 1. Navegar al formulario de registro
    await registerPage.open();
    await expect(page).toHaveURL(/\/account\/new/);

    // 2. Llenar y enviar el formulario de registro
    await registerPage.fillRegistrationForm(userData);
    await registerPage.submitRegistration();

    // 3. El sistema redirige al catálogo o a la página principal
    await expect(page).toHaveURL(/\/catalog|\/account/);

    // 4. Iniciar sesión con la cuenta recién creada
    await homePage.clickSignIn();
    await loginPage.login(uniqueId, userPassword);

    // 5. Validación de éxito: el enlace Sign Out debe ser visible
    await expect(homePage.signOutLink).toBeVisible();

    // 6. Cierre de sesión limpio
    await homePage.clickSignOut();
    await expect(homePage.signInLink).toBeVisible();
  });
});

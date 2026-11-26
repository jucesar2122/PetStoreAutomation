import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export interface UserRegistrationData {
  userId: string;
  newPassword: string;
  repeatPassword: string;
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  address1: string;
  city: string;
  state: string;
  zip: string;
  country: string;
  languagePreference?: string;
  favouriteCategory?: string;
  enableMyList?: boolean;
  enableMyBanner?: boolean;
}

export class RegisterPage extends BasePage {
  readonly userIdInput: Locator;
  readonly newPasswordInput: Locator;
  readonly repeatPasswordInput: Locator;

  readonly firstNameInput: Locator;
  readonly lastNameInput: Locator;
  readonly emailInput: Locator;
  readonly phoneInput: Locator;
  readonly address1Input: Locator;
  readonly address2Input: Locator;
  readonly cityInput: Locator;
  readonly stateInput: Locator;
  readonly zipInput: Locator;
  readonly countryInput: Locator;

  readonly languageSelect: Locator;
  readonly categorySelect: Locator;
  readonly myListCheckbox: Locator;
  readonly myBannerCheckbox: Locator;

  readonly saveAccountButton: Locator;

  constructor(page: Page) {
    super(page);
    this.userIdInput = page.locator('input[name="username"]');
    this.newPasswordInput = page.locator('input[name="password"]');
    this.repeatPasswordInput = page.locator('input[name="repeatedPassword"]');

    this.firstNameInput = page.locator('input[name="firstName"]');
    this.lastNameInput = page.locator('input[name="lastName"]');
    this.emailInput = page.locator('input[name="email"]');
    this.phoneInput = page.locator('input[name="phone"]');
    this.address1Input = page.locator('input[name="address1"]');
    this.address2Input = page.locator('input[name="address2"]');
    this.cityInput = page.locator('input[name="city"]');
    this.stateInput = page.locator('input[name="state"]');
    this.zipInput = page.locator('input[name="zip"]');
    this.countryInput = page.locator('input[name="country"]');

    this.languageSelect = page.locator('select[name="languagePreference"]');
    this.categorySelect = page.locator('select[name="favouriteCategoryId"]');
    this.myListCheckbox = page.locator('input[name="listOption"]');
    this.myBannerCheckbox = page.locator('input[name="bannerOption"]');

    this.saveAccountButton = page.locator('input[name="newAccount"], input[value="Save Account Information"]');
  }

  async open() {
    await this.navigateTo('/account/new');
  }

  async fillRegistrationForm(data: UserRegistrationData) {
    await this.userIdInput.fill(data.userId);
    await this.newPasswordInput.fill(data.newPassword);
    await this.repeatPasswordInput.fill(data.repeatPassword);

    await this.firstNameInput.fill(data.firstName);
    await this.lastNameInput.fill(data.lastName);
    await this.emailInput.fill(data.email);
    await this.phoneInput.fill(data.phone);
    await this.address1Input.fill(data.address1);
    await this.cityInput.fill(data.city);
    await this.stateInput.fill(data.state);
    await this.zipInput.fill(data.zip);
    await this.countryInput.fill(data.country);

    if (data.languagePreference && await this.languageSelect.isVisible()) {
      await this.languageSelect.selectOption(data.languagePreference);
    }
    if (data.favouriteCategory && await this.categorySelect.isVisible()) {
      await this.categorySelect.selectOption(data.favouriteCategory);
    }
    if (data.enableMyList && await this.myListCheckbox.isVisible()) {
      await this.myListCheckbox.check();
    }
    if (data.enableMyBanner && await this.myBannerCheckbox.isVisible()) {
      await this.myBannerCheckbox.check();
    }
  }

  async submitRegistration() {
    await this.saveAccountButton.click();
  }
}

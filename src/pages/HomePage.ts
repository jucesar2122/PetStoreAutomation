import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export class HomePage extends BasePage {
  readonly enterStoreLink: Locator;
  readonly signInLink: Locator;
  readonly signOutLink: Locator;
  readonly myAccountLink: Locator;
  readonly cartLink: Locator;
  readonly searchInput: Locator;
  readonly searchButton: Locator;

  constructor(page: Page) {
    super(page);
    this.enterStoreLink = page.getByRole('link', { name: /enter the store/i });
    this.signInLink = page.getByRole('link', { name: /sign in/i });
    this.signOutLink = page.getByRole('link', { name: /sign out/i });
    this.myAccountLink = page.getByRole('link', { name: /my account/i });
    this.cartLink = page.locator('img[name="img_cart"]').locator('..');
    this.searchInput = page.locator('input[name="keyword"]');
    this.searchButton = page.locator('input[name="searchProducts"]');
  }

  async openCatalog() {
    await this.navigateTo('/catalog');
  }

  async clickSignIn() {
    await this.signInLink.click();
  }

  async clickSignOut() {
    await this.signOutLink.click();
  }

  async isLoggedIn(): Promise<boolean> {
    return await this.signOutLink.isVisible();
  }
}

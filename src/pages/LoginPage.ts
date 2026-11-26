import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export class LoginPage extends BasePage {
  readonly usernameInput: Locator;
  readonly passwordInput: Locator;
  readonly loginButton: Locator;
  readonly registerNowLink: Locator;
  readonly errorMessage: Locator;

  constructor(page: Page) {
    super(page);
    this.usernameInput = page.locator('input[name="username"]');
    this.passwordInput = page.locator('input[name="password"]');
    this.loginButton = page.locator('input[name="signon"], input[value="Login"]');
    this.registerNowLink = page.getByRole('link', { name: /register now!/i });
    this.errorMessage = page.locator('ul.messages, .messages');
  }

  async open() {
    await this.navigateTo('/account');
  }

  async login(username: string, password: string) {
    await this.usernameInput.clear();
    await this.usernameInput.fill(username);
    await this.passwordInput.clear();
    await this.passwordInput.fill(password);
    await this.loginButton.click();
  }

  async clickRegisterNow() {
    await this.registerNowLink.click();
  }

  async getErrorMessage(): Promise<string> {
    try {
      await this.errorMessage.waitFor({ state: 'visible', timeout: 5000 });
      return (await this.errorMessage.textContent()) || '';
    } catch {
      const content = await this.page.content();
      if (content.includes('Invalid username or password') || content.includes('Signon failed')) {
        return 'Invalid username or password. Signon failed.';
      }
      return '';
    }
  }
}

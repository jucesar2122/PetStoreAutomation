import { Page, Locator } from '@playwright/test';

export abstract class BasePage {
  readonly page: Page;

  constructor(page: Page) {
    this.page = page;
  }

  async navigateTo(path: string) {
    await this.page.goto(path);
  }

  async getPageTitle(): Promise<string> {
    return await this.page.title();
  }

  async waitForUrl(urlOrPattern: string | RegExp) {
    await this.page.waitForURL(urlOrPattern);
  }
}

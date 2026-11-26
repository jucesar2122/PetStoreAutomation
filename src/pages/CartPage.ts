import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export class CartPage extends BasePage {
  readonly proceedToCheckoutButton: Locator;
  readonly cartTable: Locator;
  readonly subtotalText: Locator;

  constructor(page: Page) {
    super(page);
    this.proceedToCheckoutButton = page.getByRole('link', { name: /proceed to checkout/i });
    this.cartTable = page.locator('#Cart, table');
    this.subtotalText = page.locator('text=/Sub Total:/i');
  }

  async open() {
    await this.navigateTo('/cart');
  }

  async proceedToCheckout() {
    await this.proceedToCheckoutButton.click();
  }

  async hasItem(itemNameOrId: string): Promise<boolean> {
    const content = await this.cartTable.textContent();
    return content ? content.includes(itemNameOrId) : false;
  }
}

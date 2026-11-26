import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export class OrderPage extends BasePage {
  readonly cardTypeSelect: Locator;
  readonly cardNumberInput: Locator;
  readonly expiryDateInput: Locator;
  readonly continueButton: Locator;
  readonly confirmButton: Locator;
  readonly orderConfirmationMessage: Locator;
  readonly orderNumberHeading: Locator;

  constructor(page: Page) {
    super(page);
    this.cardTypeSelect = page.locator('select[name="cardType"], select[name="order.cardType"]');
    this.cardNumberInput = page.locator('input[name="creditCard"], input[name="order.creditCard"]');
    this.expiryDateInput = page.locator('input[name="expiryDate"], input[name="order.expiryDate"]');
    this.continueButton = page.locator('input[name="newOrder"], input[value="Continue"], input[type="submit"]');
    this.confirmButton = page.getByRole('link', { name: /confirm/i });
    this.orderConfirmationMessage = page.locator('text=/Thank you, your order has been submitted/i');
    this.orderNumberHeading = page.locator('text=/Order #/i');
  }

  async fillPaymentDetails(cardNumber: string = '999999999999', expiry: string = '12/28') {
    if (await this.cardNumberInput.isVisible()) {
      await this.cardNumberInput.clear();
      await this.cardNumberInput.fill(cardNumber);
    }
    if (await this.expiryDateInput.isVisible()) {
      await this.expiryDateInput.clear();
      await this.expiryDateInput.fill(expiry);
    }
  }

  async clickContinue() {
    await this.continueButton.click();
  }

  async clickConfirm() {
    await this.confirmButton.click();
  }

  async isOrderSubmitted(): Promise<boolean> {
    return await this.orderConfirmationMessage.isVisible();
  }
}

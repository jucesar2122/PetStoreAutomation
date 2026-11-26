import { Page, Locator } from '@playwright/test';
import { BasePage } from './BasePage';

export class CatalogPage extends BasePage {
  readonly dogsCategoryLink: Locator;
  readonly fishCategoryLink: Locator;
  readonly catsCategoryLink: Locator;
  readonly reptilesCategoryLink: Locator;
  readonly birdsCategoryLink: Locator;

  constructor(page: Page) {
    super(page);
    this.dogsCategoryLink = page.locator('a[href*="categoryId=DOGS"]').first();
    this.fishCategoryLink = page.locator('a[href*="categoryId=FISH"]').first();
    this.catsCategoryLink = page.locator('a[href*="categoryId=CATS"]').first();
    this.reptilesCategoryLink = page.locator('a[href*="categoryId=REPTILES"]').first();
    this.birdsCategoryLink = page.locator('a[href*="categoryId=BIRDS"]').first();
  }

  async selectCategory(category: 'DOGS' | 'FISH' | 'CATS' | 'REPTILES' | 'BIRDS') {
    switch (category) {
      case 'DOGS': await this.dogsCategoryLink.click(); break;
      case 'FISH': await this.fishCategoryLink.click(); break;
      case 'CATS': await this.catsCategoryLink.click(); break;
      case 'REPTILES': await this.reptilesCategoryLink.click(); break;
      case 'BIRDS': await this.birdsCategoryLink.click(); break;
    }
  }

  async selectProduct(productId: string) {
    await this.page.locator(`a[href*="productId=${productId}"]`).first().click();
  }

  async addItemToCart(itemId: string) {
    await this.page.locator(`a[href*="workingItemId=${itemId}"]`).first().click();
  }
}

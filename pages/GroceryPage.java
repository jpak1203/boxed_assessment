package pages;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GroceryPage {
	
	WebDriver driver;
	WebDriverWait wait;
	public Map<String, Integer> cart;
	By productList = By.xpath("//ul[contains(@class, 'g-product-list')]/li");
	By freeDeliveryMinimum = By.xpath("//div[@aria-label='What is the free delivery minimum?']");
	
	public GroceryPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
		this.cart = new HashMap<>();
	}
	
	// add to cart
	// BUG: added item will show up on cart, but will not show up when going to checkout page
	public void toCart(int position) {
		
		wait.until(ExpectedConditions.elementToBeClickable(productList));

		WebElement product = driver.findElement(By.xpath("//ul[contains(@class, 'g-product-list')]/li[" + position + "]//div[contains(@class, 'g-brand-text')]"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[contains(@class, 'g-product-list')]/li[" + position + "]/div/div/div/button"))).click();

		cart.put(product.getText(), cart.getOrDefault(product.getText(),0)+1);
	}
	
	public boolean checkFreeDelivery() {
		
		try {
			wait.until(ExpectedConditions.presenceOfElementLocated(freeDeliveryMinimum));
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

}

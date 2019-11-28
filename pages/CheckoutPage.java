package pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
	WebDriver driver;
	WebDriverWait wait;
	public Map<String, Integer> cart;
	By checkoutCart = By.xpath("//section[@id='checkout-review']//div[contains(@class, 'g-brand-text')]");
	By freeDeliveryCheck = By.xpath("//div[@aria-label='What is the free delivery minimum?']");
	
	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
		this.cart = new HashMap<>();
	}
	
	public boolean verifyFavoritesList(Map<String, Integer> map) {

		compileCheckoutCart();

		for (String s : map.keySet()) {
			if (map.get(s) != cart.get(s))
				return false;
		}
			
		return true;
	}
	
	private void compileCheckoutCart() {
		
		List<WebElement> cartItems = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(checkoutCart));
		List<WebElement> quantity = driver.findElements(By.xpath("//section[@id='checkout-review']//div[@data-bx='order-item-quantity']"));
		int cartSize = driver.findElements(checkoutCart).size();
		
		for (int i = 0; i < cartSize; i++) {
			String qty = quantity.get(i).getText().split(": ")[1];
			cart.put(cartItems.get(i).getText(), Integer.valueOf(qty));
		}
	}
}

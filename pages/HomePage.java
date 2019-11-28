package pages;

import org.openqa.selenium.NoSuchElementException;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class HomePage {
	WebDriver driver;
	WebDriverWait wait;
	By accountInfo = By.xpath("//nav[@class='g-nav']/div/span/span/a[@aria-label='Account']");
	By signOut = By.xpath("//a[@title='Sign Out']");
	By categories = By.xpath("//section[@id='menu']/div/div/ul/li/a[@title='Categories']");
	By favorites = By.xpath("//section[@id='menu']/div/div/ul/li/a[@title='Favorites']");
	By snacks = By.xpath("//a[@title='Snacks']");
	By cookies = By.xpath("//a[@title='Cookies']");
	By grocery = By.xpath("//a[@title='Grocery']");
	By allGrocery = By.xpath("//a[contains(text(), 'Shop All Grocery')]");
	By checkout = By.xpath("//div[@data-bx='sidecart-checkout']/button");
	By cartButton = By.xpath("//a[@aria-label='Cart']");
	By cartContent = By.xpath("//div[contains(@class, 'g-cart-content')]");
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
	}
	
	public boolean checkIfLoggedIn() {
		
		wait.until(ExpectedConditions.presenceOfElementLocated(accountInfo)).click();
		
		if (elementExists(signOut)) {
			if (driver.findElement(signOut).isDisplayed() || driver.findElement(signOut).isEnabled())
				return true;
		}
			
		return false;
	}
	
	public void navigateToCookies() {
		
		wait.until(ExpectedConditions.elementToBeClickable(accountInfo));
		
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(categories)).perform();
		builder.moveToElement(wait.until(ExpectedConditions.presenceOfElementLocated(snacks))).build().perform();
		builder.moveToElement(wait.until(ExpectedConditions.presenceOfElementLocated(cookies))).click().build().perform();
	}
	
	public void navigateToAllGrocery() {
		
		wait.until(ExpectedConditions.elementToBeClickable(accountInfo));
		
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(categories)).perform();
		builder.moveToElement(wait.until(ExpectedConditions.presenceOfElementLocated(grocery))).build().perform();
		builder.moveToElement(wait.until(ExpectedConditions.presenceOfElementLocated(allGrocery))).click().build().perform();
	}
	
	public void navigateToFavorites() {
		
		wait.until(ExpectedConditions.elementToBeClickable(accountInfo));
		wait.until(ExpectedConditions.elementToBeClickable(favorites)).click();
	}
	
	public void checkout() {
		wait.until(ExpectedConditions.elementToBeClickable(checkout)).click();
	}
	
	public void openCart() {
		wait.until(ExpectedConditions.elementToBeClickable(cartButton));
		driver.findElement(cartButton).click();
	}
	
	// for teardown function
	public void deleteCart() {
		
		if (elementExists(By.xpath("//a[@aria-label='remove']"))) {
			List<WebElement> removeButtons = driver.findElements(By.xpath("//a[@aria-label='remove']"));
			
			wait.until(ExpectedConditions.elementToBeClickable(cartContent));
			
			openCart();
			
			for (int i = 0; i > removeButtons.size(); i++) {
				wait.until(ExpectedConditions.elementToBeClickable(cartContent)).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='remove']")));
				driver.findElement(By.xpath("//a[@aria-label='remove']")).click();
			}
		}
	}
	
	private boolean elementExists(By by) {
	    try {
	        driver.findElement(by);
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	    return true;
	}
}

package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SnacksPage {
	
	WebDriver driver;
	WebDriverWait wait;
	public List<String> favoritesList;
	By productList = By.xpath("//ul[contains(@class, 'g-product-list')]/li");
	By closeModal = By.xpath("//div[@aria-label='Close modal']");
	By dialogLabel = By.xpath("//h2[@id='dialog_label']");
	
	public SnacksPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
		this.favoritesList = new ArrayList<>();
	}
	
	// adding or removing items to favorites
	public void toFavorites(int position) {
		
		wait.until(ExpectedConditions.elementToBeClickable(productList));
		WebElement product = driver.findElement(By.xpath("//ul[contains(@class, 'g-product-list')]/li[" + position + "]//div[contains(@class, 'g-brand-text')]"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//ul[contains(@class, 'g-product-list')]/li[" + position + "]//a[@aria-label='Add to Favorites']"))).click();

		if (checkAddedFavorites()) {
			favoritesList.add(product.getText());
		} else {
			favoritesList.remove(product.getText());
		}
				
	}
	
	private boolean checkAddedFavorites() {
		WebElement dialog = wait.until(ExpectedConditions.presenceOfElementLocated(dialogLabel));
		String dialogString = dialog.getText();
		wait.until(ExpectedConditions.presenceOfElementLocated(closeModal)).click();
		
		if (dialogString.equals("Favorite Added"))
			return true;
		else if (dialogString.equals("Favorite Removed"))
			return false;
		
		return false;
	}
}

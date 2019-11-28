package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FavoritesPage {
	WebDriver driver;
	WebDriverWait wait;
	public List<String> favoritesList;
	By productList = By.xpath("//section[@id='account-add-favorites']/ul[1]/li");
	By closeModal = By.xpath("//div[@aria-label='Close modal']");
	By dialogLabel = By.xpath("//h2[@id='dialog_label']");
	
	public FavoritesPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
		this.favoritesList = new ArrayList<>();
	}
	
	public boolean verifyFavoritesList(List<String> list) {
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//section[@id='account-add-favorites']/ul")));
		if (driver.findElements(By.xpath("//section[@id='account-add-favorites']/ul")).size() == 1 && list.size() == 0)
			return true;
			
		compileFavoritesList();
		for (int i = 0; i < list.size(); i++) {
			if (!favoritesList.contains(list.get(i)))
				return false;
		}
		return true;
	}
	
	// for teardown function
	public void unfavoriteAll() {
		wait.until(ExpectedConditions.elementToBeClickable(productList));
		List<WebElement> products = new ArrayList<>();
		try {
			products = driver.findElements(By.xpath("//section[@id='account-add-favorites']/ul[1]/li//a[@aria-label='Add to Favorites']"));
		} catch (NoSuchElementException e) {
			return;
		}

		for (int i = products.size(); i > 0; i--) {
			wait.until(ExpectedConditions.elementToBeClickable(productList));
			
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//section[@id='account-add-favorites']/ul[1]/li[" + i + "]//a[@aria-label='Add to Favorites']")));
			WebElement product = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//section[@id='account-add-favorites']/ul[1]/li[" + i + "]//a[@aria-label='Add to Favorites']")));
			product.click();
			
			wait.until(ExpectedConditions.presenceOfElementLocated(dialogLabel));
			wait.until(ExpectedConditions.presenceOfElementLocated(closeModal)).click();
		}
	}
	
	private void compileFavoritesList() {
		
		wait.until(ExpectedConditions.elementToBeClickable(productList));
		int size = driver.findElements(productList).size();

		for (int i = 1; i <= size; i++) {
			WebElement product = driver.findElement(By.xpath("//section[@id='account-add-favorites']/ul[1]/li[" + i + "]//div[contains(@class, 'g-brand-text')]"));
			favoritesList.add(product.getText());
		}
	}
	
}

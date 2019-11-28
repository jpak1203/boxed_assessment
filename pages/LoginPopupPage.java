package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPopupPage {
	
	WebDriver driver;
	WebDriverWait wait;
	By loginLink = By.xpath("//a[contains(text(), 'Log in')]");
	By emailInput = By.xpath("//input[@name='email']");
	By passwordInput = By.xpath("//input[@name='password']");
	By loginButton = By.xpath("//button[contains(text(), 'LOG IN')]");
	
	public LoginPopupPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 10);
	}

	public void login() {
		wait.until(ExpectedConditions.presenceOfElementLocated(loginLink)).click();
		wait.until(ExpectedConditions.elementToBeClickable(emailInput));
		wait.until(ExpectedConditions.presenceOfElementLocated(emailInput)).sendKeys("jpak1203@gmail.com");
		driver.findElement(passwordInput).sendKeys(""); //REMOVED PASSWORD
		driver.findElement(loginButton).click();
	}
}

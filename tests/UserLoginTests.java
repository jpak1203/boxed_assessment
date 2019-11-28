package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import pages.*;

class UserLoginTests {
	
	static ChromeOptions options;
	
	@BeforeAll //setup
	static void setup() {
		System.setProperty("webdriver.chrome.driver", ""); //Change path to chromedriver location
		options = new ChromeOptions();
		options.addArguments("--kiosk");
	}

	@Test
	void VerifyLoginTest() {
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.boxed.com/");
		
		LoginPopupPage loginPage = new LoginPopupPage(driver);
		loginPage.login();
		
		HomePage homepage = new HomePage(driver);
		assertTrue(homepage.checkIfLoggedIn());
		
		driver.close();
	}
	
	@Test
	void FavoriteCookiesTest() {
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.boxed.com/");
		
		LoginPopupPage loginPage = new LoginPopupPage(driver);
		loginPage.login();
		
		HomePage homepage = new HomePage(driver);
		homepage.navigateToCookies();
		
		SnacksPage snacks = new SnacksPage(driver);		
		for (int i = 1; i < 6; i++) {
			snacks.toFavorites(i);
		}
		
		FavoritesPage favorites = new FavoritesPage(driver);
		homepage.navigateToFavorites();
		
		assertTrue(favorites.verifyFavoritesList(snacks.favoritesList));
		
		driver.close();
	}
	
	@Test
	void FreeShippingCartTest() {
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.boxed.com/");
		
		LoginPopupPage loginPage = new LoginPopupPage(driver);
		loginPage.login();
		
		HomePage homepage = new HomePage(driver);
		homepage.navigateToAllGrocery();
		
		GroceryPage grocery = new GroceryPage(driver);
		
		do {
			grocery.toCart((int) (Math.random() * ((10 - 1) + 1) + 1));
		} while (grocery.checkFreeDelivery());
		
		homepage.checkout();
		
		CheckoutPage checkout = new CheckoutPage(driver);
		assertTrue(checkout.verifyFavoritesList(grocery.cart));
		
		driver.close();
	}

	@AfterAll //teardown
	static void teardown() {
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://www.boxed.com/");
		
		LoginPopupPage loginPage = new LoginPopupPage(driver);
		loginPage.login();
		
		HomePage homepage = new HomePage(driver);
		FavoritesPage favorites = new FavoritesPage(driver);
		homepage.navigateToFavorites();
		favorites.unfavoriteAll();
		
		homepage.deleteCart();
		
		driver.close();
	}
}

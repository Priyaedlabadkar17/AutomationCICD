package frameworks.tests;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.sun.net.httpserver.Authenticator.Retry;

import Frameworks.pageobjects.CartPage;
import Frameworks.pageobjects.CheckoutPage;
import Frameworks.pageobjects.ConfirmationPage;
import Frameworks.pageobjects.LandingPage;
import Frameworks.pageobjects.ProductCatalogue;
import frameworks.TestComponents.BaseTest;


public class ErrorValidationsTest extends BaseTest
{
	

	@Test(groups= {"ErrorHandling"},retryAnalyzer=frameworks.TestComponents.Retry.class)
	public void loginErrorValidation() throws IOException, InterruptedException
	{
		
		
		
		String productName = "ZARA COAT 3";
		landingPage.loginApplication("taimur@gmail.com", "Appspass@123");
		
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMesaage());
	}
	
	@Test
	public void productErrorValidation() throws InterruptedException
	{
		String productName = "ZARA COAT 3";
		ProductCatalogue pc= landingPage.loginApplication("pri.luvs@gmail.com", "Priya@1234");
		
		List<WebElement> products=pc.getProductList();
		pc.addToCart(productName);
		
		CartPage cartPage = pc.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay("ZARA COAT 33");
		 Assert.assertFalse(match);
	}

}

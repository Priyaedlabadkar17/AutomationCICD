package frameworks.tests;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.Action;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Frameworks.pageobjects.CartPage;
import Frameworks.pageobjects.CheckoutPage;
import Frameworks.pageobjects.ConfirmationPage;
import Frameworks.pageobjects.LandingPage;
import Frameworks.pageobjects.OrderPage;
import Frameworks.pageobjects.ProductCatalogue;
import frameworks.TestComponents.BaseTest;


public class SubmitOrderTest extends BaseTest
{
	String productName = "ZARA COAT 3";
	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String, String> input) throws IOException, InterruptedException
	{
		
		ProductCatalogue pc= landingPage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products=pc.getProductList();
		pc.addToCart(input.get("product"));
		
		CartPage cartPage = pc.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(input.get("product"));
		 Assert.assertTrue(match);  //if matches the item then will return true and assert of true means test case will pass
	    CheckoutPage checkoutPage =cartPage.gotoCheckoutPage();
	    checkoutPage.selectCountry("india");
	    
	    ConfirmationPage confirmationPage = checkoutPage.submitOrder();
	   
	    String confirmMessage =confirmationPage.getConfirmMessage();
	    
	    System.out.println(confirmMessage);
	    
        Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	    
	    
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest()
	{
		ProductCatalogue pc= landingPage.loginApplication("taimur@gmail.com", "Appspass@123");
		OrderPage orderPage = pc.goToOrdersPage();
		orderPage.verifyOrdertDisplay(productName );
		Assert.assertTrue(orderPage.verifyOrdertDisplay(productName));
	}
	
	
	
	@DataProvider
	public Object[][] getData() throws IOException
	{
		
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//frameworks//data//PurchaseOrder.json");
		//here Object is a parent data types for all 
		return new Object[][] {{data.get(0)}, {data.get(1)}}; 
	}

	/*@DataProvider
	public Object[][] getData() throws IOException
	{
		HashMap<String,String> map = new HashMap<String,String>();
	    map.put("email", "taimur@gmail.com");
		map.put("password", "Appspass@123");
		map.put("product", "ZARA COAT 3");
		
		
	HashMap<String,String> map1 = new HashMap<String,String>();
		map1.put("email", "pri.luvs@gmail.com");
	    map1.put("password", "Priya@1234");
		map1.put("product", "ADIDAS ORIGINAL");
		
		
		//return new Object [] [] {{map}, {map1}};  // 2 dimentional array
	}*/
}

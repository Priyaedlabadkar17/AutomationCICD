package frameworks.tests;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
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

import Frameworks.pageobjects.LandingPage;


public class StandAloneTest 
{

	public static void main(String[] args) 
	{
		String productName = "ZARA COAT 3";
		WebDriver driver = new ChromeDriver();
		
		LandingPage lp = new LandingPage(driver);
		
		driver.get("https://rahulshettyacademy.com/client/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		//login
		driver.findElement(By.id("userEmail")).sendKeys("taimur@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Appspass@123");
		driver.findElement(By.id("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));  //wait till products gets loaded.
		
		//Add to cart - take all carts into list by taking one common class
		List<WebElement> products =driver.findElements(By.cssSelector(".mb-3"));
		
		//iterate through streams.
		//Here in prod variable it will store the product with name "ZARA COAT 3"
		WebElement prod = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals("ZARA COAT 3")).findFirst().orElse(null); //using tag b
		
		//and istead of finiding using driver.findElement we are finidinf add to catr button using the particular product we got in prod variable
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();   //here card-body is class and next to that button tag is there i.e parent to child and 2 buttons were there so it that last button thats why last-of-type we used
		
		//while adding the product into the cart we have to validate toaster and lodder. 
		//so will give explicit wait that tells wait till that toaster gets display 
		
		
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#toast-container")));
		
		//wait till loadder gets disappear as cart button will be disabled till loader is there.
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
		
		//click on cart button
		driver.findElement(By.cssSelector("[routerlink*='cart']")).click();
		
		//Incart
		List<WebElement> cartItems =driver.findElements(By.xpath("//div[@class='cartSection']//h3"));
	    Boolean match = cartItems.stream().anyMatch(cartItem->cartItem.getText().equals(productName));   //anyMatch will verify that whatever cartItems we got it matches to productName = "ZARA COAT 3"
	    Assert.assertTrue(match);  //if matches the item then will return true and assert of true means test case will pass
	    
	    //checkout
	    driver.findElement(By.xpath("//li[@class='totalRow']/button")).click();
	    
	    Actions a = new Actions(driver);
	    a.sendKeys(driver.findElement(By.xpath("//input[@placeholder='Select Country']")), "India").build().perform();
	    
	    //give explicit wait till country options appear.
	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
	    driver.findElement(By.xpath("//button[contains(@class,'ta-item')][2]")).click();
	    driver.findElement(By.cssSelector(".action__submit")).click();
	    
	    String confirmMessage = driver.findElement(By.xpath("//h1[@class='hero-primary']")).getText();
	    Assert.assertTrue(confirmMessage.equalsIgnoreCase(" Thankyou for the order. "));
	    driver.close();
	    
	
		
		
		
		

	}

}

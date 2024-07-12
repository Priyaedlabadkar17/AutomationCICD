package Frameworks.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Frameworks.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent
{
     WebDriver driver;
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver,this);
		
	}
	
	@FindBy(xpath= "//input[@placeholder='Select Country']")
	private WebElement country;
	
	
	@FindBy(xpath = "(//button[contains(@class,'ta-item')])[2]")
	private WebElement selectCountry;
    
	//@FindBy(css = ".action__submit")
	//private WebElement submit;
	
	@FindBy(xpath= "//div[@class='actions']/*[contains(text(),'Place Order')]")
	
	private WebElement submit;
	
	By results = By.cssSelector(".ta-results");
	
	
	
	public void selectCountry(String countryName) throws InterruptedException
	{
		Actions a = new Actions(driver);
	    a.sendKeys(country, countryName).build().perform();
	    
	    //give explicit wait till country options appear.
	    waitForElementToAppear(By.cssSelector(".ta-results"));
	    selectCountry.click();
	    Thread.sleep(1000);
	}
	
	public ConfirmationPage submitOrder() throws InterruptedException
	{
		Thread.sleep(2000);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,1000)");
		
		/*JavascriptExecutor js = (JavascriptExecutor)driver;   
		//above is driver casting
		js.executeAsyncScript("arguments[0].scrollIntoView(true);", submit);
		//js.executeAsyncScript("arguments[0].click();", submit);
		Thread.sleep(3000);*/
		submit.click();
		
		ConfirmationPage confirmation = new ConfirmationPage(driver);
		return confirmation;
	}
}

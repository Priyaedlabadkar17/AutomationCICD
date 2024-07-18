package Frameworks.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Frameworks.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent
{
       //landing to login page
	WebDriver driver;
	public LandingPage(WebDriver driver) //constructor 
	{
		//initialization for driver from standalonetest class to here local driver variable 
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//But here driver is not there in pagefactory pattern so will initialize it in the constructor as before anything in the class constructor is first thing which gets executes.
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	@FindBy(id="userEmail")       //using Pagefactory we can define same webelement in this format
	WebElement userEmail;
	
	//WebElement password = driver.findElement(By.id("userPassword"));
	@FindBy(id="userPassword")
    WebElement password;
	
	//WebElement loginBtn = driver.findElement(By.id("login"));
	@FindBy(id="login")
	WebElement loginBtn;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	public ProductCatalogue loginApplication(String email, String pwd)
	{
		userEmail.sendKeys(email);
		password.sendKeys(pwd);
		loginBtn.click();
		
		ProductCatalogue pc = new ProductCatalogue(driver);
		return pc;
	}
	
	public String getErrorMesaage()
	{
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	public void goTo()
	{
		driver.get("https://rahulshettyacademy.com/client/");
	}

	
	
}

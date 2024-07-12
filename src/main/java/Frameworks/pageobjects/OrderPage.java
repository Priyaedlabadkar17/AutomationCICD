package Frameworks.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Frameworks.AbstractComponents.AbstractComponent;

public class OrderPage extends AbstractComponent
{

	WebDriver driver;
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	
	
	@FindBy (xpath = "//li[@class='totalRow']/button")
	WebElement checkoutEle;
	
	@FindBy (css = "tr td:nth-child(3)")
	List<WebElement> productNames;
	
	
	public Boolean verifyOrdertDisplay(String productName)
	{
		Boolean match = productNames.stream().anyMatch(cartItem->cartItem.getText().equals(productName));
		return match;
	}
	
	
}

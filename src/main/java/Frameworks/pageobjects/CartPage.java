package Frameworks.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import Frameworks.AbstractComponents.AbstractComponent;

public class CartPage extends AbstractComponent
{

	WebDriver driver;
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	
	@FindBy (xpath="//div[@class='cartSection']//h3")
	List<WebElement> cartProducts;
	
	@FindBy (xpath="//li[@class='totalRow']/button")
	WebElement checkoutEle;
	
	
	public Boolean verifyProductDisplay(String productName)
	{
		Boolean match = cartProducts.stream().anyMatch(cartItem->cartItem.getText().equals(productName));
		return match;
	}
	
	public CheckoutPage gotoCheckoutPage()
	{
		checkoutEle.click();
		//CheckoutPage checkoutPage = new CheckoutPage(driver);
		return new CheckoutPage(driver);
	}

}

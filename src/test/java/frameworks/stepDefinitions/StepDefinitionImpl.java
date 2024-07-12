package frameworks.stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Frameworks.pageobjects.CartPage;
import Frameworks.pageobjects.CheckoutPage;
import Frameworks.pageobjects.ConfirmationPage;
import Frameworks.pageobjects.LandingPage;
import Frameworks.pageobjects.ProductCatalogue;
import frameworks.TestComponents.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImpl extends BaseTest
{
	//public LandingPage landingPage;
	public  ProductCatalogue pc;
	public ConfirmationPage confirmationPage;
	
   @Given("I landed on Ecommerce Page")
   public void I_landed_on_Ecommerce_page() throws IOException
   {
	   landingPage = launchApplication();
   }
   
   @Given("Logged in with username {string} and password {string}")
   public void logged_in_with_username_and_password(String username, String password) {
	  pc = landingPage.loginApplication(username,password);
   }
   
   @When("I add product {string} to cart")
   public void i_add_product_to_cat(String productName) throws InterruptedException
   {
	   List<WebElement> products=pc.getProductList();
		pc.addToCart(productName);
   }
   
   @And("Checkout {string} and submit the order")
   public void checkout_and_submit_theOrder(String productName) throws InterruptedException
   {
	   CartPage cartPage = pc.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);  //if matches the item then will return true and assert of true means test case will pass
	    CheckoutPage checkoutPage =cartPage.gotoCheckoutPage();
	    checkoutPage.selectCountry("india");
	    Thread.sleep(3000);
	   confirmationPage = checkoutPage.submitOrder();
   }
   
   @Then("{string} message is displayed on ConfirmationPage")
   public void message_is_displayed_on_confirmationPge(String string)
   {
	   String confirmMessage =confirmationPage.getConfirmMessage();
	    
	    System.out.println(confirmMessage);
	    
       Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
   }
   
}

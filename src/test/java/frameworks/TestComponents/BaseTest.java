package frameworks.TestComponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import Frameworks.pageobjects.LandingPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest 
{
	public LandingPage landingPage;
	public WebDriver driver;
	public WebDriver initializeDriver() throws IOException
	{
		//Properties class
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//Frameworks//resources//GlobalData.properties");
		prop.load(fis);
		
		String browserName = System.getProperty("browser") !=null ? System.getProperty("browser") : prop.getProperty("browser");
		//String browserName = prop.getProperty("browser");
		
		if (browserName.contains("chrome")) 
		{
			ChromeOptions options = new ChromeOptions();
			//WebDriverManager.chromedriver().setup();
			if(browserName.contains("headless"))
			{
			options.addArguments("--headless");
			driver = new ChromeDriver(options);
			}		
			driver = new ChromeDriver();
			driver.manage().window().setSize(new Dimension(1440,900));
		}//full screen
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","C://Users//hp//Desktop//geckodriver");
			driver=new FirefoxDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			//edge
		}
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		return driver;
	}
	
	 public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
     {
   	  //read json to string 
   	 String jsonContent = FileUtils.readFileToString(new File(filePath));
   	  
   	 //convert String to HashMap - Jackson  Databind  this dependency will help us to convert the string to HashMap
   	 
   	 ObjectMapper mapper = new ObjectMapper();  //this class provides readvalue() method
   	
   	 List<HashMap<String, String>> data= mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
   	});  //here we are reading the value from json string using readValue() method and it will convert it into HashMap and all HashMap put it into one list
   	
   	 //so this data returns List of Hashmaps  {map,map}
   	 
   	 return data;
     }
	
	@BeforeMethod(alwaysRun=true)
	public LandingPage launchApplication() throws IOException
	{
		driver = initializeDriver();
	    landingPage = new LandingPage(driver);
		landingPage.goTo();
		
		return landingPage;
	
	}
	
	@AfterMethod(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException
	{
		TakesScreenshot ts = (TakesScreenshot)driver;
		File sourse = ts.getScreenshotAs(OutputType.FILE);	
		File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
		FileUtils.copyFile(sourse, file);
		return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
		
	}
}

package Jenkins.Jenkins;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ConfigFile.Propertiesfile;
//import com.sn.testcases.casemanagement.DataProviderClass;

public class Common extends Excel{
  static WebDriver driver;
  String url= "";
  static ExtentReports report;
  static ExtentTest test = new ExtentTest(Common.class.getName(), "test");
  static JavascriptExecutor js = (JavascriptExecutor)driver;
  public static String cartvalue = "//span[contains(text(),'Cart')]/..//span[@id='nav-cart-count']";
  public static String proceedtobuy = "//a[contains(text(),' Proceed to Buy')][1]";
  public  String username;
  public  String password;
//  @BeforeClass
//	public void startest() 
//	{
//		//report = new ExtentReports("E:\\Kunal WorkSpace\\Jenkins\\ExtentReport.html",true);
//		//test = report.startTest("Start Demo");	
//	}
	
  @BeforeTest
  
  public void LaunchBrowser() throws Exception {
	  report = new ExtentReports("C:\\Users\\kunal.seth\\test\\SelenimJenkinsRepository\\ExtentReport.html",true);
		test = report.startTest("Start Demo");
	  
	  System.out.println("Hi");
	  if(Propertiesfile.readpropertiesfile("browser").equals("Chrome")) {
		  System.setProperty("webdriver.chrome.driver","E:\\Kunal WorkSpace\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
		  driver = new ChromeDriver();
		  System.out.println("Browser=" + Propertiesfile.readpropertiesfile("browser").toString());
	  }
	  
	  String Expectedurl = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";
	//  String[][] str = readexcel("URL");
	  //System.out.println(str);
	  driver.get(Propertiesfile.readpropertiesfile("appURL"));
	  driver.manage().window().maximize();
	  //driver.get("https://www.amazon.in/");
	  String Actualtitle = driver.getTitle();
	  System.out.println(Actualtitle);
	  System.out.println("Beofre Assertion"+ Expectedurl + Actualtitle );
	  if(Expectedurl.equalsIgnoreCase(Actualtitle))
	  {
		  test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+"Test Passed");
	  }
	  else
	  {
		  test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+"Test Failed");
	  }
	  
	  //Assert.assertEquals(Actualtitle,Expectedurl);
	  System.out.println("After Assertion"+ Expectedurl + Actualtitle + "Title Matched");
	  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	  //report.endTest(test);
	  //report.flush();
  }
  
  

  	public String capture(WebDriver driver2) throws IOException {
	  TakesScreenshot scrshot = (TakesScreenshot)driver;
	  File scrfile = scrshot.getScreenshotAs(OutputType.FILE);
	  File destpath = new File("E:\\Kunal WorkSpace\\Jenkins\\ScreenShot\\ScreenShot_"+timestamp()+".png");
	  String capscrshot = destpath.getAbsolutePath();
	  FileUtils.copyFile(scrfile, destpath);
	  return capscrshot;
	  
  }
	public String timestamp() {
		
		return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
	}
	
	public void scrollpageup() {
		js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(500,0)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void scrolldown()
	{
		js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0,500)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public int checkcartvalue()
	{
		int cartval =Integer.parseInt(driver.findElement(By.xpath(Common.cartvalue)).getAttribute("Value"));
		System.out.println("Cart value contains "+cartval+" values");
		return cartval;
		
	}
	public void removecartvalue() throws IOException
	{
		int cartvalue = checkcartvalue();
		if(cartvalue > 1)
		{
			test.log(LogStatus.INFO, test.addScreenCapture(capture(driver))+"Cart contains more than one item");
			driver.close();
		}
		else {
			test.log(LogStatus.PASS, test.addScreenCapture(capture(driver))+"Cart contains more than one item");
			click("Proceed to Buy",By.xpath(Common.proceedtobuy));
			//driver.findElement(By.xpath(Locators.proceedtobuy)).click();
			
		}
	}
	
	
	
	
	public void click(String elementname, By btnname) 
	{
		try {
		driver.findElement(btnname).click();
		System.out.println("Element "+elementname+" got cliked successfully");
		}catch(Exception e)
		{
			System.out.println("Not able to click on Element "+elementname);
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public void dbclick(String elementname, By btnname) {
		Actions action = new Actions(driver);
		WebElement elelocator = driver.findElement(btnname);
		action.doubleClick(elelocator).perform();
		try {
			System.out.println("Element "+elementname+" got cliked successfully");
		}catch(Exception e)
		{
			System.out.println("Not able to click on Element "+elementname);
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	
	public void closebrowser() {
		driver.quit();
	}
	
	
  @AfterClass
	public void endtest() 
	{
		report.endTest(test);
		report.flush();
	}

}

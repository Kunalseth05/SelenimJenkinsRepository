package Jenkins.Jenkins;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.server.DriverFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Common {
  static WebDriver driver;
  String url= "";
  static ExtentReports report;
  static ExtentTest test = new ExtentTest(Common.class.getName(), "test");
  static JavascriptExecutor js = (JavascriptExecutor)driver;
  
  
//  @BeforeClass
//	public void startest() 
//	{
//		//report = new ExtentReports("E:\\Kunal WorkSpace\\Jenkins\\ExtentReport.html",true);
//		//test = report.startTest("Start Demo");	
//	}
	
  @BeforeTest
  
  public void LaunchBrowser() throws InterruptedException, IOException {
	  report = new ExtentReports("E:\\Kunal WorkSpace\\Jenkins\\ExtentReport.html",true);
		test = report.startTest("Start Demo");
	  System.setProperty("webdriver.chrome.driver","E:\\Kunal WorkSpace\\ChromeDriver\\chromedriver_win32\\chromedriver.exe");
	  driver = new ChromeDriver();
	  String Expectedurl = "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more";
	  driver.get("https://www.amazon.in/");
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
		int cartval =Integer.parseInt(driver.findElement(By.xpath(Locators.cartvalue)).getText());
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
			driver.findElement(By.xpath(Locators.proceedtobuy)).click();
			
		}
	}
	
	public void loginintoapp() throws IOException
	{
		driver.findElement(By.xpath(Locators.loginid)).sendKeys("7045073486");
		driver.findElement(By.xpath(Locators.continuebtn)).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		if(driver.findElement(By.xpath(Locators.loginpassword)).isDisplayed())
		{
			test.log(LogStatus.PASS, test.addScreenCapture(capture(driver))+"Password option is enable");
			driver.findElement(By.xpath(Locators.loginpassword)).sendKeys("Kunalseth@0912");
			driver.findElement(By.xpath(Locators.loginbtn)).click();
			driver.manage().timeouts().implicitlyWait(3,TimeUnit.SECONDS);
			if(driver.findElement(By.xpath(Locators.incorrectpassmsg)).isDisplayed())
			{
				test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+"User is entering Wrong credential");
				driver.quit();
			}
			else	
			{
				test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+"User is able to Login successfully ");
			}
		}
		else
		{
			test.log(LogStatus.FAIL, test.addScreenCapture(capture(driver))+"Entered Number is not registered");
		}
		driver.quit();
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

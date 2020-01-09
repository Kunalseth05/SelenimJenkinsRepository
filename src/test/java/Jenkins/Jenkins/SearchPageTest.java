package Jenkins.Jenkins;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;



public class SearchPageTest extends Common
{
	@Test(priority=1)
	public void searchitem() throws IOException
	{
		
		driver.findElement(By.xpath(Locators.searchtextbox)).click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.findElement(By.xpath(Locators.searchtextbox)).sendKeys("bajaj press");
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.findElement(By.xpath(Locators.clickongotbn)).click();
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		String expectedtitle = "Amazon.in: bajaj press";
		String actualtitle = driver.getTitle();
		System.out.println(actualtitle);
		if(expectedtitle.equalsIgnoreCase(actualtitle))
		{
			test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+"Test Passed");
		}
		else
		{
			test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+"Test Failed");
		}
	}
	@Test(priority=2)
	public void clickonsearchitem() throws IOException 
	{
		
		scrolldown();
		driver.findElement(By.xpath(Locators.clickonsearchitem)).click();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		String oldtab = driver.getWindowHandle();
		ArrayList<String> newtab = new ArrayList<String>(driver.getWindowHandles());
		newtab.remove(oldtab);
		driver.switchTo().window(newtab.get(0));
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		if(driver.findElement(By.xpath(Locators.clickonbuynowbtn)).isDisplayed())
		{
			test.log(LogStatus.PASS,test.addScreenCapture(capture(driver))+"Buy now option is present");
			driver.findElement(By.xpath(Locators.clickonbuynowbtn)).click();
			String signintitle = "Amazon Sign In";
			if(driver.getTitle().equalsIgnoreCase(signintitle))
			{
				test.log(LogStatus.INFO,"User need to Login before buying an selected item");
				loginintoapp();
			}
			else
			{
				test.log(LogStatus.INFO,"User is already logged in into App");
			}
			
		}
		else
		{
			
		test.log(LogStatus.FAIL,test.addScreenCapture(capture(driver))+"Buy now option is present");
		}
		removecartvalue();
	}
	
}

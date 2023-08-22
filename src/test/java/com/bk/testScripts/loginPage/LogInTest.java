package com.bk.testScripts.loginPage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bk.testbase.TestBase;
import com.bk_helper.assertion.AssertionHelper;
import com.bk_helper.browserconfiguration.config.ObjectReader;
import com.bk_pageObject.LoginPage;

/*
 * @author  Kajol Gupta
 *
 */
public class LogInTest extends TestBase {

	@Test(priority = 0, description = "Login with valid credentials")
	public void LoginWithValidCredentials() {

		loginPage = new LoginPage(driver);
		loginPage.loginToApplication(ObjectReader.reader.getUserName(), ObjectReader.reader.getPassword());

		boolean status = loginPage.verifySuccessLogin();
		if (status) {
			loginPage.logout();
			status = loginPage.verifySuccessLogOut();
			AssertionHelper.updateTestStatus(status);
		} else {

			AssertionHelper.updateTestStatus(status);
		}
	}

	@Test(dataProvider = "EmailTestData", priority = 0, description = "Login with invalid email credentials")
	public void LoginWithInvalidCredentials(String scenario, String email) {
		loginPage = new LoginPage(driver);
		
		WebElement ee = driver.findElement(By.xpath("//input[@id='i0116']"));
		ee.clear();
		ee.sendKeys(email);
		driver.findElement(By.xpath("//input[@id='idSIButton9']")).click();
		
		if (scenario.equals("invalidEmail1")) {
			String actualErrorMsg = driver.findElement(By.id("usernameError")).getText();
			Assert.assertEquals(actualErrorMsg, "Enter a valid email address, phone number, or Skype name.");
		} else if (scenario.equals("BlankEmail")) {
			String actualErrorMsg = driver.findElement(By.id("usernameError")).getText();
			Assert.assertEquals(actualErrorMsg, "Enter a valid email address, phone number, or Skype name.");
		} else if (scenario.contains("invalidEmail2")) {
			String actualErrorMsg = driver.findElement(By.id("usernameError")).getText();
			Assert.assertEquals(actualErrorMsg, "This username may be incorrect. Make sure you typed it correctly. Otherwise, contact your admin.");
		} else if (scenario.equals("invalidEmail2")) {
			String actualErrorMsg = driver.findElement(By.id("usernameError")).getText();
			Assert.assertEquals(actualErrorMsg, "We couldn't find an account with that username.");
		}

	}

	@DataProvider(name = "EmailTestData")
	public Object[][] InvalidEmailTestData() {
		Object[][] getData = { { "invalidEmail1", "kajol.guptaa@beyondkey" }, 
				{ "BlankEmail", " " },
				{ "invalidEmail2", "kajol.gupta123@beyondkey.com" }, 
				{ "invalidEmail3", "kajol.guptabeyondkey.com" } 
			};
		return getData;

	}
	
	@Test(dataProvider = "EmailPasswordTestData", priority = 0, description = "Login with invalid email/password credentials")
	public void LoginWithInvalidCredentials(String scenario, String email, String pwd) {
		loginPage = new LoginPage(driver);
		
		WebElement ee = driver.findElement(By.xpath("//input[@id='i0116']"));
		ee.clear();
		ee.sendKeys(email);
		driver.findElement(By.xpath("//input[@id='idSIButton9']")).click();
		driver.findElement(By.name("passwd")).sendKeys(pwd);
		driver.findElement(By.xpath("//input[@value=\"Sign in\"]")).click();
		
		if (scenario.equals("InvalidPassword1")) {
			String actualErrorMsg = driver.findElement(By.id("passwordError")).getText();
			Assert.assertEquals(actualErrorMsg, "Your account or password is incorrect. If you don't remember your password, reset it now.");
		} else if (scenario.equals("BlankPassword")) {
			String actualErrorMsg = driver.findElement(By.id("passwordError")).getText();
			Assert.assertEquals(actualErrorMsg, "Your account or password is incorrect. If you don't remember your password, reset it now.");
		} else if (scenario.contains("invalidPassword2")) {
			String actualErrorMsg = driver.findElement(By.id("passwordError")).getText();
			Assert.assertEquals(actualErrorMsg, "Your account or password is incorrect. If you don't remember your password, reset it now.");
		} else if (scenario.equals("invalidPassword3")) {
			String actualErrorMsg = driver.findElement(By.id("passwordError")).getText();
			Assert.assertEquals(actualErrorMsg, "Your account or password is incorrect. If you don't remember your password, reset it now.");
		}

	}

	@DataProvider(name = "EmailPasswordTestData")
	public Object[][] InvalidEmailPasswordTestData() {
		Object[][] setData = { { "InvalidPassword1", "kajol.gupta@beyondkey.com", "Test@123" }, 
				{ "BlankPassword", "kajol.gupta@beyondkey.com", " " },
				{ "invalidPassword2", "kajol.gupta@beyondkey.com", "Beyond@333" }, 
				{ "invalidPassword3", "kajol.gupta@beyondkey.com", "   " } 
			};
		return setData;

	}

}
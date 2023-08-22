package com.bk.testScripts.loginPage;

import java.util.concurrent.TimeUnit;

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
	public void LoginWithValidCredentials() throws InterruptedException {

		loginPage = new LoginPage(driver);
		loginPage.loginToApplication(ObjectReader.reader.getUserName(), ObjectReader.reader.getPassword());
		System.out.println("Added");
		boolean status = loginPage.verifySuccessLogin();
			if (status) {
				loginPage.logout();
				status = loginPage.verifySuccessLogOut();
				AssertionHelper.updateTestStatus(status);
			} else {

				AssertionHelper.updateTestStatus(status);
			}
		

	}

}
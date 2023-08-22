package com.bk_pageObject;

import org.apache.log4j.Logger;
import org.jboss.aerogear.security.otp.Totp;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.aventstack.extentreports.Status;
import com.bk.testbase.TestBase;
import com.bk_helper.assertion.VerificationHelper;
import com.bk_helper.browserconfiguration.config.ObjectReader;
import com.bk_helper.logger.LoggerHelper;
import com.bk_helper.wait.WaitHelper;

/*
 * @author  Kajol Gupta
 *
 */
public class LoginPage {

	private WebDriver driver;
	private final Logger log = LoggerHelper.getLogger(LoginPage.class);

	WaitHelper waitHelper;

	@FindBy(name = "loginfmt")
	WebElement emailAddress;

	@FindBy(xpath = "//input[@value=\"Next\"]")
	WebElement NextBtn;

	@FindBy(name = "passwd")
	WebElement password;

	@FindBy(xpath = "//input[@value=\"Sign in\"]")
	WebElement submitLogin;

	@FindBy(name = "otc")
	WebElement getOTP;

	@FindBy(xpath = "//input[@id='idSubmit_SAOTCC_Continue']")
	WebElement verifyBtn;

	@FindBy(xpath = "//div[contains(text(),'Stay signed in?')]")
	WebElement StaySignIn;

	@FindBy(xpath = "//input[@id='idSIButton9']")
	WebElement clickToYes;

	@FindBy(xpath = "//iframe[@id='AppLandingPage']")
	WebElement switchToAppsFrame;

	@FindBy(xpath = "//div[@id='AppDetailsSec_1_Item_35']")
	WebElement appToSelect;

	@FindBy(xpath = "//iframe[@id=\"WelcomePageIframe\"]")
	WebElement switchToWelcomeScreenFrame;

	@FindBy(xpath = "//button[@id=\"cancelButton\"]")
	WebElement closeWelcomeScreen;

	@FindBy(xpath = "//span[text()=\"Right Cause\"]")
	WebElement searchObject;

	@FindBy(id = "save")
	WebElement verification;

	@FindBy(id = "error")
	WebElement loginError;

	@FindBy(xpath = "//div[@id='mectrl_headerPicture']")
	WebElement profileIcon;

	@FindBy(xpath = "//button[text()=\"Sign out\"]")
	WebElement logout;
	
	@FindBy(xpath="//div[text()=\"Pick an account\"]")
	WebElement signoutPage;

	public LoginPage(WebDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		waitHelper = new WaitHelper(driver);
		waitHelper.waitForElement(emailAddress, ObjectReader.reader.getExplicitWait());

		TestBase.logExtentReport("Login Page Object Created");

	}

	public void enterEmailAddress(String emailAddress) {
		this.emailAddress.clear();
		log.info("entering email address...." + emailAddress);
		logExtentReport("entering email address...." + emailAddress);
		this.emailAddress.sendKeys(emailAddress);
	}

	public void clickOnNextButton() {
		log.info("Clickin on next button");
		logExtentReport("Clicking on next button");
		this.NextBtn.click();
	}

	public void enterPassword(String password) {
		this.password.clear();
		log.info("entering password...." + password);
		logExtentReport("entering password....******");
		this.password.sendKeys(password);
	}

	public void clickOnSubmitButton() {

		log.info("clicking on submit button...");
		logExtentReport("clicking on submit button...");
		this.submitLogin.click();

	}

	public void enterOTP() {
		log.info("entering TOTP....");
		logExtentReport("entering TOTP....******");
		Totp totp = new Totp("wn77cg7tgthrgfts");
		String twoFactorCode = totp.now();
		this.getOTP.sendKeys(twoFactorCode);
	}

	public void clickOnVerifyButton() {

		log.info("clicking on veriy button...");
		logExtentReport("clicking on verify button...");
		this.verifyBtn.click();

	}

	public void handleAdditionalPopup() {

		log.info("clicking on yes button to continue...");
		logExtentReport("clicking on yes button to continue...");
		clickToYes.click();
		log.info("User Login successfully...");
	}

	public void clickOnRightCauseApp() {

		log.info("Wait for the frame to be visible...");
		logExtentReport("Wait for the frame to be visible...");
		waitHelper.waitForframeToBeAvailableAndSwitchToIt(switchToAppsFrame, 5);
		log.info("Select the Right Cause App...");
		logExtentReport("Select the Right Cause App...");
		this.appToSelect.click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void closeWelcomeScreen()  {

		log.info("Reloading the page to close the Welcome window...");
		logExtentReport("Reloading the page to close the Welcome window...");
		try {
			Thread.sleep(5000);
			driver.navigate().refresh();
			Thread.sleep(5000);
		} catch (NoSuchWindowException | InterruptedException e) {
			e.getCause();
		}

	}

	public void clickOnProfileIconButton() {

		log.info("clicking on profile icon button...");
		logExtentReport("clicking on profile icon button...");
		this.profileIcon.click();

	}

	public void clickOnLogoutButton() {

		log.info("clicking on logout button...");
		logExtentReport("clicking on logout button...");
		waitHelper.waitForElement(logout, 10);
		this.logout.click();

	}

	public void loginToApplication(String emailAddress, String password) {
		enterEmailAddress(emailAddress);
		clickOnNextButton();
		enterPassword(password);
		clickOnSubmitButton();
		enterOTP();
		clickOnVerifyButton();
		handleAdditionalPopup();
		clickOnRightCauseApp();
		closeWelcomeScreen();
	}

	public boolean verifySuccessLogin() {
		waitHelper.waitForElement(searchObject, 5);
		return new VerificationHelper(driver).isDisplayed(searchObject);
	}

	public String verifyFailLoginMsg() {

		return new VerificationHelper(driver).getText(loginError);
	}

	public boolean verifyBlankUserLogin() {
		return new VerificationHelper(driver).isDisplayed(emailAddress);
	}

	public void verifyUserLogin(boolean result) {

		Assert.assertEquals(searchObject.isDisplayed(), result);
		logExtentReport("User Login successfully....");

	}

	public void logout() {
		clickOnProfileIconButton();
		clickOnLogoutButton();
	}

	public void verifyUserLogOut(boolean result) {

		Assert.assertEquals(emailAddress.isDisplayed(), result);
		logExtentReport("User LogOut successfully....");

	}

	public boolean verifySuccessLogOut() {
		return new VerificationHelper(driver).isDisplayed(signoutPage);
	}

	public void logExtentReport(String s1) {
		TestBase.test.log(Status.INFO, s1);
	}

}

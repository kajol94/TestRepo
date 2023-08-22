package com.bk_helper.browserconfiguration;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.bk_helper.resource.ResourceHelper;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author  Kajol Gupta
 *
 */
public class ChromeBrowser {

	public ChromeOptions getChromeOptions() {
		
		ChromeOptions option = new ChromeOptions();
		option.addArguments("--test-type");
		option.addArguments("--disable-popup-blocking");
		
		DesiredCapabilities chrome = DesiredCapabilities.chrome();
		chrome.setJavascriptEnabled(true);
		
		option.setCapability(ChromeOptions.CAPABILITY, chrome);	
		//Linux
		if(System.getProperty("os.name").contains("Linux")){
			option.addArguments("--headless", "window-size=1024,768", "--no-sandbox");
		}
		return option;
	}

	public WebDriver getChromeDriver(ChromeOptions cap) {

		if (System.getProperty("os.name").contains("Mac")){
			//WebDriverManager.chromedriver().setup();
			System.setProperty("webdriver.chrome.driver", ResourceHelper.getResourcePath("src/main/resources/drivers/chromedriver"));
			return new ChromeDriver(cap);
		}
		else if(System.getProperty("os.name").contains("Window")){
			WebDriverManager.chromedriver().setup();
			//System.setProperty("webdriver.chrome.driver", ResourceHelper.getResourcePath("src/main/resources/drivers/chromedriver.exe"));
			return new ChromeDriver(cap);
		}
		else if(System.getProperty("os.name").contains("Linux")){
			System.setProperty("webdriver.chrome.driver", "/usr/bin/chrome");
			return new ChromeDriver(cap);
		}
		return null;
	}
}

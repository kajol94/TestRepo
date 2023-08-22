package com.bk_helper.browserconfiguration.config;

import com.bk_helper.browserconfiguration.BrowserType;

/**
 * 
 * @author  Rishabh Jain
 *
 */
public interface ConfigReader {
	
	public int getImpliciteWait();
	public int getExplicitWait();
	public int getPageLoadTime();
	public BrowserType getBrowserType();
	public String getUrl();
	public String getUserName();
	public String getPassword();

}

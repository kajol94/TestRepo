package com.bk.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.bk_helper.browserconfiguration.config.ObjectReader;
import com.bk_helper.resource.ResourceHelper;

/**
 * 
 * @author Kajol Gupta
 *
 */
public class ExtentManager {

	private static ExtentReports extent;
	static DateFormat dateFormatFile = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	static Date date = new Date();
	static String TimeStamp = dateFormatFile.format(date);

	public static ExtentReports getInstance() {
		if (extent == null) {
			String location = ResourceHelper.getResourcePath("test-output/extent_report/extent" + TimeStamp + ".html");
			return createInstance(location);
		} else {
			return extent;
		}
	}
	 
	public static ExtentReports createInstance(String fileName) {
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);
		
		htmlReporter.viewConfigurer()
	    .viewOrder()
	    .as(new ViewName[] { 
		   ViewName.DASHBOARD, 
		   ViewName.TEST, 
		  
		   ViewName.EXCEPTION, 
		   ViewName.LOG 
		})
	  .apply();
		htmlReporter.config().setTheme(Theme.STANDARD);
	    htmlReporter.config().setDocumentTitle("BK Automation Report");
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName("BK Automation Report");
		extent = new ExtentReports();
		extent.setSystemInfo("os", System.getProperty("os.name"));
		extent.setSystemInfo("Browser", ObjectReader.reader.getBrowserType()+"");
		extent.setSystemInfo("Base URL", ObjectReader.reader.getUrl());
		extent.attachReporter(htmlReporter);
		return extent;
	}

}

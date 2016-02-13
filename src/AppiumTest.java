import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.Cookie.Builder;

import io.appium.java_client.*;
import io.appium.java_client.android.*;
import io.appium.java_client.ios.*;

import com.perfectomobile.selenium.util.EclipseConnector;

public class AppiumTest {
	
	public static void main(String[] args) throws MalformedURLException, IOException {

		System.out.println("Run started");
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		String host = "myHost.perfectomobile.com";		
		capabilities.setCapability("user", "myUser");
		capabilities.setCapability("password", "myPassword");
		capabilities.setCapability("deviceName", "12345");
		capabilities.setCapability("automationName", "Appium");

		// Call this method if you want the script to share the devices with the Perfecto Lab plugin.
		setExecutionIdCapability(capabilities, host);

		// Application settings examples.
		// capabilities.setCapability("app", "PRIVATE:applications/Errands.ipa");
		// For Android:
		// capabilities.setCapability("appPackage", "com.google.android.keep");
		// capabilities.setCapability("appActivity", ".activities.BrowseActivity");
		// For iOS:
		// capabilities.setCapability("bundleId", "com.yoctoville.errands");

		AndroidDriver driver = new AndroidDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
		// IOSDriver driver = new IOSDriver(new URL("https://" + host + "/nexperience/perfectomobile/wd/hub"), capabilities);
		
		try {
			// write your code here

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				driver.close();
				
				// In case you want to down the report or the report attachments, do it here.
				// RemoteWebDriverUtils.downloadReport(driver, "pdf", "C:\\test\\report");
				// RemoteWebDriverUtils.downloadAttachment(driver, "video", "C:\\test\\report\\video", "flv");
				// RemoteWebDriverUtils.downloadAttachment(driver, "image", "C:\\test\\report\\images", "jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
			driver.quit();
		}
		
		System.out.println("Run ended");
	}
	
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

 	private static void setExecutionIdCapability(DesiredCapabilities capabilities, String host) throws IOException {
		EclipseConnector connector = new EclipseConnector();
		String eclipseHost = connector.getHost();
		if ((eclipseHost == null) || (eclipseHost.equalsIgnoreCase(host))) {
			String executionId = connector.getExecutionId();
			capabilities.setCapability(EclipseConnector.ECLIPSE_EXECUTION_ID, executionId);
		}
	}
}

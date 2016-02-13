import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.html5.*;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.Cookie.Builder;

import com.perfectomobile.selenium.util.EclipseConnector;

public class MobileRemoteTest {
	
	public static void main(String[] args) throws MalformedURLException, IOException {

		System.out.println("Run started");
		String browserName = "mobileOS";
		String personaName = WindTunnelUtils.GEORGIA;
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		MyEnvironmentSettings.setCloudCredentials(capabilities);
		String reportURL = "";
		long measuredLaunchTime[] = { -1},
				measuredNavTime[] = { -1};
		

		capabilities.setCapability("scriptName", "StarbucksTest");		
		capabilities.setCapability("windTunnelPersona", personaName);	
//		capabilities.setCapability("windTunnelPersonaKey", "PUBLIC:Personas\\Alon.json");

		// Use the automationName capability to define the required framework - Appium (this is the default) or PerfectoMobile.
		// capabilities.setCapability("automationName", "PerfectoMobile");
		
		// Call this method if you want the script to share the devices with the Perfecto Lab plugin.
		//setExecutionIdCapability(capabilities, host);

        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://" + MyEnvironmentSettings.getCloudHost() + "/nexperience/perfectomobile/wd/hub"), capabilities);
		
		try {
			// write your code here
			try {
//				PerfectoUtils.stopDeviceLog(driver);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			PerfectoUtils.startDeviceLog(driver);
			StarbucksTest.runTest(driver, measuredLaunchTime, measuredNavTime, false);
			PerfectoUtils.stopDeviceLog(driver);				
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reportURL = (String)(driver.getCapabilities().getCapability("windTunnelReportUrl"));
				driver.close();
				
				// In case you want to down the report or the report attachments, do it here.
				RemoteWebDriverUtils.downloadReport(driver, "pdf", MyEnvironmentSettings.getReportPath()+"/rep_" + System.currentTimeMillis()); 
//				RemoteWebDriverUtils.downloadAttachment(driver, "video", MyEnvironmentSettings.getReportPath()+"/video", "flv");
//				RemoteWebDriverUtils.downloadAttachment(driver, "image", MyEnvironmentSettings.getReportPath()+"/images", "jpg");
			
			} catch (Exception e) {
				e.printStackTrace();
			}
			driver.quit();
		}
		
		System.out.println("Timer measured for "+ personaName+" launch: "+ measuredLaunchTime[0]+ " nav: "+ measuredNavTime[0]+ "\nreport URL: "+ reportURL);
		
		System.out.println("Run ended");
	}
	
	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	
	private static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	private static String getCurrentContextHandle(RemoteWebDriver driver) {		  
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
		return context;
	}

	private static List<String> getContextHandles(RemoteWebDriver driver) {		  
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
		return contexts;
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

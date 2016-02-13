import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class StarbucksTest {
	public static void runTest(RemoteWebDriver driver, long[] measuredLaunchTime, long[] measuredNavTime, boolean letsBlockSomeDomains) throws MalformedURLException {
		
		long measuredLaunchTimer = 0, 
				measuredNavTimer = 0;
		String app = "Starbucks";
		try {
			// write your code here
// ## ===>>	Go Home
			PerfectoUtils.comment(driver, "Test Start");
			Map<String, Object> params = new HashMap<>();
			
// ## ===>>	Block domains			
			if(letsBlockSomeDomains)
				ExtendedWindTunnelUtils.blockDomains(driver, "www.google.com", "maps.google.com");
			
// ## ===>>	Go Home
			WindTunnelUtils.pointOfInterest(driver, "Script Start", WindTunnelUtils.SUCCESS); 
			params = new HashMap<>();
			params.put("keySequence", "HOME");
			driver.executeScript("mobile:presskey", params);

// ## ===>>	Close Application
			PerfectoUtils.closeApp(driver, app);
			PerfectoUtils.sleep(3000);
// ## ===>>	Restart Application
			WindTunnelUtils.pointOfInterest(driver, "Launch App", WindTunnelUtils.SUCCESS);
			PerfectoUtils.launchApp(driver, app);
// ## ===>>	Start Application & device vitals measurement
			ExtendedWindTunnelUtils.startAppVitals(driver, app, true);

// ## ===>>	Validate the app launched- use OCR to get real UX metrics
			PerfectoUtils.ocrTextCheck(driver, "Stores", -1, 60);
// ## ===>>	How long did it take to launch?
			measuredLaunchTime[0] = measuredLaunchTimer = PerfectoUtils.getUXTimer(driver);
			
			WindTunnelUtils.reportTimer(driver, measuredLaunchTimer, 1000, app + " Launch Time", "Launch");

// ## ===>>	click on stores
			WindTunnelUtils.pointOfInterest(driver, "Find Stores", WindTunnelUtils.SUCCESS);
			PerfectoUtils.ocrTextClick(driver, "STORES", -1, 60);
			PerfectoUtils.ocrTextCheck(driver, "mi", -1, 60);
			measuredNavTimer = PerfectoUtils.getUXTimer(driver);
			PerfectoUtils.sleep(3000);
// ## ===>>	click on a store address
			WindTunnelUtils.pointOfInterest(driver, "Find Store", WindTunnelUtils.SUCCESS);
			PerfectoUtils.ocrTextClick(driver, "mi", -1, 60, 17, 83, -1, -1);
			PerfectoUtils.ocrTextCheck(driver, "Directions", -1, 60);
			measuredNavTimer += PerfectoUtils.getUXTimer(driver);
			PerfectoUtils.sleep(3000);

// ## ===>>	get directions to store
			WindTunnelUtils.pointOfInterest(driver, "Navigate", WindTunnelUtils.SUCCESS);
			PerfectoUtils.ocrTextClick(driver, "Directions", -1, 60);
			PerfectoUtils.ocrTextCheck(driver, "Start", -1, 60);
// ## ===>>	How long did it take to launch?
			measuredNavTimer += PerfectoUtils.getUXTimer(driver);
			WindTunnelUtils.reportTimer(driver, measuredNavTimer, 3000, app + " Navigate to store", "Nav 2 store");
			measuredNavTime[0] = measuredNavTimer;
			ExtendedWindTunnelUtils.stopVitals(driver);
			
// ## ===>> Just for fun
			driver.get("google.com");
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
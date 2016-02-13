import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

public abstract class ExtendedWindTunnelUtils {
	// start device vitals
	public static String startDeviceVitals(RemoteWebDriver driver,long interval) {
		Map<String, Object> params = new HashMap<>();
		List<String> vitals = new ArrayList<>();
		vitals.add("all");
		params.put("vitals", vitals);
		params.put("interval", Long.toString(interval));
		List<String> sources = new ArrayList<>();
		sources.add("device");
		params.put("sources", sources);
		return (String) driver.executeScript("mobile:monitor:start", params);
	}

	// start app vitals and optionally device vitals
	public static String startAppVitals(RemoteWebDriver driver,String app, boolean startDeviceVitals) {
		Map<String, Object> params = new HashMap<>();
		List<String> vitals = new ArrayList<>();
		vitals.add("all");
		params.put("vitals", vitals);
		params.put("interval", Long.toString(1));
		List<String> sources = new ArrayList<>();
		sources.add(app);
		if (startDeviceVitals)
			sources.add("device");
		params.put("sources", sources);
		return (String) driver.executeScript("mobile:monitor:start", params);
	}

	// stop vitals
	public static String stopVitals(RemoteWebDriver driver) {
		Map<String, Object> params = new HashMap<>();
		List<String> vitals = new ArrayList<>();
		vitals.add("all");
		params.put("vitals", vitals);
		return (String) driver.executeScript("mobile:monitor:stop", params);
	}
	
	// block domains
	public static String blockDomains(RemoteWebDriver driver, String... domains){
		Map<String, Object> params = new HashMap<>();
		List<String> blockedDomains = new ArrayList<>();
		for (String domain:domains)
			blockedDomains.add(domain);
		params.put("blockedDestinations", blockedDomains);
		return (String) driver.executeScript("mobile:vnetwork:update", params);                     
	}
	
	// block domains
	public static String unblockDomains(RemoteWebDriver driver, String... domains){
		Map<String, Object> params = new HashMap<>();
		List<String> blockedDomains = new ArrayList<>();
		for (String domain:domains)
			blockedDomains.add(domain);
		params.put("blockedDestinations", "-"+blockedDomains);
		return (String) driver.executeScript("mobile:vnetwork:update", params);                     
	}

}
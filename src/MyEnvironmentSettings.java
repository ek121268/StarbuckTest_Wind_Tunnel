import org.openqa.selenium.remote.DesiredCapabilities;

public abstract class MyEnvironmentSettings {
	private static final String HOST = "";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String DEVICENAME = "";
//	private static final String DEVICENAME = "";	
	private static final String REPORTPATH = "c:/test/report";
/*
	
	*/

/*	
	// ## ===>>	Your Perfecto cloud credentials 
	private static final String HOST = "yourCloud.perfectomobile.com";
	private static final String USERNAME = "your username";
	private static final String PASSWORD = "your password";
// ## ===>>	Your Perfecto device ID => optional, it can be chosen by the persona  
	private static final String DEVICENAME = "12345";
// ## ===>>	Your local machine report path to download
	
	private static final String REPORTPATH = "//Users/.../Downloads/eclipse/test/report";
*/		
	public static void setCloudCredentials(DesiredCapabilities capabilities){
		capabilities.setCapability("user", USERNAME);
		capabilities.setCapability("password", PASSWORD);
		capabilities.setCapability("deviceName", DEVICENAME);
	}

	public static String getCloudHost(){
		return HOST;
	}
	public static String getReportPath(){
		return REPORTPATH;
	}
}
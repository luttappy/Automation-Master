package sherpath.edu.app.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	
	private static Logger logger = LoggerFactory.getLogger(Util.class.getName());
	
	public static void copyDriverToTemp() throws IOException {
	    	
	    	String browserName = System.getProperty("browserName");
	    	OsCheck.OSType ostype=OsCheck.getOperatingSystemType();
	    	switch (ostype){
	    		case Windows:
					if(browserName.toLowerCase().contains("phantom")){
						String path = copyFileToTemp("drivers/phantomjs.exe", "phantomjs", ".exe" );
						Configurator.getConfig().put("PHANTOMJS_DRIVER_PATH", path);
					}
					if(browserName.toLowerCase().contains("chrome")){
						String path = copyFileToTemp("drivers/chromedriver.exe", "chromedriver", ".exe" );
						Configurator.getConfig().put("CHROME_DRIVER_PATH", path);
					}
					if(browserName.toLowerCase().contains("internet") || browserName.toLowerCase().contains("ie")){
		    			if(OsCheck.getOSArch().equalsIgnoreCase("64")){
							String path = copyFileToTemp("drivers/IEDriverServer-64.exe", "IEDriverServer-64", ".exe" );
							Configurator.getConfig().put("IE_DRIVER_PATH", path);
		    			}else{
							String path = copyFileToTemp("drivers/IEDriverServer-32.exe", "IEDriverServer-32", ".exe" );
							Configurator.getConfig().put("IE_DRIVER_PATH", path);
		    			}
					}
	    			
	    			break;
	    		case MacOS:
					if(browserName.toLowerCase().contains("phantom")){
						String path = copyFileToTemp("drivers/phantomjs-macosx", "phantomjs", "" );
						Configurator.getConfig().put("PHANTOMJS_DRIVER_PATH", path);
					}
					if(browserName.toLowerCase().contains("chrome")){
						String path = copyFileToTemp("drivers/chromedriver-macosx", "chromedriver", "");
						Configurator.getConfig().put("CHROME_DRIVER_PATH", path);
					}
	    			break;
	    		case Linux:
	    			if(OsCheck.getOSArch().equalsIgnoreCase("64")){
	    				if(browserName.toLowerCase().contains("phantom")){
	    					String path = copyFileToTemp("drivers/phantomjs-linux-64", "phantomjs" + Long.toString(System.currentTimeMillis()), "" );
	    					Configurator.getConfig().put("PHANTOMJS_DRIVER_PATH", path);
	    				}
	    				if(browserName.toLowerCase().contains("chrome")){
	    					String path = copyFileToTemp("drivers/chromedriver-linux-64", "chromedriver" + Long.toString(System.currentTimeMillis()), "");
	    					Configurator.getConfig().put("CHROME_DRIVER_PATH", path);
	    				}
	    			}else{
	    				if(browserName.toLowerCase().contains("phantom")){
	    					String path = copyFileToTemp("drivers/phantomjs-linux-32", "phantomjs" + Long.toString(System.currentTimeMillis()), "" );
	    					Configurator.getConfig().put("PHANTOMJS_DRIVER_PATH", path);
	    				}
	    				if(browserName.toLowerCase().contains("chrome")){
	    					String path = copyFileToTemp("drivers/chromedriver-linux-32", "chromedriver" + Long.toString(System.currentTimeMillis()), "");
	    					Configurator.getConfig().put("CHROME_DRIVER_PATH", path);
	    				}
	    			}
	    			break;
	    		case Other:
	    			break;
	    		
	    	}
	    }

	public static String copyFileToTemp(String sourcePath, String prefix, String suffix) throws IOException{
    	logger.info("Using Driver - " + sourcePath);
    	File tempFile = null;
    	InputStream inputStream = Configurator.class.getClassLoader().getResourceAsStream(sourcePath);
		tempFile = File.createTempFile(prefix, suffix, null);
    	tempFile.setExecutable(true);
    	tempFile.deleteOnExit();
    	logger.info("Path where driver is copied - " + tempFile.getAbsolutePath());
    	OutputStream fileStream = new FileOutputStream(tempFile);
    	
    	try
	        {
	            final byte[] buf;
	            int i = 0;
	            buf = new byte[1024];
	            while((i = inputStream.read(buf)) != -1)
	            {
	                fileStream.write(buf, 0, i);
	            }
	        }
        finally
	        {
	            inputStream.close();
	            fileStream.close();
	        }
    	return tempFile.getAbsolutePath();
    }


	public static String getConfigFile() {
		String configFile = "testng.xml";
		return configFile;
	}
	

	public static String getPackageId() {
		String packageId = Configurator.getConfig().get("PACKAGE_ID");
		if (packageId == null){
			packageId = System.getProperty("packageId");
		}
		return packageId;
	}

	public static String getHostname() {
		String hostname = "";
		try {
			hostname = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			hostname = "";
		}
		return hostname;
	}

	public static String getEnv() {
		String environment = "AUT Environment: " + System.getProperty("autEnvironment") + " Browser: " + System.getProperty("browserName");
		return environment;
	}

	public static String getRunID() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy:H:mm:ss");
		String formattedDate = sdf.format(date);
		String runId = formattedDate + "-" + getHostname();	
		return runId;
	}
}

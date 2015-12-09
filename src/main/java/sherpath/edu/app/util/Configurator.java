package sherpath.edu.app.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.slf4j.LoggerFactory;
//import org.slf4j.Logger;

import org.yaml.snakeyaml.Yaml;



/**
 * This class loads framework and application configuration.
 * Framework config should be defined in frameworkConfiguration.yaml
 * Application config should defined in appConfiguration.yaml
 * 
 * @author Lakshmi Tadepally
 *
 */

public class Configurator {
	
	//private static Logger logger = LoggerFactory.getLogger(Configurator.class);
	private static Map<String, String> config = new HashMap<String, String>();

	public static void loadCongifuration(){
	    	
	    	String autEnvironment = System.getProperty("autEnvironment");
			String runnerEnvironment = System.getProperty("runnerEnvironment");
			if (autEnvironment == null) {
				autEnvironment = "STG";
			}
			if (runnerEnvironment == null) {
				runnerEnvironment = "local";
			}

			if (autEnvironment.equalsIgnoreCase("QA")
					|| autEnvironment.equalsIgnoreCase("STG")
					|| autEnvironment.equalsIgnoreCase("STAGING")) {
				autEnvironment = "QA";
			} else if ((autEnvironment.equalsIgnoreCase("DEV"))
					|| (autEnvironment.equalsIgnoreCase("DEVELOPMENT"))) {
				autEnvironment = "DEV";
			}
	
			if (runnerEnvironment.equalsIgnoreCase("LOCAL")
					|| runnerEnvironment.equalsIgnoreCase("LOCALHOST")) {
				runnerEnvironment = "LOCAL";
			} else if (runnerEnvironment.equalsIgnoreCase("DEV")
					|| runnerEnvironment.equalsIgnoreCase("DEVELOPMENT")) {
				runnerEnvironment = "DEVELOPMENT";
			} else if (runnerEnvironment.equalsIgnoreCase("STG")
					|| runnerEnvironment.equalsIgnoreCase("STAGING")) {
				runnerEnvironment = "STAGING";
			}
			
			config.put("AUT_ENVIRONMENT", autEnvironment);
			config.put("RUNNER_ENVIRONMENT", runnerEnvironment);
			
			try {
				loadFrameworkConfiguration(runnerEnvironment);
				loadAppConfiguration(autEnvironment);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (NullPointerException ne) {
				ne.printStackTrace();
			}
	
		}
	
	
	@SuppressWarnings("unchecked")
	public static void loadFrameworkConfiguration(String runnerEnvironment) throws FileNotFoundException,NullPointerException {
		Yaml yaml = new Yaml();
		InputStream input = Configurator.class.getClassLoader()
				.getResourceAsStream("frameworkConfiguration.yaml");
		Map<Object, Object> YFile =  (Map<Object, Object>) yaml.load(input);
		Map<String, Object> frameworkSettings = (Map<String,Object>)YFile.get("FRAMEWORK");
		addToConfig((Map<String,String>)frameworkSettings.get(runnerEnvironment));

				
	}
	
	@SuppressWarnings("unchecked")
	public static void loadAppConfiguration(String autEnvironment){
		Yaml yaml = new Yaml();
		try {
			
			InputStream input = Configurator.class.getClassLoader().getResourceAsStream("appConfiguration.yaml");
			Map<String, Map<String, String>> YFile = (Map<String, Map<String, String>>) yaml.load(input);
			//print content
			System.out.println("Content of map");
			for(String key : YFile.keySet()){
				System.out.println(key +" " + YFile.get(key));
			}

			addToConfig(YFile.get("APP"));
			addToConfig(YFile.get(autEnvironment));
		}catch(org.yaml.snakeyaml.error.YAMLException ye){
			//logger.info("appConfiguration.yaml file not found" + ye);
		}
	}
	
	public static Map<String, String> getConfig() {
		return config;
	}

	private static void addToConfig(Map<String, String> m) {
		Iterator<String> it = m.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			//print contents
			//System.out.println(key +" " + m.get(key));
			config.put(key, m.get(key));			
		}
	}
}

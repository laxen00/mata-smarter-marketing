package com.web.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.json.JSONObject;

public class ConfigProperties {
	
	String propFileName = "config.properties";
	JSONObject obj = new JSONObject();
	InputStream inputStream;
	
	public ConfigProperties (String filename) {
		propFileName = filename;
	}
	
	public JSONObject getPropValues() throws IOException {
		 
		try {
			Properties prop = new Properties();
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
  
			// get the property value and print it out
			String hostname = prop.getProperty("hostname");
			String dbname = prop.getProperty("dbname");
			String schema = prop.getProperty("schema");
			String dbuser = prop.getProperty("dbuser");
			String dbpass = prop.getProperty("dbpass");
			String consumerKey = prop.getProperty("consumerKey");
			String consumerSecret = prop.getProperty("consumerSecret");
			String oauthToken = prop.getProperty("oauthToken");
			String oauthSecret = prop.getProperty("oauthSecret");
			String consumerKey2 = prop.getProperty("consumerKey2");
			String consumerSecret2 = prop.getProperty("consumerSecret2");
			String oauthToken2 = prop.getProperty("oauthToken2");
			String oauthSecret2 = prop.getProperty("oauthSecret2");
			String kaskusUser = prop.getProperty("kaskusUser");
			String kaskusPass = prop.getProperty("kaskusPass");
			
			obj = new JSONObject();
			obj.put("hostname", hostname);
			obj.put("dbname", dbname);
			obj.put("schema", schema);
			obj.put("dbuser", dbuser);
			obj.put("dbpass", dbpass);
			obj.put("consumerKey", consumerKey);
			obj.put("consumerSecret", consumerSecret);
			obj.put("oauthToken", oauthToken);
			obj.put("oauthSecret", oauthSecret);
			obj.put("consumerKey2", consumerKey2);
			obj.put("consumerSecret2", consumerSecret2);
			obj.put("oauthToken2", oauthToken2);
			obj.put("oauthSecret2", oauthSecret2);
			obj.put("kaskusUser", kaskusUser);
			obj.put("kaskusPass", kaskusPass);
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return obj;
	}
}

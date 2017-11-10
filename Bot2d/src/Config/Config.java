package Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Config {

	Properties properties = new Properties();
	String fileName = "client.properties";
	
	public Config() {
		readProperties();
	}
	
	public void removeProperty(Object key) {
		properties.remove(key);
	}
	
	public void setProperty(String key, String value) {
		properties.setProperty(key, value);
	}
	
	public String getProperty(String key) {
		Object object = properties.get(key);
		if(object != null) {
			return (String) properties.get(key);
		}
		return null;
	}
	
	public void readProperties() {
		try {
			File file = new File(fileName);
			InputStream is = new FileInputStream(file);
			properties.load(is);
		} catch (Exception e) {
			System.out.println("Error in Config:Config:readProperties(): " + e);
		}
	}
	
	public void saveProperties() {
		try {
			File file = new File(fileName);
			OutputStream out = new FileOutputStream(file);
			properties.store(out, "OPTIONS");
		} catch (Exception e) {
			System.out.println("Error in Config:Config:saveProperties(): " + e);
		}
	}
}

package com.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Plugin {
	
	private String name;
	private Properties properties;
	
	public Plugin(Properties prop) {
		name = "";
		properties = prop;
	}

	public String getName() {
		return name;
	}
	
	public String getNameFromFile() {
		String fileName = this.getProperty("location");
		Properties p = new Properties();
		try {
			p.load(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p.getProperty("class");
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Properties getProperties() {
		return properties;
	}
	
	public String getProperty(String propName) {
		return properties.getProperty(propName);
	}

	@Override
	public String toString() {
		return "Plugin [name=" + name + ", properties=" + properties + "]";
	}
	

}

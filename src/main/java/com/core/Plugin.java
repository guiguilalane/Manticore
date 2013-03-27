package com.core;

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

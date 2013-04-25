package com.pluginManager.model;

import java.util.Properties;

public class Plugin {
	
	private String name;
	private Properties properties;

	public Plugin(String name, Properties properties) {
		this.name = name;
		this.properties = properties;
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
	
	public void setProperty(String propName, String propValue) {
		properties.setProperty(propName, propValue);
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Plugin [name=" + name + ", properties=" + properties + "]";
	}

}

package com.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.interfaces.IHelloWorld;

/**
 *  
 */

/**
 * @author guillaume
 *
 */
/**
 * @author guillaume
 *
 */
public class Manticore {

	private String configFile;
	private Map<String, Object> loadedPlugins;
	private List<Plugin> plugins;
	URLClassLoader ucl;
	
	public List<Plugin> getPlugins() {
		return plugins;
	}
	
	public Manticore(){
		plugins = new ArrayList<Plugin>();
		loadedPlugins = new HashMap<String, Object>();
	}
	
	/**
	 * @param plugin the plugin property file
	 * @return the object that use Core functionnalities
	 */
	private Object loadConfigFile(Plugin plugin)
	{
		String configFile = plugin.getProperty("location");
		File f = new File(configFile);
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(f));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String path = f.getParentFile() + "/" + prop.getProperty("path");
		findClass(path);
		Object toReturn = null;
		
		try {
			toReturn = ucl.loadClass(prop.getProperty("class")).newInstance();
			plugin.setName(toReturn.getClass().getName());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public void findClass(String path)
	{
		URL[] urls = new URL[1];
		try {
			urls[0] = new URL("file:" + path);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ucl = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());
	}
	
	
	/**
	 * This method load plugins from the file that contains all plugins refercences.
	 * 
	 * 		The plugin file contain : 
	 * 		<ul>
	 * 			<li>the path to the property plugin file</li>
	 * 			<li>when the plugin is loaded (default or not)</li>
	 * 			<li>how the plugin is loaded (lazy/onload)</li>
	 * 		</ul>
	 *  
	 * 	
	 * @param pluginsFile the path to the file that contain all plugins
	 */
	public void getPlugins(String pluginsFile){
		File f = new File(pluginsFile);
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = builder.build(new File(pluginsFile));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Properties prop;
		Plugin plug;
		for(Element plugin: root.getChildren("plugin")){
			prop = new Properties();
			for(Attribute attr : plugin.getAttributes()){
				prop.put(attr.getName(), attr.getValue());
			}
			plug = new Plugin(prop);
			plugins.add(plug);
		}
	}
	
	public static void main(String[] args) {
		Manticore m = new Manticore();
		m.getPlugins("plugins.xml");
//		m.findClass();
		IHelloWorld hello = (IHelloWorld) m.loadConfigFile(m.getPlugins().get(0));
		hello.printHello();
		System.out.println(m.getPlugins().get(0));
		
//		Explorateur exp = new Explorateur();
//		exp.setVisible(true);
	}

}

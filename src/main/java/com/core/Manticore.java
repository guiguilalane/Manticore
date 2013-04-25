package com.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
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

import com.core.loader.Handler;
import com.interfaces.Core;
import com.interfaces.IPluginManager;
import com.interfaces.IRunnablePlugin;

/**
 * @author guillaume
 *
 */
public class Manticore implements Core{

	private String configFile;
	private String pluginPath;
	private Map<String, Object> loadedPlugins;
	private List<Plugin> plugins;
	URLClassLoader ucl;
	
	private Plugin pluginManager;
	
	public List<Plugin> getPlugins() {
		return plugins;
	}
	
	public Plugin getPluginManager() {
		return pluginManager;
	}
	
	public Manticore(){
		plugins = new ArrayList<Plugin>();
		loadedPlugins = new HashMap<String, Object>();
	}
	
	public void loadPluginManager(String pluginsFile) {
		this.pluginPath = pluginsFile;
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
		for(Element plugin: root.getChildren("pluginManager")){
			prop = new Properties();
			for(Attribute attr : plugin.getAttributes()){
				prop.put(attr.getName(), attr.getValue());
			}
			File f1 = new File(prop.getProperty(PluginConstants.LOCATION));
			Properties forGetPluginName = new Properties();
			
			try {
				forGetPluginName.load(new FileReader(f1));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plug = new Plugin(forGetPluginName.getProperty("class"), prop);
			pluginManager = plug;
		}
	}
	
//	/**
//	 * @param plugin the plugin property file
//	 * @return the object that use Core functionnalities
//	 */
//	private Object loadConfigFile(Plugin plugin)
//	{
//		String configFile = plugin.getProperty(PluginConstants.LOCATION);
//		File f = new File(configFile);
//		Properties prop = new Properties();
//		try {
//			prop.load(new FileReader(f));
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		String path = f.getParentFile() + "/" + prop.getProperty("path");
//		findClass(path);
//		Object toReturn = null;
//		try {
//			toReturn = ucl.loadClass(prop.getProperty("class")).newInstance();
//			plugin.setName(toReturn.getClass().getName());
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return toReturn;
//	}
	
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
	 * 			<li>how the plugin is loaded (lazy/onLoad)</li>
	 * 		</ul>
	 *  
	 * 	
	 * @param pluginsFile the path to the file that contain all plugins
	 */
	public void loadPlugins(String pluginsFile){
		this.pluginPath = pluginsFile;
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
			File f1 = new File(prop.getProperty(PluginConstants.LOCATION));
			Properties forGetPluginName = new Properties();
			
			try {
				forGetPluginName.load(new FileReader(f1));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			plug = new Plugin(forGetPluginName.getProperty("class"), prop);
			plugins.add(plug);
		}
	}
	
	//Object est de type plugin
	public void runPlugin(Plugin p) {
		
		String configFile = p.getProperty(PluginConstants.LOCATION);
		File f = new File(configFile);
		Properties prop = new Properties();
		try {
			prop.load(new FileReader(f));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String path = f.getParentFile() + "/" + prop.getProperty("path");
		findClass(path);
		Object objectToLoad = null;
		try {
			objectToLoad = ucl.loadClass(prop.getProperty("class")).newInstance();
			if(IRunnablePlugin.class.isAssignableFrom(objectToLoad.getClass())) {
				((IRunnablePlugin) objectToLoad).setCore(this);
			}
			if(IPluginManager.class.isAssignableFrom(objectToLoad.getClass())) {
				((IPluginManager) objectToLoad).setPluginFile(new File(pluginPath).getAbsolutePath());
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Method m = null;
//		System.out.println(p.getProperty(PluginConstants.GUI).equals("true"));
//		System.out.println(prop.getProperty(PluginConstants.GUI).equals("true"));
		//regarder si le plugin se charge en console ou GUI (dans le fichier plugins.xml attribut 'gui')
		//regarder si le plugin en question possède une interface graphique (dans le fichier .properties du plugin, attribut 'gui')
		if(p.getProperty(PluginConstants.GUI).equals("true") && prop.getProperty(PluginConstants.GUI).equals("true")) {
			m = getMethodWithStringAnnotation(prop.getProperty("class"), "com.annotations.GUI");
			if(m==null) {
				/*Ici, l'utilisateur veut demande une interface graphique, le plugin indique qu'il en propose une mais
				* l'annotation qui permet de détecter la GUI n'est présente sur aucune méthode.
				* Le plugin est donc lancé en mode console.
				*/
				//TODO: throw new Exception
				m = getRunnableMethod(prop.getProperty("class"));
			}
		} else {
			m = getRunnableMethod(prop.getProperty("class"));
		}
		
		if(m != null) {
			try {
				m.invoke(objectToLoad, null);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Method getRunnableMethod(String className) {
		Method mToLoad = null;
		Class<?> theClass = null;
		
		try {
			theClass = ucl.loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(IRunnablePlugin.class.isAssignableFrom(theClass)) {
			try {
				mToLoad = IRunnablePlugin.class.getMethod(IRunnablePlugin.RunningMethod, null);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return mToLoad;
	}
	
	private Method getMethodWithStringAnnotation(String className, String annotationName) {
		Method mToLoad = null;
		Class<?> theClass = null;
		boolean find = false;
		try {
			theClass = ucl.loadClass(className);
//			System.out.println(theClass.getName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Method methods[] = theClass.getMethods();
		int size = methods.length;
		int i = 0;
		while(!find && i < size) {
			try {
//				System.out.println(annotationName);
//				System.out.println(Class.forName("com.annotations.GUI"));
//				System.out.println(methods[i].getName());
				if(methods[i].isAnnotationPresent((Class<? extends Annotation>) Class.forName(annotationName))) {
//				if(methods[i].isAnnotationPresent(Console.class)) {
					find = true;
					mToLoad = methods[i];
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			++i;
		}
		return mToLoad;
	}
	
	public Object getProxy(String s) throws IllegalArgumentException, ClassNotFoundException {
		InvocationHandler h = new Handler(s);
		
		return Proxy.newProxyInstance(ucl, Class.forName(s).getInterfaces(), h);
	}
	
	public static void main(String[] args) {
		Manticore m = new Manticore();
		m.loadPlugins("plugins.xml");
//		m.loadPluginManager("plugins.xml");
		
//		m.runPlugin(m.getPluginManager());
		m.runPlugin(m.getPlugins().get(0));
//		m.findClass();
//		IHelloWorld hello = (IHelloWorld) m.loadConfigFile(m.getPlugins().get(0));
//		hello.printHello();
//		System.out.println(m.getPlugins().get(0));
		
		//Charge les plugins en lazy ou non
//		for ( Plugin p : m.plugins ) {
//			System.out.println("plop");
//			if ( p.getProperty("method").equals("onLoad") ) {
//				Object o = m.loadConfigFile(p);
//				m.loadedPlugins.put(p.getName(), o);
//			}
//			else if (p.getProperty("method").equals("lazy")) {
//				try {
//					Object o = m.getProxy("/comptes/E086770Q/Manticore/plugins/HelloWorld/bin/" + p.getNameFromFile());
//				} catch (IllegalArgumentException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ClassNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		IHelloWorld hello = (IHelloWorld) m.loadConfigFile(m.getPlugins().get(0));
//		hello.printHello();
//		System.out.println(m.getPlugins().get(0));
//		
//		IHelloWorld hello2 = (IHelloWorld) m.getProxy(hello);
//		hello2.printHello();
		
//		for ( Plugin p : m.plugins ) {
//			System.out.println(p.getName());
//			System.out.println(m.loadedPlugins.get(p.getName()));
//		}
//		Explorateur exp = new Explorateur();
//		exp.setVisible(true);
	}

}

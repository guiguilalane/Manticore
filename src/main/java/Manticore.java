import interfaces.IHelloWorld;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *  
 */

/**
 * @author guillaume
 *
 */
public class Manticore {

	private String configFile;
	private Map<String, Object> loadedPlugins;
	private List<String> plugins;
	URLClassLoader ucl;
	
	public List<String> getPlugins() {
		return plugins;
	}
	
	public Manticore(){
		plugins = new ArrayList<String>();
		loadedPlugins = new HashMap<String, Object>();
	}
	
	private Object loadConfigFile(String configFile)
	{
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
		for(Element plugin: root.getChildren("plugin")){
			plugins.add(plugin.getAttributeValue("location"));
		}
	}
	
	public static void main(String[] args) {
		Manticore m = new Manticore();
		m.getPlugins("plugins.xml");
//		m.findClass();
		IHelloWorld hello = (IHelloWorld) m.loadConfigFile(m.getPlugins().get(0));
		hello.printHello();
		
//		Explorateur exp = new Explorateur();
//		exp.setVisible(true);
	}

}

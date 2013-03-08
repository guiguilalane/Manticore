import interfaces.IHelloWorld;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Properties;

/**
 *  
 */

/**
 * @author guillaume
 *
 */
public class Manticore {

	private String configFile;
	private List<Object> loadedPlugins;
	URLClassLoader ucl;
	
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Manticore m = new Manticore();
//		m.findClass();
		IHelloWorld hello = (IHelloWorld) m.loadConfigFile("../HelloWorld/helloworld.properties");
		
		
		hello.printHello();
		
//		Explorateur exp = new Explorateur();
//		exp.setVisible(true);
	}

}

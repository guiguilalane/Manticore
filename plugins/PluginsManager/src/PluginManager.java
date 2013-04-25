

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.interfaces.Core;
import com.interfaces.IPluginManager;
import com.interfaces.IPluginsManagerDisplay;
import com.interfaces.IRunnablePlugin;
import com.pluginManager.IHM.PluginsManagerConsole;
import com.pluginManager.model.Plugin;
import com.pluginManager.model.PluginConstants;
import com.pluginManager.tools.ResourceUtils;

public class PluginManager implements IRunnablePlugin, IPluginManager{

	private String pluginsPath = null;
	private List<Plugin> pluginList;
	private IPluginsManagerDisplay view;
	private Document doc;
	private Core core;
	
	public PluginManager() {
		pluginList = new ArrayList<Plugin>();
	}
	
	@Override
	public void run() {
		System.out.println("plopdfvw");
		getPluginsFromFile();
		view = new PluginsManagerConsole(pluginsPath);
		boolean quit = false;
		while(!quit) {
			((PluginsManagerConsole) view).displayPlugins(pluginList);
			quit = dispachCommand(((PluginsManagerConsole) view).scan());
		}
	}
	
	public boolean dispachCommand(String command) {
		boolean b = false;
		switch (command) {
		case "quit":
			b = true;
			break;
			
		case "help":
			doHelp();
			break;
			
		case "save":
			savePropertyFile();
			break;
			
		case "add":
			addPlugin();
			break;

		default:
			executeCommand(command);
		}
		return b;
	}
	
	private void addPlugin() {
		File f = new File(pluginsPath);
		JFileChooser fileChooser = new JFileChooser(f);
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setDialogTitle("Plugin directory");
		fileChooser.setApproveButtonText("Open");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileNameExtensionFilter("*.properties", "properties"));
		int retour = fileChooser.showSaveDialog(null);
		String path = null;
		if(retour == JFileChooser.APPROVE_OPTION) {
			
			path = fileChooser.getSelectedFile().getAbsolutePath();
		}
		String base = f.getAbsolutePath();
//		System.out.println(path);
		String relativePath = ResourceUtils.getRelativePath(path, base, "/");
		
		Element root = doc.getRootElement();
		
		Element newPlugin = new Element("plugin");
		newPlugin.setAttribute(new Attribute(PluginConstants.LOCATION, relativePath));
		newPlugin.setAttribute(new Attribute(PluginConstants.LOADING, "false"));
		newPlugin.setAttribute(new Attribute(PluginConstants.METHOD, "false"));
		newPlugin.setAttribute(new Attribute(PluginConstants.GUI, "false"));
		
		root.addContent(newPlugin);
		
		Properties p = new Properties();
		try {
			p.load(new FileReader(new File(relativePath)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		prop.put(PluginConstants.LOCATION, relativePath);
		prop.put(PluginConstants.LOADING, "false");
		prop.put(PluginConstants.METHOD, "false");
		prop.put(PluginConstants.GUI, "false");
		
		Plugin plug = new Plugin(p.getProperty("class"), prop);
		
		pluginList.add(plug);
		
	}

	private void savePropertyFile() {
		Element root = doc.getRootElement();
		int i = 0;
		for(Element plugin: root.getChildren("plugin")){
			for(Entry<Object, Object> e : pluginList.get(i).getProperties().entrySet()) {
				plugin.setAttribute(e.getKey().toString(), e.getValue().toString());
			}
			++i;
		}
		try {
            XMLOutputter xmlop = new XMLOutputter (Format.getPrettyFormat());
            xmlop.output(doc, new FileOutputStream(pluginsPath));
        } catch (IOException e) {}
	}

	private void executeCommand(String command) {
		String[] str = command.split(" ");
		if(str.length > 3) {
			System.err.println("command format is wrong");
		} else {
			Plugin plug = null;
			Integer i = null;
			try{
				i = new Integer(str[0]);
				
			} catch (NumberFormatException e) {
				i = pluginList.indexOf(getPluginFromString(str[0]));
			}
			plug = pluginList.get(i);
			System.out.println(plug);
			plug.setProperty(str[1], str[2]);
			System.out.println(plug);
			pluginList.set(i, plug);
		}
	}

	private Plugin getPluginFromString(String name) {
		Plugin p = null;
		boolean find = false;
		int size = pluginList.size();
		int i = 0;
		while(!find && i < size) {
			if(pluginList.get(i).getName().equals(name)) {
				find = true;
				p = pluginList.get(i);
			}
			++i;
		}
		return p;
	}

	private void doHelp() {
		System.out.println("Available commands :");
		System.out.println("\t help - display help.");
		System.out.println("\t quit - close the plugin manager.\n");
		System.out.println("To modify plugins option command pattern is like this :");
		System.out.println("\t plugin_identifier option new_option_value");
		System.out.println("with: \n\t - plugin_identifer : the plugin name or its number");
		System.out.println("\t - option : default/lazy");
		System.out.println("\t - new_option_value : true/false");
		
	}

	public List<Plugin> getPluginList() {
		return pluginList;
	}
	
	private void getPluginsFromFile() {
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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			plug = new Plugin(forGetPluginName.getProperty("class"), prop);
			System.out.println(plug);
			pluginList.add(plug);
		}
		
	}

	public static void main(String[] args) {
		PluginManager pm = new PluginManager();
		pm.run();
	}

	@Override
	public void setCore(Core c) {
		core = c;
	}

	//le chemin est un chemin absolue
	@Override
	public void setPluginFile(String pluginPath) {
		
		this.pluginsPath = pluginPath;
		SAXBuilder builder = new SAXBuilder();
		try {
			doc = builder.build(new File(this.pluginsPath));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

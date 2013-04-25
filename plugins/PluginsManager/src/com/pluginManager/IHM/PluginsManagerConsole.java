package com.pluginManager.IHM;

import java.util.List;
import java.util.Scanner;

import com.interfaces.IPluginsManagerDisplay;
import com.pluginManager.model.Plugin;
import com.pluginManager.model.PluginConstants;
import com.pluginManager.tools.ResourceUtils;

public class PluginsManagerConsole implements IPluginsManagerDisplay{
	
	private String pluginPath;
	
	public PluginsManagerConsole(String pluginsPath) {
		pluginPath = pluginsPath;
	}
	
	public void displayPlugins(List<Plugin> pluginList) {
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("| \t\t Plugins \t\t | \t\t default \t\t | \t\t lazy \t\t | \t\t gui \t\t |");
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");

		Plugin plug;
		for(int i = 0 ; i < pluginList.size(); ++i) {
			plug = pluginList.get(i);
			System.out.println(ResourceUtils.getRelativePath(plug.getProperty(PluginConstants.LOADING), pluginPath, "/"));
			System.out.println("| \t\t" + i + ") " + plug.getName() + "\t\t | \t\t " + plug.getProperty(PluginConstants.LOADING).equals("true") + " \t\t\t | \t\t " + plug.getProperty(PluginConstants.METHOD).equals("true") + " \t\t | \t\t " + plug.getProperty(PluginConstants.GUI).equals("true") + " \t\t |");
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------");
	}
	
	public String scan() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a command (\"help\" command to get help)");
		return scan.nextLine();
	}

}

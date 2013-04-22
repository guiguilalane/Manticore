package com.interfaces;


import com.annotations.Console;
import com.annotations.GUI;


public interface IHelloWorld {

	@Console
	public void printHello();
	
	@GUI
	public void printHelloGUI();
	
}

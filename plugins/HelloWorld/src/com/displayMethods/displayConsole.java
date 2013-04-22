package com.displayMethods;

import com.interfaces.IDisplayStrategy;

public class displayConsole implements IDisplayStrategy{

	@Override
	public void display() {
		System.out.println("Hello world!!");
	}

}

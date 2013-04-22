package com.displayMethods;

import com.IHM.GuiTest;
import com.interfaces.IDisplayStrategy;

public class displayGUI implements IDisplayStrategy{

	GuiTest gui;
	
	@Override
	public void display() {
		gui = new GuiTest();
		gui.setVisible(true);
	}

}

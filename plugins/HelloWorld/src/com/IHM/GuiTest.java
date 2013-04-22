package com.IHM;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.interfaces.IHelloWorld;

public class GuiTest {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiTest window = new GuiTest();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GuiTest() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("HelloWorld!");
		frame.add(new JLabel("Helloworld!!!"));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setVisible(boolean b) {
		frame.setVisible(b);
	}

}

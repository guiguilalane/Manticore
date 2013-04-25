package com.IHM;


import javax.swing.JFrame;
import javax.swing.JLabel;

public class IHMHello extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IHMHello() {
		super("helloworld");
		initialize();
	}
	
	public void initialize() {
		setSize(300, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label = new JLabel("Helloworld");
		
		getContentPane().add(label);
	}
	
	public static void main(String[] args) {
		IHMHello test = new IHMHello();
		test.initialize();
		test.setVisible(true);
	}

}

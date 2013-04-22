import com.IHM.GuiTest;
import com.annotations.Console;
import com.annotations.GUI;
import com.interfaces.IHelloWorld;

public class HelloWorld implements IHelloWorld{

	
	public HelloWorld(){
	}

	@Console
	public void printHello() {
		System.out.println("Helloworld!!");
	}
	
	@GUI
	public void printHelloGUI() {
		GuiTest test = new GuiTest();
		test.setVisible(true);
	}
}

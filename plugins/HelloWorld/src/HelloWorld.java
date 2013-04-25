import com.IHM.IHMHello;
import com.annotations.GUI;
import com.interfaces.Core;
import com.interfaces.IRunnablePlugin;

public class HelloWorld implements IRunnablePlugin{

	private Core core;
	
	public HelloWorld(){
	}
	
	@GUI
	public void runGUI() {
		IHMHello test = new IHMHello();
		test.setVisible(true);
	}

	@Override
	public void run() {
		System.out.println("Helloworld!!");
	}

	@Override
	public void setCore(Core c) {
		core = c;
	}
}

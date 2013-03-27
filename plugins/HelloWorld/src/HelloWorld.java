import com.annotations.GUI;
import com.interfaces.IHelloWorld;

@GUI
public class HelloWorld implements IHelloWorld{

	
	public HelloWorld(){
	}

	@Override
	public void printHello() {
		System.out.println("HelloWorld!!");
	}
}

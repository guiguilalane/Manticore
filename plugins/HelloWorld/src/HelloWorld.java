import interfaces.IHelloWorld;



public class HelloWorld implements IHelloWorld{

	
	public HelloWorld(){
	}

	@Override
	public void printHello() {
		System.out.println("HelloWorld!! plus Modif");
	}
	
}

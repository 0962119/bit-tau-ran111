package pkg12;

public class TestbroadcastServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread discoveryThread = new Thread(DiscoveryThread.getInstance());
		discoveryThread.start();
	}
	public static void OpenbroascastServer()
	{
			Thread discoveryThread = new Thread(DiscoveryThread.getInstance());
			discoveryThread.start();
		
	}
}

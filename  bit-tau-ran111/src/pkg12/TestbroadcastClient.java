package pkg12;

public class TestbroadcastClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread clientimplementatio = new Thread(new Clientimplementation().getInstance("fdsf"));
		clientimplementatio.start();
	}

}

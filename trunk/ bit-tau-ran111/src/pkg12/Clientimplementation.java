package pkg12;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Clientimplementation implements Runnable {
	
	public Clientimplementation getInstance(String fileName) {
		filename = fileName;
		return ClientimplementationHolder.INSTANCE;
	}

	private static class ClientimplementationHolder  {

		
		private static final Clientimplementation INSTANCE = new Clientimplementation();
	}
	 
	DatagramSocket c;
	private static String filename;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
				  //Open a random port to send the package
				  c = new DatagramSocket();
				  c.setBroadcast(true);
				 
				  byte[] sendData = filename.getBytes();
				 
				  //Try the 255.255.255.255 first
				  try {
				    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
				    c.send(sendPacket);
				    System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
				  } catch (Exception e) {
				  }
				 
				  // Broadcast the message over all the network interfaces
				  Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
				  while (interfaces.hasMoreElements()) {
				    NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();
				 
				    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
				      continue; // Don't want to broadcast to the loopback interface
				    }
				 
				    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
				      InetAddress broadcast = interfaceAddress.getBroadcast();
				      if (broadcast == null) {
				        continue;
				      }
				 
				      // Send the broadcast package!
				      try {
				        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
				        c.send(sendPacket);
				      } catch (Exception e) {
				      }
				 
				      System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
				    }
				  }
				 
				  System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");
				 
				  //Wait for a response
				  byte[] recvBuf = new byte[618];
				  DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
				  c.receive(receivePacket);
				 
				  //We have a response
				  System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());
				 
				  //Check if the message is correct
				  String message = new String(receivePacket.getData()).trim();
				  //if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
					// 	    //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
					  	   // Clientimplementation.setServerIp(receivePacket.getAddress());
					 //	  }
				  Topology toponode =new Topology();
				  //filename = Chrysanthemum.jpg.001 098bc4448e4860accde00b0c19eae19fab9e43b2
				  toponode.chunkName=filename;
				  toponode.ipAddress=receivePacket.getAddress().getHostAddress();
				  System.out.println("message nhan tu server :" + toponode.ipAddress+"-"+message);
				  if (message.split(" ")[2].equals("True")) {
				    //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
				    //Controller_Base.setServerIp(receivePacket.getAddress());
					//have packet to server 
					  toponode.flag=1;
					  System.out.println("broadcasst server have file download");
					  String chunkN = message.substring(0, message.lastIndexOf(" ")).trim();
					  System.out.println("broadcasst client download file: "+ chunkN.substring(0, chunkN.lastIndexOf(" ")));
					  String args[]=  {"get " + chunkN.substring(0, chunkN.lastIndexOf(" ")) , toponode.ipAddress, chunkN.substring(0, chunkN.lastIndexOf("."))};
						Client.main(args);
				  }
				  else
				  {
					  toponode.flag=0;
					  System.out.println("broadcasst server don't have file download");
				  }
				  Guiserver.topology.add(toponode);
				  //Close the port!
				  c.close();
				  System.out.println("close secket sendrevice file of: " + filename);
				} catch (IOException ex) {
				  Logger.getLogger(Clientimplementation.class.getName()).log(Level.SEVERE, null, ex);
				}
	}

}

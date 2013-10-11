package pkg12;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscoveryThread implements Runnable {

	public static DiscoveryThread getInstance() {
		return DiscoveryThreadHolder.INSTANCE;
	}

	private static class DiscoveryThreadHolder {

		private static final DiscoveryThread INSTANCE = new DiscoveryThread();
	}

	DatagramSocket socket;

	@Override
	public void run() {
		try {
			// Keep a socket open to listen to all the UDP trafic that is
			// destined for this port
			socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (true) {
				System.out.println(getClass().getName()+ ">>>Ready to receive broadcast packets!");

				// Receive a packet
				byte[] recvBuf = new byte[618];
				DatagramPacket packet = new DatagramPacket(recvBuf,recvBuf.length);
				socket.receive(packet);

				// Packet received
				System.out.println(getClass().getName()+ ">>>Discovery packet received from: "+ packet.getAddress().getHostAddress());
				System.out.println(getClass().getName()+ ">>>Packet received; data: "+ new String(packet.getData()));
				byte[] sendData;
				// See if the packet holds the right command (message)
				String message = new String(packet.getData()).trim();
				// message = Chrysanthemum.jpg.001 098bc4448e4860accde00b0c19eae19fab9e43b2
				// message = Chrysanthemum.jpg.001 098bc4448e4860accde00b0c19eae19fab9e43b2 True
				String[] arrMessage = message.split(" ");
				if (arrMessage.length == 2) 
				{
					System.out.println("check chunk exixts" + message.split(" ")[0]);
					if (CheckChunkExists(message.split(" ")[0])){
						sendData = (message + " True").getBytes();
						System.out.println("send data: " + message + " True");
					}
					// sendData = Chrysanthemum.jpg.001 098bc4448e4860accde00b0c19eae19fab9e43b2 true
					else{
						sendData =(message+ " False").getBytes();
						System.out.println("send data: " + message + " false");
					}
					// Send a response
					DatagramPacket sendPacket = new DatagramPacket(sendData,sendData.length, packet.getAddress(),packet.getPort());
					socket.send(sendPacket);
					System.out.println(getClass().getName()+ ">>>Sent packet to: "+ sendPacket.getAddress().getHostAddress());
				}
				else if(arrMessage.length ==3)
				{
					 Topology toponode =new Topology();
					 toponode.chunkName=message.substring(0, message.lastIndexOf(" ")).trim();
					 toponode.ipAddress=packet.getAddress().getHostAddress();
					 if(message.substring(message.lastIndexOf(" "), message.length()-1).trim().equals("True"))
						toponode.flag = 1; 
					 else
						toponode.flag=0;
					 Guiserver.topology.add(toponode);
				}
				
			}
		} catch (IOException ex) {
			Logger.getLogger(DiscoveryThread.class.getName()).log(Level.SEVERE,null, ex);
		}
	}

	public static boolean CheckChunkExists(String name) {
		String folderName = name.substring(0, name.lastIndexOf(".")).trim();
		File checkFile =new File("DataInput/"+folderName+"/"+name);
		
		if(checkFile.exists())
		{
			System.out.println("check File " + "DataInput/"+folderName+"/"+name + "= true");
			return true;
		}
		System.out.println("check File " + "DataInput/"+folderName+"/"+name + "= false");
		return false;
	}
}

/*****
 * CT326 - Assignment 12 - c.loughnane1@nuigalway.ie - 09101916
 */
package pkg12;

import java.io.*;
import java.net.*;

public class Server
{
    private static int clientID = 1;
    private static DatagramSocket serverSocket;

    public static void main(String[] args) throws IOException
    {
    	
    	System.out.println("Server started.");
        byte[] buffer = new byte[512];

        /**
         * ASSIGNMENT INSTRUCTION The server should be multi-threaded, and
         * have one thread per connection.
         */
        serverSocket = new DatagramSocket(8550);
        while (true)
        {
            try
            {
                DatagramPacket packet =  new DatagramPacket(buffer, buffer.length );
                serverSocket.receive(packet);
                System.out.println("SERVER: Accepted connection.");
                System.out.println("SERVER: received"+new String(packet.getData(), 0, packet.getLength()));

                //new socket created with random port for thread
                DatagramSocket threadSocket = new DatagramSocket();

                Thread t = new Thread(new CLIENTConnection(threadSocket, packet, clientID++));

                t.start();

            } catch (Exception e)
            {
                System.err.println("Error in connection attempt.");
            }
        }
    }
    public static void openServer() throws SocketException
    {
    	System.out.println("Server started.");
        byte[] buffer = new byte[512];

        /**
         * ASSIGNMENT INSTRUCTION The server should be multi-threaded, and
         * have one thread per connection.
         */
        serverSocket = new DatagramSocket(8550);
        while (true)
        {
            try
            {
                DatagramPacket packet =  new DatagramPacket(buffer, buffer.length );
                serverSocket.receive(packet);
                System.out.println("SERVER: Accepted connection.");
                System.out.println("SERVER: received"+new String(packet.getData(), 0, packet.getLength()));

                //new socket created with random port for thread
                DatagramSocket threadSocket = new DatagramSocket();

                Thread t = new Thread(new CLIENTConnection(threadSocket, packet, clientID++));

                t.start();

            } catch (Exception e)
            {
                System.err.println("Error in connection attempt.");
            }
        }
    }
}
/**
 * @file	TcpTicketReservationClient.java
 * 
 * @author 	Aaron Alaniz (aaron.a.alaniz@gmail.com)
 * 
 * @author	Moshiul Arefin (marefin@globalintertech.com)
 * 
 * @brief	The following class defines a UDP client for a ticket reservation system. All operations are performed on the corresponding UDP ticket server.
 */

package ee382n.assignments.ticket_res;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class UdpTicketReservationClient {
	static int port;
	static String capacity;
	static String ipAddress;
	
	public static void main (String a[]) {
		String serverConfig;
		// Verify that user has passed in a proper server configuration
		if (a.length < 1) {
			System.out.println("Server configuration id not specified. Please enter aaron, arefin, or grader as argument");
			return;
		}
		serverConfig = a[0];
		if (!serverConfig.equals("aaron") && !serverConfig.equals("arefin") && !serverConfig.equals("grader")) {
			System.out.println("Server configuration id not valid. Please enter aaron, arefin, or grader as argument");
			return;
		}
		
		// Read in the appropriate server info
		extractServerInfo(serverConfig);
		
		// Communicate with Udp Server
		try {
			
			
			//Arefin 1/30/13
			System.out.println("Welcome to movie ticket reservation client.");
			Scanner inputScanner = new Scanner(System.in);
			
			System.out.print("Enter Input: ");
			
			String userInput = inputScanner.nextLine();
			
			
			while (userInput.toUpperCase().compareTo("QUIT") != 0)
			{
				TicketReservationClient ticket = new TicketReservationClient(userInput);
				
				if (ticket.checkUserInput())
				{
				
					DatagramChannel clientChannel = DatagramChannel.open();
					InetAddress ia = InetAddress.getByName(ipAddress);
					byte[] request = new String(userInput).getBytes();
					byte[] response = new byte[1024];
					ByteBuffer requestBuffer = ByteBuffer.wrap(request);
					clientChannel.send(requestBuffer, new InetSocketAddress(ia, port));
					clientChannel.receive(ByteBuffer.wrap(response));
					System.out.println(new String(response));
					clientChannel.close();
					
					System.out.print("Enter Input: ");
				
				}
				else
				{
					System.out.print("Invalid Input, try again: ");
				}
				
				userInput = inputScanner.nextLine();
			}
			
			inputScanner.close();
			
			System.out.print("Good Bye!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		

	private static void extractServerInfo(String serverConfig) {
		try	{
			File serverXml = new File("server_config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(serverXml);
	
			// Recommended for parsing
			doc.getDocumentElement().normalize();
	
			// Retrieving the config info
			NodeList serverInfo = doc.getElementsByTagName("server");
			Element e = (Element) serverInfo.item(0);
			
			for ( int i = 0 ; !e.getAttribute("id").equals(serverConfig) ; i++) {
				e = (Element) serverInfo.item(i);
			}
			
			// Capturing info
			port = Integer.parseInt(e.getElementsByTagName("port").item(0).getTextContent());
			capacity = e.getElementsByTagName("capacity").item(0).getTextContent();
			ipAddress = e.getElementsByTagName("ipAddress").item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
			DatagramChannel clientChannel = DatagramChannel.open();
			InetAddress ia = InetAddress.getByName(ipAddress);
			//SocketAddress sa = new InetSocketAddress(ia, port);
			//clientChannel.socket().bind(sa);
			byte[] request = new String("hello udp server! ...I'm a udp client!").getBytes();
			byte[] response = new byte[1024];
			ByteBuffer requestBuffer = ByteBuffer.wrap(request);
			clientChannel.send(requestBuffer, new InetSocketAddress(ia, port));
			clientChannel.receive(ByteBuffer.wrap(response));
			System.out.println("From Udp Server: " + new String(response));
			clientChannel.close();
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

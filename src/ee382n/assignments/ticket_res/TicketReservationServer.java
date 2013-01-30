/**
 * @file	TicketReservationServer.java
 * 
 * @author 	Aaron Alaniz (aaron.a.alaniz@gmail.com)
 * 
 * @author	Moshiul Arefin (marefin@globalintertech.com)
 * 
 * @brief	The following class defines a ticket reservation server that handles both UDP and TCP requests from clients.
 */

package ee382n.assignments.ticket_res;

import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TicketReservationServer {
	static int port;
	static int capacity;
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
		
		// Initialize the Ticket Reservation Database
		TicketReservationDatabase.init(capacity);
		
		// Configure the server
	    try {
	    	InetAddress ia = InetAddress.getByName(ipAddress);
	        SocketAddress channelPort = new InetSocketAddress(ia, port);
	        Selector selector = Selector.open();

	        // Create new Tcp Server
	        TcpServer tcpServer = new TcpServer(channelPort, selector);
	        
	        // Create a new Udp Server
	        UdpServer udpServer = new UdpServer(channelPort, selector);
	        
	        System.out.println("Ticket server launched on port " + port);
	        // Handle client connections
	        while(true) { 
	            selector.select();
	            Set<SelectionKey> keys = selector.selectedKeys();
	
	            for (Iterator<SelectionKey> i = keys.iterator(); i.hasNext();) {
	                SelectionKey key = i.next();
	                i.remove();
	                Channel protocolChannel = key.channel();
	
	                // Direct the request to the appropriate server based on the connect
	                if (key.isAcceptable() && protocolChannel == TcpServer.tcpSocketChannel) {
	                	tcpServer.handleClient();
	                } else if (key.isReadable() && protocolChannel == UdpServer.udpChannel) {
	                	udpServer.handleClient();
	                }
	            }
	        }
	    } catch (Exception e) {
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
			capacity = Integer.parseInt(e.getElementsByTagName("capacity").item(0).getTextContent());
			ipAddress = e.getElementsByTagName("ipAddress").item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

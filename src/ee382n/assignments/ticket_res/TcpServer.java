/**
 * @file	TcpServer.java
 * 
 * @author 	Aaron Alaniz (aaron.a.alaniz@gmail.com)
 * 
 * @author	Moshiul Arefin (marefin@globalintertech.com)
 * 
 * @brief	This class serves as the TCP server for the ticket reservation system.
 */

package ee382n.assignments.ticket_res;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Semaphore;

public class TcpServer {
    static ServerSocketChannel tcpSocketChannel;
    
	public TcpServer(SocketAddress channelPort, Selector selector) throws IOException {
		tcpSocketChannel = ServerSocketChannel.open();
		tcpSocketChannel.socket().bind(channelPort);
		tcpSocketChannel.configureBlocking(false);
		tcpSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}
    
	public synchronized void handleClient() throws IOException {
		byte[] request = new byte[1024];
		byte[] response;
		
		// Wait for the tcp request
		SocketChannel sc = tcpSocketChannel.accept(); 
		
		// Capture the request 
		if (sc != null) {
			sc.read(ByteBuffer.wrap(request));
			
			// Execute the request, capture the response, and send back
			String requestResponse = RequestHandler.handle(new String(request).trim().split(" "));
			response = requestResponse.getBytes();
			sc.write(ByteBuffer.wrap(response));
		}
	}

}

package ee382n.assignments.ticket_res;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class UdpServer {
	static DatagramChannel udpChannel;

	public UdpServer(SocketAddress channelPort, Selector selector) throws IOException {
		udpChannel = DatagramChannel.open();
		udpChannel.socket().bind(channelPort);
		udpChannel.configureBlocking(false);
		udpChannel.register(selector, SelectionKey.OP_READ);
	}

	public void handleClient() throws IOException {
    	byte[] request = new byte[1024];
    	byte[] response;	    	
    	
    	// Capture the request
        SocketAddress clientAddress = udpChannel.receive(ByteBuffer.wrap(request));
        
        // Execute the request, capture the response, and send back
        String requestResponse = RequestHandler.handle(new String(request).trim().split(" "));
        response = requestResponse.getBytes();
        udpChannel.send(ByteBuffer.wrap(response), clientAddress);	    
	}

}

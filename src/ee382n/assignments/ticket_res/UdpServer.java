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

	public void handleClient() {
	    try {           
	    	byte[] request = new byte[1024];
	    	byte[] response = new String("hello udp client!").getBytes();	    	
	        SocketAddress clientAddress = udpChannel.receive(ByteBuffer.wrap(request));
	        System.out.println(new String(request));
	        udpChannel.send(ByteBuffer.wrap(response), clientAddress);	    
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}

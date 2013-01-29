package ee382n.assignments.ticket_res;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TcpServer {
    static ServerSocketChannel tcpSocketChannel;
    
	public TcpServer(SocketAddress channelPort, Selector selector) throws IOException {
		tcpSocketChannel = ServerSocketChannel.open();
		tcpSocketChannel.socket().bind(channelPort);
		tcpSocketChannel.configureBlocking(false);
		tcpSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}
    
	public void handleClient() throws IOException {
		SocketChannel sc = tcpSocketChannel.accept();
		byte[] request = new byte[1024];
		byte[] response = new String("hello tcp client!").getBytes();
		
		if (sc != null) {
			sc.read(ByteBuffer.wrap(request));
			System.out.println(new String(request));
			sc.write(ByteBuffer.wrap(response));
		}
	}

}

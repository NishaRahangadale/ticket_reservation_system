package ee382n.assignments.ticket_res;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class TcpServer implements Runnable {
    static ServerSocketChannel tcpSocketChannel;


	public TcpServer(SocketAddress channelPort, Selector selector) throws IOException {
		tcpSocketChannel = ServerSocketChannel.open();
		tcpSocketChannel.socket().bind(channelPort);
		tcpSocketChannel.configureBlocking(false);
		tcpSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public void handleClient() {
		// TODO Auto-generated method stub
		
	}

}

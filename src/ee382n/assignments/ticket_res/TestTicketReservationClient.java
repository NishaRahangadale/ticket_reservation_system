package ee382n.assignments.ticket_res;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;


public class TestTicketReservationClient {
	private ExecutorService threadPool;
	
	@Before
	public void setUp() {
		threadPool = Executors.newCachedThreadPool();
	}
	
	@Test
	public void concTest() {
		
		for (int i = 0 ; i < 100 ; i++) {
			for (int j = 0 ; j < 10 ; j++) {
				threadPool.execute(new clientThread());
			}
		}
		assertTrue(true);
	}
	
	private class clientThread implements Runnable {
		int size;
		String[] commands;
		String[] names;
		boolean udpClient;
		Random rand;

		public clientThread() {
			size = 100;
			commands = new String[] { "reserve", "bookSeat", "search", "delete" };
			names = new String[] {"aaron", "jack", "jill", "phil", "mac", "denis", "gleb", "carlos", "david" };
			rand = new Random();
			udpClient = rand.nextBoolean();
		}

		@Override
		public void run() {
			String command = commands[Math.abs(rand.nextInt()) % commands.length];
			String name = names[Math.abs(rand.nextInt()) % names.length];
			String clientRequest;
			Integer num = null;
			
			if (command.equals("bookSeat")) {
				num = new Integer(Math.abs(rand.nextInt()) % size);
			}
			
			clientRequest = command + " " + name + ((num == null) ? (""): (" " + num.toString()));
			
			if (udpClient) {
				DatagramChannel clientChannel;
				try {
					clientChannel = DatagramChannel.open();
					InetAddress ia = InetAddress.getByName("192.168.1.5");
					byte[] request = clientRequest.getBytes();
					byte[] response = new byte[1024];
					ByteBuffer requestBuffer = ByteBuffer.wrap(request);
					clientChannel.send(requestBuffer, new InetSocketAddress(ia, 8080));
					clientChannel.receive(ByteBuffer.wrap(response));
					clientChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					InetAddress ia = InetAddress.getByName("192.168.1.5");
					SocketChannel sc = SocketChannel.open(new InetSocketAddress(ia, 8080));
					byte[] request = clientRequest.getBytes();
					byte[] response = new byte[1024];
					ByteBuffer requestBuffer = ByteBuffer.wrap(request);
					sc.write(requestBuffer);
					sc.read(ByteBuffer.wrap(response));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	@Test 
	public void userInputParseTest0(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve name");
		assertTrue(ticket0.call.equals("RESERVE"));
		assertTrue(ticket0.name.equals("NAME"));
		assertTrue(ticket0.seatNum == -1);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat name 5");
		assertTrue(ticket1.call.equals("BOOKSEAT"));
		assertTrue(ticket1.name.equals("NAME"));
		assertTrue(ticket1.seatNum == 5);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search name");
		assertTrue(ticket2.call.equals("SEARCH"));
		assertTrue(ticket2.name.equals("NAME"));
		assertTrue(ticket2.seatNum == -1);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete name");
		assertTrue(ticket3.call.equals("DELETE"));
		assertTrue(ticket3.name.equals("NAME"));
		assertTrue(ticket3.seatNum == -1);		
		
	}
	
	@Test 
	public void userInputParseTest1(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve <name>");
		assertTrue(ticket0.call.equals("RESERVE"));
		assertTrue(ticket0.name.equals("NAME"));
		assertTrue(ticket0.seatNum == -1);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat <name> <5>");
		assertTrue(ticket1.call.equals("BOOKSEAT"));
		assertTrue(ticket1.name.equals("NAME"));
		assertTrue(ticket1.seatNum == 5);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search <name>");
		assertTrue(ticket2.call.equals("SEARCH"));
		assertTrue(ticket2.name.equals("NAME"));
		assertTrue(ticket2.seatNum == -1);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete <name>");
		assertTrue(ticket3.call.equals("DELETE"));
		assertTrue(ticket3.name.equals("NAME"));
		assertTrue(ticket3.seatNum == -1);		
		
	}
	
	@Test
	public void userInputParseTest3(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve");
		assertTrue(ticket0.call.equals("RESERVE"));
		assertTrue(ticket0.name == null);
		assertTrue(ticket0.seatNum == -1);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat");
		assertTrue(ticket1.call.equals("BOOKSEAT"));
		assertTrue(ticket1.name == null);
		assertTrue(ticket1.seatNum == -1);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search");
		assertTrue(ticket2.call.equals("SEARCH"));
		assertTrue(ticket2.name == null);
		assertTrue(ticket2.seatNum == -1);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete");
		assertTrue(ticket3.call.equals("DELETE"));
		assertTrue(ticket3.name == null);
		assertTrue(ticket3.seatNum == -1);		
		
	}
	
	@Test
	public void userInputParseTest4(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve name 5");
		assertTrue(ticket0.call.equals("RESERVE"));
		assertTrue(ticket0.name.equals("NAME"));
		assertTrue(ticket0.seatNum == 5);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat name 5");
		assertTrue(ticket1.call.equals("BOOKSEAT"));
		assertTrue(ticket1.name.equals("NAME"));
		assertTrue(ticket1.seatNum == 5);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search name 5");
		assertTrue(ticket2.call.equals("SEARCH"));
		assertTrue(ticket2.name.equals("NAME"));
		assertTrue(ticket2.seatNum == 5);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete name 5");
		assertTrue(ticket3.call.equals("DELETE"));
		assertTrue(ticket3.name.equals("NAME"));
		assertTrue(ticket3.seatNum == 5);		
		
	}

	@Test
	public void userInputCheckTest0(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve name");		
		assertTrue(ticket0.checkUserInput() == true);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat name 5");
		assertTrue(ticket1.checkUserInput() == true);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search name");
		assertTrue(ticket2.checkUserInput() == true);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete name");
		assertTrue(ticket3.checkUserInput() == true);
		
	}
	
	@Test
	public void userInputCheckTest1(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve <name>");
		assertTrue(ticket0.checkUserInput() == true);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat <name> <5>");
		assertTrue(ticket1.checkUserInput() == true);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search <name>");
		assertTrue(ticket2.checkUserInput() == true);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete <name>");
		assertTrue(ticket3.checkUserInput() == true);
	}
	
	@Test
	public void userInputCheckTest3(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve");
		assertTrue(ticket0.checkUserInput() == false);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat");
		assertTrue(ticket1.checkUserInput() == false);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search");
		assertTrue(ticket2.checkUserInput() == false);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete");
		assertTrue(ticket3.checkUserInput() == false);
	}
	
	@Test
	public void userInputCheckTest4(){
		TicketReservationClient ticket0 = new TicketReservationClient("reserve name 5");
		assertTrue(ticket0.checkUserInput() == false);
		
		TicketReservationClient ticket1 = new TicketReservationClient("bookseat name 5");
		assertTrue(ticket1.checkUserInput() == true);
		
		TicketReservationClient ticket2 = new TicketReservationClient("search name 5");
		assertTrue(ticket2.checkUserInput() == false);
		
		TicketReservationClient ticket3 = new TicketReservationClient("delete name 5");
		assertTrue(ticket3.checkUserInput() == false);
	}

}

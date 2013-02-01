import static org.junit.Assert.*;

import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;

import ee382n.assignments.ticket_res.TicketReservationClient;


public class TestTicketReservationClient {

	@Test public void userInputParseTest0(){
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
	
	@Test public void userInputParseTest1(){
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
	
	@Test public void userInputParseTest3(){
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
	
	@Test public void userInputParseTest4(){
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
}

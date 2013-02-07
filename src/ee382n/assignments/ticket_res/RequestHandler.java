/**
 * @file	RequestHandler.java
 * 
 * @author 	Aaron Alaniz (aaron.a.alaniz@gmail.com)
 * 
 * @author	Moshiul Arefin (marefin@globalintertech.com)
 * 
 * @brief	This class handles concurrent requests to interact with ticket reservation database.
 */

package ee382n.assignments.ticket_res;

public class RequestHandler{
	final static int RESERVE = 1097075900;
	final static int BOOK_SEAT = 2004607118;
	final static int BOOKSEAT = 2005560430;
	final static int SEARCH = -906336856;
	final static int DELETE = -1335458389;
	
	public static synchronized String handle(String commandBuffer[]) {		
		String command = commandBuffer[0];
		String name = commandBuffer[1];
		String response = "";
		
		try {
			switch (command.hashCode()) {
			
			case RESERVE:
				System.out.println("Trying reservation for " + name + " on " + Thread.currentThread().getName());
				response = TicketReservationDatabase.reserve(name);
				break;
				
			case BOOK_SEAT:
			case BOOKSEAT:
				int seatNum = Integer.parseInt(commandBuffer[2]);
				
				System.out.println("Trying reservation for " + name + " in seat " + seatNum + " on " + Thread.currentThread().getName());				
				response = TicketReservationDatabase.bookSeat(name, seatNum);
				break;
				
			case SEARCH:
				System.out.println("Searching reservations for " + name + " on " + Thread.currentThread().getName());
				response = TicketReservationDatabase.search(name);
				break;
				
			case DELETE:
				System.out.println("Deleting Reservations for " + name + " on " + Thread.currentThread().getName());
				response = TicketReservationDatabase.delete(name);
				break;
				
			default:
				response = "Invalid request on ticket reservation database";
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = "Unknown exception caused on server";
		}
		return response;
	}
}

package ee382n.assignments.ticket_res;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TicketReservationDatabase {
	static TicketReservationDatabase trd = null;
	static int capacity;
	static HashMap<String, Integer> reservations;
	static Set<Integer> availableSeats;

	public static synchronized void init(int cap) {
		if (trd == null) {
			trd = new TicketReservationDatabase();
			reservations = new HashMap<String, Integer>();
			availableSeats = new HashSet<Integer>();
			capacity = cap;
			
			// Populate available seats
			for (int i = 0 ; i < capacity ; i++) {
				availableSeats.add(new Integer(i));
			}
		}
	}
	
	public static boolean isAvailable(int seat) {
		return availableSeats.contains(new Integer(seat));
	}
	
	public static boolean hasReservation(String name) {
		return reservations.containsKey(name);
	}
	
	public static String bookSeat(String name, int seatNum) {
		String response = "";
		
		if (hasReservation(name)) {
			response = name + " all ready has reservation on " + search(name);
		} else if (TicketReservationDatabase.isAvailable(seatNum)) {
			reservations.put(name, seatNum);
			availableSeats.remove(new Integer(seatNum));
			response = "Seat assigned to you is " + seatNum;
		} else {
			response = seatNum + " is not available";
		}
		
		return response;
	}
	
	public static String reserve(String name) {
		String response = "";
		
		if (availableSeats.isEmpty()) {
			response = "Sold out - No seat available";
		} else if (hasReservation(name)) {
			response = "Seat already booked against the name provided";
		} else {
			Integer seatNum = availableSeats.iterator().next();
			response = "Seat assigned to you is " + seatNum;
			reservations.put(name, seatNum);
			availableSeats.remove(new Integer(seatNum));
		}
		return response;
	}
	
	public static String search(String name) {
		String response = "";
		
		if (reservations.containsKey(name)) {
			response = new String(reservations.get(name).toString());
		} else {
			response = "No reservations found for " + name;
		}
		return response;
	}
	
	public static String delete(String name) {
		String response = "";
		
		if (hasReservation(name)) {
			response = new String(reservations.get(name).toString());
			availableSeats.add(new Integer(search(name)));
			reservations.remove(name);
		} else {
			response = "No reservations found for " + name;
		}
		return response;
	}
}

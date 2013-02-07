/**
 * @file	TicketReservationClient.java
 * 
 * @author 	Aaron Alaniz (aaron.a.alaniz@gmail.com)
 * 
 * @author	Moshiul Arefin (marefin@globalintertech.com)
 * 
 * @brief	This serves as the general ticket reservation client.
 */

package ee382n.assignments.ticket_res;

import java.util.*;

public class TicketReservationClient {

	public String call;
	public String name;
	public int seatNum;
	
	public TicketReservationClient(String userInput){
		
		try
		{
			String delimeters  = "[ <>]";
			String[] userInputArray = userInput.split(delimeters);
			
			//this.
			call = userInputArray.length >= 1 ? userInputArray[0].toUpperCase() : null;
			//this.
			name = userInputArray.length >= 2 ? userInputArray[1].toUpperCase() : null;
			//this.
			seatNum = userInputArray.length >= 3 ? Integer.parseInt(userInputArray[2]) : -1; 
		}
		catch(NumberFormatException e)
		{
			//this.
			call = null;
			//this.
			name = null;
			//this.
			seatNum = -1;				
			//System.out.println("Invalid Input, try again: ");
		}
		
		
	}
	
	
	/*
	 * 
	 * 1. reserve <name> – inputs the name of a person and reserves a seat against this name. If the theater does not
have enough seats(completely booked), no seat is assigned and the command responds with message: ‘Sold out -
No seat available’. If a reservation has already been made under that name, then the command responds with
message: ‘Seat already booked against the name provided’.
Otherwise, a seat is reserved against the name provided and the client is relayed a message: ‘Seat assigned to you
is <seat-number>’.
2. bookSeat <name> <seatNum> – behaves similar to reserve command, but imposes an additional constraint that
a seat is reserved if and only if there is no existing reservation against name and the seat having the number seatNum
is available. If there is no existing reservation but seatNum is not available, the response is: ‘<seatNum> is not
available’.
3. search <name> – returns the seat number reserved for name. If no reservation is found for name the system responds
with a message: ‘No reservation found for <name>’.
4. delete <name> – frees up the seat allocated to that person. The command returns the seat number that was
released. If no existing reservation was found, responds with: ‘No reservation found for <name>’.
Here <text> is used to indicate the value of the parameter denoted by text in the given context. Note that you
also have to write the client program that takes input from the user and then communicates with a server using sockets.
Your program should behave correctly in presence of multiple concurrent clients.
	 * 
	 * 
	 */
	
	
	public boolean checkUserInput()//TicketReservationClient userInput)
	{
		//Valid calls/commands
		List<String> validCalls = Arrays.asList("RESERVE", "BOOKSEAT", "SEARCH", "DELETE");
				
		//make sure it's one of the valid calls
		if (!validCalls.contains(call))//userInput.call))
		{	
			return false;
		}
		
		//make sure every call has the name parameter
		if ( name == null)
		{
			return false;
		}
		
		//seatnum cannot be null/0 for bookseat
		if (call.compareTo("BOOKSEAT") == 0  && seatNum == -1)
		{
			return false;
		}
		//make there's no additional param for the following calls
		if ((call.compareTo("RESERVE") == 0 || call.compareTo("SEARCH") == 0 || call.compareTo("DELETE") == 0) 
			&& seatNum != -1)
		{
			return false;			
		}
		
		
		return true;
		
	
	}
	
	
}

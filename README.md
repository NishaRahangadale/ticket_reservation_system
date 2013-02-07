ee382n Assignment 1 -- Ticket Reservation System with TCP/UDP
==============================================================

Authors
-------
Aaron Alaniz and Moshiul Arefin

Brief
-----
The following directory contains all source needed for assignment 1. Please execute the following commands within the directory that these files reside. You will find the file "server_config.xml". Open this file and you will find a server tag with the id of "grader". This is where you enter your information for execution (port number, server capacity, and ip address). Once you have edited the "server_config.xml" appropriately you are ready to proceed with the following guide.

Compiling
---------
javac *

Starting the Server
-------------------
java -cp . TicketReservationServer grader

Starting the Udp Client
-----------------------
java -cp . UdpTicketReservationClient grader

Starting the Tcp Client
-----------------------
java -cp . TcpTicketReservationClient grader
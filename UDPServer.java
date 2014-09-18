package udp; 

import java.io.IOException; 
import java.net.DatagramPacket; 
import java.net.DatagramSocket; 
import java.net.SocketException; 
import java.net.SocketTimeoutException; 
import java.util.Arrays; 

import common.MessageInfo; 

public class UDPServer { 
    
    private DatagramSocket recvSoc; 
    private int totalMessages = -1; 
    private int[] receivedMessages; 
    private boolean close; 
    
    private void run() { 
	int		pacSize; 
	byte[]		pacData; 
	DatagramPacket 	pac; 
	
	// TO-DO: Receive the messages and process them by calling processMessage(...). 
	//        Use a timeout (e.g. 30 secs) to ensure the program doesn't block forever 
	
	try{ 
	    pacData = new byte[1024]; 			//initialise array size 
	    pacSize = pacData.length; 			//set pacSize = pacData array length/size 
	    
	    while(!close){ 
		pac = new DatagramPacket(pacData, pacSize); 	             //create the datagram packet 
		try{ 
		    recvSoc.setSoTimeout(30000); 	//set timeout (in miliseconds - 30s) 
		    recvSoc.receive(pac);		//receive message 
		    byte[] bytes = pac.getData(); 
		    String data = new String(bytes);	//convert byte array into string 
		    processMessage(data);		//process the message 
		} 
		catch (SocketTimeoutException e){	
		    System.out.println("Socket timeout: " + e); 
		} 
	    }		 
	} catch (SocketException e) { 
	    System.out.println("Socket exception: " + e); 
	} catch (Exception e){ 
	    System.out.println("Exception: " + e); 
	} 
    } 
    
    public void processMessage(String data) { 
	
	MessageInfo msg = null; 
	
	// TO-DO: Use the data to construct a new MessageInfo object 
	try{ 
	    msg = new MessageInfo(data.trim()); 
	}catch (Exception e){ 
	    System.out.println("Error creating MessageInfo " + e); 
	} 
	
	// TO-DO: On receipt of first message, initialise the receive buffer 
	if(receivedMessages == null){					//if first msg: 
	    receivedMessages = new int[msg.totalMessages]; 		//initialises buffer to same size as 
	}								//total number of expected msgs 
	
	// TO-DO: Log receipt of the message 
	receivedMessages[msg.messageNum] = 5;		//arbitrary value, to show if msg received 
	
	
	// TO-DO: If this is the last expected message, then identify 
	//        any missing messages 
	String messagesSent = "Messages sent: " + msg.totalMessages; 
	String recMessages = "Messages received: "; 
	String lostMessages = "Lost messages: ";		//list of lost messages	 
	int lost = 0;	 
	if(++msg.messageNum == msg.totalMessages){	 
	    for(int i = 0; i < msg.totalMessages; i++){ 
		if(receivedMessages[i] != 5){ 			//check if message was logged 
		    lostMessages = lostMessages + (i + 1) + " "; 
		    lost++; 
		} 
	    } 
	    totalMessages = -1;   				//reset 
	    receivedMessages = null;			//reset 
	    //Print results:	 
	    System.out.println(messagesSent); 
	    System.out.println(recMessages + (msg.totalMessages - lost)); 
	    System.out.println(lostMessages); 
	} 
    } 
    
    
    public UDPServer(int rp) { 
	// TO-DO: Initialise UDP socket for receiving data 
	try{ 
	    recvSoc = new DatagramSocket(rp); 
	} 
	catch (SocketException e){ 
	    System.out.println("Error initialising UDP socket - recvSOC"); 
	    System.exit(-1); 
	} 
	close = false; 
	
	// Done Initialisation 
	//System.out.println("UDPServer ready"); 
    } 
    
    
    
    

    public static void main(String args[]) { 
	int	recvPort; 
	
	// Get the parameters from command line 
	if (args.length < 1) { 
	    System.err.println("Arguments required: recv port"); 
	    System.exit(-1); 
	} 
	recvPort = Integer.parseInt(args[0]); 
	
	// TO-DO: Construct Server object and start it by calling run(). 
	UDPServer udpServer = new UDPServer(recvPort); 
	udpServer.run(); 
    } 
    
}

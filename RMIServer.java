package rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;

import common.*;

public class RMIServer extends UnicastRemoteObject implements RMIServerI {
    
    private int totalMessages = -1;
    private int[] receivedMessages;
    
    public RMIServer() throws RemoteException {
    }
    
    public void receiveMessage(MessageInfo msg) throws RemoteException {
	
	// TO-DO: On receipt of first message, initialise the receive buffer
	if(receivedMessages == null){					//if first msg, initialises buffer
	    receivedMessages = new int[msg.totalMessages];		//size equal to totalMessages
	}
	// TO-DO: Log receipt of the message
	receivedMessages[msg.messageNum] = 5;  			//arbitrary value stored in buffer, 									//signifies msg received
	
	// TO-DO: If this is the last expected message, then identify
	//        any missing messages
	String messagesSent = "Messages sent: " + msg.totalMessages;
	String recMessages = "Messages received: ";
	String lostMessages = "Lost messages: ";			//list of lost messages	
	int lost = 0;	
	if(++msg.messageNum == msg.totalMessages){	
	    for(int i = 0; i < msg.totalMessages; i++){
		if(receivedMessages[i] != 5){ 				//check if message was logged
		    lostMessages = lostMessages + (++i) + " ";
		    lost++;
		}
	    }
	    totalMessages = -1;						//reset
	    receivedMessages = null;					//reset buffer
	    //Print results:
	    System.out.println(messagesSent);
	    System.out.println(recMessages + (msg.totalMessages - lost));
	    System.out.println(lostMessages);
	}
    }
    
    public static void main(String[] args) {
	
	RMIServer rmis = null;
	
	// TO-DO: Initialise Security Manager
	SecurityManager security = System.getSecurityManager();
	if(security == null){
	    System.setSecurityManager(new SecurityManager());
	    System.out.println("Security manager initialised");
	}
	
	// TO-DO: Instantiate the server class
	try {
	    rmis = new RMIServer();
	    System.out.println("Server class instantiated");
	} catch(Exception e){
	    System.out.println("Error instantiating server class: " + e);
	}
	
	// TO-DO: Bind to RMI registry
	rebindServer("RMIServer", rmis);
	System.out.println("Bind to RMI registry succesful");
	
    }
    
    protected static void rebindServer(String serverURL, RMIServer server) {
	
	// TO-DO:
	// Start / find the registry (hint use LocateRegistry.createRegistry(...)
	// If we *know* the registry is running we could skip this (eg run rmiregistry in the start script)		
	
	
	// TO-DO:
	// Now rebind the server to the registry (rebind replaces any existing servers bound to the serverURL)
	// Note - Registry.rebind (as returned by createRegistry / getRegistry) does something similar but
	// expects different things from the URL field.
	try{
	    LocateRegistry.createRegistry(1099).rebind(serverURL, server);//registry.rebind(serverURL, server);
	} catch(Exception e){						      //1099 - default rmi port
	    System.out.println("Exception rebinding server: " + e);
	}
    }
}

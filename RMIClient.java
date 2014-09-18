package rmi;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import common.MessageInfo;

public class RMIClient {
    
    public static void main(String[] args) {
	
	RMIServerI iRMIServer = null;
	
	// Check arguments for Server host and number of messages
	if (args.length < 2){
	    System.out.println("Needs 2 arguments: ServerHostName/IPAddress, TotalMessageCount");
	    System.exit(-1);
	}
	
	String urlServer = new String("rmi://" + args[0] + "/RMIServer");
	int numMessages = Integer.parseInt(args[1]);
	System.out.println("urlServer: " + urlServer);
	System.out.println("numMessages: " + numMessages);
	
	// TO-DO: Initialise Security Manager
	SecurityManager security = System.getSecurityManager();
	if(security == null){
	    System.setSecurityManager(new SecurityManager());
	    System.out.println("Security manager initialised");
	}
	
	// TO-DO: Bind to RMIServer
	try {
	    iRMIServer = (RMIServerI) Naming.lookup(urlServer);
	} catch(Exception e){
	    System.out.println("Bind to RMIServer - exception: " + e);
	}
	
	// TO-DO: Attempt to send messages the specified number of times
	try{
	    for(int i = 0; i < numMessages; i++){
		iRMIServer.receiveMessage(new MessageInfo(numMessages, i));
	    }
	} catch (Exception e){
	    System.out.println("Exception - sending messages: " + e);
	}
	
    }
}

package messenger.client.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import messenger.protocol.Port;

public class Client {
	
   Socket socket = null;
   ObjectInputStream ois = null;
   ObjectOutputStream oos = null;

   
   public Client()
   
   {
	 try {
		 
		socket = new Socket("192.168.0.235",Port.LOGIN);
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	 
   }



}

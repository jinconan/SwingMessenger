package messenger.client.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import messenger._db.vo.ChatVO;
import messenger.client.view.ClientFrame;
import messenger.client.view.dialog.ChatDialog;
import messenger.protocol.Message;
import messenger.protocol.Port;
import messenger.protocol.Server;

public class ExitRoom {
	
	Socket             socket = null;
	ObjectInputStream  ois    = null;
	ObjectOutputStream oos    = null;
	Message<ChatVO>    msg    = null;
	Message<ChatVO>    msg1    = null;
	
	
	public void method(Message<ChatVO> msg1) {

	   this.msg1 = msg1;
		
	}
	public void Exit(ChatDialog chat) {
		try {
			socket = new Socket(Server.IP,Port.LOGIN);
			oos    = new ObjectOutputStream(socket.getOutputStream());
			msg1.setType(Message.CHATROOM_EXIT);
			//서버에게  나의 MSG를 보낸다.~
		    oos.writeObject(msg1);
		    ois    = new ObjectInputStream(socket.getInputStream());
		    //서버로부터 완료가 되었는지 받는다.
		    this.msg = (Message<ChatVO>)ois.readObject();
			//응답이 들어오면 테이블을 갱신해준다. ~ 
		    
		    if(msg.getResponse().size()==1) {
		    	System.out.println("갱신완료");
		    }
		    else {
		    	JOptionPane.showMessageDialog(chat,"서버로부터 연결 중단","알림",JOptionPane.CLOSED_OPTION);
		    }
		    
		 } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
}

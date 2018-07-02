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
			//��������  ���� MSG�� ������.~
		    oos.writeObject(msg1);
		    ois    = new ObjectInputStream(socket.getInputStream());
		    //�����κ��� �Ϸᰡ �Ǿ����� �޴´�.
		    this.msg = (Message<ChatVO>)ois.readObject();
			//������ ������ ���̺��� �������ش�. ~ 
		    
		    if(msg.getResponse().size()==1) {
		    	System.out.println("���ſϷ�");
		    }
		    else {
		    	JOptionPane.showMessageDialog(chat,"�����κ��� ���� �ߴ�","�˸�",JOptionPane.CLOSED_OPTION);
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

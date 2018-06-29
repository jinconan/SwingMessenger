package messenger.client.Emoticon;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;

import messenger.protocol.Message;
import messenger.protocol.Port;

public class GetEmoticon {
	//회원이 로그인에 성공해서 서버에 접속하는 순간 이모티콘을 불러오면됨
	 HashMap<String, JLabel> list = new HashMap<String, JLabel>();
	 
	public HashMap GetEmoticon() {
		// TODO Auto-generated constructor stub
	
		try {
			Socket socket = new Socket("localhost",Port.EMOTICON);
			
			Message<JLabel> msg = new Message<JLabel>();
			msg.setType(Message.EMOTICON_LOAD);
			
			try (
				OutputStream 		os = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(os); 
			) {
				oos.writeObject(msg);
				oos.flush();
				
				try (InputStream is = socket.getInputStream();
						BufferedInputStream bis = new BufferedInputStream(is);
						ObjectInputStream 	ois = new ObjectInputStream(bis);
				) {
					msg = (Message<JLabel>)ois.readObject();
					ArrayList<JLabel> response = (ArrayList<JLabel>)msg.getResponse();
					for(JLabel label : response) {
						list.put(label.getText(), label);
					}

				} catch(Exception e) {
					e.printStackTrace();
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
}

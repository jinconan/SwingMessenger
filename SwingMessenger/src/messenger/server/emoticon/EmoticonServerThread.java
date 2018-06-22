package messenger.server.emoticon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import messenger._db.dao.EmoticonDAO;
import messenger._db.dao.LoginDAO;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.server.view.ServerView;

public class EmoticonServerThread implements Runnable{
	private Socket 		socket;
	private JTextArea	jta_log;
	
	public EmoticonServerThread(JTextArea jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}

	@Override
	public void run() {
		try (
				InputStream			in 	= socket.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream	oin = new ObjectInputStream(bin);
			){
				System.out.println("socket.getInputStream()");
				Message<JLabel> msg = (Message<JLabel>)oin.readObject();
				System.out.println("oin.readObject()");
				if(jta_log != null) 
					jta_log.append("��û : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				
				//���Ͽ��� ��½�Ʈ���� ���, DAO���� ���� ó���� �Ͽ� ���� ����� Ŭ���̾�Ʈ�� �����Ѵ�.
				try(
					OutputStream			out		= socket.getOutputStream();
					BufferedOutputStream	bout	= new BufferedOutputStream(out);
					ObjectOutputStream		oout	= new ObjectOutputStream(bout);
				){
					System.out.println("socket.getOutputStream()");
					EmoticonDAO dao = EmoticonDAO.getInstance();
					System.out.println("DAO.getInstance()");
					
					ArrayList<JLabel> response = dao.getEmoticonList();
					msg.setResponse(response);
					oout.writeObject(msg);
					oout.flush();
					System.out.println("oout.writeObject(res_msg);");
					if(jta_log != null) {
						jta_log.append("����: " + socket.getInetAddress().toString() + ", " + socket.getPort() + ": " +response.size()+ "��\n");
					}
				}catch (Exception e) {
					e.printStackTrace();
					if(jta_log != null) {
						jta_log.append(e.toString()+"\n");
					}
				}
					
			}catch (Exception e) {
				e.printStackTrace();
				if(jta_log != null) {
					jta_log.append(e.toString()+"\n");
				}
			}
	}

}

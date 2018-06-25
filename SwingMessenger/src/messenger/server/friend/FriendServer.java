package messenger.server.friend;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JTextArea;

import messenger.protocol.Port;
import messenger.server.chat.ChatServerThread;
import messenger.server.view.ServerView;

/*********************************************
 * ģ������
 * �����ͺ��̽� �������
 * Ŀ�ؼ��� �̷�����°��� Ȯ��
 * 
 *********************************************/
public class FriendServer {
	JTextArea jta_log;
	ServerSocket s_socket;
	public FriendServer(JTextArea jta_log){
		try {
			this.jta_log = jta_log;
			s_socket = new ServerSocket(Port.FRIEND);//���� �����ϱ� ���� ����[��Ʈ��ȣ]
			if(this.jta_log != null)
				jta_log.append("���� ����. port : "+s_socket.getLocalPort()+"\n");
			System.out.println("���� �������");
			System.out.println("���� ����");
			System.out.println("Ŭ���̾�Ʈ �����");
			String ServerMsg = "Server���� ����";
		} catch (Exception e) {
			e.printStackTrace();
			if(this.jta_log != null)
				jta_log.append(e.toString()+"\n");
	}
	}
	public void run() {
		while(true) {
			try {
				Socket	c_socket = s_socket.accept();//���ѷ����� ���� ������ �����.
				Thread thread = new Thread(new FriendThread(jta_log, c_socket));
				thread.start();
			}catch (Exception e) {
				e.printStackTrace();
				if(this.jta_log != null) {
					jta_log.append(e.toString()+"\n");
					jta_log.append("���� �����.");
				}
				return;
			} 
		}
	}
}

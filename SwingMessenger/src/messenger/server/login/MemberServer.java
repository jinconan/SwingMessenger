package messenger.server.login;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JTextArea;

import messenger.protocol.Port;
import messenger.server.ServerView;
import messenger.server.chat.ChatServerThread;

/*********************************************
 * ģ������ �����ͺ��̽� ������� Ŀ�ؼ��� �̷�����°��� Ȯ��
 * 
 *********************************************/
public class MemberServer {
	JTextArea jta_log;
	ServerSocket s_socket;

	public MemberServer(JTextArea jta_log) {
		try {
			this.jta_log = jta_log;
			s_socket = new ServerSocket(Port.MEMBER);// ���� �����ϱ� ���� ����[��Ʈ��ȣ]
			if (this.jta_log != null)
				jta_log.append("���� ����. port : " + s_socket.getLocalPort() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			if (this.jta_log != null)
				jta_log.append(e.toString() + "\n");
		}
	}

	public void run() {
		while (true) {
			try {
				Socket c_socket = s_socket.accept();// ���ѷ����� ���� ������ �����.
				Thread thread = new Thread(new MemberServerThread(jta_log, c_socket));
				thread.start();
			} catch (Exception e) {
				e.printStackTrace();
				if (this.jta_log != null) {
					jta_log.append(e.toString() + "\n");
					jta_log.append("���� �����.");
				}
				return;
			}
		}
	}
}

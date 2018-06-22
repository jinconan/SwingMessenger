package messenger.server.friend;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import messenger.protocol.Port;
import messenger.server.emoticon.EmoticonServerThread;

public class FriendServer {
	private JTextArea jta_log;
	private ServerSocket serverSocket;
	
	public FriendServer(JTextArea jta_friendlog) {
		try {
			this.jta_log = jta_friendlog;
			serverSocket = new ServerSocket(Port.FRIEND);
			if(this.jta_log != null)
				jta_log.append("서버 시작. port : "+serverSocket.getLocalPort()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
			if(this.jta_log != null)
				jta_log.append(e.toString()+"\n");
		}
	}
	
	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
//				Thread thread = new Thread(new FriendServer(jta_log, socket));
//				thread.start();
			}catch (Exception e) {
				e.printStackTrace();
				if(this.jta_log != null) {
					jta_log.append(e.toString()+"\n");
					jta_log.append("서버 종료됨.");
				}
				return;
			} 
		}
	}
}

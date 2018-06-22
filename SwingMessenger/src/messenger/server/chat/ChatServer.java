package messenger.server.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextArea;

import messenger.protocol.Port;
import messenger.server.view.ServerView;

public class ChatServer {
	private ServerSocket serverSocket;
	private JTextArea jta_log;
	
	public ChatServer(JTextArea jta_log) {
		try {
			this.jta_log = jta_log;
			serverSocket = new ServerSocket(Port.CHAT);
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
				Thread thread = new Thread(new ChatServerThread(jta_log, socket));
				thread.start();
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

package messenger.server.login;

import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JTextArea;

import messenger.protocol.Port;
import messenger.server.view.ServerView;

public class LoginServer {
	private JTextArea jta_log;
	private ServerSocket serverSocket;
	
	public LoginServer(JTextArea jta_log) {
		try {
			this.jta_log = jta_log;
			serverSocket = new ServerSocket(Port.LOGIN);
			if(this.jta_log != null)
				jta_log.append("서버 시작. port : "+serverSocket.getLocalPort()+"\n");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(this.jta_log != null)
				jta_log.append(e.toString()+"\n");
		}
	}

	public void run() {
		while(true) {
			try {
				//클라이언트로 부터 연결 받음. 인증 처리를 위한 쓰레드 생성.
				Socket socket = serverSocket.accept();
				Thread thread = new Thread(new LoginServerThread(jta_log, socket));
				thread.start();
			} catch (Exception e) {
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

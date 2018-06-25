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
 * 친구서버
 * 데이터베이스 연결관리
 * 커넥션이 이루어지는것을 확인
 * 
 *********************************************/
public class FriendServer {
	JTextArea jta_log;
	ServerSocket s_socket;
	public FriendServer(JTextArea jta_log){
		try {
			this.jta_log = jta_log;
			s_socket = new ServerSocket(Port.FRIEND);//서버 접속하기 위한 소켓[포트번호]
			if(this.jta_log != null)
				jta_log.append("서버 시작. port : "+s_socket.getLocalPort()+"\n");
			System.out.println("서버 생성대기");
			System.out.println("서버 생성");
			System.out.println("클라이언트 대기중");
			String ServerMsg = "Server접속 성공";
		} catch (Exception e) {
			e.printStackTrace();
			if(this.jta_log != null)
				jta_log.append(e.toString()+"\n");
	}
	}
	public void run() {
		while(true) {
			try {
				Socket	c_socket = s_socket.accept();//무한루프로 인해 접속을 대기중.
				Thread thread = new Thread(new FriendThread(jta_log, c_socket));
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

package messenger.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTextArea;

import messenger._db.vo.MemberVO;
import messenger._protocol.Port;

/**
 * 채팅서버. 클라이언트의 접속을 accept하며 쓰레드를 인스턴스화하는 클래스
 * @author Jin Lee
 *
 */
public class ChatServer {
	private ServerSocket serverSocket;
	private JTextArea jta_log; //채팅서버에서 기록을 출력할 텍스트영역
	
	private ChatServerThreadList threadList = new ChatServerThreadList();
	private	Map<Integer,MemberVO> loginMap;
	/**
	 * 생성자
	 * @param jta_log - 기록을 출력할 JTextArea
	 */
	public ChatServer(JTextArea jta_log, Map<Integer,MemberVO> loginMap) {
		try {
			this.jta_log = jta_log;
			this.loginMap =loginMap;
			serverSocket = new ServerSocket(Port.CHAT);
			if(this.jta_log != null)
				jta_log.append("서버 시작. port : "+serverSocket.getLocalPort()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
			if(this.jta_log != null)
				jta_log.append(e.toString()+"\n");
		}
	}

	/**
	 * 채팅서버 동작 메소드. 특별한 에러가 없으면 무한히 클라이언트의 요청을 accept한다. 
	 */
	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				Thread thread = new Thread(new ChatServerThread(jta_log, socket,threadList,loginMap));
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

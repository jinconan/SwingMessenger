package messenger.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import javax.swing.JTextArea;

import messenger._db.vo.MemberVO;
import messenger._protocol.Port;

/*********************************************
 * 친구서버 데이터베이스 연결관리 커넥션이 이루어지는것을 확인
 * 
 *********************************************/
public class MemberServer {
	JTextArea jta_log;
	ServerSocket s_socket;
	Map<Integer, MemberVO> loginMap;
	
	public MemberServer(JTextArea jta_log, Map<Integer,MemberVO> loginMap) {
		try {
			this.jta_log = jta_log;
			this.loginMap = loginMap;
			s_socket = new ServerSocket(Port.MEMBER);// 서버 접속하기 위한 소켓[포트번호]
			if (this.jta_log != null)
				jta_log.append("서버 시작. port : " + s_socket.getLocalPort() + "\n");
		} catch (Exception e) {
			e.printStackTrace();
			if (this.jta_log != null)
				jta_log.append(e.toString() + "\n");
		}
	}

	public void run() {
		while (true) {
			try {
				Socket c_socket = s_socket.accept();// 무한루프로 인해 접속을 대기중.
				Thread thread = new Thread(new MemberServerThread(jta_log, c_socket, loginMap));
				thread.start();
			} catch (Exception e) {
				e.printStackTrace();
				if (this.jta_log != null) {
					jta_log.append(e.toString() + "\n");
					jta_log.append("서버 종료됨.");
				}
				return;
			}
		}
	}
}

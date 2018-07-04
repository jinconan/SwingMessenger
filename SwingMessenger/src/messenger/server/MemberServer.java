package messenger.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import javax.swing.JTextArea;

import messenger._db.vo.MemberVO;
import messenger._protocol.Port;

/*********************************************
 * ģ������ �����ͺ��̽� ������� Ŀ�ؼ��� �̷�����°��� Ȯ��
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
				Thread thread = new Thread(new MemberServerThread(jta_log, c_socket, loginMap));
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

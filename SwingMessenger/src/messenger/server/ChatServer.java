package messenger.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JTextArea;

import messenger._db.vo.MemberVO;
import messenger._protocol.Port;

/**
 * ä�ü���. Ŭ���̾�Ʈ�� ������ accept�ϸ� �����带 �ν��Ͻ�ȭ�ϴ� Ŭ����
 * @author Jin Lee
 *
 */
public class ChatServer {
	private ServerSocket serverSocket;
	private JTextArea jta_log; //ä�ü������� ����� ����� �ؽ�Ʈ����
	
	private ChatServerThreadList threadList = new ChatServerThreadList();
	private	Map<Integer,MemberVO> loginMap;
	/**
	 * ������
	 * @param jta_log - ����� ����� JTextArea
	 */
	public ChatServer(JTextArea jta_log, Map<Integer,MemberVO> loginMap) {
		try {
			this.jta_log = jta_log;
			this.loginMap =loginMap;
			serverSocket = new ServerSocket(Port.CHAT);
			if(this.jta_log != null)
				jta_log.append("���� ����. port : "+serverSocket.getLocalPort()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
			if(this.jta_log != null)
				jta_log.append(e.toString()+"\n");
		}
	}

	/**
	 * ä�ü��� ���� �޼ҵ�. Ư���� ������ ������ ������ Ŭ���̾�Ʈ�� ��û�� accept�Ѵ�. 
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
					jta_log.append("���� �����.");
				}
				return;
			} 
		}
	}
	
}

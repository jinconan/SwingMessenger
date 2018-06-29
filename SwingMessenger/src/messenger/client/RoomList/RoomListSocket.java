package messenger.client.RoomList;

import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class RoomListSocket extends JFrame {
	Socket socket = null;
	String serverIP = "192.168.0.235";
	// 테스트용
	JPanel jp = new JPanel();
	JTable jt = new JTable();
	Message<MemberVO> msg = new Message<MemberVO>();
	MemberVO mVO = new MemberVO();
	ArrayList<MemberVO> arraylist = new ArrayList<MemberVO>();

	// 여기까지 테스트용임 원래는 로그인할 시 서버가 건내준 사용자의 memberVO를 사용해야함
	// 로그인 서버로부터 받은 회원정보가 담긴 memberVO가 필요함
	private RoomListSocket() {
		// 테스트
		mVO.setMem_no(25);
		arraylist.add(mVO);
		msg.setResponse(arraylist);
		
		try {
			// Message<MemberVO> msg = new Message<MemberVO>();

			socket = new Socket(serverIP, Port.CHAT);
			SendChatVO sendChatVO = new SendChatVO(socket);
			sendChatVO.sendChatVO(msg);
			// this는 테스트용임
			// this말고 ui단의 jtable의 주소번지를 파라미터로 줘야함
			// sendChatVO의 파라미터에 로그인시 서버가 전송한 MemberVO를 주고
			// 그 후 쓰레드에서는 readObject만 함
			Thread thread = new Thread(new RoomList(socket, this));
			initDisplay();
			thread.start();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initDisplay() {

		jp.add(new JScrollPane(jt));
		add("Center", jp);
		
		setSize(500, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	// 테스트용 메인메소드
	public static void main(String args[]) {
		new RoomListSocket();
	}
}

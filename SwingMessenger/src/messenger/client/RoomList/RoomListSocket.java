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
	// �׽�Ʈ��
	JPanel jp = new JPanel();
	JTable jt = new JTable();
	Message<MemberVO> msg = new Message<MemberVO>();
	MemberVO mVO = new MemberVO();
	ArrayList<MemberVO> arraylist = new ArrayList<MemberVO>();

	// ������� �׽�Ʈ���� ������ �α����� �� ������ �ǳ��� ������� memberVO�� ����ؾ���
	// �α��� �����κ��� ���� ȸ�������� ��� memberVO�� �ʿ���
	private RoomListSocket() {
		// �׽�Ʈ
		mVO.setMem_no(25);
		arraylist.add(mVO);
		msg.setResponse(arraylist);
		
		try {
			// Message<MemberVO> msg = new Message<MemberVO>();

			socket = new Socket(serverIP, Port.CHAT);
			SendChatVO sendChatVO = new SendChatVO(socket);
			sendChatVO.sendChatVO(msg);
			// this�� �׽�Ʈ����
			// this���� ui���� jtable�� �ּҹ����� �Ķ���ͷ� �����
			// sendChatVO�� �Ķ���Ϳ� �α��ν� ������ ������ MemberVO�� �ְ�
			// �� �� �����忡���� readObject�� ��
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

	// �׽�Ʈ�� ���θ޼ҵ�
	public static void main(String args[]) {
		new RoomListSocket();
	}
}

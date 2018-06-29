package messenger.client.RoomList;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import messenger._db.vo.*;
import messenger.protocol.*;
import javax.swing.table.DefaultTableModel;

public class RoomList implements Runnable {
	String colNames[] = { "ä�ù��̸�" };
	ObjectInputStream ois = null; // ���
	Socket socket = null;
	DefaultTableModel chatTable = null;
	// �׽�Ʈ��
	RoomListSocket roomSocket = null;
	// �α����� ȸ���� VO�� �����κ��� ����
	// �α��� �������� Ŭ���̾�Ʈ�� ������ ������
	// chatServerThreadŬ������ �ĸ��Ӹ� ���������
	// Message<ChatVO>Ÿ���� ChatVO���� ������� MemberVO�� ��Ƽ�

	/* �������� �޾ƿ� ������ �븮��Ʈ�� �ٽ� ������ ��û�ϰ� �޾ƿ°��� ArrayList�� ��Ƽ� ����ָ�� */
	// �� ����Ʈ������ �Ķ���ͷ� �����Ŵ� �׽�Ʈ�Ϸ�����
	// ���߿� ��ĥ���� ����ٰ� ui�ּҹ����� ��
	public RoomList(Socket socket, RoomListSocket roomSocket) {
		this.socket = socket;
		this.roomSocket = roomSocket;

	}

	public void getChatVO() {
		/*
		 * ChatVO�� �����ϴ°� ������X ( ä�� ������, �� ��û�ϱ�, ģ�� �ʴ��ϱ�, etc)
		 * 
		 * �޴°��� ������(�������� ���� �޽�����, �� �ʴ����, �� ����Ʈ ����)
		 */
		boolean isRun = false;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			while (isRun!= true) {
				// ������ ������ ��� �����ؼ� while���� ���ѹݺ���Ŵ
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();

				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// ���� ä���� ȭ�鿡 ���
					break;
				case Message.CHATROOM_LOAD:
					// ����Ʈ���̺�� ���� addROw�� �ο� �߰��ϰ� JTable�� ����.
					chatRoom_Load(chatRoomTableModel(msg));

					// �� ����Ʈ�� ���ΰ�ħ
					roomSocket.jt.setModel(chatTable);
//					refreshTable();
					break;
				case Message.CHATROOM_EXIT:
					// ������ ���� �����κ��� ���޹޴µ�, �̰��� ����Ʈ���̺�𵨿��� ã�Ƽ� ����.
					// �� ����Ʈ�� ���ΰ�ħ
					break;
				// case Message.CHATROOM_INVITE:

				// break;
				}
			}

		} catch (EOFException ee) {
			ee.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// dtm����� �޼ҵ�
	public DefaultTableModel chatRoomTableModel(Message<ChatVO> msg) {

		ArrayList<ChatVO> room = (ArrayList<ChatVO>) msg.getResponse();
		chatTable = new DefaultTableModel(colNames, room.size());
		for(ChatVO c : room) 
			chatTable.addRow(new String[] {c.getRoomVO().getRoom_title()});
		
		return chatTable;
	}

	public void chatRoom_Load(DefaultTableModel room) {
		roomSocket.jt.setModel(room);
	}

	// ���ΰ�ħ �޼ҵ�//���̺� �ø��� ���ΰ�ħ��(����� �͸� �ִ� ��)
	public void refreshTable() {
		while (chatTable.getRowCount() > 0) {
			chatTable.setRowCount(0); // ����°�
		}

	}

	@Override
	public void run() {
		getChatVO();

	}
}

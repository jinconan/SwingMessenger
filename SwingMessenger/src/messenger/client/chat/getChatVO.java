package messenger.client.chat;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.Socket;

import messenger._db.vo.ChatVO;
import messenger.client.RoomList.RoomList;
import messenger.client.RoomList.RoomListSocket;
import messenger.protocol.Message;

public class getChatVO implements Runnable{

	Socket socket = null;
	ObjectInputStream ois = null;
	RoomListSocket roomSocket = null;
	public getChatVO(Socket socket){
		this.socket = socket; //ui���� �ּҹ�ȣ
	}
	public void getChatVO() { //������ ChatSocket���� ��
		/*
		 * ChatVO�� �����ϴ°� ������X ( ä�� ������, �� ��û�ϱ�, ģ�� �ʴ��ϱ�, etc)
		 * 
		 * �޴°��� ������(�������� ���� �޽�����, �� �ʴ����, �� ����Ʈ ����)
		 */
		boolean isRun = false;
		RoomList roomList = new RoomList(socket, roomSocket);
		SendChat sendChat = new SendChat(socket);
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			while(isRun !=true) {
				// ������ ������ ��� �����ؼ� while���� ���ѹݺ���Ŵ
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();
				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// ���� ä���� ȭ�鿡 ���
					
					sendChat.findEmoticon(sendChat.getChat(msg),/*���� ui�� jTextPane�� ������*/ );
					break;
					
				case Message.CHATROOM_LOAD:
					// ����Ʈ���̺�� ���� addROw�� �ο� �߰��ϰ� JTable�� ����.
					roomList.chatRoom_Load(roomList.chatRoomTableModel(msg));

					// �� ����Ʈ�� ���ΰ�ħ
					
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		getChatVO();
	}
}

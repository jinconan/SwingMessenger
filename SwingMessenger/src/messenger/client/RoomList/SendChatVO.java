package messenger.client.RoomList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import messenger._db.vo.*;
import messenger.protocol.*;

public class SendChatVO {
	Socket socket = null;
	ObjectOutputStream oos = null;

	public SendChatVO(Socket socket) {
		this.socket = socket;
	}

	// �������� �α��������� ������ MemberVO�� �Ķ���ͷ� �޾ƾ���
	public void sendChatVO(Message<MemberVO> msg) {// �����κ��� ȸ����ȣ�� �ް� cVO�� ȸ����ȣ�� ����ؼ�
		Message<ChatVO> sendMsg = new Message<ChatVO>(); // �ٽ� ������ �����ִ� �޼ҵ�
		ArrayList<ChatVO> list = new ArrayList<ChatVO>();
		ChatVO cVO = new ChatVO();
		cVO.setMemVO(msg.getResponse().get(0));
		list.add(cVO);
		sendMsg.setRequest(list);
		sendMsg.setType(Message.CHATROOM_LOAD);
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(sendMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

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

	// 서버에서 로그인했을때 보내준 MemberVO를 파라미터로 받아야함
	public void sendChatVO(Message<MemberVO> msg) {// 서버로부터 회원번호를 받고 cVO에 회원번호를 등록해서
		Message<ChatVO> sendMsg = new Message<ChatVO>(); // 다시 서버로 보내주는 메소드
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

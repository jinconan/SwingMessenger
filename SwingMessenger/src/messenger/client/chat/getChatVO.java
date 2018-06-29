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
		this.socket = socket; //ui켓의 주소번호
	}
	public void getChatVO() { //실행은 ChatSocket에서 함
		/*
		 * ChatVO를 전달하는건 쓰레드X ( 채팅 보내기, 방 요청하기, 친구 초대하기, etc)
		 * 
		 * 받는것은 쓰레드(서버에서 받은 메시지들, 방 초대받음, 방 리스트 받음)
		 */
		boolean isRun = false;
		RoomList roomList = new RoomList(socket, roomSocket);
		SendChat sendChat = new SendChat(socket);
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			while(isRun !=true) {
				// 서버의 응답을 계속 들어야해서 while문을 무한반복시킴
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();
				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// 받은 채팅을 화면에 출력
					
					sendChat.findEmoticon(sendChat.getChat(msg),/*여기 ui의 jTextPane이 들어가야함*/ );
					break;
					
				case Message.CHATROOM_LOAD:
					// 디폴트테이블모델 만들어서 addROw로 로우 추가하고 JTable에 부착.
					roomList.chatRoom_Load(roomList.chatRoomTableModel(msg));

					// 방 리스트를 새로고침
					
//					refreshTable();
					break;
				case Message.CHATROOM_EXIT:
					// 삭제된 놈을 서버로부터 전달받는데, 이것을 디폴트테이블모델에서 찾아서 제거.
					// 방 리스트를 새로고침
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

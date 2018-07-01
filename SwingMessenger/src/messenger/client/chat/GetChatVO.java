package messenger.client.chat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import messenger._db.vo.ChatVO;
import messenger.client.view.ClientData;
import messenger.client.view.ClientFrame;
import messenger.protocol.Message;

public class GetChatVO extends Thread {
	private Socket 				socket;
	private ClientData 			clientData;
	private ClientFrame 		clientFrame;
	private ObjectOutputStream 	out;
	
	public GetChatVO(Socket socket, ObjectOutputStream out, ClientData clientData) {
		this.socket 	 = socket;
		this.out 		 = out;
		this.clientData  = clientData;
		this.clientFrame = clientData.clientFrame;
	}

	@Override
	public void run() {
		try (
			BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
			ObjectInputStream ois = new ObjectInputStream(bis);
		) {
			while (true) {
				// 서버의 응답을 계속 들어야해서 while문을 무한반복시킴
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();
				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// 받은 채팅을 화면에 출력
					PrintChatContent printChatContent = new PrintChatContent();
					printChatContent.method(msg,clientData);
					break;
				case Message.CHATROOM_LOAD:
					// 디폴트테이블모델 만들어서 addROw로 로우 추가하고 JTable에 부착하고 방 목록 새로고침.
					GetRoomList getRoomList = new GetRoomList();
					getRoomList.method(msg, clientFrame);
					break;
				case Message.CHATROOM_INVITE:
					//새로 참가한 방을 서버로부터 전달받아서 디폴트테이블모델에 추가.
					AttendRoom attendRoom =new AttendRoom();
					attendRoom.method(msg);
					break;
				case Message.CHATROOM_EXIT:
					// 삭제된 놈을 서버로부터 전달받는데, 이것을 디폴트테이블모델에서 찾아서 제거.
					// 방 리스트를 새로고침
					ExitRoom exitRoom = new ExitRoom();
					exitRoom.method(msg);
					break;
				}
			}
		} catch (Exception e) {
			try {
				e.printStackTrace();
				if(out != null)
					out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}


	}
	
}

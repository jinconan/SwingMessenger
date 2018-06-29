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
	String colNames[] = { "채팅방이름" };
	ObjectInputStream ois = null; // 듣기
	Socket socket = null;
	DefaultTableModel chatTable = null;
	// 테스트용
	RoomListSocket roomSocket = null;
	// 로그인한 회원의 VO를 서버로부터 받음
	// 로그인 서버에서 클라이언트로 보내준 정보임
	// chatServerThread클래스의 파리머를 보내줘야함
	// Message<ChatVO>타입임 ChatVO에는 사용자의 MemberVO를 담아서

	/* 서버에서 받아온 정보로 룸리스트를 다시 서버에 요청하고 받아온것을 ArrayList에 담아서 띄워주면됨 */
	// 룸 리스트소켓을 파라미터로 받은거는 테스트하려고임
	// 나중에 합칠때는 저기다가 ui주소번지면 됨
	public RoomList(Socket socket, RoomListSocket roomSocket) {
		this.socket = socket;
		this.roomSocket = roomSocket;

	}

	public void getChatVO() {
		/*
		 * ChatVO를 전달하는건 쓰레드X ( 채팅 보내기, 방 요청하기, 친구 초대하기, etc)
		 * 
		 * 받는것은 쓰레드(서버에서 받은 메시지들, 방 초대받음, 방 리스트 받음)
		 */
		boolean isRun = false;
		try {
			ois = new ObjectInputStream(socket.getInputStream());
			while (isRun!= true) {
				// 서버의 응답을 계속 들어야해서 while문을 무한반복시킴
				Message<ChatVO> msg = (Message<ChatVO>) ois.readObject();

				switch (msg.getType()) {
				case Message.CHAT_SEND:
					// 받은 채팅을 화면에 출력
					break;
				case Message.CHATROOM_LOAD:
					// 디폴트테이블모델 만들어서 addROw로 로우 추가하고 JTable에 부착.
					chatRoom_Load(chatRoomTableModel(msg));

					// 방 리스트를 새로고침
					roomSocket.jt.setModel(chatTable);
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
		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	// dtm만드는 메소드
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

	// 새로고침 메소드//테이블에 올리고 새로고침임(지우는 것만 있는 거)
	public void refreshTable() {
		while (chatTable.getRowCount() > 0) {
			chatTable.setRowCount(0); // 지우는거
		}

	}

	@Override
	public void run() {
		getChatVO();

	}
}

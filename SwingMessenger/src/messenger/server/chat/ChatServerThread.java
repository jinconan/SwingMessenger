package messenger.server.chat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import messenger._db.dao.ChatDAO;
import messenger._db.vo.ChatVO;
import messenger.protocol.Message;
import messenger.server.view.ServerView;

public class ChatServerThread implements Runnable{
	private 			Socket 				socket;
	private 			JTextArea 			jta_log;
	
	private 			ObjectInputStream 	in 		= null;
	private 			ObjectOutputStream 	out 	= null;

	private				int					mem_no;
	
	public ChatServerThread(JTextArea jta_chatlog, Socket socket) {
		try {
			this.jta_log = jta_chatlog;
			this.socket = socket;
			//리스트에다가 추가해야함.
			ChatServerThreadList list = ChatServerThreadList.getInstance();
			list.addTotalList(this);
	
			if(this.jta_log != null)
				jta_chatlog.append("접속 : "+socket.getInetAddress()+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try (
			InputStream 		is 	= socket.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ObjectInputStream 	ois = new ObjectInputStream(bis);
		){
			in = ois;
			try (
				OutputStream			os 	= socket.getOutputStream();
				BufferedOutputStream	bos = new BufferedOutputStream(os);
				ObjectOutputStream		oos = new ObjectOutputStream(bos);
			){
				out = oos;
				while(true) {
					//1. 클라이언트로부터 채팅 메시지를 받음.
					Message<ChatVO> msg = (Message<ChatVO>)in.readObject();
					
					//1-2. 메시지 타입 파악(채팅 전송, 채팅 내역 불러오기)
					int msg_type = msg.getType();
					switch(msg_type) {
					case Message.CHATROOM_LOAD: //채팅방 리스트 요청 (클라이언트 실행시 채팅서버에 먼저 요청함)
						if(this.jta_log != null)
							jta_log.append("CHATROOM_LOAD | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendRoomList(msg);
						break;
					case Message.CHAT_SEND: //채팅 전송
						if(this.jta_log != null)
							jta_log.append("CHAT_SEND     | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendChatToMembers(msg);
						break;
					case Message.CHATROOM_EXIT: //채팅방 퇴장
						if(this.jta_log != null)
							jta_log.append("CHATROOM_EXIT | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendExitResponse(msg);
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				out = null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			in = null;
			ChatServerThreadList.getInstance().removeThread(this); //리스트에서 쓰레드를 제거함.
			if(jta_log != null)
				jta_log.append(mem_no + " 나감\n");
		}
	}
	
	/**
	 * 해당 쓰레드와 연결된 클라이언트의 회원번호를 얻음.
	 * @return 클라이언트의 회원번호.(0 - 쓰레드에 회원번호가 저장되지 않음)
	 */
	public int getMem_no() {
		return mem_no;
	}

	/**
	 * 방 멤버들에게 채팅 전달.
	 * @param msg 클라이언트로부터 받은 메시지
	 */
	private void sendChatToMembers(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//1. 클라이언트가 보낸 메시지에서 ChatVO를 얻음.
		ArrayList<ChatVO> request = (ArrayList<ChatVO>) msg.getRequest();
		ArrayList<ChatVO> response = new ArrayList<ChatVO> ();
		ChatVO chatVO = request.get(0);

		//2. 해당 채팅 메시지를 db에 추가하여 로그를 저장.
		int insertResult = dao.insertChat(chatVO);
		
		
		try {
			//2-1. db에 등록 실패시 클라이언트에게 사이즈가 0인 response를 추가하여 전달.
			if(insertResult == 0) {
				if(jta_log != null)
					jta_log.append("실패: "+ chatVO.getMem_no()+ " -> " + chatVO.getRoom_no()+"\n");
				msg.setResponse(response);
				sendMessage(msg);
			}
			//2-2. db에 등록 성공시 방 리스트를 참고로 하여 해당 방안에 있는 모든 쓰레드에게 메시지 전달.
			else {
				//3. 해당 채팅 메시지에서 방 번호를 얻음.
				int room_no = chatVO.getRoom_no();
				ChatServerThreadList roomList = ChatServerThreadList.getInstance();
				
				//4. 번호가 같은 방 안에 접속 중인 쓰레드 검색
				List<ChatServerThread> member_list = roomList.getMembers(room_no);

				//5. 메시지 객체에 response 채우기(request와 다를 바 없다.)
				response.add(chatVO);
				msg.setResponse(response);
				
				//6. 해당 쓰레드 멤버들에게 메시지 전달.
				if(member_list != null) {
					for(ChatServerThread thread : member_list) {
						if(jta_log != null)
							jta_log.append("(방:" + chatVO.getRoom_no() + ", 회원:" + thread.getMem_no()+") : "+chatVO.getChat_time()+"\n");
						thread.sendMessage(msg);
					}
				}
				
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 서버가 클라이언트에게 참여 중인 방 리스트를 전달해줌.
	 * @param msg 클라이언트의 방 리스트 조회 요청메시지
	 */
	private void sendRoomList(Message<ChatVO> msg) {
		//1. 클라이언트가 보낸 메시지에서 ChatVO를 추출.
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();
		ChatVO chatVO = request.get(0);
		
		//2. 메세지 안에 있는 회원번호로 그 계정과 연결된 쓰레드의 회원번호 설정.
		mem_no = (mem_no == 0) ? chatVO.getMem_no() : mem_no;
		
		//3. DB에서 유저가 참여한 방 리스트를 얻음.
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = dao.selectRoomList(mem_no);
		
		//4. 위에서 얻은 방 별로 현재 쓰레드를 멤버로 리스트에 추가시킴.
		ChatServerThreadList list = ChatServerThreadList.getInstance();
		list.addMember(this);
		
		//5. 클라이언트에세 메시지(방 리스트) 전달.
		//클라이언트는 이 메시지를 토대로 방 리스트를 구축하여 패널에 출력.
		msg.setResponse(response);
		sendMessage(msg);
		
	}
	
	/**
	 * 클라이언트로부터 받은 퇴장 요청을 처리함.
	 * delete성공시 response에는 나간 방 번호가 포함되어있고, 실패시 response에는 아무것도 포함되어있지 않다.
	 * @param msg 클라이언트로부터 받은 방 퇴장 메시지.
	 */
	private void sendExitResponse(Message<ChatVO> msg) {
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();
		ChatVO chatVO = request.get(0);
		
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = new ArrayList<ChatVO>();
		
		int deleteResult = dao.deleteRoomMember(chatVO.getMem_no(), chatVO.getRoom_no());
		
		if(deleteResult != 0) {
			response.add(chatVO);
			ChatServerThreadList.getInstance().removeThreadInRoom(this, chatVO.getRoom_no());
		}
		msg.setResponse(response);
		sendMessage(msg);
	}
	
	/**
	 * 출력스트림을 통해서 Message를 전달한다.
	 * @param msg : 보낼 메세지
	 */
	private void sendMessage(Message<ChatVO> msg) {
		try {
			out.writeObject(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

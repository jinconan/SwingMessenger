package messenger.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextArea;

import messenger._db.ChatDAO;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger._protocol.Message;

/**
 * 채팅서버에서 생성되는 쓰레드.
 * @author Jin Lee
 *
 */
public class ChatServerThread implements Runnable{
	private 			Socket 				socket;
	private 			JTextArea 			jta_log;
	private				ChatServerThreadList threadList;
	private				Map<Integer,MemberVO> loginMap;
	
	private 			ObjectInputStream 	in 		= null;
	private 			ObjectOutputStream 	out 	= null;
	private				InetAddress			inetAddr= null;
	private				MemberVO			memVO;
	public ChatServerThread(JTextArea jta_chatlog, Socket socket, ChatServerThreadList threadList, Map<Integer,MemberVO> loginMap) {
		try {
			this.jta_log = jta_chatlog;
			this.socket = socket;
			this.inetAddr = socket.getInetAddress(); 
			this.threadList = threadList;
			this.loginMap = loginMap;
			//쓰레드 생성되면 전체 쓰레드 리스트에 추가한다.
			threadList.addTotalList(this);
	
			if(this.jta_log != null)
				jta_chatlog.append("접속 : "+inetAddr+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 채팅서버 쓰레드 동작 메소드
	 * 클라이언트로부터 메시지 read -> 처리-> 클라이언트에게 메시지 write
	 */
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
					@SuppressWarnings("unchecked")
					Message<ChatVO> msg = (Message<ChatVO>)in.readObject();
					
					//1-2. 메시지 타입 파악(채팅 전송, 채팅 내역 불러오기)
					int msg_type = msg.getType();
					switch(msg_type) {
					case Message.CHATROOM_LOAD: //채팅방 리스트 요청 (클라이언트 실행시 채팅서버에 먼저 요청함)
						if(this.jta_log != null)
							jta_log.append("CHATROOM_LOAD  | " +inetAddr.toString() + ", " + socket.getPort()+"\n");
						sendRoomList(msg);
						break;
					case Message.CHAT_SEND: //채팅 전송
						if(this.jta_log != null)
							jta_log.append("CHAT_SEND      | " + inetAddr.toString() + ", " + socket.getPort()+"\n");
						sendChatToMembers(msg);
						break;
					case Message.CHATROOM_INVITE: //초대
						if(this.jta_log != null)
							jta_log.append("CHATROOM_INVITE| " + inetAddr.toString() + ", " + socket.getPort()+"\n");
						sendInviteResponse(msg);
						break;
					case Message.CHATROOM_EXIT: //채팅방 퇴장
						if(this.jta_log != null)
							jta_log.append("CHATROOM_EXIT  | " + inetAddr.toString() + ", " + socket.getPort()+"\n");
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
			//전체 쓰레드 리스트, 방 별 쓰레드 리스트에서 이 쓰레드를 제거한다.
			threadList.removeThread(this); 
			loginMap.remove(memVO.getMem_no());
			if(jta_log != null)
				jta_log.append(inetAddr.toString() + " 나감\n");
		}
	}
	
	/**
	 * 해당 쓰레드와 연결된 클라이언트의 회원정보
	 * @return 클라이언트의 회원정보.(null - 쓰레드에 회원정보가 저장되지 않음)
	 */
	public MemberVO getMemVO() {
		return memVO;
	}
	
	/**
	 * 방 멤버들에게 채팅 전달.
	 * @param msg 클라이언트로부터 받은 메시지
	 */
	private void sendChatToMembers(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//1-1. 클라이언트가 보낸 메시지에서 request를 추출하고, 보낼 response를 인스턴스화 한다..
		ArrayList<ChatVO> request = (ArrayList<ChatVO>) msg.getRequest();
		ArrayList<ChatVO> response = new ArrayList<ChatVO> ();

		try {
			//1-2. request에서 chatVO를 추출하고, chatVO에서도 roomVO와 memberVO를 추출한다.
			ChatVO chatVO = (request.size() > 0) ? request.get(0) : null;
			RoomVO roomVO = (chatVO != null) ? chatVO.getRoomVO() : null;
			MemberVO memVO = (chatVO != null) ? chatVO.getMemVO() : null;
			String mem_id = (memVO != null) ? memVO.getMem_id() : null;
			int room_no = (roomVO != null) ? roomVO.getRoom_no() : 0;
			
			//2. 해당 채팅 메시지를 db에 추가하여 로그를 저장.
			int insertResult = (chatVO != null) ? dao.insertChat(chatVO) : 0;
			
			//3-1. db에 등록 실패시 클라이언트에게 size가 0인 response를 전달.
			if(insertResult == 0) {
				if(jta_log != null)
					jta_log.append("실패: "+"("+mem_id +" -> " + room_no + ")\n");
				msg.setResponse(response);
				sendMessage(msg);
			}
			//3-2. db에 등록 성공시 방 리스트를 참고로 하여 해당 방안에 있는 모든 쓰레드에게 메시지 전달.
			else {
				//4. 번호가 같은 방 안에 접속 중인 쓰레드 검색
				List<ChatServerThread> member_list = threadList.getMembersOfRoom(room_no);

				//5. 메시지 객체에 response 채우기(request와 다를 바 없다.)
				response.add(chatVO);
				msg.setResponse(response);
				
				//6. 해당 쓰레드 멤버들에게 메시지 전달.
				if(member_list != null) {
					for(ChatServerThread thread : member_list)
						thread.sendMessage(msg);
				}
				if(jta_log != null)
					jta_log.append("("+mem_id +" -> " + room_no + ") : "+chatVO.getChat_content()+"\n");
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
		ChatDAO dao = ChatDAO.getInstance();
		//1. 클라이언트가 보낸 메시지에서 ChatVO를 추출.
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();
		ChatVO chatVO = (request.size() > 0) ? request.get(0) : null;
		
		//2. 클라이언트가 방 리스트 요청을 할때 서버는 클라이언트에게 클라이언트 정보를 요구해서 초기화함.
		memVO = (chatVO != null) ? chatVO.getMemVO() : null;
		int mem_no = (memVO != null) ? memVO.getMem_no() : 0;
		
		//3. DB에서 유저가 참여한 방 리스트를 얻음.
		ArrayList<ChatVO> response = dao.selectRoomList(mem_no);
		
		//4. 위에서 얻은 방 별로 현재 쓰레드를 멤버로 리스트에 추가시킴.
		threadList.addMember(this);
		
		//5. 클라이언트에세 메시지(방 리스트) 전달.
		//클라이언트는 이 메시지를 토대로 방 리스트를 구축하여 패널에 출력.
		msg.setResponse(response);
		sendMessage(msg);
		
	}
	
	/**
	 * 클라이언트로부터 받은 퇴장 요청을 처리함.
	 * delete성공시 response에는 나간 방 번호가 포함되어있고, 실패시 response는 size가 0이다.
	 * @param msg 클라이언트로부터 받은 방 퇴장 메시지.
	 */
	private void sendExitResponse(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = new ArrayList<ChatVO>();
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();
		ChatVO chatVO = (request.size() > 0) ? request.get(0) : null;
		MemberVO memVO = (chatVO != null) ? chatVO.getMemVO() : null;
		RoomVO roomVO = (chatVO != null) ? chatVO.getRoomVO() : null;
		int mem_no = (memVO != null) ? memVO.getMem_no(): 0;
		int room_no = (roomVO != null) ? roomVO.getRoom_no() : 0;
		
		int deleteResult = dao.deleteRoomMember(mem_no, room_no);
		
		if(deleteResult != 0) {
			response.add(chatVO);
			threadList.removeThreadInRoom(this, room_no);
		}
		msg.setResponse(response);
		sendMessage(msg);
	}
	
	/**
	 * 클라이언트의 방 개설 요청을 처리함.
	 * @param msg
	 */
	private void sendInviteResponse(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = new ArrayList<ChatVO>();
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();

		//첫 멤버는 방 개설 주도자가된다.
		ChatVO chatVO = (request.size() > 0) ? request.get(0) : null;
		if(chatVO == null) {
			//참가자가 없는 경우에는 방 개설을 실패한다(response가 null).
			sendMessage(msg);
		}
		RoomVO roomVO = chatVO.getRoomVO();
		
		ArrayList<ChatVO> newMemberList = dao.insertRoomMember(request, roomVO);
		ArrayList<ChatServerThread> newThreadList = new ArrayList<ChatServerThread>();
		//해당 멤버 중에서 현재 접속자가 있는 경우, 해당 멤버의 쓰레드를 해쉬맵에 추가하고 그 사람들에게 메시지를 보낸다.
		for(ChatVO c : newMemberList) {
			ChatServerThread thread = threadList.getThread(c.getMemVO().getMem_no());
			if(thread != null) {
				threadList.addMember(thread);
				newThreadList.add(thread);
				response.add(c);
			}
		}
		msg.setResponse(response);
		
		//새로 참가한 쓰레드들에게 방 개설 메시지를 보낸다.
		for(ChatServerThread thread: newThreadList)
			thread.sendMessage(msg);
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

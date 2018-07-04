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
 * ä�ü������� �����Ǵ� ������.
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
			//������ �����Ǹ� ��ü ������ ����Ʈ�� �߰��Ѵ�.
			threadList.addTotalList(this);
	
			if(this.jta_log != null)
				jta_chatlog.append("���� : "+inetAddr+"\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ä�ü��� ������ ���� �޼ҵ�
	 * Ŭ���̾�Ʈ�κ��� �޽��� read -> ó��-> Ŭ���̾�Ʈ���� �޽��� write
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
					//1. Ŭ���̾�Ʈ�κ��� ä�� �޽����� ����.
					@SuppressWarnings("unchecked")
					Message<ChatVO> msg = (Message<ChatVO>)in.readObject();
					
					//1-2. �޽��� Ÿ�� �ľ�(ä�� ����, ä�� ���� �ҷ�����)
					int msg_type = msg.getType();
					switch(msg_type) {
					case Message.CHATROOM_LOAD: //ä�ù� ����Ʈ ��û (Ŭ���̾�Ʈ ����� ä�ü����� ���� ��û��)
						if(this.jta_log != null)
							jta_log.append("CHATROOM_LOAD  | " +inetAddr.toString() + ", " + socket.getPort()+"\n");
						sendRoomList(msg);
						break;
					case Message.CHAT_SEND: //ä�� ����
						if(this.jta_log != null)
							jta_log.append("CHAT_SEND      | " + inetAddr.toString() + ", " + socket.getPort()+"\n");
						sendChatToMembers(msg);
						break;
					case Message.CHATROOM_INVITE: //�ʴ�
						if(this.jta_log != null)
							jta_log.append("CHATROOM_INVITE| " + inetAddr.toString() + ", " + socket.getPort()+"\n");
						sendInviteResponse(msg);
						break;
					case Message.CHATROOM_EXIT: //ä�ù� ����
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
			//��ü ������ ����Ʈ, �� �� ������ ����Ʈ���� �� �����带 �����Ѵ�.
			threadList.removeThread(this); 
			loginMap.remove(memVO.getMem_no());
			if(jta_log != null)
				jta_log.append(inetAddr.toString() + " ����\n");
		}
	}
	
	/**
	 * �ش� ������� ����� Ŭ���̾�Ʈ�� ȸ������
	 * @return Ŭ���̾�Ʈ�� ȸ������.(null - �����忡 ȸ�������� ������� ����)
	 */
	public MemberVO getMemVO() {
		return memVO;
	}
	
	/**
	 * �� ����鿡�� ä�� ����.
	 * @param msg Ŭ���̾�Ʈ�κ��� ���� �޽���
	 */
	private void sendChatToMembers(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//1-1. Ŭ���̾�Ʈ�� ���� �޽������� request�� �����ϰ�, ���� response�� �ν��Ͻ�ȭ �Ѵ�..
		ArrayList<ChatVO> request = (ArrayList<ChatVO>) msg.getRequest();
		ArrayList<ChatVO> response = new ArrayList<ChatVO> ();

		try {
			//1-2. request���� chatVO�� �����ϰ�, chatVO������ roomVO�� memberVO�� �����Ѵ�.
			ChatVO chatVO = (request.size() > 0) ? request.get(0) : null;
			RoomVO roomVO = (chatVO != null) ? chatVO.getRoomVO() : null;
			MemberVO memVO = (chatVO != null) ? chatVO.getMemVO() : null;
			String mem_id = (memVO != null) ? memVO.getMem_id() : null;
			int room_no = (roomVO != null) ? roomVO.getRoom_no() : 0;
			
			//2. �ش� ä�� �޽����� db�� �߰��Ͽ� �α׸� ����.
			int insertResult = (chatVO != null) ? dao.insertChat(chatVO) : 0;
			
			//3-1. db�� ��� ���н� Ŭ���̾�Ʈ���� size�� 0�� response�� ����.
			if(insertResult == 0) {
				if(jta_log != null)
					jta_log.append("����: "+"("+mem_id +" -> " + room_no + ")\n");
				msg.setResponse(response);
				sendMessage(msg);
			}
			//3-2. db�� ��� ������ �� ����Ʈ�� ����� �Ͽ� �ش� ��ȿ� �ִ� ��� �����忡�� �޽��� ����.
			else {
				//4. ��ȣ�� ���� �� �ȿ� ���� ���� ������ �˻�
				List<ChatServerThread> member_list = threadList.getMembersOfRoom(room_no);

				//5. �޽��� ��ü�� response ä���(request�� �ٸ� �� ����.)
				response.add(chatVO);
				msg.setResponse(response);
				
				//6. �ش� ������ ����鿡�� �޽��� ����.
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
	 * ������ Ŭ���̾�Ʈ���� ���� ���� �� ����Ʈ�� ��������.
	 * @param msg Ŭ���̾�Ʈ�� �� ����Ʈ ��ȸ ��û�޽���
	 */
	private void sendRoomList(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		//1. Ŭ���̾�Ʈ�� ���� �޽������� ChatVO�� ����.
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();
		ChatVO chatVO = (request.size() > 0) ? request.get(0) : null;
		
		//2. Ŭ���̾�Ʈ�� �� ����Ʈ ��û�� �Ҷ� ������ Ŭ���̾�Ʈ���� Ŭ���̾�Ʈ ������ �䱸�ؼ� �ʱ�ȭ��.
		memVO = (chatVO != null) ? chatVO.getMemVO() : null;
		int mem_no = (memVO != null) ? memVO.getMem_no() : 0;
		
		//3. DB���� ������ ������ �� ����Ʈ�� ����.
		ArrayList<ChatVO> response = dao.selectRoomList(mem_no);
		
		//4. ������ ���� �� ���� ���� �����带 ����� ����Ʈ�� �߰���Ŵ.
		threadList.addMember(this);
		
		//5. Ŭ���̾�Ʈ���� �޽���(�� ����Ʈ) ����.
		//Ŭ���̾�Ʈ�� �� �޽����� ���� �� ����Ʈ�� �����Ͽ� �гο� ���.
		msg.setResponse(response);
		sendMessage(msg);
		
	}
	
	/**
	 * Ŭ���̾�Ʈ�κ��� ���� ���� ��û�� ó����.
	 * delete������ response���� ���� �� ��ȣ�� ���ԵǾ��ְ�, ���н� response�� size�� 0�̴�.
	 * @param msg Ŭ���̾�Ʈ�κ��� ���� �� ���� �޽���.
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
	 * Ŭ���̾�Ʈ�� �� ���� ��û�� ó����.
	 * @param msg
	 */
	private void sendInviteResponse(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = new ArrayList<ChatVO>();
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();

		//ù ����� �� ���� �ֵ��ڰ��ȴ�.
		ChatVO chatVO = (request.size() > 0) ? request.get(0) : null;
		if(chatVO == null) {
			//�����ڰ� ���� ��쿡�� �� ������ �����Ѵ�(response�� null).
			sendMessage(msg);
		}
		RoomVO roomVO = chatVO.getRoomVO();
		
		ArrayList<ChatVO> newMemberList = dao.insertRoomMember(request, roomVO);
		ArrayList<ChatServerThread> newThreadList = new ArrayList<ChatServerThread>();
		//�ش� ��� �߿��� ���� �����ڰ� �ִ� ���, �ش� ����� �����带 �ؽ��ʿ� �߰��ϰ� �� ����鿡�� �޽����� ������.
		for(ChatVO c : newMemberList) {
			ChatServerThread thread = threadList.getThread(c.getMemVO().getMem_no());
			if(thread != null) {
				threadList.addMember(thread);
				newThreadList.add(thread);
				response.add(c);
			}
		}
		msg.setResponse(response);
		
		//���� ������ ������鿡�� �� ���� �޽����� ������.
		for(ChatServerThread thread: newThreadList)
			thread.sendMessage(msg);
	}
	
	/**
	 * ��½�Ʈ���� ���ؼ� Message�� �����Ѵ�.
	 * @param msg : ���� �޼���
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

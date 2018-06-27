package messenger.server.chat;

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
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.protocol.Message;

/**
 * ä�ü������� �����Ǵ� ������.
 * @author Jin Lee
 *
 */
public class ChatServerThread implements Runnable{
	private 			Socket 				socket;
	private 			JTextArea 			jta_log;
	
	private 			ObjectInputStream 	in 		= null;
	private 			ObjectOutputStream 	out 	= null;

	private				MemberVO			memVO;
	
	public ChatServerThread(JTextArea jta_chatlog, Socket socket) {
		try {
			this.jta_log = jta_chatlog;
			this.socket = socket;

			ChatServerThreadList list = ChatServerThreadList.getInstance();
			list.addTotalList(this);
	
			if(this.jta_log != null)
				jta_chatlog.append("���� : "+socket.getInetAddress()+"\n");
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
					Message<ChatVO> msg = (Message<ChatVO>)in.readObject();
					
					//1-2. �޽��� Ÿ�� �ľ�(ä�� ����, ä�� ���� �ҷ�����)
					int msg_type = msg.getType();
					switch(msg_type) {
					case Message.CHATROOM_LOAD: //ä�ù� ����Ʈ ��û (Ŭ���̾�Ʈ ����� ä�ü����� ���� ��û��)
						if(this.jta_log != null)
							jta_log.append("CHATROOM_LOAD  | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendRoomList(msg);
						break;
					case Message.CHAT_SEND: //ä�� ����
						if(this.jta_log != null)
							jta_log.append("CHAT_SEND      | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendChatToMembers(msg);
						break;
					case Message.CHATROOM_INVITE: //�ʴ�
						if(this.jta_log != null)
							jta_log.append("CHATROOM_INVITE| " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendInviteResponse(msg);
						break;
					case Message.CHATROOM_EXIT: //ä�ù� ����
						if(this.jta_log != null)
							jta_log.append("CHATROOM_EXIT  | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
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
			ChatServerThreadList.getInstance().removeThread(this); //����Ʈ���� �����带 ������.
			if(jta_log != null)
				jta_log.append(memVO + " ����\n");
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
		
		//1. Ŭ���̾�Ʈ�� ���� �޽������� ChatVO�� ����.
		ArrayList<ChatVO> request = (ArrayList<ChatVO>) msg.getRequest();
		ArrayList<ChatVO> response = new ArrayList<ChatVO> ();
		ChatVO chatVO = request.get(0);

		//2. �ش� ä�� �޽����� db�� �߰��Ͽ� �α׸� ����.
		int insertResult = dao.insertChat(chatVO);
		
		
		try {
			//2-1. db�� ��� ���н� Ŭ���̾�Ʈ���� ����� 0�� response�� �߰��Ͽ� ����.
			if(insertResult == 0) {
				if(jta_log != null)
					jta_log.append("����: "+ chatVO.getMemVO()+ " -> " + chatVO.getRoomVO()+"\n");
				msg.setResponse(response);
				sendMessage(msg);
			}
			//2-2. db�� ��� ������ �� ����Ʈ�� ����� �Ͽ� �ش� ��ȿ� �ִ� ��� �����忡�� �޽��� ����.
			else {
				//3. �ش� ä�� �޽������� �� ��ȣ�� ����.
				int room_no = (chatVO.getRoomVO() != null) ? chatVO.getRoomVO().getRoom_no() : 0;
				ChatServerThreadList roomList = ChatServerThreadList.getInstance();
				
				//4. ��ȣ�� ���� �� �ȿ� ���� ���� ������ �˻�
				List<ChatServerThread> member_list = roomList.getMembers(room_no);

				//5. �޽��� ��ü�� response ä���(request�� �ٸ� �� ����.)
				response.add(chatVO);
				msg.setResponse(response);
				
				//6. �ش� ������ ����鿡�� �޽��� ����.
				if(member_list != null) {
					for(ChatServerThread thread : member_list) {
						thread.sendMessage(msg);
					}
				}
				
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
		//1. Ŭ���̾�Ʈ�� ���� �޽������� ChatVO�� ����.
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();
		ChatVO chatVO = request.get(0);
		
		//2. �޼��� �ȿ� �ִ� memberVO�� �� ������ ����� �������� ȸ������ ����.
		memVO = chatVO.getMemVO();
		int mem_no = (memVO != null) ? memVO.getMem_no() : 0;
		//3. DB���� ������ ������ �� ����Ʈ�� ����.
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = dao.selectRoomList(mem_no);
		
		//4. ������ ���� �� ���� ���� �����带 ����� ����Ʈ�� �߰���Ŵ.
		ChatServerThreadList list = ChatServerThreadList.getInstance();
		list.addMember(this);
		
		//5. Ŭ���̾�Ʈ���� �޽���(�� ����Ʈ) ����.
		//Ŭ���̾�Ʈ�� �� �޽����� ���� �� ����Ʈ�� �����Ͽ� �гο� ���.
		msg.setResponse(response);
		sendMessage(msg);
		
	}
	
	/**
	 * Ŭ���̾�Ʈ�κ��� ���� ���� ��û�� ó����.
	 * delete������ response���� ���� �� ��ȣ�� ���ԵǾ��ְ�, ���н� response���� �ƹ��͵� ���ԵǾ����� �ʴ�.
	 * @param msg Ŭ���̾�Ʈ�κ��� ���� �� ���� �޽���.
	 */
	private void sendExitResponse(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = new ArrayList<ChatVO>();
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();
		ChatVO chatVO = request.get(0);
		int mem_no = (chatVO.getMemVO() != null) ? chatVO.getMemVO().getMem_no(): 0;
		int room_no = (chatVO.getRoomVO() != null) ? chatVO.getRoomVO().getRoom_no() : 0;
		
		int deleteResult = dao.deleteRoomMember(mem_no, room_no);
		
		if(deleteResult != 0) {
			response.add(chatVO);
			ChatServerThreadList.getInstance().removeThreadInRoom(this, room_no);
		}
		msg.setResponse(response);
		sendMessage(msg);
	}
	
	/**
	 * Ŭ���̾�Ʈ�� �� ���� �Ǵ� ���� �濡 ģ�� �߰� ��û�� ó����.
	 * @param msg
	 */
	private void sendInviteResponse(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		ArrayList<ChatVO> response = new ArrayList<ChatVO>();
		ArrayList<ChatVO> request = (ArrayList<ChatVO>)msg.getRequest();

		//ù ����� ��ȸ�Ͽ� RoomVO�� ������ ���� �濡 �ʴ�, ������ ���ο� �� ����.
		ChatVO chatVO = request.get(0);
		RoomVO roomVO = chatVO.getRoomVO();
		
		ArrayList<ChatVO> newMemberList = dao.insertRoomMember(request, roomVO);
		ArrayList<ChatServerThread> newThreadList = new ArrayList<ChatServerThread>();
		//�ش� ��� �߿��� ���� �����ڰ� �ִ� ���, �ش� ����� �����带 �ؽ��ʿ� �߰��ϰ� �� ����鿡�� �޽����� ������.
		for(ChatVO c : newMemberList) {
			ChatServerThread thread = ChatServerThreadList.getInstance().getThread(c.getMemVO().getMem_no());
			if(thread != null) {
				ChatServerThreadList.getInstance().addMember(thread);
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

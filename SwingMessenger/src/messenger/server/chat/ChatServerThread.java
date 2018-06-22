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
			//����Ʈ���ٰ� �߰��ؾ���.
			ChatServerThreadList list = ChatServerThreadList.getInstance();
			list.addTotalList(this);
	
			if(this.jta_log != null)
				jta_chatlog.append("���� : "+socket.getInetAddress()+"\n");
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
					//1. Ŭ���̾�Ʈ�κ��� ä�� �޽����� ����.
					Message<ChatVO> msg = (Message<ChatVO>)in.readObject();
					
					//1-2. �޽��� Ÿ�� �ľ�(ä�� ����, ä�� ���� �ҷ�����)
					int msg_type = msg.getType();
					switch(msg_type) {
					case Message.CHATROOM_LOAD: //ä�ù� ����Ʈ ��û (Ŭ���̾�Ʈ ����� ä�ü����� ���� ��û��)
						if(this.jta_log != null)
							jta_log.append("CHATROOM_LOAD | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendRoomList(msg);
						break;
						
					case Message.CHAT_SEND: //ä�� ����
						if(this.jta_log != null)
							jta_log.append("CHAT_SEND | " + socket.getInetAddress().toString() + ", " + socket.getPort()+"\n");
						sendChatToMembers(msg);
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
			//�ش� Ŭ���̾�Ʈ�� �����ٴ� �Ϳ� ���� ó���� ���־�� ��.
			ChatServerThreadList.getInstance().removeThread(this);
			if(jta_log != null)
				jta_log.append(mem_no + " ����\n");
		}
	}
	
	/**
	 * ����� Ŭ���̾�Ʈ�� ȸ����ȣ�� ����.
	 * @return
	 */
	public int getMem_no() {
		return mem_no;
	}

	/**
	 * �� ����鿡�� ä�� ����.
	 * @param msg
	 */
	private void sendChatToMembers(Message<ChatVO> msg) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//1. Ŭ���̾�Ʈ�� ���� �޽������� ChatVO�� ����.
		ArrayList<ChatVO> request = (ArrayList<ChatVO>) msg.getRequest();
		ArrayList<ChatVO> response = new ArrayList<ChatVO> ();
		ChatVO chatVO = request.get(0);

		//2. �ش� ä�� �޽����� db�� �߰��Ͽ� �α׸� ����.
		chatVO = dao.insertChat(chatVO);
		
		
		try {
			//2-1. db�� ��� ���н� Ŭ���̾�Ʈ���� ����� 0�� response�� �߰��Ͽ� ����.
			if(chatVO == null) {
				msg.setResponse(response);
				sendMessage(msg);
			}
			//2-2. db�� ��� ������ �� ����Ʈ�� ����� �Ͽ� �ش� ��ȿ� �ִ� ��� �����忡�� �޽��� ����.
			else {
				//3. �ش� ä�� �޽������� �� ��ȣ�� ����.
				int roomno = chatVO.getRoom_no();
				ChatServerThreadList roomList = ChatServerThreadList.getInstance();
				
				//4. ��ȣ�� ���� �� �ȿ� ���� ���� ������ �˻�
				List<ChatServerThread> member_list = roomList.getMembers(roomno);

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
		
		//2. �޼��� �ȿ� �ִ� ȸ����ȣ�� �� ������ ����� �������� ȸ����ȣ ����.
		mem_no = (mem_no == 0) ? chatVO.getMem_no() : mem_no;
		
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

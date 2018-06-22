package messenger.server.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextArea;

import messenger._db.dao.ChatDAO;
import messenger._db.vo.ChatVO;
import messenger._db.vo.RoomVO;


/**
 * ä�ü����� ������ ��� �������� ����Ʈ�� �� �� ����Ʈ�� ���� Ŭ����
 * @author 518
 *
 */
public class ChatServerThreadList {
	private ArrayList<ChatServerThread> totalList; //��� ������ ����Ʈ
	private HashMap<Integer, ArrayList<ChatServerThread>> roomList; //�� ����Ʈ(���ȣ, �����ڸ���Ʈ)
	
	private static class LazyHolder {
		private static final ChatServerThreadList INSTANCE = new ChatServerThreadList();
	}
	
	private ChatServerThreadList() {
		totalList = new ArrayList<ChatServerThread>();
		roomList = new HashMap<Integer, ArrayList<ChatServerThread>>();
	}
	
	/**
	 * �ν��Ͻ� �������� �޼ҵ�
	 * @return �̱��� ��ü
	 */
	static public ChatServerThreadList getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * Ư�� ���� ����Ʈ�� �����ϴ��� �ľ�
	 * @param room_no : �� ��ȣ
	 * @return : true : �ش� �� ��ȣ�� ��ġ�ϴ� ���� ����
	 * 			 false: ����.
	 */
	public synchronized boolean hasRoom(int room_no) {
		return roomList.containsKey(Integer.valueOf(room_no));
	}
	
	
	/**
	 * ���� ���� ������ ����Ʈ ����.
	 * @return ��ü ������
	 */
	public synchronized ArrayList<ChatServerThread> getTotalList() {
		return totalList;
	}
	
	/**
	 * Ư�� �� �ȿ� �����ϴ� Ŭ���̾�Ʈ�� ����� ������ ���� ��������� ����Ʈ�� ����.
	 * @param room_no : �� ��ȣ
	 * @return : �ش� �濡 �����ϴ� ����� ����Ʈ
	 */
	public synchronized ArrayList<ChatServerThread> getMembers(int room_no) {
		return roomList.get(room_no);
	}
	
	/**
	 * ������ ä�ü����� �����Ͽ� ������ �����带 ��ü ����Ʈ�� �߰�
	 * @param thread : �߰��� ������
	 * @return : true
	 */
	public synchronized boolean addTotalList(ChatServerThread thread) {
		totalList.add(thread);
		return true;
	}
	
	/**
	 * Ư�� �����带 ������ ������ ���� ã�Ƽ� ������Ų��.
	 * @param thread : ����Ʈ�� ���� ������
	 */
	public synchronized void addMember(ChatServerThread thread) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//db���� ȸ����ȣ�� ���� �ش� ȸ���� ������ �� ����Ʈ ChatVO Ÿ������ ��´�.
		ArrayList<ChatVO> listOfThread = dao.selectRoomList(thread.getMem_no());
		
		//�� ����Ʈ�� ���� �ݺ��� ����
		for(ChatVO chatVO : listOfThread) {
			int room_no = chatVO.getRoom_no();
			
			//�ش� ��ȣ�� �� �ȿ� ���Ե� ������(������)�� ����Ʈ�� ��´�.
			ArrayList<ChatServerThread> memList = roomList.get(room_no);
			
			//�ش� ��ȣ�� Ű������ �ϴ� ������ ����Ʈ�� �������� �ʴ� ��� ����Ʈ�� �ν��Ͻ�ȭ�ϰ�
			//�ؽ���(roomList)�� ����Ʈ�� �־��ش�.
			if(memList == null) {
				memList = new ArrayList<ChatServerThread>();
				roomList.put(room_no, memList);
			}
			else {
				//������ ����Ʈ�� ������
				//������ ����Ʈ�ȿ� �̹� �ش� �����尡 �����ϴ� ��� ���� �ݺ������� �Ѿ��.
				if(memList.contains(thread))
					continue;
			}
			
			//������ ����Ʈ�� �ش� �����带 �߰��Ѵ�.
			memList.add(thread);
		}
	}
	
	/**
	 * �ش� ����� ������ �� ����Ʈ�� ����.
	 * @param mem_no
	 */
	public synchronized ArrayList<RoomVO> getRoomsOfMember(int mem_no) {
		ArrayList<RoomVO> list = null;
		
		for(int i : roomList.keySet()) {
			ArrayList<ChatServerThread> tmp_list = roomList.get(Integer.valueOf(i));
			if(tmp_list.contains(this))
				list.add(new RoomVO(i,null));
		}
		
		return list;
	}

	/**
	 * ����Ʈ���� �����带 ����.
	 * @param thread
	 */
	public synchronized void removeThread(ChatServerThread thread) {
		for(Integer i : roomList.keySet()) {
			ArrayList<ChatServerThread> list = roomList.get(i);
			list.remove(thread);
		}
		totalList.remove(thread);
	}
}

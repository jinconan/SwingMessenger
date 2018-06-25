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
	 * @return �ν��Ͻ�
	 */
	static public ChatServerThreadList getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * Ư�� ���� �ؽ��ʿ� �����ϴ��� �ľ�
	 * @param room_no : �� ��ȣ
	 * @return : true : �ش� �� ��ȣ�� ��ġ�ϴ� ���� ����
	 * 			 false: ����.
	 */
	public synchronized boolean hasRoom(int room_no) {
		return roomList.containsKey(Integer.valueOf(room_no));
	}
	
	
	/**
	 * ���� ���� ��ü ������ ����Ʈ ����.
	 * @return ��ü ������
	 */
	public synchronized ArrayList<ChatServerThread> getTotalList() {
		return totalList;
	}
	
	/**
	 * ���� ������ �� ����
	 * @return ������ ��
	 */
	public synchronized int getNumberOfThreads() {
		return totalList.size();
	}	
	/**
	 * Ư�� �� �ȿ� ���� ���� ������ ����Ʈ�� ����
	 * @param room_no : �� ��ȣ
	 * @return : �ش� �濡 �����ϴ� ������ ����Ʈ
	 */
	public synchronized ArrayList<ChatServerThread> getMembers(int room_no) {
		return roomList.get(room_no);
	}
	
	/**
	 * Ư�� �� �ȿ� ���� ���� ������ ���� ����
	 * @param room_no : �� ��ȣ
	 * @return : �ش� �濡 �����ϴ� ������ ��
	 */
	public synchronized int getNumberOfRoomMembers(int room_no) {
		ArrayList<ChatServerThread> list =roomList.get(room_no); 
		return (list != null) ? list.size() : 0; 
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
	 * Ư�� �����带 ������ ������ ���� db���� ã�Ƽ� ������Ų��.
	 * @param thread : ����Ʈ�� ���� ������
	 */
	public synchronized void addMember(ChatServerThread thread) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//db���� ȸ����ȣ�� ���� �ش� ȸ���� ������ �� ����Ʈ ChatVO Ÿ������ ��´�.
		ArrayList<ChatVO> listOfThread = dao.selectRoomList(thread.getMemVO().getMem_no());
		
		//�� ����Ʈ�� ���� �ݺ��� ����
		for(ChatVO chatVO : listOfThread) {
			int room_no = (chatVO.getRoomVO() != null) ? chatVO.getRoomVO().getRoom_no() : 0;
			
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
	 * �ش� �����尡 ������ �� ����Ʈ�� ����.
	 * @param thread
	 */
	public synchronized ArrayList<RoomVO> getRoomsOfMember(ChatServerThread thread) {
		ArrayList<RoomVO> list = null;
		
		for(int i : roomList.keySet()) {
			ArrayList<ChatServerThread> tmp_list = roomList.get(Integer.valueOf(i));
			if(tmp_list.contains(thread))
				list.add(new RoomVO(i,null));
		}
		
		return list;
	}

	/**
	 * ����Ʈ���� �����带 ����.
	 * @param thread ������ ������
	 * @return :  true : ����Ʈ���� ���� ����, false : totalList�� �������� �ʾ���.
	 */
	public synchronized boolean removeThread(ChatServerThread thread) {
		for(Integer i : roomList.keySet()) {
			removeThreadInRoom(thread, i);
		}
		return totalList.remove(thread);
	}
	
	/**
	 * �濡 �����ϴ� ��� �� �����带 ����Ʈ���� �����Ѵ�.
	 * @param thread : �� ������ ����Ʈ���� ������ ������
	 * @param room_no : �� ��ȣ
	 * @return : true : ����Ʈ���� ���� ����, false : ����Ʈ�� �����尡 �������� �ʾ���.
	 */
	public synchronized boolean removeThreadInRoom(ChatServerThread thread, int room_no) {
		ArrayList<ChatServerThread> list = roomList.get(room_no);
		return list.remove(thread);
	}
}

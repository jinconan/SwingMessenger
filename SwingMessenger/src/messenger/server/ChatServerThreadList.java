package messenger.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import messenger._db.ChatDAO;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;


/**
 * ä�ü����� ������ ��� �������� ����Ʈ�� �� �� ����Ʈ�� ���� Ŭ����
 *
 */
public class ChatServerThreadList {
	private List<ChatServerThread> totalList; //��� ������ ����Ʈ
	private HashMap<Integer, List<ChatServerThread>> roomMap; //�� ����Ʈ(���ȣ, �����ڸ���Ʈ)
	
	public ChatServerThreadList() {
		totalList = new ArrayList<ChatServerThread>();
		roomMap = new HashMap<Integer, List<ChatServerThread>>();
	}
	
	/**
	 * Ư�� ���� �ؽ��ʿ� �����ϴ��� �ľ�
	 * @param room_no : �� ��ȣ
	 * @return : true : �ش� �� ��ȣ�� ��ġ�ϴ� ���� ����
	 * 			 false: ����.
	 */
	public synchronized boolean hasRoom(int room_no) {
		return roomMap.containsKey(Integer.valueOf(room_no));
	}
	
	
	/**
	 * ���� ���� ��ü ������ ����Ʈ ����.
	 * @return ��ü ������
	 */
	public synchronized List<ChatServerThread> getTotalList() {
		return totalList;
	}
	
	/**
	 * ������ ����Ʈ���� �ش� ȸ����ȣ�� ���� ������ ����
	 * @param mem_no ã�� ȸ����ȣ
	 * @return �ش� ȸ���� ����� ������ ����. ������ null
	 */
	public synchronized ChatServerThread getThread(int mem_no) {
		for(ChatServerThread t : totalList) {
			MemberVO mem = t.getMemVO();
			if(mem != null && mem.getMem_no() == mem_no) {
				return t;
			}
		}
		return null;
	}
	
	
	/**
	 * ���� ������ �� ����
	 * @return ������ ��
	 */
	public synchronized int getNumberOfThreads() {
		return totalList.size();
	}
	
	/**
	 * �� ��ȣ�� room_no�� ���� ������ ���� �����Ѵ�.
	 * @param room_no : �� ��ȣ
	 * @return : ������ ��.
	 */
	public synchronized int getNumberOfRoomMember(int room_no) {
		List<ChatServerThread> list = roomMap.get(room_no);
		return (list != null) ? list.size() : 0;
	}
	/**
	 * Ư�� �� �ȿ� ���� ���� ������ ����Ʈ�� ����
	 * @param room_no : �� ��ȣ
	 * @return : �ش� �濡 �����ϴ� ������ ����Ʈ
	 */
	public synchronized List<ChatServerThread> getMembersOfRoom(int room_no) {
		return roomMap.get(room_no);
	}
	
	/**
	 * Ư�� �� �ȿ� ���� ���� ������ ���� ����
	 * @param room_no : �� ��ȣ
	 * @return : �ش� �濡 �����ϴ� ������ ��
	 */
	public synchronized int getNumberOfRoomMembers(int room_no) {
		List<ChatServerThread> list = roomMap.get(room_no); 
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
		List<ChatVO> roomListOfThread = (thread.getMemVO() != null) ?
											dao.selectRoomList(thread.getMemVO().getMem_no()) : new ArrayList<ChatVO>();
		
		//�� ����Ʈ�� ���� �ݺ��� ����
		for(ChatVO chatVO : roomListOfThread) {
			int room_no = (chatVO.getRoomVO() != null) ? chatVO.getRoomVO().getRoom_no() : 0;
			
			//�ش� ��ȣ�� �� �ȿ� ���Ե� ������(������)�� ����Ʈ�� ��´�.
			List<ChatServerThread> memListOfRoom = roomMap.get(room_no);
			//�ش� ��ȣ�� Ű������ �ϴ� ������ ����Ʈ�� �������� �ʴ� ��� ����Ʈ�� �ν��Ͻ�ȭ�ϰ�
			//�ؽ��ʿ� ����Ʈ�� �־��ش�.
			if(memListOfRoom == null) {
				memListOfRoom = new ArrayList<ChatServerThread>();
				roomMap.put(room_no, (ArrayList<ChatServerThread>) memListOfRoom);
			}
			else {
				//������ ����Ʈ�� ������
				//������ ����Ʈ�ȿ� �̹� �ش� �����尡 �����ϴ� ��� ���� �ݺ������� �Ѿ��.
				if(memListOfRoom.contains(thread))
					continue;
			}
			
			//������ ����Ʈ�� �ش� �����带 �߰��Ѵ�.
			memListOfRoom.add(thread);
		}
	}
	
	/**
	 * �ش� �����尡 ������ �� ����Ʈ�� ����.
	 * @param thread
	 */
	public synchronized List<RoomVO> getRoomsOfMember(ChatServerThread thread) {
		List<RoomVO> list = new ArrayList<RoomVO>();
		Set<Integer> keySet = roomMap.keySet();
		Iterator<Integer> iter = keySet.iterator();
		
		while(iter.hasNext()) {
			int i = iter.next();
			List<ChatServerThread> tmp_list = roomMap.get(Integer.valueOf(i));
			if(tmp_list.contains(thread))
				list.add(new RoomVO(i,null, null));
		}
		
		return list;
	}

	/**
	 * ����Ʈ���� �����带 ����.
	 * @param thread ������ ������
	 * @return :  true : ����Ʈ���� ���� ����, false : totalList�� �������� �ʾ���.
	 */
	public synchronized boolean removeThread(ChatServerThread thread) {
		Set<Integer> keySet = roomMap.keySet();
		Iterator<Integer> iter = keySet.iterator();
		while(iter.hasNext()) {
			int room_no = iter.next();
			removeThreadInRoom(thread, room_no);
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
		List<ChatServerThread> list = roomMap.get(room_no);
		return (list != null) ? list.remove(thread) : false;
	}
}

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
 * 채팅서버에 참여한 모든 쓰레드의 리스트와 각 방 리스트를 갖는 클래스
 * @author 518
 *
 */
public class ChatServerThreadList {
	private ArrayList<ChatServerThread> totalList; //모든 쓰레드 리스트
	private HashMap<Integer, ArrayList<ChatServerThread>> roomList; //방 리스트(방번호, 참가자리스트)
	
	private static class LazyHolder {
		private static final ChatServerThreadList INSTANCE = new ChatServerThreadList();
	}
	
	private ChatServerThreadList() {
		totalList = new ArrayList<ChatServerThread>();
		roomList = new HashMap<Integer, ArrayList<ChatServerThread>>();
	}
	
	/**
	 * 인스턴스 가져오는 메소드
	 * @return 싱글톤 객체
	 */
	static public ChatServerThreadList getInstance() {
		return LazyHolder.INSTANCE;
	}

	/**
	 * 특정 방이 리스트에 존재하는지 파악
	 * @param room_no : 방 번호
	 * @return : true : 해당 방 번호와 일치하는 방이 있음
	 * 			 false: 없음.
	 */
	public synchronized boolean hasRoom(int room_no) {
		return roomList.containsKey(Integer.valueOf(room_no));
	}
	
	
	/**
	 * 접속 중인 쓰레드 리스트 리턴.
	 * @return 전체 쓰레드
	 */
	public synchronized ArrayList<ChatServerThread> getTotalList() {
		return totalList;
	}
	
	/**
	 * 특정 방 안에 존재하는 클라이언트와 연결된 소켓을 갖는 쓰레드들의 리스트를 얻음.
	 * @param room_no : 방 번호
	 * @return : 해당 방에 존재하는 멤버의 리스트
	 */
	public synchronized ArrayList<ChatServerThread> getMembers(int room_no) {
		return roomList.get(room_no);
	}
	
	/**
	 * 새로이 채팅서버에 접속하여 생성된 쓰레드를 전체 리스트에 추가
	 * @param thread : 추가할 스레드
	 * @return : true
	 */
	public synchronized boolean addTotalList(ChatServerThread thread) {
		totalList.add(thread);
		return true;
	}
	
	/**
	 * 특정 쓰레드를 기존에 참여한 방을 찾아서 참여시킨다.
	 * @param thread : 리스트로 넣을 쓰레드
	 */
	public synchronized void addMember(ChatServerThread thread) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//db에서 회원번호를 통해 해당 회원이 참여한 방 리스트 ChatVO 타입으로 얻는다.
		ArrayList<ChatVO> listOfThread = dao.selectRoomList(thread.getMem_no());
		
		//방 리스트에 대해 반복을 실행
		for(ChatVO chatVO : listOfThread) {
			int room_no = chatVO.getRoom_no();
			
			//해당 번호의 방 안에 포함된 쓰레드(접속자)의 리스트를 얻는다.
			ArrayList<ChatServerThread> memList = roomList.get(room_no);
			
			//해당 번호를 키값으로 하는 쓰레드 리스트가 존재하지 않는 경우 리스트를 인스턴스화하고
			//해쉬맵(roomList)에 리스트를 넣어준다.
			if(memList == null) {
				memList = new ArrayList<ChatServerThread>();
				roomList.put(room_no, memList);
			}
			else {
				//쓰레드 리스트가 존재함
				//쓰레드 리스트안에 이미 해당 쓰레드가 존재하는 경우 다음 반복문으로 넘어간다.
				if(memList.contains(thread))
					continue;
			}
			
			//쓰레드 리스트에 해당 쓰레드를 추가한다.
			memList.add(thread);
		}
	}
	
	/**
	 * 해당 멤버가 참여한 방 리스트를 얻음.
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
	 * 리스트에서 쓰레드를 제거.
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

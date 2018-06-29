package messenger.server.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTextArea;

import messenger._db.dao.ChatDAO;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;


/**
 * 채팅서버에 참여한 모든 쓰레드의 리스트와 각 방 리스트를 갖는 클래스
 *
 */
public class ChatServerThreadList {
	private ArrayList<ChatServerThread> totalList; //모든 쓰레드 리스트
	private HashMap<Integer, ArrayList<ChatServerThread>> roomMap; //방 리스트(방번호, 참가자리스트)
	
	public ChatServerThreadList() {
		totalList = new ArrayList<ChatServerThread>();
		roomMap = new HashMap<Integer, ArrayList<ChatServerThread>>();
	}
	
	/**
	 * 특정 방이 해쉬맵에 존재하는지 파악
	 * @param room_no : 방 번호
	 * @return : true : 해당 방 번호와 일치하는 방이 있음
	 * 			 false: 없음.
	 */
	public synchronized boolean hasRoom(int room_no) {
		return roomMap.containsKey(Integer.valueOf(room_no));
	}
	
	
	/**
	 * 접속 중인 전체 쓰레드 리스트 리턴.
	 * @return 전체 쓰레드
	 */
	public synchronized ArrayList<ChatServerThread> getTotalList() {
		return totalList;
	}
	
	/**
	 * 접속자 리스트에서 해당 회원번호를 갖는 쓰레드 리턴
	 * @param mem_no 찾을 회원번호
	 * @return 해당 회원과 연결된 쓰레드 리턴. 없으면 null
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
	 * 현재 접속자 수 리턴
	 * @return 접속자 수
	 */
	public synchronized int getNumberOfThreads() {
		return totalList.size();
	}
	
	/**
	 * 방 번호가 room_no인 방의 참여자 수를 리턴한다.
	 * @param room_no : 방 번호
	 * @return : 참여자 수.
	 */
	public synchronized int getNumberOfRoomMember(int room_no) {
		ArrayList<ChatServerThread> list = roomMap.get(room_no);
		return (list != null) ? list.size() : 0;
	}
	/**
	 * 특정 방 안에 참여 중인 쓰레드 리스트를 리턴
	 * @param room_no : 방 번호
	 * @return : 해당 방에 존재하는 쓰레드 리스트
	 */
	public synchronized ArrayList<ChatServerThread> getMembersOfRoom(int room_no) {
		return roomMap.get(room_no);
	}
	
	/**
	 * 특정 방 안에 참여 중인 참여자 수를 리턴
	 * @param room_no : 방 번호
	 * @return : 해당 방에 존재하는 참석자 수
	 */
	public synchronized int getNumberOfRoomMembers(int room_no) {
		ArrayList<ChatServerThread> list =roomMap.get(room_no); 
		return (list != null) ? list.size() : 0; 
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
	 * 특정 쓰레드를 기존에 참여한 방을 db에서 찾아서 참여시킨다.
	 * @param thread : 리스트에 넣을 쓰레드
	 */
	public synchronized void addMember(ChatServerThread thread) {
		ChatDAO dao = ChatDAO.getInstance();
		
		//db에서 회원번호를 통해 해당 회원이 참여한 방 리스트 ChatVO 타입으로 얻는다.
		ArrayList<ChatVO> roomListOfThread = (thread.getMemVO() != null) ?
											dao.selectRoomList(thread.getMemVO().getMem_no()) : new ArrayList<ChatVO>();
		
		//방 리스트에 대해 반복을 실행
		for(ChatVO chatVO : roomListOfThread) {
			int room_no = (chatVO.getRoomVO() != null) ? chatVO.getRoomVO().getRoom_no() : 0;
			
			//해당 번호의 방 안에 포함된 쓰레드(접속자)의 리스트를 얻는다.
			ArrayList<ChatServerThread> memListOfRoom = roomMap.get(room_no);
			//해당 번호를 키값으로 하는 쓰레드 리스트가 존재하지 않는 경우 리스트를 인스턴스화하고
			//해쉬맵에 리스트를 넣어준다.
			if(memListOfRoom == null) {
				memListOfRoom = new ArrayList<ChatServerThread>();
				roomMap.put(room_no, memListOfRoom);
			}
			else {
				//쓰레드 리스트가 존재함
				//쓰레드 리스트안에 이미 해당 쓰레드가 존재하는 경우 다음 반복문으로 넘어간다.
				if(memListOfRoom.contains(thread))
					continue;
			}
			
			//쓰레드 리스트에 해당 쓰레드를 추가한다.
			memListOfRoom.add(thread);
		}
	}
	
	/**
	 * 해당 쓰레드가 참여한 방 리스트를 얻음.
	 * @param thread
	 */
	public synchronized ArrayList<RoomVO> getRoomsOfMember(ChatServerThread thread) {
		ArrayList<RoomVO> list = new ArrayList<RoomVO>();
		
		for(int i : roomMap.keySet()) {
			ArrayList<ChatServerThread> tmp_list = roomMap.get(Integer.valueOf(i));
			if(tmp_list.contains(thread))
				list.add(new RoomVO(i,null, null));
		}
		
		return list;
	}

	/**
	 * 리스트에서 쓰레드를 제거.
	 * @param thread 제거할 쓰레드
	 * @return :  true : 리스트에서 제거 성공, false : totalList에 존재하지 않았음.
	 */
	public synchronized boolean removeThread(ChatServerThread thread) {
		for(Integer room_no : roomMap.keySet()) {
			removeThreadInRoom(thread, room_no);
		}
		return totalList.remove(thread);
	}
	
	/**
	 * 방에 존재하는 어느 한 쓰레드를 리스트에서 제거한다.
	 * @param thread : 방 참여자 리스트에서 제거할 쓰레드
	 * @param room_no : 방 번호
	 * @return : true : 리스트에서 제거 성공, false : 리스트에 쓰레드가 존재하지 않았음.
	 */
	public synchronized boolean removeThreadInRoom(ChatServerThread thread, int room_no) {
		ArrayList<ChatServerThread> list = roomMap.get(room_no);
		return (list != null) ? list.remove(thread) : false;
	}
}

package messenger.server.friend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/*******************************
 * 2018.06.20 쓰레드 클래스 코딩 스탑.
 * 	 - 아직 제대로 이해하지 못했기 때문에 이대로 코딩 할 경우 위험부담이 큼.
 *   - 멀티쓰레드에대해 이해하기 
 * 2018.06.28 쓰레드 마스터 해서 다시 제대로 코딩하기
 *  ! 학습 목표
 *    - 멀티쓰레드에 대해 이해하고 응용할 수 있다. 
 * 
 *******************************/
public class FriendThread extends Thread{
	ObjectInputStream ois = null;//듣기
	ObjectOutputStream oos = null;//말하기
//	클라이언트친구기능 cfm = null;//쓰레드에서 메뉴 기능을 불러와서 실행함.
	//임시로 스트링으로 칭함.
	ArrayList<String> menu = null;
	FriendMenu fm = new FriendMenu();
	String menu_index = "";
	FriendThread(){
		start();
	}
//	public FriendThread(클라이언트친구기능 cfm) {
//		this.cfm = cfm;
//		try {
//			ois = new ObjectInputStream
//					(cfm.친구클라이언트소켓.getInputStream());
//			oos = new ObjectOutputStream
//					(cfm.친구클라이언트소켓.getOutputStream());
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.toString());
//		}
//	}
	@Override
	public synchronized void run() {
		try {	
			while(true) {
				/************************************************************
				 * 메세지타입 번호.
				 * public static final int MEMBER_LOGIN   = 0; //회원 로그인
				 * public static final int MEMBER_JOIN    = 1; //회원 가입
				 * public static final int MEMBER_MODIFY  = 2; //회원 수정
				 * public static final int FRIEND_ALL     = 3; //친구 전체 리스트
				 * public static final int FRIEND_INSERT  = 4; //친구 추가
				 * public static final int FRIEND_DELETE  = 5; //친구 삭제
				 * public static final int FRIEND_SEARCH  = 6; //친구 검색
				 * public static final int CHAT_SEND      = 7; //채팅 전송
				 * public static final int CHAT_LOAD      = 8; //채팅 내역 조회
				 ************************************************************/
				Message<MemberVO> msg = (Message<MemberVO>)ois.readObject();
				msg.getType();//메세지오브젝트 안에 들어있는 메세지 타입을 불러옴
				
				
				//클라이언트가 담은 데이터들을 어레이리스트 타입으로 변환해서 받아오기
				ArrayList<MemberVO> list = (ArrayList<MemberVO>)msg.getRequest();
				MemberVO mem = list.get(0);//
				mem.getMem_id();//클라이언트가 요청한 ID를 가져옴.
				
				//받아오는 타입이 달라서 컴파일에러 이부분 좀더 공부해야할듯....
//				ArrayList<MemberVO> result = (ArrayList<MemberVO>)fm.FriendSelectALL(mem.getMem_id());
//				msg.setResponse(result);
//				oos.writeObject(msg);
							//각각 쓰레드에서 받은 정보(인풋스트림을 읽어와 menu객체변수에 담음(어레이리스트))
				switch(msg.getType()) {
				case 3:
					fm.FriendSelectALL(list);
					
					break;
				case 4:
					fm.FriendInsert(list);
					
					break;
				case 5:
					fm.FriendDelete(list);
					
				}
				//ObjectOutputStream
				
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
		}

	}
	public static void main(String args[]) {
		new FriendThread();
	}
}


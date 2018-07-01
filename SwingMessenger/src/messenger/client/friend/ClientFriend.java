package messenger.client.friend;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;
import messenger.protocol.Server;

/**********************************************************************
 * [클러이언트]친구리스트 불러오기
 * 기능 : 친구(Friend)관련한 작업의 쓰레드는 이곳에서 처리되고 닫음
 * 
 * @author1 이정렬...18/06/27
 * [체크사항] ~ <cliendFriendList.java>
 * 1.do-while문 제거 및 EOFException주석처리
 *   ▶▶▶서버가 상시가동되고 있고, 단방향 요청+단방향 응답이기 때문에, do-while문으로 반복할 필요가 없음
 * 2. List타입 변수 res에 <MemberVO>타입으로 확정지어주어서 타입에러 잡음
 * 
 **********************************************************************/

public class ClientFriend extends Thread{

	Socket flsc	= null;
	String IP	= Server.IP;	//서버IP주소
	ObjectOutputStream	oos = null;	//클라이언트가 전달하는 것이 먼저이므로 out을 먼저 사용
	ObjectInputStream	ois = null;
	
	Message<MemberVO> mms = null;
	List<MemberVO> mli = null;
	MemberVO mvo = null;
	
	ClientFriendList	cfl = null;
	ClientFriendSearch	cfs = null;
	ClientFriendAdd		cfa = null;
	ClientFriendDelete	cfd = null;

	//친구관련 메소드는 모두 이것으로 실행
	public ClientFriend(Message<MemberVO> mms) {
		this.mms = mms;
		this.start();
	}

	// Thread클래스 오버라이드
	@Override
	public void run() {
		try {//소켓을 생성하고, 소켓을 통한 듣기와 말하기 창구 생성
			flsc = new Socket(IP, Port.FRIEND);
			oos  = new ObjectOutputStream(flsc.getOutputStream());

		//do {
			//말하기
			//친구전체조회 : 서버로 본인회원번호를 보내고 친구목록 요청하기
			//친구찾기 : 서버로 대상회원아이디를 보내고 친구자료 요청하기
			oos.writeObject(mms);

			//듣기
			ois = new ObjectInputStream(flsc.getInputStream());
			//들은 내용을 확인하고 메시지프로토콜로 담아냄
			mms = (Message<MemberVO>)ois.readObject();
			//메시지프로토콜에 들어있는 데이터부분을 골라냄 (List타입)
			List<MemberVO> res = mms.getResponse();
				//메시지데이터의 부분을 얻어내야하는 경우
				//MemberVO memVO = res.get(0);

			//들은 내용의 메시지타입에 따라 개별처리과정
			switch (mms.getType()) {
			
			case Message.FRIEND_ALL://친구전체조회
				//화면에 띄우는 메소드호출
				cfl = new ClientFriendList();
				cfl.setFriendList(res);//골라낸 List자료 넘김
				break;
			
			case Message.FRIEND_SEARCH://친구찾기
				cfs = new ClientFriendSearch();
				cfs.setFriendSearch(res);//골라낸 List자료 넘김
				break;
				
			case Message.FRIEND_INSERT://친구추가
				cfa = new ClientFriendAdd();
				cfa.setFriendAdd(res);//골라낸 List자료 넘김
				break;
				
			case Message.FRIEND_DELETE://친구삭제
				cfd = new ClientFriendDelete();
				cfd.setFriendDelete(res);//골라낸 List자료 넘김
				break;
				
			default:
				System.out.println("[알림]4가지 타입 외 다른 타입의 메시지가 서버로부터 넘어왔습니다.");
				break;
			}
/*		} while (ois.readObject() == null);// 만약 서버로부터 받지못하면 반복

		} catch (EOFException e) {
			System.out.println("EOF예외 : "mms);
			return;
		//서버는 상시열려있고, 친구목록에 관계한 과정은 단방향의 요청과 응답관계이므로
		//do-while문으로 반복될 필요가 없음.(18/06/27) 
*/		}
		  catch (IOException ioe) {// In&Output Stream Exception
			System.out.println("ClientFreindList.run() in&out오류발생");
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println("ClientFreindList.run() 기타 오류발생");
			e.printStackTrace();

		} finally {// 화면에 자료올리는것을 마친후 닫기 실행
			try {
				ois.close();
				oos.close();
			} catch (IOException ioe2) {
				System.out.println("ClientFreindList.run() 닫는 중에 오류발생");
				ioe2.printStackTrace();
			}

		} ///// end of try-catch-finally

	}///////// end of run()

}

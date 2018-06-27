package messenger.client.friend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class ClientFriend extends Thread{

	Socket flsc	= null;
	String IP	= "192.168.0.235";	//서버IP주소
	ObjectOutputStream	oos = null;	//클라이언트가 전달하는 것이 먼저이므로 out을 먼저 사용
	ObjectInputStream	ois = null;
	
	Message<MemberVO> mms = null;
	List<MemberVO> mli = null;
	MemberVO mvo = null;
	
	ClientFriendList cfl = null;

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

			do {//말하기 : 서버로 본인회원번호를 보내고 친구목록 요청하기
				oos.writeObject(mms);
				//듣기 : 서버로부터 친구목록 받고, 받은 내용을 UI에 올리기
				ois = new ObjectInputStream(flsc.getInputStream());

				//들은 내용을 확인하고 메시지프로토콜로 담아냄
				mms = (Message<MemberVO>) ois.readObject();
				//메시지프로토콜에 들어있는 데이터부분을 골라냄 (List타입)
				List<MemberVO> res = mms.getResponse();
					//메시지데이터의 부분을 얻어내야하는 경우
					//MemberVO memVO = res.get(0);

				//메시지의 타입에 따라 개별처리과정 판단
				switch (mms.getType()) {
				case Message.FRIEND_ALL:
					//화면에 띄우는 메소드호출
					cfl = new ClientFriendList();
					cfl.setFriendList(res);
					break;
				case Message.FRIEND_INSERT:
				default:
					break;
				}
				
			} while (ois.readObject() == null);// 만약 서버로부터 받지못하면 반복

		} catch (IOException ioe) {// In&Output Stream Exception
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

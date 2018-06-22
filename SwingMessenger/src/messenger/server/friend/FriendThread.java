package messenger.server.friend;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.server.chat.ChatServerThreadList;
/*******************************
 * 2018.06.20 쓰레드 클래스 코딩 스탑.
 * 	 - 아직 제대로 이해하지 못했기 때문에 이대로 코딩 할 경우 위험부담이 큼.
 *   - 멀티쓰레드에대해 이해하기 
 * 2018.06.28 쓰레드 마스터 해서 다시 제대로 코딩하기
 *  ! 학습 목표
 *    - 멀티쓰레드에 대해 이해하고 응용할 수 있다. 
 * 
 *******************************/
public class FriendThread implements Runnable{
	Socket					socket;
	JTextArea 				jta_log;
	FriendMenu 				fm 			= new FriendMenu();
	
	public FriendThread(JTextArea jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}
	
	@Override
	public synchronized void run() {
		try (	
			InputStream			in 	= socket.getInputStream();
			BufferedInputStream bin = new BufferedInputStream(in);
			ObjectInputStream	oin = new ObjectInputStream(bin);

				){
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
				Message<MemberVO> msg = (Message<MemberVO>)oin.readObject();
				msg.getType();//메세지오브젝트 안에 들어있는 메세지 타입을 불러옴
				if(jta_log != null)
				jta_log.append("요청 : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				/*private List<T> request;	//클라이언트가 데이터를 담는 부분
				private List<T> response;	//서버가 데이터를 담는 부분
*/				try(
						OutputStream			out		= socket.getOutputStream();
						BufferedOutputStream	bout	= new BufferedOutputStream(out);
						ObjectOutputStream		oout	= new ObjectOutputStream(bout);
						){
					ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
					ArrayList<MemberVO> response = new ArrayList<MemberVO>();
					MemberVO mvo = request.get(0);
					switch(msg.getType()) {
					case 3:
//						response.add(mvo);
//						response = (ArrayList<MemberVO>)fm.FriendSelectALL(mvo);
//						fm.FriendSelectALL(response);

						break;
					case 4:
//						response.add(mvo);
//						fm.FriendInsert(response);

						break;
					case 5:
//						response.add(mvo);
//						fm.FriendDelete(response);
						break;

				}
				}
				} 
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}

}


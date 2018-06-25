package messenger.protocol;


import java.io.Serializable;
import java.util.List;

/**
 * 클라이언트-서버간 주고받는 메시지 객체.
 * 클라이언트에서 type과 request를 포함하여 전달하면
 * 서버에서 type을 보고 request를 분해하여 처리를 해준 다음 response에 정보를 담아서 다시 클라이언트에 전달함.
 * @author Jin Lee
 *
 * @param <T>
 */
public class Message<T> implements Serializable{
	private static final long serialVersionUID = 8997353509224999302L;
	
	private int 	type;		//메시지 타입
	private List<T> request;	//클라이언트가 데이터를 담는 부분
	private List<T> response;	//서버가 데이터를 담는 부분

	///////////////////////메시지 타입////////////////////////////
	public static final int MEMBER_LOGIN	= 0; //회원 로그인
	public static final int MEMBER_JOIN		= 1; //회원 가입
	public static final int MEMBER_MODIFY 	= 2; //회원 수정
	public static final int MEMBER_IDCHECK	= 3; //아이디 중복검사
	public static final int FRIEND_ALL		= 4; //친구 전체 리스트
	public static final int FRIEND_INSERT	= 5; //친구 추가
	public static final int FRIEND_DELETE	= 6; //친구 삭제
	public static final int FRIEND_SEARCH	= 7; //친구 검색
	public static final int CHAT_SEND		= 8; //채팅 전송
	public static final int CHATROOM_LOAD	= 9; //채팅방 리스트 조회
	public static final int CHATROOM_INVITE	= 10; //친구 초대(또는 방 만들기)
	public static final int CHATROOM_EXIT	= 11;//채팅방 나가기
	public static final int EMOTICON_LOAD	= 12; //서버에서 이모티콘 리스트 요청 (T에는 ImageIcon계열이 들어감)
	//또 뭐가있을까?
	///////////////////////////////////////////////////////////
	
	public Message() {
		this(0,null,null);
	}
	
	public Message(int type, List<T> request, List<T> response) {
		this.type = type;
		this.request = request;
		this.response = response;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<T> getRequest() {
		return request;
	}

	public void setRequest(List<T> request) {
		this.request = request;
	}

	public List<T> getResponse() {
		return response;
	}

	public void setResponse(List<T> response) {
		this.response = response;
	}
	
	
}

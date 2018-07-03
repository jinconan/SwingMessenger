package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger.client.view.ClientFrame;
import messenger.protocol.Message;

/**********************************************************************
 * [클러이언트]친구삭제하기
 * 기능 : 삭제할 친구 아이디와 내 회원번호 전달. 서버는 클라이언트에게 검색 결과를 리스트에 담아서 전달. 
 * 
 * @author1 이정렬 [18/06/28]
 * [시나리오]
 * 1. 친구리스트에서 마우스팝업창으로 친구삭제하기 메뉴를 실행한다.
 * 2. 삭제할 친구아이디를 얻어서 나의 회원번호와 함께 Message<MemberVO>에 담아 보낸다.
 * 2. 서버로부터 받은 정보에 대하여 아래3,4번에 따라 움직이는 메소드를 작성한다
 * 3. Message<MemberVO>가 있을때, 삭제가 완료되었다는 알림창을 띄운 후
 * 		친구목록을 다시 조회하여 리스트를 갱신한다.(ClientFriendList.java 재사용)
 * 4. 없을 경우, 삭제를 실패했다는 안내와함께 반복될 경우 관리자에게 문의해달라는 안내를 한다.
 * 
 * cf. UI구성 후 서버로부터 받은 값을 올리는 것을 구현하도록 할 예정(06/29 프로젝트시간 중...희망사항)
 * 
 * @Author2 이정렬 [18/07/02]
 * [수정사항]
 * 1. 친구추가 및 삭제 프로시저의 input값은 본인회원아이디와 친구회원아이디 이므로,
 * 	userNo(int)를 userId(String)으로 변경함 ( 서버테스트 완료 ) 
 * 
 **********************************************************************/
public class ClientFriendDelete {

	/*선언부*/
	int userNo = 0;				 //본인 회원번호
	String userId	= null;		 //본인아이디
	String friendId = null;		 //UI에서 조회한 검색결과로 추가하고자하는 친구아이디
	ClientFrame frame = null;	 //화면에 담는 f_Panel 전역변수
	
	Message<MemberVO> mms = null;//Client-Server간 주고받을 메세지와
	List<MemberVO> mli = null;	 //메시지에 담길 자료구조 List
	MemberVO mvo = null;		 //List에 담겨질 클래스자료 MemberVO
	MemberVO mvo_f = null;		 //친구꺼
	ClientFriend cf = null;		 //친구관련 작업을 수행할 Thread가 위치한 클래스
	
	Vector<MemberVO> vec = null;;//서버로부터 받은 메시지를 순서대로 담을 변수
	/*생성자*/
	//디펄트 생성자
	public ClientFriendDelete() {}
	
	//userNo,friendId 전역변수 초기화
	public ClientFriendDelete(String userId, String friendId) {
		this.userId   = userId;
		this.friendId = friendId;
	}
	
	//userNo,friendId 전역변수 초기화
	//화면에 담는 f_Panel 전역변수 초기화 추가
	public ClientFriendDelete(String userId, String friendId,ClientFrame frame) {
		this.userId   = userId;
		this.friendId = friendId;
		this.frame = frame;
	}
	
	/*사용자정의메소드*/
	//본인회원번호와 친구아이디를 서버로 전달하기
	public void getFriendDelete() {
		//서버로 보낼 메세지&자료구조&클래스자료 생성
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		mvo_f = new MemberVO();
		
		//담기
		mvo.setMem_id(userId);//본인아이디담음
		mvo_f.setMem_id(friendId);//친구아이디담음
		mli.add(mvo);//회원번호를 ArrayList에 담음
		mli.add(mvo_f);//친구아이디를ArrayList에 담음
		mms.setRequest(mli);//보낼 데이터를 메시지로 묶음
		mms.setType(Message.FRIEND_DELETE);//이 메시지의 프로토콜 지정
		
		//Thread클래스로 보내서 실행
		cf = new ClientFriend(mms, this);//메시지를 넘겨서 start()호출
	}
	
	//서버로부터 받은 메시지에 대해 판단하기
	public void setFriendDelete(List<MemberVO> res) {
		System.out.println(res.size());//테스트용 출력문
		
	/*	서버로부터 받은 메시지의 첫번째 자리에 들어있는 값은
		INSERT프로시저에 대한 결과값 1과0에 대한 서버측의 응답(response)가 담겨있으므로
		이 값이 비어있으면 0, 비어있지않으면 1로 하여 판단한다. */
		switch(res.get(0)==null?0:1) {
			case 0://비어있어요
			/*	UI측에 안내용 팝업창을 띄웁니다.(이 UI는 mainmenu창)
				"친구삭제에 실패했습니다. 같은 문제가 반복될 경우 관리자에게 문의해주세요."*/
				System.out.println("List<MemberVO> res 는 비어있습니다.");//테스트용 출력문
				System.out.println("0이 들어있나요?"+res.contains(0));//테스트용 출력문
				//Insert Here
				break;
				
			case 1:
			/*	UI측에 안내용 팝업창을 띄웁니다.(이 UI는 mainmenu창)
				"친구삭제에 성공했습니다. 목록을 갱신합니다."
				라는 팝업창과 함께 친구목록을 갱신한다*/
				//도전과제.. 갱신된 목록에서 방금 추가된 회원이 선택되도록 커서?를 위치하여 보여주는건 어떨지?
				//Insert Here..
				System.out.println("List<MemberVO> res 는 차있는거같은데...");//테스트용 출력문
				System.out.println("1이 들어있나요?"+res.contains(1));//테스트용 출력문
				System.out.println(res);//테스트용 출력문
				
				renewFriendList();//화면을 갱신해줌
				break;
		}//end of switch
	}//////end of setAddFriend()
	
	//친구추가 완료후 리스트를 갱신해주는 메소드
	public void renewFriendList() {
		//회원리스트전체를 다시검색한다
		ClientFriendList cfl = new ClientFriendList(userNo);
		cfl.getFriendList();
	}//////end of renewFriendList()
}//////////end of class
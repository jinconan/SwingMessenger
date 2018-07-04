package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger.client.view.ClientFrame;
import messenger.client.view.dialog.SearchDialog;
import messenger.protocol.Message;

/**********************************************************************
 * [클러이언트]친구검색하기
 * 기능 : 검색할 친구 아이디만 전달. 서버는 클라이언트에게 검색 결과를 리스트에 담아서 전달. 
 * 
 * @author1 이정렬 [18/06/27]
 * [시나리오]
 * 1. ui에서 검색하고자하는 아이디를 파라미터로 얻어서, Message<MemberVO>에 담아 보낸다.
 * 2. 서버로부터 받은 정보에 대하여 아래3,4번에 따라 움직이는 메소드를 작성한다
 * 3. Message<MemberVO>가 있을때, 해당 사용자의 정보를 화면에 출력한다.
 * 4. 없을 경우, 표시할 정보가 없고, 아이디를 다시 확인해달라는 알림말을 출력한다.
 * 
 * @author2 이정렬 [18/06/28]
 * Q. 이 자료를 기반으로 친구추가를 찾기후에 바로 함께 이루어져야..? UML확인필요..
 * A. UI담당자와 디자인 확정 후 매칭시켜서 함께 처리하기로 함 (6/29 진행예정)
 * 
 **********************************************************************/
public class ClientFriendSearch{

	/*선언부*/
	String userId_f = null;		 //UI로부터받은 '찾고자하는 친구의 아이디'를 담을 변수
//	ClientFrame frame = null;	 //[주석처리]화면에 담는 f_Panel 전역변수
	SearchDialog searchDialog = null; //JDialog로 처리
	
	
	Message<MemberVO> mms = null;//Client-Server간 주고받을 메세지와
	List<MemberVO> mli_f = null; //메시지에 담길 자료구조 List
	MemberVO mvo_f = null;		 //List에 담겨질 클래스자료 MemberVO
	ClientFriend cf = null;		 //친구관련 작업을 수행할 Thread가 위치한 클래스
	
	Vector<MemberVO> vec = null; //서버로부터 받은 메시지를 순서대료 담을 변수
	
	/*생성자*/
	//디펄트 생성자
	public ClientFriendSearch() {}
	
	//userId 전역변수 초기화 : 사용자가 찾고자하는 아이디의 입력값을 파라미터로 받음
	public ClientFriendSearch(String userId_f) {
		this.userId_f = userId_f;
	}
	
	//userId 전역변수 초기화 : 사용자가 찾고자하는 아이디의 입력값을 파라미터로 받음
	//화면에 담는 f_Panel 전역변수 초기화 추가
	public ClientFriendSearch(String userId_f,ClientFrame frame, SearchDialog searchDialog) {
		this.userId_f = userId_f;
		//this.frame = frame;
		this.searchDialog = searchDialog;
	}
	
	/*사용자정의메소드*/
	//찾고자하는 회원의 아이디를 서버로 전달하기
	public void getFriendSearch() {//UI에서 호출할때 사용할 메소드
		//서버로 보낼 메세지&자료구조&클래스자료 생성
		mms = new Message<MemberVO>();
		mli_f = new ArrayList<MemberVO>();
		mvo_f = new MemberVO();
		
		//담기
		mvo_f.setMem_id(userId_f);
		mli_f.add(mvo_f);
		mms.setRequest(mli_f);//담기완료
		mms.setType(Message.FRIEND_SEARCH);//이 메시지의 프로토콜 지정
		
		//Thread클래스로 보내서 실행
		cf = new ClientFriend(mms, this);
	}

	public void setFriendSearch(List<MemberVO> res) {
		System.out.println(res);//테스트용 출력문
		String[][]	datas	= new String[res.size()][3];
		//List에 담긴 MemberVO의 데이터를 dtm에 담기
		//vec = new Vector<MemberVO>();
		for(int i=0;i<res.size();i++) {
			//골라진 List자료는 회원1명에 대한 로우(row)자료가 순서대로 담겨져있음. 그러므로 벡터에 바로 담음
			//vec.add(res.get(i));
			datas[i][0]=res.get(i).getMem_id(); 
			datas[i][1]=res.get(i).getMem_name(); 
			datas[i][2]=res.get(i).getMem_nick(); 
		}
		//Insert Here-친구찾기 UI에 자료담기
		searchDialog.setDialog(datas);
	}

	
}

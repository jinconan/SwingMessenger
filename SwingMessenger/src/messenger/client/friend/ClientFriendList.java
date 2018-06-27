package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

/**********************************************************************
 * [클러이언트]친구리스트 불러오기
 * 기능 : 본인회원번호만 담아서 서버로 전달. 서버로부터 MemberVO의 리스트를 받은 후 친구목록 갱신하기
 * @author1 이정렬 [18/06/26]
 * [시나리오]
 * 1. 본인회원정보(userNo)를 초기화하기
 * 2. 소켓생성후 서버로 친구목록을 요청하도록 메소드로 실행시키기(수신되고나면 닫기)
 * 3. 서버에서 넘어온 친구목록을 받는 메소드를 실행시키기(무한반복으로 열어두기)
 * 4. 2번과 3번을 순차적으로 실행하는 메소드를 만들어서, UI에서 호출하면 바로 작동하도록 하기
 * 
 * @author2 이정렬 [18/06/27]
 * [체크사항]
 * 1. oos를 선언한 이후 write를 먼저하고, ois를 선언할 것
 * 2. 사용자의 회원번호를 넘길때, userNo로 바로넘기는것이 아니라,
 *    MemberVO와 Message클래스, ArrayList를 이용할 것
 * 3. 서버에 요청하는 MemberVO는 request
 * 4. 서버로 부터 받아온 MemberVO는 response : 이 내용을 해체하여 UI에 올리기
 * 5. Table형태로 올려서 열람하기 (JTable)
 *  
 **********************************************************************/
public class ClientFriendList extends Thread {

	int userNo	= 0;
	ClientFriend cf = null;
	Message<MemberVO> mms = null;
	List<MemberVO> mli = null;
	MemberVO mvo = null;
	Vector vec = null;

	//int타입 생성자가 있어서 만들어준 디펄트 생성자
	public ClientFriendList() {}
	
	//userNo 전역변수 초기화
	public ClientFriendList(int userNo) {
		this.userNo = userNo;
	}

	//UI에서 호출할때 사용할 메소드
	//본인회원번호를 서버로 전달하기
	public void getFriendList() {
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		
		mvo.setMem_no(userNo);
		mli.add(mvo);
		mms.setRequest(mli);//보낼 데이터를 메시지로 묶음
		
		cf = new ClientFriend(mms);//메시지를 넘겨서 start()호출
	}
	
	//전달 받은 정보를 UI에 띄우기(run메소드에서 호출)
	public void setFriendList(List<MemberVO> res) {
		//넘어왔는지 확인용
		System.out.println(res);
		
		//List에 담긴 MemberVO의 데이터를 dtm에 담기
		vec = new Vector();;
		for(int i=0;i<res.size();i++) {
			vec.add(res.get(i).getMem_id());
			vec.add(res.get(i).getMem_name());
			vec.add(res.get(i).getMem_nick());
			vec.add(res.get(i).getMem_hp());
			vec.add(res.get(i).getMem_profile());
			vec.add(res.get(i).getMem_background());
			//친구리스트 패널에 들어가는 dtm을 호출
			dtm.addRow(vec);
		}
	}
	
		
}/////////////end of class

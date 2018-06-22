package messenger.server.chat;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

public class Info {
	/*
	 * <클라이언트가 할 것>
	 * 1. (로그인)로그인 창에 양식 채우고 서버에 인증 요청하기
	 * 2. (회원가입)회원가입창에 양식 채우고 서버에 회원가입 요청 후 로그인까지 하기
	 * 3. (회원가입-아이디 중복검사)회원가입시 아이디 중복검사하기

	 * 4. (친구리스트 가져오기)로그인 후 서버에 요청해서 친구리스트 가져오기
	 * 5. (대화방 리스트 가져오기)로그인 후 서버에 요청해서 방 목록 가져오기
	 * 6. (이모티콘 리스트 가져오기) - 아직 못정함.
	 *
	 * 7.(회원정보수정) 양식 수정하고 서버에 요청해서 응답 받은 후 변경점 화면에 적용.
	 * 8. (대화방 생성)방 생성 버튼 클릭 후에 멤버를 선택하고 서버에 전달하여 방 목록을 갱신하기
	 * 9. (친구 추가)친구 추가 버튼 클릭 후 아이디를 입력 후 검색버튼 클릭-> 검색 결과를 서버로부터 받음. -> 선택한 아이디를 추가요청하고 친구리스트 갱신하기
	 * 10. (친구 삭제)멤버 선택하고 친구 삭제 요청을 서버에 전달하고 결과를 리턴받아 친구리스트 갱신하기
	 * 11. (친구 초대)멤버 선택하고 친구 초대 요청을 서버에 전달만 하기.(초대 로직은 서버에서 진행) 
	 * 12.(사진 추가) 이미지 파일을 선택하여 BufferedImage에 저장하는 방법을 사용.
	
	 * 13.(뉴스) 뉴스 메뉴 클릭시 서버에 뉴스 요청해서 결과 받아오고 화면 갱신하기
	 * 14.(일정리스트 가져오기) 메뉴 선택시 서버에 등록된 일정 받아오기
	 * 15.(일정 등록) 양식 채우고 서버에 전달하고, 응답을 받으면 일정 리스트 갱신하기.

	 * 
	 * 
	 * 
	 * 15. 이스터 에그
	 *     - (파일첨부?) - 클릭시 비꼬는 메시지 띄우기 (파일 첨부 창까지는 띄움)
	 *     - (VoIP)    - 클릭시 비꼬는 메시지 띄우기 (연락처 입력 창 까지는 띄움)
	 *     
	 */

/*
 * 클라이언트 채팅 전달.
 * 서버: 받은 메시지 db에 등록 후 방 번호 확인 -> 해당 방 번호에 존재하는 쓰레드에게 메시지 전달.
 * 클라이언트 :해당 방 번호에 해당하는 채팅방에 메시지 출력
 * 
 * 
 */

/*
 * <방생성>
 * 클라이언트 : 친구 선택 후 방 생성 메시지 서버에 전달
 * 서버 : db에 새 방 등록 후  방 객체에 해당 인원 추가 후 전달
 *  클라이언트 : 해당 방을 방 목록에 추가 후 새로고침
 */

/*
 * <초대>
 * 방 생성과 메우 유사
 * */

/*
	<접속시>
	1. 인증
	2. 친구리스트 불러오기
	3. 채팅 서버에 연결하여 방 목록 불러오기
	클라이언트 자기 자신에 대한 정보가 담긴 MemberVO 전달
	서버 : db에서 해당 멤버가 참여한 방 리스트 불러오기
	서버 : 해당 방 들을 전부 해쉬 맵에 추가.(이미 맵에 존재하면 리스트에만 추가)-> 참여 리스트를 클라이언트에 전달
	클라이언트 : 받은 정보 토대로 방 리스트 새로고침.

*/
	
//	public ArrayList<MemberVO> method() {
//		Message<MemberVO> msg = new Message<MemberVO>();
//		
//		msg.setType(Message.FRIEND_ALL);
//		ArrayList<MemberVO> request = new ArrayList<MemberVO>();
//		MemberVO mem = new MemberVO();
//		mem.setMem_no(mem_no);
//		request.add(mem);
//		
//		msg.setRequest(request);
//		
//		Socket socket = new Socket(서버);
//		
//		
//		oos.writeObject(msg);
//		
//		
//		msg = (Message<MemberVO>)ois.readObject();
//		
//		
//		return  msg.getResponse();
		
		
		
		
		
}






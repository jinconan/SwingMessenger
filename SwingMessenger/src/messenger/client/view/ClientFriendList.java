package messenger.client.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

/**********************************************************************
 * [클러이언트]친구리스트 불러오기
 * 기능 : 본인회원번호만 담아서 서버로 전달. 서버로부터 MemberVO의 리스트를 받은 후 친구목록 갱신하기
 * @author 이정렬
 * [시나리오]
 * 1. 본인회원정보(userNo),연결정보(oos,ois)를 초기화하기(열기)
 * 2. 서버로 친구목록을 요청하도록 메소드로 실행시키기(수신되고나면 닫기)
 * 3. 서버에서 넘어온 친구목록을 받는 메소드를 실행시키기(무한반복으로 열어두기)
 * 4. 2번과 3번을 순차적으로 실행하는 메소드를 만들어서, UI에서 호출하면 바로 작동하도록 하기
 **********************************************************************/
public class ClientFriendList extends Thread {

	int userNo	= 0;
	Socket flsc	= null;
	String IP	= "192.168.0.235";	//서버IP주소
	ObjectOutputStream	oos = null;	//클라이언트가 전달하는 것이 먼저이므로 out을 먼저 사용
	ObjectInputStream	ois = null;
	
	Message<MemberVO> mvo = null;
	
	//userNo, ois, oos 전역변수 초기화
	public ClientFriendList(int userNo, ObjectInputStream ois,ObjectOutputStream oos) {
		this.userNo = userNo;
		this.oos = oos;
		this.ois = ois;
	}
	
	//UI에서 호출할때 사용할 메소드
	public void FriendList() {
		getFriendList();//사용자의 
	}
	
	//본인회원번호를 서버로 전달하기
	public void getFriendList() {
		start();
	}
	
	//전달 받은 정보를 UI에 띄우기
	public void setFriendList(Object obj) {
		//서버로부터 전달받은 정보 담기
		List<MemberVO> li = new ArrayList<MemberVO>();
		li.add((MemberVO) obj);
		//담은 정보를 클래스주입
		mvo = new Message<MemberVO>();
		mvo.setRequest((List<MemberVO>)li);
		//받아온 정보를 UI에 띄우고 싶은데..??? 여긴 감이 안옴..????
		
	}
	
	//Thread클래스 오버라이드
	@Override
	public void run() {
		try {//소켓을 생성하고, 소켓을 통한 듣기와 말하기 창구 생성
			flsc = new Socket(IP,Port.FRIEND);
			oos	 = new ObjectOutputStream(flsc.getOutputStream());
			ois	 = new ObjectInputStream(flsc.getInputStream());
			do{ //말하기 : 서버로 본인회원번호를 보내고 친구목록 요청하기
				oos.writeObject(userNo);
				//듣기 : 서버로부터 친구목록 받고, 받은 내용을 UI에 올리기
				if(ois.readObject()!=null)
					setFriendList(ois.readObject());
			}while(ois.readObject()==null);//만약 서버로부터 받지못하면 반복
			
		} catch (IOException ioe) {//In&Output Stream Exception
			System.out.println("ClientFreindList.run() in&out오류발생");
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println("ClientFreindList.run() 기타 오류발생");
			e.printStackTrace();
			
		} finally {//화면에 자료올리는것을 마친후 닫기 실행
			try{
				ois.close();
				oos.close();
			}catch (IOException ioe2) {
				System.out.println("ClientFreindList.run() 닫는 중에 오류발생");
				ioe2.printStackTrace();
			}
			
		}/////end of try-catch-finally
		
	}/////////end of run()
	
}/////////////end of class

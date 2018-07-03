package messenger.client.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class AttendRoom {
	
    //대화에 추가할 친구들 ~~~ 목록	
	List   <ChatVO>      chatVO_list      = null;
	List   <MemberVO>    MemberVO_list    = null;
	//기존에 있던 방을 가져올 메세지 처리부 
	Message<MemberVO>    Mem_message      = null;
	Message<ChatVO>      Chat_message     = null;
	        RoomVO       roomVO           = null;
	        ChatVO       chatVO           = null;
	        MemberVO     memberVO         = null;
	        
	        
	        
	//서버측 선언 ~ 
	Socket             socket   = null;
	ObjectInputStream  ois      = null;
	ObjectOutputStream oos      = null;
	

	//우선 방데이터를 받아온다 유저 정보를 입력해서 .
	public void method(Message<MemberVO> msg) {
		//로그인 했을때 유저의 번호를 메세지에 담아서 전송한다.~~ 
		Mem_message       = new Message  <MemberVO>();
		MemberVO_list     = new ArrayList<MemberVO>();
		
		memberVO          = new MemberVO();
		
		memberVO     .setMem_no(msg.getResponse().get(0).getMem_no());
		MemberVO_list.add(memberVO);
		
		Mem_message.setType   (Mem_message.CHATROOM_LOAD);
		Mem_message.setRequest(MemberVO_list);
		
		try {
			
			socket       = new Socket("192.168.0.235",Port.CHAT);
			oos          = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(Mem_message);
			ois          = new ObjectInputStream(socket.getInputStream());
			
			Chat_message = (Message<ChatVO>) ois.readObject();
			//이 아래는 가지고온 방정보를 테이블에 갱신해준다. ~~ 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //여기서 받아온데이터를 테이블에 추가해주고	
	}
	
	//방을 만드는 메소드 제목과 벡터로 담은 친구들이 넘어온다.~~ 
	public void Room_Create(String title,Vector friend) {
		
		//불러온 방과 비교를 해서 방이 존재하는지 없는지 비교한다.
		for(ChatVO msg:Chat_message.getResponse()) {
			//혹시 너 불러온 방이름과 내가 개설할려는 방이름과 같니? 비교해서 방이 존재하면 밖으로 탈출 ~~ 
			if(msg.getRoomVO().getRoom_title().equals(title)) {
				JOptionPane.showMessageDialog(null, "이미 방이 존재합니다.", "알림", JOptionPane.CLOSED_OPTION);
				return;
			}
		}
		//없으면 그방에 데이타를 이제 담아준다. ~~ 
		Chat_message     = new Message  <ChatVO>();
		chatVO_list      = new ArrayList<ChatVO>();
		
		chatVO           = new ChatVO();
		roomVO           = new RoomVO();
		memberVO         = new MemberVO();
		
		//방제목을 보낸다.
		roomVO .setRoom_title(title);
		//백터에 담아서 친구 목록을 보낸다
//        memberVO.setFriend_name(friend);
		
		chatVO .setMemVO  (memberVO);
		chatVO .setRoomVO (roomVO);
		Chat_message.setRequest(chatVO_list);
		Chat_message.setType   (Chat_message.CHATROOM_INVITE);
		
        try {
			
			socket       = new Socket("192.168.0.235",Port.CHAT);
			oos          = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(Chat_message);
			ois          = new ObjectInputStream(socket.getInputStream());
			
			//만약에 거기서 응답이왔니?
			if(ois.readObject()!=null) {
				System.out.println("방만들기 성공");
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

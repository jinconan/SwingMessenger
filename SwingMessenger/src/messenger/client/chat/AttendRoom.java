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
	
    //��ȭ�� �߰��� ģ���� ~~~ ���	
	List   <ChatVO>      chatVO_list      = null;
	List   <MemberVO>    MemberVO_list    = null;
	//������ �ִ� ���� ������ �޼��� ó���� 
	Message<MemberVO>    Mem_message      = null;
	Message<ChatVO>      Chat_message     = null;
	        RoomVO       roomVO           = null;
	        ChatVO       chatVO           = null;
	        MemberVO     memberVO         = null;
	        
	        
	        
	//������ ���� ~ 
	Socket             socket   = null;
	ObjectInputStream  ois      = null;
	ObjectOutputStream oos      = null;
	

	//�켱 �浥���͸� �޾ƿ´� ���� ������ �Է��ؼ� .
	public void method(Message<MemberVO> msg) {
		//�α��� ������ ������ ��ȣ�� �޼����� ��Ƽ� �����Ѵ�.~~ 
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
			//�� �Ʒ��� ������� �������� ���̺� �������ش�. ~~ 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        //���⼭ �޾ƿµ����͸� ���̺� �߰����ְ�	
	}
	
	//���� ����� �޼ҵ� ����� ���ͷ� ���� ģ������ �Ѿ�´�.~~ 
	public void Room_Create(String title,Vector friend) {
		
		//�ҷ��� ��� �񱳸� �ؼ� ���� �����ϴ��� ������ ���Ѵ�.
		for(ChatVO msg:Chat_message.getResponse()) {
			//Ȥ�� �� �ҷ��� ���̸��� ���� �����ҷ��� ���̸��� ����? ���ؼ� ���� �����ϸ� ������ Ż�� ~~ 
			if(msg.getRoomVO().getRoom_title().equals(title)) {
				JOptionPane.showMessageDialog(null, "�̹� ���� �����մϴ�.", "�˸�", JOptionPane.CLOSED_OPTION);
				return;
			}
		}
		//������ �׹濡 ����Ÿ�� ���� ����ش�. ~~ 
		Chat_message     = new Message  <ChatVO>();
		chatVO_list      = new ArrayList<ChatVO>();
		
		chatVO           = new ChatVO();
		roomVO           = new RoomVO();
		memberVO         = new MemberVO();
		
		//�������� ������.
		roomVO .setRoom_title(title);
		//���Ϳ� ��Ƽ� ģ�� ����� ������
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
			
			//���࿡ �ű⼭ �����̿Դ�?
			if(ois.readObject()!=null) {
				System.out.println("�游��� ����");
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

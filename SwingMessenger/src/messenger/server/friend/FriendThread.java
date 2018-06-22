package messenger.server.friend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/*******************************
 * 2018.06.20 ������ Ŭ���� �ڵ� ��ž.
 * 	 - ���� ����� �������� ���߱� ������ �̴�� �ڵ� �� ��� ����δ��� ŭ.
 *   - ��Ƽ�����忡���� �����ϱ� 
 * 2018.06.28 ������ ������ �ؼ� �ٽ� ����� �ڵ��ϱ�
 *  ! �н� ��ǥ
 *    - ��Ƽ�����忡 ���� �����ϰ� ������ �� �ִ�. 
 * 
 *******************************/
public class FriendThread extends Thread{
	ObjectInputStream ois = null;//���
	ObjectOutputStream oos = null;//���ϱ�
//	Ŭ���̾�Ʈģ����� cfm = null;//�����忡�� �޴� ����� �ҷ��ͼ� ������.
	//�ӽ÷� ��Ʈ������ Ī��.
	ArrayList<String> menu = null;
	FriendMenu fm = new FriendMenu();
	String menu_index = "";
	FriendThread(){
		start();
	}
//	public FriendThread(Ŭ���̾�Ʈģ����� cfm) {
//		this.cfm = cfm;
//		try {
//			ois = new ObjectInputStream
//					(cfm.ģ��Ŭ���̾�Ʈ����.getInputStream());
//			oos = new ObjectOutputStream
//					(cfm.ģ��Ŭ���̾�Ʈ����.getOutputStream());
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//			System.out.println(e.toString());
//		}
//	}
	@Override
	public synchronized void run() {
		try {	
			while(true) {
				/************************************************************
				 * �޼���Ÿ�� ��ȣ.
				 * public static final int MEMBER_LOGIN   = 0; //ȸ�� �α���
				 * public static final int MEMBER_JOIN    = 1; //ȸ�� ����
				 * public static final int MEMBER_MODIFY  = 2; //ȸ�� ����
				 * public static final int FRIEND_ALL     = 3; //ģ�� ��ü ����Ʈ
				 * public static final int FRIEND_INSERT  = 4; //ģ�� �߰�
				 * public static final int FRIEND_DELETE  = 5; //ģ�� ����
				 * public static final int FRIEND_SEARCH  = 6; //ģ�� �˻�
				 * public static final int CHAT_SEND      = 7; //ä�� ����
				 * public static final int CHAT_LOAD      = 8; //ä�� ���� ��ȸ
				 ************************************************************/
				Message<MemberVO> msg = (Message<MemberVO>)ois.readObject();
				msg.getType();//�޼���������Ʈ �ȿ� ����ִ� �޼��� Ÿ���� �ҷ���
				
				
				//Ŭ���̾�Ʈ�� ���� �����͵��� ��̸���Ʈ Ÿ������ ��ȯ�ؼ� �޾ƿ���
				ArrayList<MemberVO> list = (ArrayList<MemberVO>)msg.getRequest();
				MemberVO mem = list.get(0);//
				mem.getMem_id();//Ŭ���̾�Ʈ�� ��û�� ID�� ������.
				
				//�޾ƿ��� Ÿ���� �޶� �����Ͽ��� �̺κ� ���� �����ؾ��ҵ�....
//				ArrayList<MemberVO> result = (ArrayList<MemberVO>)fm.FriendSelectALL(mem.getMem_id());
//				msg.setResponse(result);
//				oos.writeObject(msg);
							//���� �����忡�� ���� ����(��ǲ��Ʈ���� �о�� menu��ü������ ����(��̸���Ʈ))
				switch(msg.getType()) {
				case 3:
					fm.FriendSelectALL(list);
					
					break;
				case 4:
					fm.FriendInsert(list);
					
					break;
				case 5:
					fm.FriendDelete(list);
					
				}
				//ObjectOutputStream
				
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
		}

	}
	public static void main(String args[]) {
		new FriendThread();
	}
}


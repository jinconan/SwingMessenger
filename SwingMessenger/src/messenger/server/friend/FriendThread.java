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
 * 2018.06.20 ������ Ŭ���� �ڵ� ��ž.
 * 	 - ���� ����� �������� ���߱� ������ �̴�� �ڵ� �� ��� ����δ��� ŭ.
 *   - ��Ƽ�����忡���� �����ϱ� 
 * 2018.06.28 ������ ������ �ؼ� �ٽ� ����� �ڵ��ϱ�
 *  ! �н� ��ǥ
 *    - ��Ƽ�����忡 ���� �����ϰ� ������ �� �ִ�. 
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
				Message<MemberVO> msg = (Message<MemberVO>)oin.readObject();
				msg.getType();//�޼���������Ʈ �ȿ� ����ִ� �޼��� Ÿ���� �ҷ���
				if(jta_log != null)
				jta_log.append("��û : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
				/*private List<T> request;	//Ŭ���̾�Ʈ�� �����͸� ��� �κ�
				private List<T> response;	//������ �����͸� ��� �κ�
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


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

import messenger._db.dao.FriendMenu;
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

			Message<MemberVO> msg = (Message<MemberVO>)oin.readObject();
			msg.getType();//�޼���������Ʈ �ȿ� ����ִ� �޼��� Ÿ���� �ҷ���
			System.out.println(msg.getType());
			if(jta_log != null)
				jta_log.append("��û : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
			/*private List<T> request;	//Ŭ���̾�Ʈ�� �����͸� ��� �κ�
				private List<T> response;	//������ �����͸� ��� �κ�
			 */				
			try(
					OutputStream			out		= socket.getOutputStream();
					BufferedOutputStream	bout	= new BufferedOutputStream(out);
					ObjectOutputStream		oout	= new ObjectOutputStream(bout);
					){
				ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
				ArrayList<MemberVO> response = new ArrayList<MemberVO>();
				MemberVO mvo = request.get(0);//Ŭ���̾�Ʈ���� ���� ������ mvo�� ����.
				/*
				 * �޴� ��ǲ��Ʈ���� 1���� ������ �ƿ�ǲ ��Ʈ���� ������ 1�����ߵ�.
				 * �޴� ��ǲ��Ʈ���� 2���� ������ �ƿ�ǲ ��Ʈ���� 1���ϰ�� EOFException ������ ��.
				 * �޴°� �ΰ��ε� ���������� �Ѱ��� �ڵ����� ������ ��ǲ��Ʈ���� �η� �����·� �Ǵ��� �˾Ҵµ� �ƴϿ���..
				 * 
				 */
				switch(msg.getType()) {
				case Message.FRIEND_ALL://ģ�� ��ü ��ȸ 1�ܰ� �Ϸ�. --�ϴ�����..(�����߸� ���� ������ ����)
					ArrayList<MemberVO> result = fm.FriendSelectALL(request);//�������� ���� ������ selectAll�� �Ķ���ͷ� ����.
					response = result;
					msg.setResponse(response);//������ ����.
					oout.writeObject(msg);//�ƿ�ǲ ��Ʈ������ �ۼ�
					oout.flush();//������ ������ ����.
					break;
				case Message.FRIEND_INSERT:
					MemberVO fmvo = request.get(1);//Ŭ���̾�Ʈ���� ���� ģ�������� fmvo�� ����.
					fm.FriendInsert(request, 4);
					String out_msg = fm.FriendInsert(request, 4);//�̰� ���� �ڽ��� ������ �������°���... �׷� ģ���μ�Ʈ�� ������... ���� ����?
					//ģ���������� ���� request �ε���0���� ������ ���� �ε��� 1���� ģ�������� ������..?
					if(out_msg!=null) {
					response.add(fmvo);//mvo ��� ģ��VO���� ���ڴ�
					//�̰� �ڱ� �ڽſ� ���� ������ ��°���.. ���°Ͱ� ���°��� ����...
					//��� �̷��� �ǹ̰� ����...  �׷� ������������ �� ��ƾ� �ɱ�..?
					//���������� ��̸���Ʈ ���VOŸ���ε�, ��°���� �ΰ��� �ƴϸ� �״�� �ڱ� �ڽ��� ������ Ŭ���̾�Ʈ�� �����ִ°��̰�.
					//��°���� null�̸� �׳� ������� ������
					}
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.FRIEND_DELETE:
					MemberVO fmvo2 = request.get(1);//Ŭ���̾�Ʈ���� ���� ģ�������� fmvo�� ����.
					fm.FriendDelete(request, 5);
					String out_msg2 = fm.FriendDelete(request, 5);
					if(out_msg2!=null) {
						response.add(fmvo2);//mvo ��� ģ��VO���� ���ڴ�
						//�̰� �ڱ� �ڽſ� ���� ������ ��°���.. ���°Ͱ� ���°��� ����...
						//��� �̷��� �ǹ̰� ����...  �׷� ������������ �� ��ƾ� �ɱ�..?
						//���������� ��̸���Ʈ ���VOŸ���ε�, ��°���� �ΰ��� �ƴϸ� �״�� �ڱ� �ڽ��� ������ Ŭ���̾�Ʈ�� �����ִ°��̰�.
						//��°���� null�̸� �׳� ������� ������
						}
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.FRIEND_SEARCH:
					response = fm.FriendSearch(request);//ģ�� �˻� ����� ���������� ����.
					oout.writeObject(msg);
					oout.flush();
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


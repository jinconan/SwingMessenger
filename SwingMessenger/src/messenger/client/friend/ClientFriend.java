package messenger.client.friend;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;
import messenger.protocol.Server;

/**********************************************************************
 * [Ŭ���̾�Ʈ]ģ������Ʈ �ҷ�����
 * ��� : ģ��(Friend)������ �۾��� ������� �̰����� ó���ǰ� ����
 * 
 * @author1 ������...18/06/27
 * [üũ����] ~ <cliendFriendList.java>
 * 1.do-while�� ���� �� EOFException�ּ�ó��
 *   ������������ ��ð����ǰ� �ְ�, �ܹ��� ��û+�ܹ��� �����̱� ������, do-while������ �ݺ��� �ʿ䰡 ����
 * 2. ListŸ�� ���� res�� <MemberVO>Ÿ������ Ȯ�������־ Ÿ�Կ��� ����
 * 
 **********************************************************************/

public class ClientFriend extends Thread{

	Socket flsc	= null;
	String IP	= Server.IP;	//����IP�ּ�
	ObjectOutputStream	oos = null;	//Ŭ���̾�Ʈ�� �����ϴ� ���� �����̹Ƿ� out�� ���� ���
	ObjectInputStream	ois = null;
	
	Message<MemberVO> mms = null;
	List<MemberVO> mli = null;
	MemberVO mvo = null;
	
	ClientFriendList	cfl = null;
	ClientFriendSearch	cfs = null;
	ClientFriendAdd		cfa = null;
	ClientFriendDelete	cfd = null;

	//ģ������ �޼ҵ�� ��� �̰����� ����
	public ClientFriend(Message<MemberVO> mms) {
		this.mms = mms;
		this.start();
	}

	// ThreadŬ���� �������̵�
	@Override
	public void run() {
		try {//������ �����ϰ�, ������ ���� ���� ���ϱ� â�� ����
			flsc = new Socket(IP, Port.FRIEND);
			oos  = new ObjectOutputStream(flsc.getOutputStream());

		//do {
			//���ϱ�
			//ģ����ü��ȸ : ������ ����ȸ����ȣ�� ������ ģ����� ��û�ϱ�
			//ģ��ã�� : ������ ���ȸ�����̵� ������ ģ���ڷ� ��û�ϱ�
			oos.writeObject(mms);

			//���
			ois = new ObjectInputStream(flsc.getInputStream());
			//���� ������ Ȯ���ϰ� �޽����������ݷ� ��Ƴ�
			mms = (Message<MemberVO>)ois.readObject();
			//�޽����������ݿ� ����ִ� �����ͺκ��� ��� (ListŸ��)
			List<MemberVO> res = mms.getResponse();
				//�޽����������� �κ��� �����ϴ� ���
				//MemberVO memVO = res.get(0);

			//���� ������ �޽���Ÿ�Կ� ���� ����ó������
			switch (mms.getType()) {
			
			case Message.FRIEND_ALL://ģ����ü��ȸ
				//ȭ�鿡 ���� �޼ҵ�ȣ��
				cfl = new ClientFriendList();
				cfl.setFriendList(res);//��� List�ڷ� �ѱ�
				break;
			
			case Message.FRIEND_SEARCH://ģ��ã��
				cfs = new ClientFriendSearch();
				cfs.setFriendSearch(res);//��� List�ڷ� �ѱ�
				break;
				
			case Message.FRIEND_INSERT://ģ���߰�
				cfa = new ClientFriendAdd();
				cfa.setFriendAdd(res);//��� List�ڷ� �ѱ�
				break;
				
			case Message.FRIEND_DELETE://ģ������
				cfd = new ClientFriendDelete();
				cfd.setFriendDelete(res);//��� List�ڷ� �ѱ�
				break;
				
			default:
				System.out.println("[�˸�]4���� Ÿ�� �� �ٸ� Ÿ���� �޽����� �����κ��� �Ѿ�Խ��ϴ�.");
				break;
			}
/*		} while (ois.readObject() == null);// ���� �����κ��� �������ϸ� �ݺ�

		} catch (EOFException e) {
			System.out.println("EOF���� : "mms);
			return;
		//������ ��ÿ����ְ�, ģ����Ͽ� ������ ������ �ܹ����� ��û�� ��������̹Ƿ�
		//do-while������ �ݺ��� �ʿ䰡 ����.(18/06/27) 
*/		}
		  catch (IOException ioe) {// In&Output Stream Exception
			System.out.println("ClientFreindList.run() in&out�����߻�");
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println("ClientFreindList.run() ��Ÿ �����߻�");
			e.printStackTrace();

		} finally {// ȭ�鿡 �ڷ�ø��°��� ��ģ�� �ݱ� ����
			try {
				ois.close();
				oos.close();
			} catch (IOException ioe2) {
				System.out.println("ClientFreindList.run() �ݴ� �߿� �����߻�");
				ioe2.printStackTrace();
			}

		} ///// end of try-catch-finally

	}///////// end of run()

}

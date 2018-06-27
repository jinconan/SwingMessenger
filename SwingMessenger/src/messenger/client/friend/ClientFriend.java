package messenger.client.friend;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

public class ClientFriend extends Thread{

	Socket flsc	= null;
	String IP	= "192.168.0.235";	//����IP�ּ�
	ObjectOutputStream	oos = null;	//Ŭ���̾�Ʈ�� �����ϴ� ���� �����̹Ƿ� out�� ���� ���
	ObjectInputStream	ois = null;
	
	Message<MemberVO> mms = null;
	List<MemberVO> mli = null;
	MemberVO mvo = null;
	
	ClientFriendList cfl = null;

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

			do {//���ϱ� : ������ ����ȸ����ȣ�� ������ ģ����� ��û�ϱ�
				oos.writeObject(mms);
				//��� : �����κ��� ģ����� �ް�, ���� ������ UI�� �ø���
				ois = new ObjectInputStream(flsc.getInputStream());

				//���� ������ Ȯ���ϰ� �޽����������ݷ� ��Ƴ�
				mms = (Message<MemberVO>) ois.readObject();
				//�޽����������ݿ� ����ִ� �����ͺκ��� ��� (ListŸ��)
				List<MemberVO> res = mms.getResponse();
					//�޽����������� �κ��� �����ϴ� ���
					//MemberVO memVO = res.get(0);

				//�޽����� Ÿ�Կ� ���� ����ó������ �Ǵ�
				switch (mms.getType()) {
				case Message.FRIEND_ALL:
					//ȭ�鿡 ���� �޼ҵ�ȣ��
					cfl = new ClientFriendList();
					cfl.setFriendList(res);
					break;
				case Message.FRIEND_INSERT:
				default:
					break;
				}
				
			} while (ois.readObject() == null);// ���� �����κ��� �������ϸ� �ݺ�

		} catch (IOException ioe) {// In&Output Stream Exception
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

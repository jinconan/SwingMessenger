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
 * [Ŭ���̾�Ʈ]ģ������Ʈ �ҷ�����
 * ��� : ����ȸ����ȣ�� ��Ƽ� ������ ����. �����κ��� MemberVO�� ����Ʈ�� ���� �� ģ����� �����ϱ�
 * @author ������
 * [�ó�����]
 * 1. ����ȸ������(userNo),��������(oos,ois)�� �ʱ�ȭ�ϱ�(����)
 * 2. ������ ģ������� ��û�ϵ��� �޼ҵ�� �����Ű��(���ŵǰ��� �ݱ�)
 * 3. �������� �Ѿ�� ģ������� �޴� �޼ҵ带 �����Ű��(���ѹݺ����� ����α�)
 * 4. 2���� 3���� ���������� �����ϴ� �޼ҵ带 ����, UI���� ȣ���ϸ� �ٷ� �۵��ϵ��� �ϱ�
 **********************************************************************/
public class ClientFriendList extends Thread {

	int userNo	= 0;
	Socket flsc	= null;
	String IP	= "192.168.0.235";	//����IP�ּ�
	ObjectOutputStream	oos = null;	//Ŭ���̾�Ʈ�� �����ϴ� ���� �����̹Ƿ� out�� ���� ���
	ObjectInputStream	ois = null;
	
	Message<MemberVO> mvo = null;
	
	//userNo, ois, oos �������� �ʱ�ȭ
	public ClientFriendList(int userNo, ObjectInputStream ois,ObjectOutputStream oos) {
		this.userNo = userNo;
		this.oos = oos;
		this.ois = ois;
	}
	
	//UI���� ȣ���Ҷ� ����� �޼ҵ�
	public void FriendList() {
		getFriendList();//������� 
	}
	
	//����ȸ����ȣ�� ������ �����ϱ�
	public void getFriendList() {
		start();
	}
	
	//���� ���� ������ UI�� ����
	public void setFriendList(Object obj) {
		//�����κ��� ���޹��� ���� ���
		List<MemberVO> li = new ArrayList<MemberVO>();
		li.add((MemberVO) obj);
		//���� ������ Ŭ��������
		mvo = new Message<MemberVO>();
		mvo.setRequest((List<MemberVO>)li);
		//�޾ƿ� ������ UI�� ���� ������..??? ���� ���� �ȿ�..????
		
	}
	
	//ThreadŬ���� �������̵�
	@Override
	public void run() {
		try {//������ �����ϰ�, ������ ���� ���� ���ϱ� â�� ����
			flsc = new Socket(IP,Port.FRIEND);
			oos	 = new ObjectOutputStream(flsc.getOutputStream());
			ois	 = new ObjectInputStream(flsc.getInputStream());
			do{ //���ϱ� : ������ ����ȸ����ȣ�� ������ ģ����� ��û�ϱ�
				oos.writeObject(userNo);
				//��� : �����κ��� ģ����� �ް�, ���� ������ UI�� �ø���
				if(ois.readObject()!=null)
					setFriendList(ois.readObject());
			}while(ois.readObject()==null);//���� �����κ��� �������ϸ� �ݺ�
			
		} catch (IOException ioe) {//In&Output Stream Exception
			System.out.println("ClientFreindList.run() in&out�����߻�");
			ioe.printStackTrace();
		} catch (Exception e) {
			System.out.println("ClientFreindList.run() ��Ÿ �����߻�");
			e.printStackTrace();
			
		} finally {//ȭ�鿡 �ڷ�ø��°��� ��ģ�� �ݱ� ����
			try{
				ois.close();
				oos.close();
			}catch (IOException ioe2) {
				System.out.println("ClientFreindList.run() �ݴ� �߿� �����߻�");
				ioe2.printStackTrace();
			}
			
		}/////end of try-catch-finally
		
	}/////////end of run()
	
}/////////////end of class

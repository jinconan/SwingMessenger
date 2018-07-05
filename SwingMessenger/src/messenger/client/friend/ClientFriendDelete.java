package messenger.client.friend;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger._protocol.Message;
import messenger._protocol.Port;
import messenger._protocol.Server;
import messenger.client.view.ClientFrame;

/**********************************************************************
 * [Ŭ���̾�Ʈ]ģ�������ϱ�
 * ��� : ������ ģ�� ���̵�� �� ȸ����ȣ ����. ������ Ŭ���̾�Ʈ���� �˻� ����� ����Ʈ�� ��Ƽ� ����. 
 * 
 * @author1 ������ [18/06/28]
 * [�ó�����]
 * 1. ģ������Ʈ���� ���콺�˾�â���� ģ�������ϱ� �޴��� �����Ѵ�.
 * 2. ������ ģ�����̵� �� ���� ȸ����ȣ�� �Բ� Message<MemberVO>�� ��� ������.
 * 2. �����κ��� ���� ������ ���Ͽ� �Ʒ�3,4���� ���� �����̴� �޼ҵ带 �ۼ��Ѵ�
 * 3. Message<MemberVO>�� ������, ������ �Ϸ�Ǿ��ٴ� �˸�â�� ��� ��
 * 		ģ������� �ٽ� ��ȸ�Ͽ� ����Ʈ�� �����Ѵ�.(ClientFriendList.java ����)
 * 4. ���� ���, ������ �����ߴٴ� �ȳ����Բ� �ݺ��� ��� �����ڿ��� �����ش޶�� �ȳ��� �Ѵ�.
 * 
 * cf. UI���� �� �����κ��� ���� ���� �ø��� ���� �����ϵ��� �� ����(06/29 ������Ʈ�ð� ��...�������)
 * 
 * @Author2 ������ [18/07/02]
 * [��������]
 * 1. ģ���߰� �� ���� ���ν����� input���� ����ȸ�����̵�� ģ��ȸ�����̵� �̹Ƿ�,
 * 	userNo(int)�� userId(String)���� ������ ( �����׽�Ʈ �Ϸ� ) 
 * 
 **********************************************************************/
public class ClientFriendDelete {

	/*�����*/
	int userNo = 0;				 //���� ȸ����ȣ
	String userId	= null;		 //���ξ��̵�
	String friendId = null;		 //UI���� ��ȸ�� �˻������ �߰��ϰ����ϴ� ģ�����̵�
	ClientFrame frame = null;	 //ȭ�鿡 ��� f_Panel ��������
	
	Message<MemberVO> mms = null;//Client-Server�� �ְ���� �޼�����
	List<MemberVO> mli = null;	 //�޽����� ��� �ڷᱸ�� List
	MemberVO mvo = null;		 //List�� ����� Ŭ�����ڷ� MemberVO
	MemberVO mvo_f = null;		 //ģ����
	
	/*������*/
	//����Ʈ ������
	public ClientFriendDelete() {}
	
	//userNo,friendId �������� �ʱ�ȭ
	public ClientFriendDelete(String userId, String friendId) {
		this.userId   = userId;
		this.friendId = friendId;
	}
	
	//userNo,friendId �������� �ʱ�ȭ
	//ȭ�鿡 ��� f_Panel �������� �ʱ�ȭ �߰�
	public ClientFriendDelete(String userId, String friendId,ClientFrame frame) {
		this.userId   = userId;
		this.friendId = friendId;
		this.frame = frame;
	}
	
	/*��������Ǹ޼ҵ�*/
	//����ȸ����ȣ�� ģ�����̵� ������ �����ϱ�
	public void getFriendDelete() {
		//������ ���� �޼���&�ڷᱸ��&Ŭ�����ڷ� ����
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		mvo_f = new MemberVO();
		
		//���
		mvo.setMem_id(userId);//���ξ��̵����
		mvo_f.setMem_id(friendId);//ģ�����̵����
		mli.add(mvo);//ȸ����ȣ�� ArrayList�� ����
		mli.add(mvo_f);//ģ�����̵�ArrayList�� ����
		mms.setRequest(mli);//���� �����͸� �޽����� ����
		mms.setType(Message.FRIEND_DELETE);//�� �޽����� �������� ����
		
		//������ �����ϰ�, ������ ���� ���� ���ϱ� â�� ����
		//���ϱ�
		try(
			Socket socket = new Socket(Server.IP, Port.MEMBER);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		) {
			oos.writeObject(mms);
			//���
			try (
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			){
				//���� ������ Ȯ���ϰ� �޽����������ݷ� ��Ƴ�
				mms = (Message<MemberVO>)ois.readObject();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		//�޽����������ݿ� ����ִ� �����ͺκ��� ��� (ListŸ��)
		List<MemberVO> res = mms.getResponse();
		setFriendDelete(res);
		
	}
	
	//�����κ��� ���� �޽����� ���� �Ǵ��ϱ�
	public void setFriendDelete(List<MemberVO> res) {
		System.out.println(res.size());//�׽�Ʈ�� ��¹�
		
	/*	�����κ��� ���� �޽����� ù��° �ڸ��� ����ִ� ����
		INSERT���ν����� ���� ����� 1��0�� ���� �������� ����(response)�� ��������Ƿ�
		�� ���� ��������� 0, ������������� 1�� �Ͽ� �Ǵ��Ѵ�. */
		
		switch(res.get(0)==null?0:1) {
			case 0://����־��
			/*	UI���� �ȳ��� �˾�â�� ���ϴ�.(�� UI�� mainmenuâ)
				"ģ�������� �����߽��ϴ�. ���� ������ �ݺ��� ��� �����ڿ��� �������ּ���."*/
				System.out.println("List<MemberVO> res �� ����ֽ��ϴ�.");//�׽�Ʈ�� ��¹�
				//Insert Here
				break;
				
			case 1:
			/*	UI���� �ȳ��� �˾�â�� ���ϴ�.(�� UI�� mainmenuâ)
				"ģ�������� �����߽��ϴ�. ����� �����մϴ�."
				��� �˾�â�� �Բ� ģ������� �����Ѵ�*/
				//��������.. ���ŵ� ��Ͽ��� ��� �߰��� ȸ���� ���õǵ��� Ŀ��?�� ��ġ�Ͽ� �����ִ°� ���?
				//Insert Here..
				System.out.println("List<MemberVO> res �� ���ִ°Ű�����...");//�׽�Ʈ�� ��¹�
				System.out.println(res);//�׽�Ʈ�� ��¹�
				
				frame.getClientData().actionFriendList(); //ȭ���� ��������.
				break;
		}//end of switch
	}//////end of setAddFriend()
}//////////end of class
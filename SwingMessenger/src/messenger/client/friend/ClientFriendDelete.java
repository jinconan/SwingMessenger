package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

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
 **********************************************************************/
public class ClientFriendDelete {

	/*�����*/
	int userNo = 0;				 //
	String friendId = null;		 //UI���� ��ȸ�� �˻������ �߰��ϰ����ϴ� ģ�����̵�
	Message<MemberVO> mms = null;//Client-Server�� �ְ���� �޼�����
	List<MemberVO> mli = null;	 //�޽����� ��� �ڷᱸ�� List
	MemberVO mvo = null;		 //List�� ����� Ŭ�����ڷ� MemberVO
	ClientFriend cf = null;		 //ģ������ �۾��� ������ Thread�� ��ġ�� Ŭ����
	
	Vector<MemberVO> vec = null;;//�����κ��� ���� �޽����� ������� ���� ����
	/*������*/
	//����Ʈ ������
	public ClientFriendDelete() {}
	//userNo,friendId �������� �ʱ�ȭ
	public ClientFriendDelete(int userNo, String friendId) {
		this.userNo   = userNo;
		this.friendId = friendId;
	}
	
	/*��������Ǹ޼ҵ�*/
	//����ȸ����ȣ�� ģ�����̵� ������ �����ϱ�
	public void getFriendDelete() {
		//������ ���� �޼���&�ڷᱸ��&Ŭ�����ڷ� ����
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		
		//���
		mvo.setMem_no(userNo);
		mvo.setMem_id(friendId);
		mli.add(mvo);
		mms.setRequest(mli);//���� �����͸� �޽����� ����
		mms.setType(Message.FRIEND_DELETE);//�� �޽����� �������� ����
		
		//ThreadŬ������ ������ ����
		cf = new ClientFriend(mms);//�޽����� �Ѱܼ� start()ȣ��
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
				//Insert Here
				break;
				
			case 1:
			/*	UI���� �ȳ��� �˾�â�� ���ϴ�.(�� UI�� mainmenuâ)
				"ģ�������� �����߽��ϴ�. ����� �����մϴ�."
				��� �˾�â�� �Բ� ģ������� �����Ѵ�*/
				//��������.. ���ŵ� ��Ͽ��� ��� �߰��� ȸ���� ���õǵ��� Ŀ��?�� ��ġ�Ͽ� �����ִ°� ���?
				//Insert Here..
				
				renewFriendList();//ȭ���� ��������
				break;
		}//end of switch
	}//////end of setAddFriend()
	
	//ģ���߰� �Ϸ��� ����Ʈ�� �������ִ� �޼ҵ�
	public void renewFriendList() {
		//ȸ������Ʈ��ü�� �ٽð˻��Ѵ�
		ClientFriendList cfl = new ClientFriendList(userNo);
		cfl.getFriendList();
	}//////end of renewFriendList()
}//////////end of class
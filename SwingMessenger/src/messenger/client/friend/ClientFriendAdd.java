package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

/**********************************************************************
 * [Ŭ���̾�Ʈ]ģ���߰��ϱ�
 * ��� : �߰��� ģ�� ���̵�� �� ȸ����ȣ ����. ������ Ŭ���̾�Ʈ���� �˻� ����� ����Ʈ�� ��Ƽ� ����. 
 * 
 * @author1 ������ [18/06/28]
 * [�ó�����]
 * 1. �˻��Ǿ� ��ȸ�� ���̵� �� ���� ȸ����ȣ�� �Բ� Message<MemberVO>�� ��� ������.
 * 2. �����κ��� ���� ������ ���Ͽ� �Ʒ�3,4���� ���� �����̴� �޼ҵ带 �ۼ��Ѵ�
 * 3. Message<MemberVO>�� ������, �߰��� �Ϸ�Ǿ��ٴ� �˸�â�� ��� ��, �˾�â�� �ݰ�,
 * 		ģ������� �ٽ� ��ȸ�Ͽ� ����Ʈ�� �����Ѵ�.(ClientFriendList.java ����)
 * 4. ���� ���, �߰��� �����ߴٴ� �ȳ����Բ� �ݺ��� ��� �����ڿ��� �����ش޶�� �ȳ��� �Ѵ�.
 * 
 * cf. UI���� �� �����κ��� ���� ���� �ø��� ���� �����ϵ��� �� ����(06/29 ������Ʈ�ð� ��)
 * 
 **********************************************************************/
public class ClientFriendAdd {

	/*�����*/
	int userNo = 0;				 //
	String friendId = null;		 //UI���� ��ȸ�� �˻������ �߰��ϰ����ϴ� ģ�����̵�
	Message<MemberVO> mms = null;//Client-Server�� �ְ���� �޼�����
	List<MemberVO> mli = null;	 //�޽����� ��� �ڷᱸ�� List
	List<MemberVO> mli_f = null; //ģ��
	MemberVO mvo = null;		 //List�� ����� Ŭ�����ڷ� MemberVO
	MemberVO mvo_f = null;		 //ģ��
	ClientFriend cf = null;		 //ģ������ �۾��� ������ Thread�� ��ġ�� Ŭ����
	
	Vector<MemberVO> vec = null;;//�����κ��� ���� �޽����� ������� ���� ����
	/*������*/
	//����Ʈ ������
	public ClientFriendAdd() {}
	//userNo,friendId �������� �ʱ�ȭ
	public ClientFriendAdd(int userNo, String friendId) {
		this.userNo   = userNo;
		this.friendId = friendId;
	}
	
	/*��������Ǹ޼ҵ�*/
	//����ȸ����ȣ�� ģ�����̵� ������ �����ϱ�
	public void getAddFriend() {
		//������ ���� �޼���&�ڷᱸ��&Ŭ�����ڷ� ����
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mli_f = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		mvo_f = new MemberVO();
		
		//���
		mvo.setMem_no(userNo);
		mvo_f.setMem_id(friendId);
		mli.add(mvo);
		mli_f.add(mvo_f);
		mms.setRequest(mli);//���� �����͸� �޽����� ����
		mms.setRequest(mli_f);//���� �����͸� �޽����� ����
		mms.setType(Message.FRIEND_INSERT);//�� �޽����� �������� ����
		
		//ThreadŬ������ ������ ����
		cf = new ClientFriend(mms);//�޽����� �Ѱܼ� start()ȣ��
	}
	
	//�����κ��� ���� �޽����� ���� �Ǵ��ϱ�
	public void setFriendAdd(List<MemberVO> res) {
		System.out.println(res.size());//�׽�Ʈ�� ��¹�
		
	/*	�����κ��� ���� �޽����� ù��° �ڸ��� ����ִ� ����
		INSERT���ν����� ���� ����� 1��0�� ���� �������� ����(response)�� ��������Ƿ�
		�� ���� ��������� 0, ������������� 1�� �Ͽ� �Ǵ��Ѵ�. */
		switch(res.get(0)==null?0:1) {
			case 0://����־��
			/*	UI���� �ȳ��� �˾�â�� ���ϴ�.(�� UI��submenuâ)
				"ģ���߰��� �����߽��ϴ�. ���� ������ �ݺ��� ��� �����ڿ��� �������ּ���."*/
				//Insert Here
				break;
				
			case 1:
			/*	UI���� �ȳ��� �˾�â�� ���ϴ�.(�� UI��submenuâ)
				"ģ���߰��� �����߽��ϴ�. ����� �����մϴ�."
				��� �˾�â�� �Բ� SubMenuâ�� �ݰ� ģ������� �����Ѵ�*/
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
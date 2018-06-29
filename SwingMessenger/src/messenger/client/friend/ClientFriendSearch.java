package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

/**********************************************************************
 * [Ŭ���̾�Ʈ]ģ���˻��ϱ�
 * ��� : �߰��� ģ�� ���̵� ����. ������ Ŭ���̾�Ʈ���� �˻� ����� ����Ʈ�� ��Ƽ� ����. 
 * 
 * @author1 ������ [18/06/27]
 * [�ó�����]
 * 1. ui���� �˻��ϰ����ϴ� ���̵� �Ķ���ͷ� ��, Message<MemberVO>�� ��� ������.
 * 2. �����κ��� ���� ������ ���Ͽ� �Ʒ�3,4���� ���� �����̴� �޼ҵ带 �ۼ��Ѵ�
 * 3. Message<MemberVO>�� ������, �ش� ������� ������ ȭ�鿡 ����Ѵ�.
 * 4. �������, ǥ���� ������ ����, ���̵� �ٽ� Ȯ���ش޶�� �˸����� ����Ѵ�.
 * 
 * Q. �� �ڷḦ ������� ģ���߰��� ã���Ŀ� �ٷ� �Բ� �̷������..? UMLȮ���ʿ�..
 * 
 **********************************************************************/
public class ClientFriendSearch{

	/*�����*/
	String userId = null;		 //UI�κ��͹��� 'ã�����ϴ� ģ���� ���̵�'�� ���� ����
	Message<MemberVO> mms = null;//Client-Server�� �ְ���� �޼�����
	List<MemberVO> mli = null;	 //�޽����� ��� �ڷᱸ�� List
	MemberVO mvo = null;		 //List�� ����� Ŭ�����ڷ� MemberVO
	ClientFriend cf = null;		 //ģ������ �۾��� ������ Thread�� ��ġ�� Ŭ����
	
	/*������*/
	//����Ʈ ������
	public ClientFriendSearch() {}
	
	//userId �������� �ʱ�ȭ /������
	//����ڰ� ã�����ϴ� ���̵��� �Է°��� �Ķ���ͷ� ����
	public ClientFriendSearch(String userId) {
		this.userId = userId;
	}
	
	/*��������Ǹ޼ҵ�*/
	//ã�����ϴ� ȸ���� ���̵� ������ �����ϱ�
	public void getFriendData() {//UI���� ȣ���Ҷ� ����� �޼ҵ�
		//������ ���� �޼���&�ڷᱸ��&Ŭ�����ڷ� ����
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		
		//���
		mvo.setMem_id(userId);
		mli.add(mvo);
		mms.setRequest(mli);//���Ϸ�
		mms.setType(Message.FRIEND_SEARCH);//�� �޽����� �������� ����
		
		//ThreadŬ������ ������ ����
		cf = new ClientFriend(mms);
	}

	public void setFriendSearch(List<MemberVO> res) {
		System.out.println(res);//�׽�Ʈ�� ��¹�
		
		//List�� ��� MemberVO�� �����͸� ȭ�鿡 ����ϱ�
		
	}

	
}

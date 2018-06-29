package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

/**********************************************************************
 * [Ŭ���̾�Ʈ]ģ���˻��ϱ�
 * ��� : �˻��� ģ�� ���̵� ����. ������ Ŭ���̾�Ʈ���� �˻� ����� ����Ʈ�� ��Ƽ� ����. 
 * 
 * @author1 ������ [18/06/27]
 * [�ó�����]
 * 1. ui���� �˻��ϰ����ϴ� ���̵� �Ķ���ͷ� ��, Message<MemberVO>�� ��� ������.
 * 2. �����κ��� ���� ������ ���Ͽ� �Ʒ�3,4���� ���� �����̴� �޼ҵ带 �ۼ��Ѵ�
 * 3. Message<MemberVO>�� ������, �ش� ������� ������ ȭ�鿡 ����Ѵ�.
 * 4. ���� ���, ǥ���� ������ ����, ���̵� �ٽ� Ȯ���ش޶�� �˸����� ����Ѵ�.
 * 
 * @author2 ������ [18/06/28]
 * Q. �� �ڷḦ ������� ģ���߰��� ã���Ŀ� �ٷ� �Բ� �̷������..? UMLȮ���ʿ�..
 * A. UI����ڿ� ������ Ȯ�� �� ��Ī���Ѽ� �Բ� ó���ϱ�� �� (6/29 ���࿹��)
 * 
 **********************************************************************/
public class ClientFriendSearch{

	/*�����*/
	String userId_f = null;		 //UI�κ��͹��� 'ã�����ϴ� ģ���� ���̵�'�� ���� ����
	Message<MemberVO> mms = null;//Client-Server�� �ְ���� �޼�����
	List<MemberVO> mli_f = null;	 //�޽����� ��� �ڷᱸ�� List
	MemberVO mvo_f = null;		 //List�� ����� Ŭ�����ڷ� MemberVO
	ClientFriend cf = null;		 //ģ������ �۾��� ������ Thread�� ��ġ�� Ŭ����
	
	Vector<MemberVO> vec = null; //�����κ��� ���� �޽����� ������� ���� ����
	
	/*������*/
	//����Ʈ ������
	public ClientFriendSearch() {}
	
	//userId �������� �ʱ�ȭ /������
	//����ڰ� ã�����ϴ� ���̵��� �Է°��� �Ķ���ͷ� ����
	public ClientFriendSearch(String userId_f) {
		this.userId_f = userId_f;
	}
	
	/*��������Ǹ޼ҵ�*/
	//ã�����ϴ� ȸ���� ���̵� ������ �����ϱ�
	public void getFriendSearch() {//UI���� ȣ���Ҷ� ����� �޼ҵ�
		//������ ���� �޼���&�ڷᱸ��&Ŭ�����ڷ� ����
		mms = new Message<MemberVO>();
		mli_f = new ArrayList<MemberVO>();
		mvo_f = new MemberVO();
		
		//���
		mvo_f.setMem_id(userId_f);
		mli_f.add(mvo_f);
		mms.setRequest(mli_f);//���Ϸ�
		mms.setType(Message.FRIEND_SEARCH);//�� �޽����� �������� ����
		
		//ThreadŬ������ ������ ����
		cf = new ClientFriend(mms);
	}

	public void setFriendSearch(List<MemberVO> res) {
		System.out.println(res);//�׽�Ʈ�� ��¹�
		
		//List�� ��� MemberVO�� �����͸� dtm�� ���
		vec = new Vector<MemberVO>();
		for(int i=0;i<res.size();i++) {
			vec.add(res.get(i));//����� List�ڷ�� ȸ��1�� ���� �ο�(row)�ڷᰡ ������� ���������. �׷��Ƿ� ���Ϳ� �ٷ� ����
		
			//Insert Here-ģ��ã�� UI�� �ڷ���(UI���� �� �ϼ��� ����)
		}
	}

	
}

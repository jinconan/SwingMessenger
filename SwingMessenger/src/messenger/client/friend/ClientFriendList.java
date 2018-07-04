package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger._protocol.Message;
import messenger.client.view.ClientFrame;

/**********************************************************************
 * [Ŭ���̾�Ʈ]ģ������Ʈ �ҷ�����
 * ��� : ����ȸ����ȣ�� ��Ƽ� ������ ����. �����κ��� MemberVO�� ����Ʈ�� ���� �� ģ����� �����ϱ�
 * @author1 ������...18/06/26
 * [�ó�����]
 * 1. ����ȸ������(userNo)�� �ʱ�ȭ�ϱ�
 * 2. ���ϻ����� ������ ģ������� ��û�ϵ��� �޼ҵ�� �����Ű��(���ŵǰ��� �ݱ�)
 * 3. �������� �Ѿ�� ģ������� �޴� �޼ҵ带 �����Ű��(���ѹݺ����� ����α�)
 * 4. 2���� 3���� ���������� �����ϴ� �޼ҵ带 ����, UI���� ȣ���ϸ� �ٷ� �۵��ϵ��� �ϱ�
 * 
 * @author2 ������...18/06/27
 * [üũ����]
 * 1. oos�� ������ ���� write�� �����ϰ�, ois�� ������ ��
 * 2. ������� ȸ����ȣ�� �ѱ涧, userNo�� �ٷγѱ�°��� �ƴ϶�,
 *    MemberVO�� MessageŬ����, ArrayList�� �̿��� ��
 * 3. ������ ��û�ϴ� MemberVO�� request
 * 4. ������ ���� �޾ƿ� MemberVO�� response : �� ������ ��ü�Ͽ� UI�� �ø���
 * 5. Table���·� �÷��� �����ϱ� (JTable)
 * 
 * @author3 ������...18/06/27
 * [üũ����]
 * 1. �������� �ۼ��� Ȯ�οϷ� : VectorŸ�Կ� ������� �ʿ���� (�ּ�Ȯ��)
 * 
 * @author4 ������...18/07/02
 * [üũ����]
 * 1. ���������� �����ͼۼ��� �׽�Ʈ�Ϸ� : ģ����� ��ü��ȸ/�˻�/�߰�/����
 * 2. �� �ܿ��� ����� �� �ֵ���, test����ó�� �����غ���
 *
 **********************************************************************/
public class ClientFriendList extends Thread {

	int userNo	= 0;
	ClientFrame frame = null;  //ȭ�鿡 ��� f_Panel ��������
	
	Message<MemberVO> mms = null;//Client-Server�� �ְ���� �޼�����
	List<MemberVO> mli = null;	 //�޽����� ��� �ڷᱸ�� List
	MemberVO mvo = null;		 //List�� ����� Ŭ�����ڷ� MemberVO
	ClientFriend cf = null;		 //ģ������ �۾��� ������ Thread�� ��ġ�� Ŭ����
	
	Vector<MemberVO> vec = null; //�����κ��� ���� �޽����� ������� ���� ����

	//intŸ�� �����ڰ� �־ ������� ����Ʈ ������
	public ClientFriendList() {}
	
	//userNo �������� �ʱ�ȭ
	public ClientFriendList(int userNo) {
		this.userNo = userNo;
	}
	
	//userNo �������� �ʱ�ȭ
	//ȭ�鿡 ��� f_Panel �������� �ʱ�ȭ �߰�
	public ClientFriendList(int userNo,ClientFrame frame) {
		this.userNo = userNo;
		this.frame = frame;
	}

	//����ȸ����ȣ�� ������ �����ϱ�
	public void getFriendList() {//UI���� ȣ���Ҷ� ����� �޼ҵ�
		mms = new Message<MemberVO>();
		mli = new ArrayList<MemberVO>();
		mvo = new MemberVO();
		
		mvo.setMem_no(userNo);
		
		mli.add(mvo);
		mms.setRequest(mli);//���� �����͸� �޽����� ����
		mms.setType(Message.FRIEND_ALL);//Ÿ�Լ��� -18.06.27 ���� �����
		cf = new ClientFriend(mms, this);//�޽����� �Ѱܼ� start()ȣ��
	}
	
	//���� ���� ������ UI�� ����(run�޼ҵ忡�� ȣ��)
	public void setFriendList(List<MemberVO> res) {
		frame.refreshFriendTable((ArrayList<MemberVO>) res);
	}//////////////end of setFriendList
		
}//////////////////end of class

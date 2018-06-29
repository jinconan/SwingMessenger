package messenger.client.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

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
 * 2. 
 **********************************************************************/
public class ClientFriendList extends Thread {

	int userNo	= 0;
	ClientFriend cf = null;
	Message<MemberVO> mms = null;
	List<MemberVO> mli = null;
	MemberVO mvo = null;
	Vector<MemberVO> vec = null;

	//intŸ�� �����ڰ� �־ ������� ����Ʈ ������
	public ClientFriendList() {}
	
	//userNo �������� �ʱ�ȭ
	public ClientFriendList(int userNo) {
		this.userNo = userNo;
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
		cf = new ClientFriend(mms);//�޽����� �Ѱܼ� start()ȣ��
	}
	
	//���� ���� ������ UI�� ����(run�޼ҵ忡�� ȣ��)
	public void setFriendList(List<MemberVO> res) {
		//System.out.println(res);//�׽�Ʈ�� ��¹�
		
		//List�� ��� MemberVO�� �����͸� dtm�� ���
		vec = new Vector<MemberVO>();;
		for(int i=0;i<res.size();i++) {
		/*	//���� �̷��� ��� ���Ϳ� �������� �ʿ䰡 ����..
			//�ֳ��ϸ�, �̹� �����κ��� �ϳ��� ������ ����������� �Ա⶧��
			vec.add(res.get(i).getMem_id());
			vec.add(res.get(i).getMem_name());
			vec.add(res.get(i).getMem_nick());
			vec.add(res.get(i).getMem_hp());
			vec.add(res.get(i).getMem_profile());
			vec.add(res.get(i).getMem_background());*/
			
			vec.add(res.get(i));
			//System.out.println(vec.get(i).getMem_id());//�׽�Ʈ�� ��¹�
			
			//Insert Here - ģ������Ʈ �г�UI�� �ڷ���(UI���� �� �ϼ��� ����)
			//dtm.addRow(vec);
		
		}//////////end of for
		
	}//////////////end of setFriendList
		
}//////////////////end of class

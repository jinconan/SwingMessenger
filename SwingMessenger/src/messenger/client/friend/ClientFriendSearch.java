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
import messenger.client.view.dialog.SearchDialog;

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
	ClientFrame frame = null;	 //[�ּ�ó��]ȭ�鿡 ��� f_Panel ��������
	SearchDialog searchDialog = null; //JDialog�� ó��
	
	
	Message<MemberVO> mms = null;//Client-Server�� �ְ���� �޼�����
	List<MemberVO> mli_f = null; //�޽����� ��� �ڷᱸ�� List
	MemberVO mvo_f = null;		 //List�� ����� Ŭ�����ڷ� MemberVO
	
	/*������*/
	//����Ʈ ������
	public ClientFriendSearch() {}
	
	//userId �������� �ʱ�ȭ : ����ڰ� ã�����ϴ� ���̵��� �Է°��� �Ķ���ͷ� ����
	public ClientFriendSearch(String userId_f) {
		this.userId_f = userId_f;
	}
	
	//userId �������� �ʱ�ȭ : ����ڰ� ã�����ϴ� ���̵��� �Է°��� �Ķ���ͷ� ����
	//ȭ�鿡 ��� f_Panel �������� �ʱ�ȭ �߰�
	public ClientFriendSearch(String userId_f,ClientFrame frame, SearchDialog searchDialog) {
		this.userId_f = userId_f;
		this.frame = frame;
		this.searchDialog = searchDialog;
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
		
//		//ThreadŬ������ ������ ����
//		cf = new ClientFriend(mms, this);
		
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
		setFriendSearch(res);
	}

	public void setFriendSearch(List<MemberVO> res) {
		searchDialog.setDialog(res);
	}

	
}

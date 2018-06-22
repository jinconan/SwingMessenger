package messenger.protocol;


import java.io.Serializable;
import java.util.List;

public class Message<T> implements Serializable{
	private static final long serialVersionUID = 8997353509224999302L;
	
	private int 	type;		//�޽��� Ÿ��
	private List<T> request;	//Ŭ���̾�Ʈ�� �����͸� ��� �κ�
	private List<T> response;	//������ �����͸� ��� �κ�

	///////////////////////�޽��� Ÿ��////////////////////////////
	public static final int MEMBER_LOGIN	= 0; //ȸ�� �α���
	public static final int MEMBER_JOIN		= 1; //ȸ�� ����
	public static final int MEMBER_MODIFY 	= 2; //ȸ�� ����
	public static final int FRIEND_ALL		= 3; //ģ�� ��ü ����Ʈ
	public static final int FRIEND_INSERT	= 4; //ģ�� �߰�
	public static final int FRIEND_DELETE	= 5; //ģ�� ����
	public static final int FRIEND_SEARCH	= 6; //ģ�� �˻�
	public static final int CHAT_SEND		= 7; //ä�� ����
	public static final int CHATROOM_LOAD	= 8; //ä�ù� ����Ʈ ��ȸ
	public static final int EMOTICON_LOAD	= 9; //�������� �̸�Ƽ�� ����Ʈ ��û (T���� ImageIcon�迭�� ��)
	public static final int CHATROOM_EXIT	= 10;//ä�ù� ������
	//�� ����������?
	///////////////////////////////////////////////////////////
	
	public Message() {
		this(0,null,null);
	}
	
	public Message(int type, List<T> request, List<T> response) {
		this.type = type;
		this.request = request;
		this.response = response;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<T> getRequest() {
		return request;
	}

	public void setRequest(List<T> request) {
		this.request = request;
	}

	public List<T> getResponse() {
		return response;
	}

	public void setResponse(List<T> response) {
		this.response = response;
	}
	
	
}

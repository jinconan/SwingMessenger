package messenger.client.friend;

public class test {

	public static void main(String[] args) {
		int	   testUserNo	= 25;
		String testFriendId = "weback";
		
		//ģ����� ��ü��ȸ �׽�Ʈ�Ϸ�
		//ClientFriendList cfl = new ClientFriendList(testUserNo);
		//cfl.getFriendList();
		
		//ģ���Ѹ� ��ȸ�Ϸ�
		//ClientFriendSearch cfc = new ClientFriendSearch(testFriendId);
		//cfc.getFriendSearch();
		
		//ģ���Ѹ� �߰��׽�Ʈ
		//�����߻� java.io.EOFException
		ClientFriendAdd cfa = new ClientFriendAdd(testUserNo,testFriendId);
		cfa.getAddFriend();
		
		//�߰��׽�Ʈ �� ���డ��
		//ClientFriendDelete cfd = new ClientFriendDelete(testUserNo,testFriendId);
		//cfd.getFriendDelete();
	}
}

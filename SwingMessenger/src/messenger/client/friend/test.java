package messenger.client.friend;

public class test {

	public static void main(String[] args) {
		int	   testUserNo	= 25;
		String testFriendId = "weback";
		
		//친구목록 전체조회 테스트완료
		//ClientFriendList cfl = new ClientFriendList(testUserNo);
		//cfl.getFriendList();
		
		//친구한명 조회완료
		//ClientFriendSearch cfc = new ClientFriendSearch(testFriendId);
		//cfc.getFriendSearch();
		
		//친구한명 추가테스트
		//에러발생 java.io.EOFException
		ClientFriendAdd cfa = new ClientFriendAdd(testUserNo,testFriendId);
		cfa.getAddFriend();
		
		//추가테스트 후 실행가능
		//ClientFriendDelete cfd = new ClientFriendDelete(testUserNo,testFriendId);
		//cfd.getFriendDelete();
	}
}

package SemiProjectChat;

public class FriendDTO {

	
	String FRIEND_NAME     =  null;
	String FRIEND_HIREDATE =  null;
	int    FRIEND       =  0;
	int    MEM_NO          =  0;
	
	
	public final String getFRIEND_NAME() {
		return FRIEND_NAME;
	}
	public final void setFRIEND_NAME(String fRIEND_NAME) {
		this.FRIEND_NAME = fRIEND_NAME;
	}
	public final String getFRIEND_HIREDATE() {
		return FRIEND_HIREDATE;
	}
	public final void setFRIEND_HIREDATE(String fRIEND_HIREDATE) {
		this.FRIEND_HIREDATE = fRIEND_HIREDATE;
	}
	public final int getFRIEND() {
		return FRIEND;
	}
	public final void setFRIEND(int fRIEND) {
		this.FRIEND = fRIEND;
	}
	public final int getMEM_NO() {
		return MEM_NO;
	}
	public final void setMEM_NO(int mEM_NO) {
		this.MEM_NO = mEM_NO;
	}
	
}

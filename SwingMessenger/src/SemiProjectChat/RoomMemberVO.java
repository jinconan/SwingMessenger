package SemiProjectChat;

import java.io.Serializable;

public class RoomMemberVO implements Serializable{
	private static final long serialVersionUID = 998874286030264633L;
	
	private int room_seq;
	private int room_no;
	private int mem_no;
	
	public RoomMemberVO() {}

	public RoomMemberVO(int room_seq, int room_no, int mem_no) {
		super();
		this.room_seq = room_seq;
		this.room_no = room_no;
		this.mem_no = mem_no;
	}

	public int getRoom_seq() {
		return room_seq;
	}

	public void setRoom_seq(int room_seq) {
		this.room_seq = room_seq;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public int getMem_no() {
		return mem_no;
	}

	public void setMem_no(int mem_no) {
		this.mem_no = mem_no;
	}
	
	
}

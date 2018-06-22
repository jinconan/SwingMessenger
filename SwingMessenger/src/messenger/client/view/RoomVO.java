package messenger.client.view;

public class RoomVO {
	private int room_no;
	private String room_starttime;
	
	public RoomVO() {}

	public RoomVO(int room_no, String room_starttime) {
		super();
		this.room_no = room_no;
		this.room_starttime = room_starttime;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public String getRoom_starttime() {
		return room_starttime;
	}

	public void setRoom_starttime(String room_starttime) {
		this.room_starttime = room_starttime;
	}
	
	
	
	
}

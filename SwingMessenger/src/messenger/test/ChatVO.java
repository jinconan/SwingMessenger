package messenger.test;

import java.io.Serializable;

import messenger._db.vo.MemberVO;

public class ChatVO implements Serializable {
	private static final long serialVersionUID = 484396718324553957L;
	
	private int chat_no; // 대화번호
	private int room_no; // 방번호
	private String chat_content; // 대화내용
	private String chat_time; // 대화전달시간
	private MemberVO memVO;
	
	public ChatVO() {
	}

	public ChatVO(int chat_no, int room_no, String chat_content, String chat_time, MemberVO memVO) {
		this.chat_no = chat_no;
		this.room_no = room_no;
		this.chat_content = chat_content;
		this.chat_time = chat_time;
		this.memVO = memVO;
	}

	public int getChat_no() {
		return chat_no;
	}

	public void setChat_no(int chat_no) {
		this.chat_no = chat_no;
	}

	public int getRoom_no() {
		return room_no;
	}

	public void setRoom_no(int room_no) {
		this.room_no = room_no;
	}

	public String getChat_content() {
		return chat_content;
	}

	public void setChat_content(String chat_content) {
		this.chat_content = chat_content;
	}

	public String getChat_time() {
		return chat_time;
	}

	public void setChat_time(String chat_time) {
		this.chat_time = chat_time;
	}

	public MemberVO getMemVO() {
		return memVO;
	}

	public void setMemVO(MemberVO memVO) {
		this.memVO = memVO;
	}

	
	

}

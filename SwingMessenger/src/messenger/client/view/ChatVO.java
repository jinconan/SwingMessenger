package messenger.client.view;

import java.io.Serializable;

public class ChatVO implements Serializable{
	 private int chat_no; //��ȭ��ȣ                                                      
	 private int room_no; //���ȣ                                                   
	 private String chat_content; //��ȭ����                                         
	 private String chat_time; //��ȭ���޽ð�
	 
	 public ChatVO() {}

	public ChatVO(int chat_no, int room_no, String chat_content, String chat_time) {
		this.chat_no = chat_no;
		this.room_no = room_no;
		this.chat_content = chat_content;
		this.chat_time = chat_time;
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
	 
	 
}

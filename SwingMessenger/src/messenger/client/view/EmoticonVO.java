package messenger.client.view;

public class EmoticonVO {
	private int emo_no;
	private String emo_url;
	private String emo_name;
	
	public EmoticonVO() {}

	public EmoticonVO(int emo_no, String emo_url, String emo_name) {
		this.emo_no = emo_no;
		this.emo_url = emo_url;
		this.emo_name = emo_name;
	}

	public int getEmo_no() {
		return emo_no;
	}

	public void setEmo_no(int emo_no) {
		this.emo_no = emo_no;
	}

	public String getEmo_url() {
		return emo_url;
	}

	public void setEmo_url(String emo_url) {
		this.emo_url = emo_url;
	}

	public String getEmo_name() {
		return emo_name;
	}

	public void setEmo_name(String emo_name) {
		this.emo_name = emo_name;
	}
	
	
}

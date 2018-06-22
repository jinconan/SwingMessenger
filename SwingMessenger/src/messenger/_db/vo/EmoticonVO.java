package messenger._db.vo;

import java.io.Serializable;

public class EmoticonVO implements Serializable{
	private static final long serialVersionUID = 8364571906757335702L;
	
	private int emo_no; //이모티콘 번호
	private String emo_url; //이모티콘 경로
	private String emo_name; //이모티콘 이름
	
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

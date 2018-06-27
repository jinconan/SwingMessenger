package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

public class MemberUpdate<T> {
	
	MemberVO mvo = new MemberVO();
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	SubMenu sm = new SubMenu();
	MemberVO mem = new MemberVO();
	Message<T> info = null;
	
	public void Certified() {
		try {
			ois.readObject();
			if(sm.jtf_upw.getText() == mem.getMem_pw()) {
				sm.UpdateInfo();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	@SuppressWarnings("unchecked")
	public void Update() {
		//String info = null;
		Client client = new Client();
		try {
			client.oos.writeObject(mem.getMem_id());
			mem.setMem_pw(sm.jtf_upw.getText());
			mem.setMem_name(sm.jtf_uname.getText());
			//mem.setMem_gender(sm.jtf_ugender);
			mem.setMem_hp(sm.jtf_uhp.getText());
			info.setType(2);
			List<MemberVO> Lmem = new ArrayList<MemberVO>();
			info.setResponse((List<T>) Lmem);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
}

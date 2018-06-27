package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

public class Jungbok<T> {
	MemberVO mvo = new MemberVO();
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	Login log = new Login();
	MemberVO mem = new MemberVO();
	Message<T> info = null;
	
	public void Gumsa() {
		try {
			ois.readObject();
			if(log.jtf_gid.getText() == mem.getMem_id()) {
				JOptionPane.showInputDialog("중복된 아이디입니다.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}

package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;

public class MemberUpdate<T> {
	Socket socket = new Socket();
	MemberVO mvo = new MemberVO();
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	SubMenu sm = new SubMenu();
	MemberVO mem = new MemberVO();

	public void Certified() {
		try {
			socket = new Socket("",);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(sm.jtf_upw.getText());
			ois = new ObjectInputStream(socket.getInputStream());
			if(sm.jtf_upw.getText() == ois.readObject()) {
				sm.UpdateInfo();
			}else {
				JOptionPane.showMessageDialog(sm.jtf_upw, "잘못된 비밀번호입니다.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}///////// end try catch
		
	}///////// end Certified()

	public void Update() {
		
		try {
			socket = new Socket("",);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(mem);
			//MemberVO에 수정한 회원정보 담기
			mem.setMem_nick(sm.jtf_unick.getText());
			mem.setMem_pw(sm.jtf_upw.getText());
			mem.setMem_name(sm.jtf_uname.getText());
			mem.setMem_gender((String)sm.jtf_ugender.getSelectedItem());
			mem.setMem_hp(sm.jtf_uhp.getText());
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}///////// end try catch

	}///////// end Update()

}///////// end class

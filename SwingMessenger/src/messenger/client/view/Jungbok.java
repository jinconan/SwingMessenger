package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;


public class Jungbok {
	Socket socket = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	ClientFrame cf = null;

	int answer = 3;

	public Jungbok(ClientFrame clientFrame) {
		// TODO Auto-generated constructor stub
		this.cf = clientFrame;
		try {
			Socket socket = new Socket("192.168.0.24", 7777);
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject("중복검사 클라이언트");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public int Gumsa() {
		System.out.println(cf.jtf_gid.getText());
		if (cf.jtf_gid.getText().equals("") ) {
			JOptionPane.showMessageDialog(cf.jtf_gid, "아이디를 입력해주세요.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				 
				if (cf.jtf_gid.getText().equals("jin")) {
					JOptionPane.showMessageDialog(cf.jtf_gid, "이미 사용중인 아이디입니다.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					//JOptionPane.showConfirmDialog(cf.jtf_gid,"이 아이디를 사용하시겠습니까?","사용가능한 아이디입니다.",JOptionPane.YES_NO_OPTION);
					answer = JOptionPane.showConfirmDialog(cf.jtf_gid,"이 아이디를 사용하시겠습니까?","사용가능한 아이디입니다.",JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION) {
						answer = 0;
					}
					else if(answer == JOptionPane.NO_OPTION) {
						cf.jtf_gid.setText("");
					}
					System.out.println(answer);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}///////// end try catch 
			
		}///////// end if(ELSE)
		return answer;
	}//////// end Gumsa()

}/////// end class

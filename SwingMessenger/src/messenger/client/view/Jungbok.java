package messenger.client.view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import messenger._db.vo.MemberVO;

public class Jungbok {
	Socket socket = null;
	ObjectOutputStream oos = null;
	ObjectInputStream ois = null;
	ClientFrame cf = null;

	int answer = 3;

	public Jungbok(ClientFrame clientFrame) {
		// TODO Auto-generated constructor stub
		this.cf = clientFrame;
	}

	public int Gumsa() {
		System.out.println(cf.jtf_gid.getText());
		if (cf.jtf_gid.getText().equals("") ) {
			JOptionPane.showMessageDialog(cf.jtf_gid, "���̵� �Է����ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				 socket = new Socket("",);
				 oos = new ObjectOutputStream(socket.getOutputStream());
				 oos.writeObject(cf.jtf_gid.getText());
				 ois = new ObjectInputStream(socket.getInputStream());
				 if(cf.jtf_gid.getText() == ois.readObject()) {
				 JOptionPane.showInputDialog(cf.jtf_gid,"�̹� ������� ���̵��Դϴ�.","Error",JOptionPane.
				 ERROR_MESSAGE); 
				 } else {
						answer = JOptionPane.showConfirmDialog(cf.jtf_gid,"�� ���̵� ����Ͻðڽ��ϱ�?","��밡���� ���̵��Դϴ�.",JOptionPane.YES_NO_OPTION);
						if(answer == JOptionPane.YES_OPTION) {
							answer = 0;
						}
						else if(answer == JOptionPane.NO_OPTION) {
							cf.jtf_gid.setText("");
						}
						System.out.println(answer);
					}
				/*if (cf.jtf_gid.getText().equals("jin")) {
					JOptionPane.showMessageDialog(cf.jtf_gid, "�̹� ������� ���̵��Դϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
				} else {
					//JOptionPane.showConfirmDialog(cf.jtf_gid,"�� ���̵� ����Ͻðڽ��ϱ�?","��밡���� ���̵��Դϴ�.",JOptionPane.YES_NO_OPTION);
					answer = JOptionPane.showConfirmDialog(cf.jtf_gid,"�� ���̵� ����Ͻðڽ��ϱ�?","��밡���� ���̵��Դϴ�.",JOptionPane.YES_NO_OPTION);
					if(answer == JOptionPane.YES_OPTION) {
						answer = 0;
					}
					else if(answer == JOptionPane.NO_OPTION) {
						cf.jtf_gid.setText("");
					}
					System.out.println(answer);
				}*/
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}///////// end try catch 
			
		}///////// end if(ELSE)
		return answer;
	}//////// end Gumsa()

}/////// end class

package messenger.client.view.dialog;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SearchDialog extends JDialog implements ActionListener{
	JFrame clientFrame;
	JTextField  jtf_shfri = new JTextField();
	JButton 	jbtn_gum = new JButton("검색");
	
	public SearchDialog(JFrame clientFrame) {
		super(clientFrame, "친구 찾기", true);
		this.clientFrame = clientFrame;
		jbtn_gum.addActionListener(this);
		this.setLayout(null);
		this.setSize(300, 200);
		
		Font f = new Font("굴림", Font.CENTER_BASELINE, 10);
		jbtn_gum.setFont(f);
		jbtn_gum.setBounds(120, 100, 60, 30);

		this.add(jtf_shfri);
		this.add(jbtn_gum);
		this.setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		int answer = 0;
		
		if(e.getActionCommand().equals("검색")) {
			System.out.println("검색실행");
			try {
				// 검색 요청 클라이언트
				// 성공하면
				answer = JOptionPane.showConfirmDialog(this, jtf_shfri.getText() + "님을 추가하시겠습니까?", "친구추가",
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					try {
						// 친구 추가 클라이언트
						// 추가하면
						JOptionPane.showMessageDialog(this, jtf_shfri.getText() + "님이 추가되었습니다. \n 목록을 갱신합니다.",
								"친구추가", JOptionPane.OK_OPTION);
					} catch (Exception fe2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(this, "친구추가에 실패하였습니다.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					this.dispose();
				}
			} catch (Exception fe) {
				JOptionPane.showMessageDialog(this, jtf_shfri.getText() + "님을 찾을 수 없습니다.", "검색실패",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	

}

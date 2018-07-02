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
	JButton 	jbtn_gum = new JButton("�˻�");
	
	public SearchDialog(JFrame clientFrame) {
		super(clientFrame, "ģ�� ã��", true);
		this.clientFrame = clientFrame;
		jbtn_gum.addActionListener(this);
		this.setLayout(null);
		this.setSize(300, 200);
		
		Font f = new Font("����", Font.CENTER_BASELINE, 10);
		jbtn_gum.setFont(f);
		jbtn_gum.setBounds(120, 100, 60, 30);

		this.add(jtf_shfri);
		this.add(jbtn_gum);
		this.setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		int answer = 0;
		
		if(e.getActionCommand().equals("�˻�")) {
			System.out.println("�˻�����");
			try {
				// �˻� ��û Ŭ���̾�Ʈ
				// �����ϸ�
				answer = JOptionPane.showConfirmDialog(this, jtf_shfri.getText() + "���� �߰��Ͻðڽ��ϱ�?", "ģ���߰�",
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					try {
						// ģ�� �߰� Ŭ���̾�Ʈ
						// �߰��ϸ�
						JOptionPane.showMessageDialog(this, jtf_shfri.getText() + "���� �߰��Ǿ����ϴ�. \n ����� �����մϴ�.",
								"ģ���߰�", JOptionPane.OK_OPTION);
					} catch (Exception fe2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(this, "ģ���߰��� �����Ͽ����ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					this.dispose();
				}
			} catch (Exception fe) {
				JOptionPane.showMessageDialog(this, jtf_shfri.getText() + "���� ã�� �� �����ϴ�.", "�˻�����",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	

}

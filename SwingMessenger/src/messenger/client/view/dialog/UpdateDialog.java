package messenger.client.view.dialog;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateDialog extends JDialog implements ActionListener{
	JFrame clientFrame;
	JDialog				jd_upd;
	JPanel				jp_card = new JPanel();
	JPanel				jp_cert = new JPanel();
	JPanel				jp_upd = new JPanel();
	JLabel 				jl_unick = new JLabel("���¸޽��� :");
	JLabel 				jl_upw = new JLabel("��й�ȣ :");
	JLabel 				jl_uname = new JLabel("�̸� :");
	JLabel 				jl_ugender = new JLabel("���� :");
	JLabel 				jl_uhp = new JLabel("�ڵ�����ȣ :");
	JTextField  		jtf_cert = new JTextField(20);
	JTextField  		jtf_unick = new JTextField(20);
	JTextField  		jtf_upw = new JTextField(20);
	JTextField  		jtf_uname = new JTextField(20);
	JTextField  		jtf_uhp = new JTextField(20);
	String[] 			genderList = {"����","����"};
	JComboBox<String>   jtf_ugender = new JComboBox<String>(genderList);
	JButton				jbtn_cert = new JButton("�����ϱ�");
	JButton				jbtn_upd = new JButton("����");
	JButton				jbtn_back = new JButton("�ڷΰ���");	
	
	
	public UpdateDialog(JFrame clientFrame) {
		super(clientFrame, "ȸ����������", true);
		this.clientFrame = clientFrame;
		
		jbtn_upd.addActionListener(this);
		
		jd_upd.setLayout(null);
		jd_upd.setSize(350,400);
		jd_upd.setBackground(new Color(126, 195, 237));
		jl_unick.setBounds(30, 30, 80, 20);
		jl_upw.setBounds(30, 70, 80, 20);
		jl_uname.setBounds(30, 110, 80, 20);
		jl_ugender.setBounds(30, 150, 80, 20);
		jl_uhp.setBounds(30, 190, 80, 20);
		jl_uhp.setBounds(30, 190, 80, 20);
		jtf_unick.setBounds(120, 30, 180, 20);
		jtf_upw.setBounds(120, 70, 180, 20);
		jtf_uname.setBounds(120, 110, 180, 20);
		jtf_ugender.setBounds(120, 150, 180, 20);
		jtf_uhp.setBounds(120, 190, 180, 20);
		jbtn_upd.setBounds(90, 250, 180, 30);
		jbtn_back.setBounds(90, 280, 180, 30);
		jd_upd.setBackground(Color.YELLOW);
		jd_upd.add(jbtn_upd);
		jd_upd.add(jbtn_back);
		jd_upd.add(jl_unick);
		jd_upd.add(jl_upw);
		jd_upd.add(jl_uname);
		jd_upd.add(jl_uhp);
		jd_upd.add(jl_ugender);
		jd_upd.add(jtf_unick);
		jd_upd.add(jtf_upw);
		jd_upd.add(jtf_uname);
		jd_upd.add(jtf_ugender); 
		jd_upd.add(jtf_uhp);

		jd_upd.setVisible(true);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("����")) {
			System.out.println("MemberUpdate Ŭ���� ȣ��");
//			MemberUpdate mupd = new MemberUpdate();
//			mupd.Update();
		}
		
	}
	
	

}

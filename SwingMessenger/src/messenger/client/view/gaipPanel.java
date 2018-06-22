package messenger.client.view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class gaipPanel extends JFrame {
	JPanel jp_gaip = new JPanel();
	JButton jbtn_get = new JButton();
	JLabel jl_id = new JLabel();
	JLabel jl_pw = new JLabel();
	JLabel jl_name = new JLabel();
	JLabel jl_birth = new JLabel();
	JLabel jl_mail = new JLabel();
	JLabel jl_gender = new JLabel();
	JTextField jtf_id = new JTextField();
	JTextField jtf_pw = new JTextField();
	JTextField jtf_name = new JTextField();
	JTextField jtf_birth = new JTextField();
	JTextField jtf_mail = new JTextField();
	String[] 	genderList  = {"남자","여자"};
	JComboBox   jtf_gender  = new JComboBox(genderList);
	
	public void initDisplay() {
		this.setVisible(true);
		this.setSize(500, 700);
		this.add(jp_gaip);
		jp_gaip.setLayout(new GridLayout(7,1,0,0));
		jp_gaip.setVisible(true);
		jp_gaip.add(jbtn_get);
		jp_gaip.add(jl_id);
		jp_gaip.add(jl_pw);
		jp_gaip.add(jl_name);
		jp_gaip.add(jl_birth);
		jp_gaip.add(jl_mail);
		jp_gaip.add(jl_gender);
		jp_gaip.add(jtf_id);
		jp_gaip.add(jtf_pw);
		jp_gaip.add(jtf_name);
		jp_gaip.add(jtf_birth);
		jp_gaip.add(jtf_mail);
		jp_gaip.add(jtf_gender); 
		jp_gaip.setBackground(Color.YELLOW);
		/*jl_id.setBounds(40, 50, 50, 20);
		jl_id.setVisible(true);
		jtf_id.setBounds(100, 300, 160, 20);
		jtf_id.setVisible(true);
		jl_pw.setBounds(40, 80, 50, 20);
		jl_pw.setVisible(true);
		jtf_pw.setBounds(100, 300, 160, 20);
		jtf_pw.setVisible(true);
		jl_name.setBounds(40, 50, 50, 20);
		jl_name.setVisible(true);
		jtf_name.setBounds(100, 300, 160, 20);
		jtf_name.setVisible(true);
		jl_birth.setBounds(40, 50, 50, 20);
		jl_birth.setVisible(true);
		jtf_birth.setBounds(100, 300, 160, 20);
		jtf_birth.setVisible(true);
		jl_mail.setBounds(40, 50, 50, 20);
		jl_mail.setVisible(true);
		jtf_mail.setBounds(100, 300, 160, 20);
		jtf_mail.setVisible(true);*/
	}
	
	public static void main(String[] args) {
		gaipPanel gp = new gaipPanel();
		gp.initDisplay();
	}
}

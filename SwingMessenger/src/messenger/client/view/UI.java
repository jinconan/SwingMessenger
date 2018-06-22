package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.CardLayout;

public class UI implements ActionListener {
	CardLayout 	card		= new CardLayout();
	JFrame 		jf_login 	= new JFrame();
	JPanel 		jp_card		= new JPanel();
	JPanel 		jp_login 	= new JPanel();
	JPanel 		jp_img 		= new JPanel();
	JPanel 		jp_gaip		= new JPanel();
	JPanel 		jp_list		= new JPanel();
	JLabel 		jl_id 		= new JLabel("아이디");
	JLabel 		jl_pw 		= new JLabel("비밀번호");
	JLabel 		jl_name		= new JLabel("이름");
	JLabel 		jl_birth	= new JLabel("생년월일");
	JLabel 		jl_mail		= new JLabel("이메일");
	JLabel 		jl_gender	= new JLabel("성별");
	JTextField 	jtf_id   	= new JTextField();
	JTextField 	jtf_pw 		= new JTextField();
	JTextField 	jtf_name 	= new JTextField();
	JTextField 	jtf_birth	= new JTextField();
	JTextField 	jtf_mail 	= new JTextField();
	JButton 	jbtn_log 	= new JButton("로그인");
	JButton 	jbtn_gaip 	= new JButton("회원가입");
	JButton 	jbtn_get	= new JButton("가입");
	JButton 	jbtn_back	= new JButton("<-");
	String[] 	genderList  = {"남자","여자"};
	JComboBox   jtf_gender  = new JComboBox(genderList);
	
	public void initDisplay() {
		jbtn_gaip.addActionListener(this);
		jbtn_log.addActionListener(this);
		jbtn_get.addActionListener(this);
		jbtn_back.addActionListener(this);
		//카드 패널
		jp_card.setLayout(card);
		jp_card.add(jp_login,"로그인창");
		jp_card.add(jp_gaip,"가입창");
		jp_card.add(jp_list,"목록창");
		//로그인창
		jf_login.setTitle("코스모톡");
		jf_login.setSize(360, 550);
		jf_login.setVisible(true);
		jf_login.add(jp_card, BorderLayout.CENTER);
		jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp_login.setVisible(true);
		jp_login.add(jp_img);
		jp_login.add(jl_id);
		jp_login.add(jl_pw);
		jp_login.add(jtf_id);
		jp_login.add(jtf_pw);
		jp_login.add(jbtn_log);
		jp_login.add(jbtn_gaip);
		jp_login.setLayout(null);
		jp_login.setBackground(Color.YELLOW);
		jp_img.setBackground(Color.BLACK);
		jp_img.setBounds(70, 70, 200, 200);
		jp_img.setVisible(true);
		//jp_img.setIcon(new ImageIcon("E:dev_kosmo201804.dev_javasrc.com.image"+"kakao.jpg"));
		jl_id.setBounds(50, 300, 60, 20);
		jl_id.setVisible(true);
		jtf_id.setBounds(130, 300, 160, 20);
		jtf_id.setVisible(true);
		jl_pw.setBounds(50, 330, 60, 20);
		jl_pw.setVisible(true);
		jtf_pw.setBounds(130, 330, 160, 20);
		jtf_pw.setVisible(true);
		jbtn_log.setBounds(80, 360, 200, 20);
		jbtn_log.setVisible(true);
		jbtn_log.setBackground(Color.YELLOW);
		jbtn_gaip.setVisible(true);
		jbtn_gaip.setBounds(130, 480, 100, 20);
		jbtn_gaip.setBackground(Color.YELLOW);
		
		//가입창
		/*jp_gaip.add(jbtn_back);
		jp_gaip.setBackground(Color.YELLOW);
		jp_gaip.setLayout(null);
		jbtn_back.setBounds(100,100,50,50);
		jbtn_back.setVisible(true);
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
		jp_login.setLayout(null);
		jl_id.setBounds(40, 50, 50, 20);
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
		
				
		//로그인창
		jp_list.setVisible(true);
		jp_list.setBackground(Color.YELLOW);
		
	}
	//성별 박스
	public String getGender() {
		if("남자".equals(jtf_gender.getSelectedItem())) return "1";
		else return "2";
	}
	public void setGender(String gender) {
	if("남자".equals(gender)) jtf_gender.setSelectedItem("남자");
	else jtf_gender.setSelectedItem("여자");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UI ui = new UI();
		ui.initDisplay();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		UI ui = new UI();
		if(e.getActionCommand().equals("<-")) {
			//card.first(jp_card);
			card.show(jp_card,"로그인창"); 
		}
		else if(e.getActionCommand().equals("회원가입")) {
			//System.out.println(jp_card);
			//card.next(jp_card);
			card.show(jp_card,"가입창");
		}
		else if(e.getActionCommand().equals("로그인")) {
			//card.next(jp_card);
			card.show(jp_card,"목록창");
		}
		
	}//버튼이벤트성공->액션이벤트->card.show

}
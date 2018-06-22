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

public class finalUI implements ActionListener {
	CardLayout 	card		= new CardLayout();
	JFrame 		jf_login 	= new JFrame();
	JPanel 		jp_card		= new JPanel();
	JPanel 		jp_login 	= new JPanel();
	JButton		jp_img 		= new JButton();
	JPanel 		jp_gaip		= new JPanel();
	JPanel 		jp_list		= new JPanel();
	JLabel 		jl_id 		= new JLabel("아이디");
	JLabel 		jl_pw 		= new JLabel("비밀번호");
	JTextField 	jtf_id   	= new JTextField();
	JTextField 	jtf_pw 		= new JTextField();
	JButton 	jbtn_log 	= new JButton("로그인");
	JButton 	jbtn_gaip 	= new JButton("회원가입");
	
	//가입창 패널
	JLabel 		jl_gid  	= new JLabel("아이디 :");
	JLabel 		jl_gpw 		= new JLabel("비밀번호 :");
	JLabel 		jl_gname 	= new JLabel("이름 :");
	JLabel 		jl_ggender  = new JLabel("성별 :");
	JLabel 		jl_gbirth 	= new JLabel("생년월일 :");
	JLabel 		jl_gtel		= new JLabel("핸드폰번호 :");
	JTextField  jtf_gid		= new JTextField(20);
	JTextField  jtf_gpw		= new JTextField(20);
	JTextField  jtf_gname 	= new JTextField(20);
	JTextField  jtf_gbirth 	= new JTextField(20);
	JTextField  jtf_gtel 	= new JTextField(20);
	String[] 	genderList  = {"남자","여자"};
	JComboBox   jtf_ggender = new JComboBox(genderList);
	JButton		jbtn_get	= new JButton("가입");
	JButton		jbtn_back	= new JButton("뒤로가기");	
	String		imgPath		= "C:\\Users\\ASUS\\eclipse-workspace\\dev_java\\KJH\\src\\image";
	public void initDisplay() {
		jbtn_gaip.addActionListener(this);
		jbtn_log.addActionListener(this);
		jbtn_get.addActionListener(this);
		jbtn_back.addActionListener(this);
		//카드 패널
		jp_card.setLayout(card);
		jp_card.add(jp_login,"로그인창");
		jp_card.add(jp_gaip,"가입창");
		//jp_card.add(jp_list,"목록창");
		//프레임
		jf_login.setTitle("코스모톡");
		jf_login.setSize(380, 550);
		jf_login.setVisible(true);
		jf_login.add(jp_card, BorderLayout.CENTER);
		jf_login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//로그인창패널
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
		jp_img.setBounds(70, 70, 180, 180);
		jp_img.setVisible(true);
		jp_img.setIcon(new ImageIcon(imgPath+"kakao.jpg"));
		jl_id.setBounds(50, 300, 80, 20);
		jl_id.setVisible(true);
		jtf_id.setBounds(130, 300, 160, 20);
		jtf_id.setVisible(true);
		jl_pw.setBounds(50, 330, 80, 20);
		jl_pw.setVisible(true);
		jtf_pw.setBounds(130, 330, 160, 20);
		jtf_pw.setVisible(true);
		jbtn_log.setBounds(80, 380, 180, 20);
		jbtn_log.setVisible(true);
		jbtn_log.setBackground(Color.YELLOW);
		jbtn_gaip.setVisible(true);
		jbtn_gaip.setBounds(130, 480, 120, 20);
		jbtn_gaip.setBackground(Color.YELLOW);
		//가입창 패널
		jp_gaip.setLayout(null);
		//jp_gaip.setVisible(true);
		jp_gaip.add(jbtn_get);
		jp_gaip.add(jbtn_back);
		jp_gaip.add(jl_gid);
		jp_gaip.add(jl_gpw);
		jp_gaip.add(jl_gname);
		jp_gaip.add(jl_gbirth);
		jp_gaip.add(jl_gtel);
		jp_gaip.add(jl_ggender);
		jp_gaip.add(jtf_gid);
		jp_gaip.add(jtf_gpw);
		jp_gaip.add(jtf_gname);
		jp_gaip.add(jtf_gbirth);
		jp_gaip.add(jtf_ggender); 
		jp_gaip.add(jtf_gtel);
		jp_gaip.setBackground(Color.YELLOW);
		jl_gid.setBounds(30, 30, 80, 20);
		jl_gid.setVisible(true);
		jl_gpw.setBounds(30, 70, 80, 20);
		jl_gpw.setVisible(true);
		jl_gname.setBounds(30, 110, 80, 20);
		jl_gname.setVisible(true);
		jl_gbirth.setBounds(30, 150, 80, 20);
		jl_gbirth.setVisible(true);
		jl_ggender.setBounds(30, 190, 80, 20);
		jl_ggender.setVisible(true);
		jl_gtel.setBounds(30, 230, 80, 20);
		jl_gtel.setVisible(true);
		jl_gtel.setBounds(30, 230, 80, 20);
		jl_gtel.setVisible(true);
		jtf_gid.setBounds(120, 30, 180, 20);
		jtf_gid.setVisible(true);
		jtf_gpw.setBounds(120, 70, 180, 20);
		jtf_gpw.setVisible(true);
		jtf_gname.setBounds(120, 110, 180, 20);
		jtf_gname.setVisible(true);
		jtf_gbirth.setBounds(120, 150, 180, 20);
		jtf_gbirth.setVisible(true);
		jtf_ggender.setBounds(120, 190, 180, 20);
		jtf_ggender.setVisible(true);
		jtf_gtel.setBounds(120, 230, 180, 20);
		jtf_gtel.setVisible(true);
		jbtn_get.setBounds(90, 450, 180, 30);
		jbtn_back.setBounds(90, 480, 180, 30);
						
		//로그인창
		jp_list.setVisible(true);
		jp_list.setBackground(Color.YELLOW);
	}
	//성별 박스
	public String getGender() {
		if("남자".equals(jtf_ggender.getSelectedItem())) return "1";
		else return "2";
	}
	public void setGender(String gender) {
	if("남자".equals(gender)) jtf_ggender.setSelectedItem("남자");
	else jtf_ggender.setSelectedItem("여자");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		finalUI ui = new finalUI();
		ui.initDisplay();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		UI ui = new UI();
		
		if(e.getActionCommand().equals("뒤로가기")) {
			//card.first(jp_card);
			card.show(jp_card,"로그인창"); 
		}
		else if(e.getActionCommand().equals("회원가입")) {
			//System.out.println(jp_card);
			//card.next(jp_card);
			card.show(jp_card,"가입창");
		}
		else if(e.getActionCommand().equals("로그인")) {
			FriendsList fl = new FriendsList();
			fl.initDisplay();
		}
		
	}//버튼이벤트성공->액션이벤트->card.show

}

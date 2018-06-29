package messenger.client.view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

public class SubMenu implements ActionListener{
	///////선언부
	//프로필창
	JDialog				jd_profile = new JDialog();
	JPanel				jp_profile = new JPanel();
	JLabel				jl_profile = new JLabel();
	
	//친구검색창
	JDialog 			jd_fri 	   = new JDialog();
	JTable 				jt_fri 	   = new JTable();
	JTextField 			jtf_fri    = new JTextField();
	JScrollPane 		jsp_fri	   = new JScrollPane(jt_fri);
	DefaultTableModel   dftm_fri   = new DefaultTableModel();
	
	//채팅창
	JDialog 			jd_chat	   = new JDialog();
	JTextField 			jtf_chat   = new JTextField();
	JTextPane 			jtp_chat   = new JTextPane();
	JPanel				jp_chatF   = new JPanel();
	JButton				jbtn_jun   = new JButton("전송");
	JButton				jbtn_inv   = new JButton("친구초대");
	JButton				jbtn_emti  = new JButton("이모티콘");
	JToolBar			jtb_chat   = new JToolBar();
	JScrollPane 		jsp_chatA  = new JScrollPane(jtp_chat);
	JScrollPane 		jsp_chatF  = new JScrollPane(jtf_chat);
	
	//친구초대창
	JDialog				jd_inv	   = new JDialog();
	JTable				jt_inv	   = new JTable();
	JTextField			jtf_inv    = new JTextField();
	JButton 			jbtn_gum   = new JButton("검색");
	JButton				jbtn_ok    = new JButton("확인");
	JButton				jbtn_cancel= new JButton("취소");
	JScrollPane 		jsp_inv    = new JScrollPane(jt_inv);
	
	//이모티콘창
	JDialog				jd_emti    = new JDialog();
	JLabel				jl_emti    = new JLabel();
	String				img_path   = "C:\\Users\\516\\Desktop\\tales_emoticon\\";
	
	//회원정보 수정창
	CardLayout 	card		= new CardLayout();
	JDialog		jd_upd		= new JDialog();
	JPanel		jp_card		= new JPanel();
	JPanel		jp_cert		= new JPanel();
	JPanel		jp_upd		= new JPanel();
	JLabel 		jl_unick  	= new JLabel("닉네임 :");
	JLabel 		jl_upw 		= new JLabel("비밀번호 :");
	JLabel 		jl_uname 	= new JLabel("이름 :");
	JLabel 		jl_ugender  = new JLabel("성별 :");
	JLabel 		jl_uhp		= new JLabel("핸드폰번호 :");
	JTextField  jtf_cert 	= new JTextField(20);
	JTextField  jtf_unick	= new JTextField(20);
	JTextField  jtf_upw		= new JTextField(20);
	JTextField  jtf_uname 	= new JTextField(20);
	JTextField  jtf_uhp 	= new JTextField(20);
	String[] 	genderList  = {"남자","여자"};
	JComboBox<String>   jtf_ugender = new JComboBox<String>(genderList);
	JButton		jbtn_cert	= new JButton("인증하기");
	JButton		jbtn_upd	= new JButton("수정");
	JButton		jbtn_back	= new JButton("뒤로가기");	
	
	//프로필창 다이얼로그
	public void Profile() {
		jd_profile.setSize(350,500);
		jd_profile.setVisible(true);
		jd_profile.setTitle("프로필");
	}
	
	//친구검색 다이얼로그
	public void addFriends() {
		jbtn_gum.addActionListener(this);
		jbtn_jun.addActionListener(this);
		jd_fri.setTitle("친구검색");
		jd_fri.setSize(400, 300);
		jd_fri.setVisible(true);
		jd_fri.setLayout(null);
		jd_fri.add(jtf_fri);
		jd_fri.add(jsp_fri);
		jd_fri.add(jbtn_gum);
		jd_fri.add(jt_fri);
		Font f = new Font("굴림",Font.CENTER_BASELINE,10);
		jbtn_gum.setFont(f);
		jt_fri.setVisible(true);
		jsp_fri.setVisible(true);
		jbtn_gum.setBounds(300, 10, 50, 30);
		jtf_fri.setBounds(20, 10, 280, 30);
		jsp_fri.setBounds(20, 50, 330, 200);
	}
	
	//새 채팅 다이얼로그
	public void addChatting() {
		int i = 0;
		jbtn_inv.addActionListener(this);
		jbtn_emti.addActionListener(this);
		/*jd_chat.setTitle(chatList);
		String chatList = "";*/
		jd_chat.setSize(360,550);
		jd_chat.setVisible(true);
		jsp_chatA.setVisible(true);
		jsp_chatF.setVisible(true);
		jtb_chat.setVisible(true);
		jd_chat.add("North", jtb_chat);
		jd_chat.add("Center",jsp_chatA);
		jd_chat.add("South",jsp_chatF);
		jtb_chat.setLayout(new GridLayout(1,4,10,10));
		jtb_chat.add(jbtn_emti);
		jtb_chat.add(jbtn_inv);
		//jp_chatF.add(jsp_chatF);
		//jp_chatF.add("East",jbtn_jun);		
		/*jp_chatF.setLayout(null);
		jsp_chatF.setBounds(0, 360, 360, 40);
		jbtn_jun.setBounds(360, 360, 40, 40);*/
		
	}
	
	//친구초대창
	public void Invited() {
		jbtn_ok.addActionListener(this);
		jbtn_cancel.addActionListener(this);
		jd_inv.setTitle("친구초대");
		jd_inv.setSize(400, 300);
		jd_inv.setVisible(true);
		jd_inv.setLayout(new GridLayout(4,1,0,0));
		jd_inv.add(jtf_inv);
		jd_inv.add(jt_fri);
		jd_inv.add(jbtn_ok);
		jd_inv.add(jbtn_cancel);
		Font f = new Font("굴림",Font.CENTER_BASELINE,10);
		jbtn_ok.setFont(f);
		jbtn_cancel.setFont(f);
		jt_inv.setVisible(true);
		jsp_inv.setVisible(true);
	}
	
	//이모티콘 다이얼로그창
	public void Emoticon() {
		jd_emti.setTitle("이모티콘");
		jd_emti.setVisible(true);
		jd_emti.setSize(400,300);
		jd_emti.add(jl_emti);
		jl_emti.setVisible(true);
		ArrayList<JLabel> list = new ArrayList<JLabel>();
		for(int i=0;i<list.size();i++) {
			jl_emti.setIcon(new ImageIcon(img_path+list.get(i)));
			
		}
	}
	
	//회원정보 수정 다이얼로그창
	public void UpdateInfo() {
		jbtn_upd.addActionListener(this);
		jd_upd.setTitle("회원정보수정");
		jd_upd.setSize(350,400);
		jd_upd.setVisible(true);
		/*//카드패널
		jp_card.setLayout(card);
		jp_card.add(jp_cert,"인증창");
		jp_card.add(jp_upd, "수정창");			
		//인증창
		jp_cert.add("Center",jtf_cert);
		jp_cert.add("South",jbtn_cert);
		//수정창
		jp_upd.setVisible(true);
		jp_upd.setLayout(null);
		jp_upd.add(jbtn_upd);
		jp_upd.add(jbtn_back);
		jp_upd.add(jl_uid);
		jp_upd.add(jl_upw);
		jp_upd.add(jl_uname);
		jp_upd.add(jl_uhp);
		jp_upd.add(jl_ugender);
		jp_upd.add(jtf_uid);
		jp_upd.add(jtf_upw);
		jp_upd.add(jtf_uname);
		jp_upd.add(jtf_ugender); 
		jp_upd.add(jtf_uhp);
		jp_upd.setBackground(Color.YELLOW);*/
		jd_upd.setVisible(true);
		jd_upd.setLayout(null);
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
		jd_upd.setBackground(Color.YELLOW);
		jl_unick.setBounds(30, 30, 80, 20);
		jl_unick.setVisible(true);
		jl_upw.setBounds(30, 70, 80, 20);
		jl_upw.setVisible(true);
		jl_uname.setBounds(30, 110, 80, 20);
		jl_uname.setVisible(true);
		jl_ugender.setBounds(30, 150, 80, 20);
		jl_ugender.setVisible(true);
		jl_uhp.setBounds(30, 190, 80, 20);
		jl_uhp.setVisible(true);
		jl_uhp.setBounds(30, 190, 80, 20);
		jl_uhp.setVisible(true);
		jtf_unick.setBounds(120, 30, 180, 20);
		jtf_unick.setVisible(true);
		jtf_upw.setBounds(120, 70, 180, 20);
		jtf_upw.setVisible(true);
		jtf_uname.setBounds(120, 110, 180, 20);
		jtf_uname.setVisible(true);
		jtf_ugender.setBounds(120, 150, 180, 20);
		jtf_ugender.setVisible(true);
		jtf_uhp.setBounds(120, 190, 180, 20);
		jtf_uhp.setVisible(true);
		jbtn_upd.setBounds(90, 250, 180, 30);
		jbtn_back.setBounds(90, 280, 180, 30);
	}
	
	//버튼 액션부
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		String msg = jtf_chat.getText();				
		//addChatting
		if(obj == jtf_chat) {
			try {
				//oos.writeObject(Protocol.MESSAGE+"|"+nickName+"|"+msg);
				jtf_chat.setText("");
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();//저장된 메세지 이력관리
			}
		}
		//addFriends
		if(e.getActionCommand().equals("검색")) {
			System.out.println("검색실행");
		}
		//Emoticon
		if(e.getActionCommand().equals("전송")) {
			System.out.println("전송실행");
		}
		if(e.getActionCommand().equals("이모티콘")) {
			System.out.println("이모티콘실행");
			Emoticon();
		}
		//Invited
		if(e.getActionCommand().equals("친구초대")) {
			System.out.println("초대실행");
			Invited();
		}
		if(e.getActionCommand().equals("확인")) {
			System.out.println("확인실행");		
		}
		else if(e.getActionCommand().equals("취소")) {
			System.out.println("취소실행");
			System.exit(0);
		}
		//회원정보 수정
		if(e.getActionCommand().equals("인증하기")) {
			card.show(jp_card, "수정창");
		}
		else if(e.getActionCommand().equals("수정")) {
			System.out.println("MemberUpdate 클래스 호출");
//			MemberUpdate mupd = new MemberUpdate();
//			mupd.Update();
		}
	}
	public static void main(String[] args) {
		SubMenu ad = new SubMenu();
		//ad.Emoticon();
		//ad.addChatting();
		ad.UpdateInfo();
		//ad.Profile();
	}
}

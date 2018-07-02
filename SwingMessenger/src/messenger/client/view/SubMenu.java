package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;

public class SubMenu implements ActionListener{
	///////선언부
	//프로필창
	JDialog				jd_profile = new JDialog();
	JPanel				jp_profile = new JPanel();
	JLabel				jl_profile = new JLabel();
	
	//친구검색창
	JDialog 	jd_shfri  = new JDialog();
	JTextField  jtf_shfri = new JTextField();
	JButton 	jbtn_gum  = new JButton("검색");
	
	//채팅창
	JDialog 			jd_chat	   = new JDialog();
	JTextPane 			jtf_South  = new JTextPane();
	JTextPane 			jtp_Center = new JTextPane();
	JPanel				jp_North   = new JPanel();
	JPanel				jp_South   = new JPanel();
	JToolBar			jtb_North  = new JToolBar();
	JButton				jbtn_jun   = new JButton("전송");
	JButton				jbtn_inv   = new JButton("친구초대");
	JButton				jbtn_exit  = new JButton("채팅방나가기");
	JButton				jbtn_close = new JButton("창닫기");
	JButton				jbtn_emti  = new JButton("이모티콘");
	JScrollPane 		jsp_South  = new JScrollPane(jtf_South,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane 		jsp_Center = new JScrollPane(jtp_Center,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane 		jsp_North  = new JScrollPane(jtb_North);
	
	//친구초대창
	JDialog				jd_inv	   = new JDialog();
	JTable				jt_inv	   = new JTable();
	JTextField			jtf_inv    = new JTextField();
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
		jd_shfri.setTitle("친구검색");
		jd_shfri.setSize(300, 200);
		jd_shfri.setVisible(true);
		jd_shfri.setLayout(null);
		jd_shfri.add(jtf_shfri);
		jtf_shfri.setVisible(true);
		jd_shfri.add(jbtn_gum);
		Font f = new Font("굴림", Font.CENTER_BASELINE, 10);
		jbtn_gum.setFont(f);
		;
		jbtn_gum.setBounds(120, 100, 60, 30);

	}
	
	//새 채팅 다이얼로그
	public void addChatting() {
		jbtn_jun.addActionListener(this);
		jbtn_inv.addActionListener(this);
		jbtn_emti.addActionListener(this);
		jbtn_exit.addActionListener(this);
		jbtn_close.addActionListener(this);
		jd_chat.setSize(360,550);
		jd_chat.setVisible(true);
		jsp_South.setVisible(true);
		jsp_South.setVisible(true);
		jtb_North.setVisible(true);
		jd_chat.add("North", jtb_North);
		jd_chat.add("Center",jsp_Center);
		jd_chat.add("South",jp_South);
		jp_South.setLayout(new BorderLayout());
		jp_South.add("Center",jsp_South);
		jp_South.add("East",jbtn_jun);
		jbtn_jun.setBackground(new Color(126, 195, 237));
		jsp_South.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jtb_North.setLayout(new GridLayout(2,2));
		jtb_North.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_emti);
		jbtn_emti.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_inv);
		jbtn_inv.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_exit);
		jbtn_exit.setBackground(new Color(126, 195, 237));
		jtb_North.add(jbtn_close);
		jbtn_close.setBackground(new Color(126, 195, 237));
		
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
		jd_upd.setBackground(new Color(126, 195, 237));
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
		int answer = 0;
		Object obj = e.getSource();
		String msg = jtf_South.getText();				
		//addChatting
		if(obj == jtf_South) {
			
			
			
			
		} else if(obj == jbtn_jun) {
			jtp_Center.setText(jtf_South.getText());
			try {
				//oos.writeObject(Protocol.MESSAGE+"|"+nickName+"|"+msg);
				jtf_South.setText("");
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();//저장된 메세지 이력관리
			}
		}
		if(obj == jbtn_emti) {
			Emoticon();
		}else if(obj == jbtn_inv) {
			Invited();
		}else if(obj == jbtn_exit) {
			
			
			
			
			
		}else if(obj == jbtn_close) {
			jd_chat.dispose();
		}
		//addFriends
		if(e.getActionCommand().equals("검색")) {
			System.out.println("검색실행");
			try {
				// 검색 요청 클라이언트
				// 성공하면
				answer = JOptionPane.showConfirmDialog(jd_shfri, jtf_shfri.getText() + "님을 추가하시겠습니까?", "친구추가",
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					try {
						// 친구 추가 클라이언트
						// 추가하면
						JOptionPane.showMessageDialog(jd_shfri, jtf_shfri.getText() + "님이 추가되었습니다. \n 목록을 갱신합니다.",
								"친구추가", JOptionPane.OK_OPTION);
					} catch (Exception fe2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(jd_shfri, "친구추가에 실패하였습니다.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					jd_shfri.dispose();
				}
			} catch (Exception fe) {
				JOptionPane.showMessageDialog(jd_shfri, jtf_shfri.getText() + "님을 찾을 수 없습니다.", "검색실패",
						JOptionPane.ERROR_MESSAGE);
			}
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
		ad.addChatting();
		//ad.UpdateInfo();
		//ad.Profile();
	}
}

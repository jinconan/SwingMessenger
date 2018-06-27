package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ClientFrame extends JFrame implements ActionListener{
		//선언부
		CardLayout 	card		= new CardLayout();
		//JFrame 		jf_login 	= new JFrame();
		JPanel 		jp_card		= new JPanel();
		JPanel 		jp_login 	= new JPanel();
		JLabel		jp_img 		= new JLabel();
		JLabel 		jl_id 		= new JLabel("아이디");
		JLabel 		jl_pw 		= new JLabel("비밀번호");
		JTextField 	jtf_id   	= new JTextField();
		JTextField 	jtf_pw 		= new JTextField();
		JButton 	jbtn_log 	= new JButton("로그인");
		JButton 	jbtn_gaip 	= new JButton("회원가입");
		JLabel		jta_error	= new JLabel();
		
		//가입창 패널
		JPanel 		jp_gaip		= new JPanel();
		JLabel 		jl_gid  	= new JLabel("아이디 :");
		JLabel 		jl_gpw 		= new JLabel("비밀번호 :");
		JLabel 		jl_gname 	= new JLabel("이름 :");
		JLabel 		jl_ggender  = new JLabel("성별 :");
		JLabel 		jl_ghp		= new JLabel("핸드폰번호 :");
		JTextField  jtf_gid		= new JTextField(20);
		JTextField  jtf_gpw		= new JTextField(20);
		JTextField  jtf_gname 	= new JTextField(20);
		JTextField  jtf_ghp 	= new JTextField(20);
		String[] 	genderList  = {"남자","여자"};
		JComboBox   jtf_ggender = new JComboBox(genderList);
		JButton		jbtn_get	= new JButton("가입");
		JButton		jbtn_back	= new JButton("뒤로가기");	
		JButton		jbtn_bok	= new JButton("중복검사");
		String		imgPath		= "E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\";
		
		// 메뉴바
		JMenuBar jmb_menu = new JMenuBar();
		JMenuItem jmi_fri = new JMenuItem("친구목록");
		JMenuItem jmi_chat = new JMenuItem("대화목록");
		JMenuItem jmi_news = new JMenuItem("뉴스");
		JMenuItem jmi_calender = new JMenuItem("캘린더");
		JMenu jm_add = new JMenu("메뉴");
		JMenuItem jmi_addfri = new JMenuItem("새로운 친구");
		JMenuItem jmi_addchat = new JMenuItem("새로운 채팅");
		JMenuItem jmi_upd = new JMenuItem("회원정보수정");
		JMenuItem jmi_logout = new JMenuItem("로그아웃");
		JMenuItem jmi_exit = new JMenuItem("종료");
		// 상태창
		JPanel jp_list	= new JPanel();
		JPanel jp_List = new JPanel();
		JPanel jp_chat = new JPanel();
		JPanel jp_talk = new JPanel();
		JPanel jp_news = new JPanel();
		JPanel jp_calender = new JPanel();
		JPanel jp_my = new JPanel();
		JPanel jp_fri = new JPanel();
		JLabel jl_my = new JLabel("내정보");
		JTextField jtf_my = new JTextField();
		JLabel jl_fri = new JLabel("친구정보");
		JLabel jl_talk = new JLabel("대화방");
		JLabel jl_news = new JLabel("뉴스");
		JLabel jl_calender = new JLabel("캘린더");
		JScrollPane jsp_list = new JScrollPane(jp_card);
		// 임시 이미지 소스
		String noname = "E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\";
		JLabel jl_no = new JLabel();
		boolean isView = false;
		
		//화면부
		public void initDisplay() {
		//메인 액션
			jbtn_gaip.addActionListener(this);
			jbtn_log.addActionListener(this);
			jbtn_get.addActionListener(this);
			jbtn_back.addActionListener(this);
			jbtn_bok.addActionListener(this);
			jmi_fri.addActionListener(this);
			jmi_chat.addActionListener(this);
			jmi_news.addActionListener(this);
			jmi_calender.addActionListener(this);
			jmi_addfri.addActionListener(this);
			jmi_addchat.addActionListener(this);
			jmi_upd.addActionListener(this);
			jmi_logout.addActionListener(this);
			jmi_exit.addActionListener(this);
			//카드 패널
			jp_card.setLayout(card);
			jp_card.add(jp_login,"로그인창");
			jp_card.add(jp_gaip,"가입창");
			jp_card.add(jp_List, "친구목록");
			jp_card.add(jp_chat, "대화목록");
			jp_card.add(jp_news, "뉴스");
			jp_card.add(jp_calender, "캘린더");
			//프레임
			this.setTitle("코스모톡");
			this.setSize(380, 550);
			this.setVisible(true);
			this.add(jp_card, BorderLayout.CENTER);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//로그인창패널
			//로그인창
			jp_login.add(jp_img);
			jp_login.add(jl_id);
			jp_login.add(jl_pw);
			jp_login.add(jtf_id);
			jp_login.add(jtf_pw);
			jp_login.add(jta_error);
			jp_login.add(jbtn_log);
			jp_login.add(jbtn_gaip);
			jp_login.setLayout(null);
			jp_login.setBackground(Color.YELLOW);
			jp_img.setBounds(10, 10, 336, 255);
			jp_img.setIcon(new ImageIcon(imgPath+"kakao.jpg"));
			jl_id.setBounds(50, 300, 80, 20);
			jp_img.setVisible(true);
			jl_id.setVisible(true);
			jtf_id.setBounds(130, 300, 160, 20);
			jtf_id.setVisible(true);
			jta_error.setBounds(150, 320, 200, 20);
			jta_error.setText("금지된 문자열입니다.");
			Font e = new Font("돋움체",Font.CENTER_BASELINE,9);
			jta_error.setFont(e);
			jta_error.setForeground(Color.RED);
			jta_error.setVisible(isView);
			jl_pw.setBounds(50, 340, 80, 20);
			jl_pw.setVisible(true);
			jtf_pw.setBounds(130, 340, 160, 20);
			jtf_pw.setVisible(true);
			jbtn_log.setBounds(80, 380, 180, 20);
			jbtn_log.setVisible(true);
			jbtn_log.setBackground(Color.YELLOW);
			jbtn_gaip.setVisible(true);
			jbtn_gaip.setBounds(130, 480, 120, 20);
			jbtn_gaip.setBackground(Color.YELLOW);
			jp_login.setVisible(true);
			
		//가입창 패널
			jp_gaip.setLayout(null);
			//jp_gaip.setVisible(true);
			jp_gaip.add(jbtn_get);
			jp_gaip.add(jbtn_back);
			jp_gaip.add(jbtn_bok);
			jp_gaip.add(jl_gid);
			jp_gaip.add(jl_gpw);
			jp_gaip.add(jl_gname);
			jp_gaip.add(jl_ghp);
			jp_gaip.add(jl_ggender);
			jp_gaip.add(jtf_gid);
			jp_gaip.add(jtf_gpw);
			jp_gaip.add(jtf_gname);
			jp_gaip.add(jtf_ggender); 
			jp_gaip.add(jtf_ghp);
			jp_gaip.setBackground(Color.YELLOW);
			jl_gid.setBounds(30, 30, 80, 20);
			jl_gid.setVisible(true);
			jbtn_bok.setBounds(250, 30, 60, 20);
			jbtn_bok.setVisible(true);
			jl_gpw.setBounds(30, 70, 80, 20);
			jl_gpw.setVisible(true);
			jl_gname.setBounds(30, 110, 80, 20);
			jl_gname.setVisible(true);
			jl_ggender.setBounds(30, 150, 80, 20);
			jl_ggender.setVisible(true);
			jl_ghp.setBounds(30, 190, 80, 20);
			jl_ghp.setVisible(true);
			jl_ghp.setBounds(30, 190, 80, 20);
			jl_ghp.setVisible(true);
			jtf_gid.setBounds(120, 30, 120, 20);
			jtf_gid.setVisible(true);
			jtf_gpw.setBounds(120, 70, 180, 20);
			jtf_gpw.setVisible(true);
			jtf_gname.setBounds(120, 110, 180, 20);
			jtf_gname.setVisible(true);
			jtf_ggender.setBounds(120, 150, 180, 20);
			jtf_ggender.setVisible(true);
			jtf_ghp.setBounds(120, 190, 180, 20);
			jtf_ghp.setVisible(true);
			jbtn_get.setBounds(90, 450, 180, 30);
			jbtn_back.setBounds(90, 480, 180, 30);
							
		// 메인화면
			this.add("North", jmb_menu);
			jmb_menu.setVisible(false);
			// 사이드 메뉴바
			jmb_menu.setLayout(new GridLayout(1, 5));
			jmb_menu.add(jmi_fri);
			jmb_menu.add(jmi_chat);
			jmb_menu.add(jmi_calender);
			jmb_menu.add(jmi_news);
			jmb_menu.add(jm_add);
			jm_add.add(jmi_addfri);
			jm_add.add(jmi_addchat);
			jm_add.add(jmi_upd);
			jm_add.add(jmi_logout);
			jm_add.add(jmi_exit);
			
			// 목록창
			jp_list.setVisible(true);
			jp_list.setBackground(Color.YELLOW);
			jp_List.setLayout(new GridLayout(5, 1));
			jp_List.add(jl_no, "내정보");
			jl_no.setIcon(new ImageIcon(noname + "알수없음2.png"));
			jl_no.setText("내이름");
			jp_List.add(jp_fri, "친구정보");
			jp_my.add(jl_my);
			jp_fri.add(jl_fri);
			jp_my.setBackground(Color.CYAN);
			jp_fri.setBackground(Color.PINK);
			jp_chat.setBackground(Color.orange);
			jp_news.setBackground(Color.green);
			jp_calender.setBackground(Color.yellow);

			
			//금지문자열 메소드
			jtf_id.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(jta_error.getText() != " " && jta_error.getText() != "/" && jta_error.getText() != "-"
							&& jta_error.getText() != "=" && jta_error.getText() != "|" 
							&& jta_error.getText() != "," && jta_error.getText() != "."
							&& jta_error.getText() != "[" && jta_error.getText() != "]"
							&& jta_error.getText() != "`" ) {
						jta_error.setVisible(false);
					}
					super.keyPressed(e);
					System.out.println("Key Released: "+ e.getKeyCode());
					switch(e.getKeyCode()) {
					case 32:
						try {
							System.out.println("NUMPAD0: "+ e.getKeyCode());
							System.out.println("금지된 문자열입니다.");
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
						jta_error.setVisible(true);
						break;
					case 44:
						jta_error.setVisible(true);
						break;
					case 45:
						jta_error.setVisible(true);
						break;
					case 46:
						jta_error.setVisible(true);
						break;
					case 47:
						jta_error.setVisible(true);
						break;
					case 61:
						jta_error.setVisible(true);
						break;
					case 91:
						jta_error.setVisible(true);
						break;
					case 92:
						jta_error.setVisible(true);
						break;
					case 93:
						jta_error.setVisible(true);
						break;
					case 192:
						jta_error.setVisible(true);
						break;
					default:
					break;
					}///////////// end switch
				}///////////// end keyPressed
			});/////////// end addKeyListener
		}///////////// end initDisplay
		
			
		//성별 박스
		public String getGender() {
			if("남자".equals(jtf_ggender.getSelectedItem())) return "1";
			else return "2";
		}
		public void setGender(String gender) {
		if("남자".equals(gender)) jtf_ggender.setSelectedItem("남자");
		else jtf_ggender.setSelectedItem("여자");
		}////////////// end Gender

		//메인메소드
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			ClientFrame ui = new ClientFrame();
			ui.initDisplay();
		}////////////// end main
		
		
		//버튼 액션부
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			SubMenu ad = new SubMenu();
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
				card.show(jp_card,"친구목록");
				jmb_menu.setVisible(true);
			}
			else if(e.getActionCommand().equals("중복검사")) {
				System.out.println("중복검사 실행");
				Jungbok jb = new Jungbok();
				jb.Gumsa();
			}
			// 메인화면 이벤트
			if (e.getActionCommand().equals("친구목록")) {
				card.show(jp_card, "친구목록");
			} else if (e.getActionCommand().equals("대화목록")) {
				System.out.println("대화목록");
				card.show(jp_card, "대화목록");
			} else if (e.getActionCommand().equals("뉴스")) {
				System.out.println("뉴스");
				card.show(jp_card, "뉴스");
			} else if (e.getActionCommand().equals("캘린더")) {
				System.out.println("캘린더");
				card.show(jp_card, "캘린더");
			}
			// 메뉴바 이벤트
			if (e.getActionCommand().equals("새로운 친구")) {
				ad.addFriends();
			} else if (e.getActionCommand().equals("새로운 채팅")) {
				ad.addChatting();
			} else if (e.getActionCommand().equals("회원정보수정")) {
				ad.UpdateInfo();
			} else if (e.getActionCommand().equals("로그아웃")) {
				// frl.dispose();
				Login ui = new Login();
				ui.initDisplay();
			} else if (e.getActionCommand().equals("종료")) {
				System.exit(0);
			}

		//버튼이벤트성공->액션이벤트->card.show
		}///////////// end actionPerformed
}


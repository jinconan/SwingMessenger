package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class MainMenu implements ActionListener {
	// 선언부
	CardLayout card = new CardLayout();
	JFrame jf_m = new JFrame();
	// 메뉴바
	JMenuBar jmb_menu = new JMenuBar();
	JMenuItem jmi_fri = new JMenuItem("친구목록");
	JMenuItem jmi_chat = new JMenuItem("대화목록");
	JMenuItem jmi_news = new JMenuItem("뉴스");
	JMenuItem jmi_calender = new JMenuItem("캘린더");
	JMenu jm_add = new JMenu("메뉴");
	JMenuItem jmi_addfri = new JMenuItem("새로운 친구");
	JMenuItem jmi_addchat = new JMenuItem("새로운 채팅");
	JMenuItem jmi_logout = new JMenuItem("로그아웃");
	JMenuItem jmi_exit = new JMenuItem("종료");
	// 상태창
	JPanel jp_card = new JPanel();
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

	Login login = null;
	public MainMenu() {
		
	}
	public MainMenu(Login login) {
		// TODO Auto-generated constructor stub
		this.login = login;
	}

	// 화면부
	public void initDisplay() {
		jmi_fri.addActionListener(this);
		jmi_chat.addActionListener(this);
		jmi_news.addActionListener(this);
		jmi_calender.addActionListener(this);
		jmi_addfri.addActionListener(this);
		jmi_addchat.addActionListener(this);
		jmi_logout.addActionListener(this);
		jmi_exit.addActionListener(this);
		// 메인화면
		jf_m.setSize(500, 600);
		jf_m.setVisible(true);
		jf_m.add("West", jmb_menu);
		// 사이드 메뉴바
		jmb_menu.setLayout(new GridLayout(5, 1, 0, 0));
		jmb_menu.add(jmi_fri);
		jmb_menu.add(jmi_chat);
		jmb_menu.add(jmi_calender);
		jmb_menu.add(jmi_news);
		jmb_menu.add(jm_add);
		jm_add.add(jmi_addfri);
		jm_add.add(jmi_addchat);
		jm_add.add(jmi_logout);
		jm_add.add(jmi_exit);
		// 카드
		jf_m.add(jp_card, BorderLayout.CENTER);
		jp_card.setLayout(card);
		jp_card.add(jp_List, "친구목록");
		jp_card.add(jp_chat, "대화목록");
		jp_card.add(jp_news, "뉴스");
		jp_card.add(jp_calender, "캘린더");
		// 목록창
		jp_List.setLayout(new GridLayout(5, 1, 0, 0));
		// jp_List.add(jp_my,"내정보");
		jp_List.add(jl_no, "내정보");
		jl_no = login.msg.getResponse().get(0).getMem_profile();
		System.out.println(jl_no.getWidth());
		jl_no.setText("내이름");
		jp_List.add(jp_fri, "친구정보");
		jp_my.add(jl_my);
		jp_fri.add(jl_fri);
		jp_my.setBackground(Color.CYAN);
		jp_fri.setBackground(Color.PINK);
		jp_chat.setBackground(Color.orange);
		jp_news.setBackground(Color.green);
		jp_calender.setBackground(Color.yellow);

	}////////// end initDisplay

	// 메인메소드


	//버튼 액션부
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		SubMenu ad = new SubMenu();
		MainMenu mm = new MainMenu();
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
		} else if (e.getActionCommand().equals("로그아웃")) {
			// frl.dispose();
			Login ui = new Login();
			ui.initDisplay();
		} else if (e.getActionCommand().equals("종료")) {
			System.exit(0);
		}
		if (e.getActionCommand().equals(jp_my)) {
			/*Identify idf = new Identify();
			idf.initDisplay();*/
		}
	}////////// end actionPerformed
}/////////// end Login

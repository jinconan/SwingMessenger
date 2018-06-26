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
	// �����
	CardLayout card = new CardLayout();
	JFrame jf_m = new JFrame();
	// �޴���
	JMenuBar jmb_menu = new JMenuBar();
	JMenuItem jmi_fri = new JMenuItem("ģ�����");
	JMenuItem jmi_chat = new JMenuItem("��ȭ���");
	JMenuItem jmi_news = new JMenuItem("����");
	JMenuItem jmi_calender = new JMenuItem("Ķ����");
	JMenu jm_add = new JMenu("�޴�");
	JMenuItem jmi_addfri = new JMenuItem("���ο� ģ��");
	JMenuItem jmi_addchat = new JMenuItem("���ο� ä��");
	JMenuItem jmi_logout = new JMenuItem("�α׾ƿ�");
	JMenuItem jmi_exit = new JMenuItem("����");
	// ����â
	JPanel jp_card = new JPanel();
	JPanel jp_List = new JPanel();
	JPanel jp_chat = new JPanel();
	JPanel jp_talk = new JPanel();
	JPanel jp_news = new JPanel();
	JPanel jp_calender = new JPanel();
	JPanel jp_my = new JPanel();
	JPanel jp_fri = new JPanel();
	JLabel jl_my = new JLabel("������");
	JTextField jtf_my = new JTextField();
	JLabel jl_fri = new JLabel("ģ������");
	JLabel jl_talk = new JLabel("��ȭ��");
	JLabel jl_news = new JLabel("����");
	JLabel jl_calender = new JLabel("Ķ����");
	JScrollPane jsp_list = new JScrollPane(jp_card);

	// �ӽ� �̹��� �ҽ�
	String noname = "E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\";
	JLabel jl_no = new JLabel();

	Login login = null;
	public MainMenu() {
		
	}
	public MainMenu(Login login) {
		// TODO Auto-generated constructor stub
		this.login = login;
	}

	// ȭ���
	public void initDisplay() {
		jmi_fri.addActionListener(this);
		jmi_chat.addActionListener(this);
		jmi_news.addActionListener(this);
		jmi_calender.addActionListener(this);
		jmi_addfri.addActionListener(this);
		jmi_addchat.addActionListener(this);
		jmi_logout.addActionListener(this);
		jmi_exit.addActionListener(this);
		// ����ȭ��
		jf_m.setSize(500, 600);
		jf_m.setVisible(true);
		jf_m.add("West", jmb_menu);
		// ���̵� �޴���
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
		// ī��
		jf_m.add(jp_card, BorderLayout.CENTER);
		jp_card.setLayout(card);
		jp_card.add(jp_List, "ģ�����");
		jp_card.add(jp_chat, "��ȭ���");
		jp_card.add(jp_news, "����");
		jp_card.add(jp_calender, "Ķ����");
		// ���â
		jp_List.setLayout(new GridLayout(5, 1, 0, 0));
		// jp_List.add(jp_my,"������");
		jp_List.add(jl_no, "������");
		jl_no = login.msg.getResponse().get(0).getMem_profile();
		System.out.println(jl_no.getWidth());
		jl_no.setText("���̸�");
		jp_List.add(jp_fri, "ģ������");
		jp_my.add(jl_my);
		jp_fri.add(jl_fri);
		jp_my.setBackground(Color.CYAN);
		jp_fri.setBackground(Color.PINK);
		jp_chat.setBackground(Color.orange);
		jp_news.setBackground(Color.green);
		jp_calender.setBackground(Color.yellow);

	}////////// end initDisplay

	// ���θ޼ҵ�


	//��ư �׼Ǻ�
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		SubMenu ad = new SubMenu();
		MainMenu mm = new MainMenu();
		// ����ȭ�� �̺�Ʈ
		
		if (e.getActionCommand().equals("ģ�����")) {
			card.show(jp_card, "ģ�����");
		} else if (e.getActionCommand().equals("��ȭ���")) {
			System.out.println("��ȭ���");
			card.show(jp_card, "��ȭ���");
		} else if (e.getActionCommand().equals("����")) {
			System.out.println("����");
			card.show(jp_card, "����");
		} else if (e.getActionCommand().equals("Ķ����")) {
			System.out.println("Ķ����");
			card.show(jp_card, "Ķ����");
		}
		// �޴��� �̺�Ʈ
		if (e.getActionCommand().equals("���ο� ģ��")) {
			ad.addFriends();
		} else if (e.getActionCommand().equals("���ο� ä��")) {
			ad.addChatting();
		} else if (e.getActionCommand().equals("�α׾ƿ�")) {
			// frl.dispose();
			Login ui = new Login();
			ui.initDisplay();
		} else if (e.getActionCommand().equals("����")) {
			System.exit(0);
		}
		if (e.getActionCommand().equals(jp_my)) {
			/*Identify idf = new Identify();
			idf.initDisplay();*/
		}
	}////////// end actionPerformed
}/////////// end Login

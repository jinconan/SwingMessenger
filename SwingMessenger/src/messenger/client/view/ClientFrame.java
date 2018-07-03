package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.client.member_table.MemberVOTable;
import messenger.client.member_table.MemberVOTableModel;
import messenger.client.view.dialog.ChatDialog;
import messenger.client.view.dialog.CreateRoomDialog;
import messenger.client.view.dialog.ProfileDialog;
import messenger.client.view.dialog.SearchDialog;
import messenger.client.view.dialog.UpdateDialog;
/*2018-07-03 ������ ������ 
 *1. �н����� 1 2 3 4 ���� �״�� ���̴°� ���� 
 *2. Ư�� ���� �Է� ����
 */
public class ClientFrame extends JFrame implements ActionListener, FocusListener, KeyListener{
		Joongbok jb = new Joongbok(this);
		ClientData clientData = new ClientData(this);
	//�����
		//�α��� �г�
		CardLayout 	card		= new CardLayout();
		JPanel 		jp_card		= new JPanel();
		JPanel 		jp_login 	= new JPanel();
		JLabel		jp_img 		= new JLabel();
		JTextField 	jtf_id   	= new JTextField(20);
		JPasswordField jtf_pw	= new JPasswordField(20);
		JButton 	jbtn_log 	= new JButton("�α���");
		JButton 	jbtn_gaip 	= new JButton("ȸ������");
		JButton 	jbtn_exit 	= new JButton("������");
		JMenuItem	jmi_gaip	= new JMenuItem("ȸ������");
		JMenuItem	jmi_system	= new JMenuItem("������");
		JLabel		jta_error	= new JLabel();
		int         delay       = 0;
		//����â �г�
		JPanel 		jp_gaip		= new JPanel();
		JLabel 		jl_gid  	= new JLabel("���̵� :");
		JLabel 		jl_gpw 		= new JLabel("��й�ȣ :");
		JLabel 		jl_gpw_re 	= new JLabel("��й�ȣ :");
		JLabel 		jl_gname 	= new JLabel("�̸� :");
		JLabel 		jl_ggender  = new JLabel("���� :");
		JLabel 		jl_ghp		= new JLabel("�ڵ�����ȣ :");
		JLabel		jta_gerror	= new JLabel();
		JTextField  jtf_gid		= new JTextField(20);
		JPasswordField jtf_gpw		= new JPasswordField(20);
		JPasswordField jtf_gpw_re	= new JPasswordField(20);
		JTextField  jtf_gname 	= new JTextField(20);
		JTextField  jtf_ghp 	= new JTextField(20);
		String[] 	genderList  = {"��","��"};
		JComboBox   jtf_ggender = new JComboBox(genderList);
		JMenuItem	jmi_get		= new JMenuItem("   ȸ     ��     ��     ��");
		JMenuItem	jmi_back	= new JMenuItem("   ��     ��     ��     ��");
		JButton		jbtn_bok	= new JButton("�ߺ��˻�");
		String		imgPath		= "E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\";
		
		// �޴���
		JMenuBar  jmb_menu 		= new JMenuBar();
		JMenuItem jmi_fri 		= new JMenuItem("ģ�����");
		JMenuItem jmi_chat 		= new JMenuItem("��ȭ���");
		JMenuItem jmi_news 		= new JMenuItem("����");
		JMenuItem jmi_calender  = new JMenuItem("Ķ����");
		JMenu 	  jm_add 		= new JMenu("�޴�");
		JMenuItem jmi_addfri 	= new JMenuItem("���ο� ģ��");
		JMenuItem jmi_addchat   = new JMenuItem("���ο� ä��");
		JMenuItem jmi_upd 		= new JMenuItem("ȸ����������");
		JMenuItem jmi_logout 	= new JMenuItem("�α׾ƿ�");
		JMenuItem jmi_exit 		= new JMenuItem("����");
		
		// ����â
		JPanel 		jp_list		= new JPanel();
		JPanel 		jp_talk 	= new JPanel();
		JPanel 		jp_news 	= new JPanel();
		JPanel 		jp_calender = new JPanel();
		JLabel 		jl_no 		= new JLabel();
		JLabel 		jl_my 		= new JLabel("������");
		JTextField  jtf_my 		= new JTextField();
		JLabel 		jl_fri 		= new JLabel("ģ������");
		JLabel 		jl_talk 	= new JLabel("��ȭ��");
		JLabel 		jl_news 	= new JLabel("����");
		JLabel 		jl_calender = new JLabel("Ķ����");
		JScrollPane jsp_list 	= new JScrollPane(jp_card);
		
		//ģ����� �г�
		JPanel 		jp_List 	= new JPanel();
		JPanel 		jp_my 		= new JPanel();
		JPanel 		jp_fri 		= new JPanel();
		private MemberVOTableModel myTableModel 	= new MemberVOTableModel("��");
		private MemberVOTableModel friendTableModel = new MemberVOTableModel("ģ�� ����Ʈ");
		private MemberVOTable	   myTable 			= new MemberVOTable();
		private MemberVOTable 	   friendTable 		= new MemberVOTable();		
		private JScrollPane 	   jsp_friendTable  = new JScrollPane(friendTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
																	 	 , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//��ȭ���� �г�
		JPanel 		jp_chat 	= new JPanel();
		private JButton				jb_newRoom 	= new JButton("�� �����");
		private DefaultTableModel 	dtm  		= new DefaultTableModel(new String[] {"��ȣ", "����"}, 0) {
													//�� ��� ����
												 	@Override
												 	public boolean isCellEditable(int row, int column) {
												 		// TODO Auto-generated method stub
												 		return false;
												 	}
				 								};
		private JTable				jt_room 	= new JTable(dtm);
		private JScrollPane 		jsp_room 	= new JScrollPane(jt_room, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
																		 , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		
		//���콺 �˾� �޴�
		JPopupMenu popup 	   = new JPopupMenu();
		JMenuItem  jmi_popfile = new JMenuItem("�����ʺ���");
		JMenuItem  jmi_popchat = new JMenuItem("��ȭ�ϱ�");
		//JMenuItem  jmi_popdel  = new JMenuItem("�����ϱ�");
		
		// �ӽ� �̹��� �ҽ�
		String noname = ".//src//messenger//client//images//";
		
	//ȭ���
		public void initDisplay() {
		//���� �׼�
			jmi_gaip.addActionListener(this);
			jbtn_log.addActionListener(this);
			jmi_system.addActionListener(this);
			jmi_get.addActionListener(this);
			jmi_back.addActionListener(this);
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
			jtf_id.addKeyListener(this);
			jtf_gid.addKeyListener(this);
			//ī�� �г�
			jp_card.setLayout(card);
			jp_card.add(jp_login,"�α���â");
			jp_card.add(jp_gaip,"����â");
			jp_card.add(jp_List, "ģ�����");
			jp_card.add(jp_chat, "��ȭ���");
			//������
			this.setTitle("���뺸����");
			this.setSize(380, 550);
			this.setVisible(true);
			this.add(jp_card, BorderLayout.CENTER);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			char c = '*';
		//�α���â�г�
			//�α���â
			jp_login.add(jp_img);
			jp_login.add(jtf_id);
			jp_login.add(jtf_pw);
			jp_login.add(jta_error);
			jp_login.add(jbtn_log);
			jp_login.add(jmi_gaip);
			jp_login.add(jmi_system);
			jp_login.setLayout(null);
			jp_login.setBackground(new Color(126, 195, 237));
			jp_img.setBounds(70, 10, 250, 260);
			jp_img.setIcon(new ImageIcon(noname+"bonobono2.jpg"));
			jp_img.setVisible(true);
			jtf_id.setBounds(60, 260, 250, 30);
			jtf_id.setVisible(true);
			jtf_id.setText("");
			jta_error.setBounds(60, 320, 250, 20);
			jta_error.setText("������ ���ڿ��Դϴ�.");
			Font e = new Font("����ü",Font.CENTER_BASELINE,9);
			jta_error.setFont(e);
			jta_error.setForeground(Color.RED);
			jta_error.setVisible(false);
			jtf_pw.setBounds(60, 290, 250, 30);
			jtf_pw.setVisible(true);
			jtf_pw.setEchoChar(c);
			jtf_pw.setText("��й�ȣ");
			jbtn_log.setBounds(60, 340, 250, 30);
			jbtn_log.setVisible(true);
			jbtn_log.setBackground(new Color(126, 195, 237));
			jmi_gaip.setVisible(true);
			jmi_gaip.setBounds(90, 460, 100, 20);
			jmi_gaip.setHorizontalTextPosition(0);
			jmi_gaip.setBackground(new Color(126, 195, 237));
			jmi_system.setVisible(true);
			jmi_system.setBounds(210, 460, 100, 20);
			jmi_system.setHorizontalTextPosition(0);
			jmi_system.setBackground(new Color(126, 195, 237));
			jp_login.setVisible(true);
			
		//����â �г�
			jp_gaip.setLayout(null);
			jp_gaip.add(jmi_get);
			jp_gaip.add(jmi_back);
			jp_gaip.add(jbtn_bok);
			jp_gaip.add(jl_gid);
			jp_gaip.add(jta_gerror);
			jp_gaip.add(jl_gpw);
			jp_gaip.add(jl_gpw_re);
			jp_gaip.add(jl_gname);
			jp_gaip.add(jl_ghp);
			jp_gaip.add(jl_ggender);
			jp_gaip.add(jtf_gid);
			jp_gaip.add(jtf_gpw);
			jp_gaip.add(jtf_gpw_re);
			jp_gaip.add(jtf_gname);
			jp_gaip.add(jtf_ggender); 
			jp_gaip.add(jtf_ghp);
			jp_gaip.setBackground(new Color(126, 195, 237));
			jl_gid.setBounds(40, 70, 180, 20);
			jl_gid.setVisible(true);
			jbtn_bok.setBounds(120, 110, 200, 20);
			jbtn_bok.setVisible(true);
			jbtn_bok.setBackground(new Color(126, 195, 237));
			jta_gerror.setBounds(120, 90, 200, 20);
			jta_gerror.setHorizontalTextPosition(0);
			jta_gerror.setText("������ ���ڿ��Դϴ�.");
			Font f = new Font("����ü",Font.CENTER_BASELINE,9);
			jta_gerror.setFont(f);
			jta_gerror.setForeground(Color.RED);
			jta_gerror.setVisible(false);
			jl_gpw.setBounds(40, 150, 80, 20);
			jl_gpw.setVisible(true);
			jl_gpw_re.setBounds(40, 190, 80, 20);
			jl_gpw_re.setVisible(true);
			jl_gname.setBounds(40, 230, 80, 20);
			jl_gname.setVisible(true);
			jl_ggender.setBounds(40, 270, 80, 20);
			jl_ggender.setVisible(true);
			jl_ghp.setBounds(40, 310, 80, 20);
			jl_ghp.setVisible(true);
			jtf_gid.setBounds(120, 70, 200, 20);
			jtf_gid.setVisible(true);
			jtf_gpw.setBounds(120, 150, 200, 20);
			jtf_gpw.setVisible(true);
			jtf_gpw.setEchoChar(c);
			jtf_gpw_re.setBounds(120, 190, 200, 20);
			jtf_gpw_re.setVisible(true);
			jtf_gname.setBounds(120, 230, 200, 20);
			jtf_gname.setVisible(true);
			jtf_ggender.setBounds(120, 270, 200, 20);
			jtf_ggender.setVisible(true);
			jtf_ghp.setBounds(120, 310, 200, 20);
			jtf_ghp.setVisible(true);
			jmi_get.setBounds(40, 430, 140, 30);
			jmi_get.setBackground(new Color(126, 195, 237));
			jmi_back.setBounds(180, 430, 140, 30);
			jmi_back.setBackground(new Color(126, 195, 237));
		
			
			//�� ��� �г� ����////////////////////
			jp_chat.setLayout(new BorderLayout());
			jt_room.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
			//�� ��Ͽ��� ���� Ŭ�������� ä�ù� ���̾�α׸� ȭ�鿡 ����.
			jt_room.addMouseListener(new MouseAdapter() {
				
				//��ȭ�� Ŭ���� �̺�Ʈ
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) { //���� ��Ŭ������ �� - ��Ŭ�� ��� ������ ã�� �ʿ䰡 ����.
						
						int row = jt_room.getSelectedRow();
						int room_no = Integer.parseInt((String)jt_room.getValueAt(row, 0));
						ChatDialog dialog = clientData.getChatDialog(room_no);
						if(dialog != null) 
							dialog.setVisible(true);
					}
				}
			});
			
			jb_newRoom.addActionListener(this);
			jp_chat.add("North", jb_newRoom);
			jp_chat.add("Center", jsp_room);
			////�� ��� �г� ���� ��////////////////////////////////
			
			//ģ�� ��� �г� ����//////////
			jp_List.setLayout(new BorderLayout());
			myTable.setModel(myTableModel);
			friendTable.setModel(friendTableModel);
			jp_List.add("North", myTable);
			jp_List.add("Center", jsp_friendTable);
			//ģ�� ��� �г� ���� ��/////
			
			
			
		// ����ȭ��
			this.add("North", jmb_menu);
			jmb_menu.setVisible(false);
			// ���̵� �޴���
			jmb_menu.setLayout(new GridLayout(1, 5));
			jmb_menu.add(jmi_fri);
			jmb_menu.add(jmi_chat);
//			jmb_menu.add(jmi_calender);
//			jmb_menu.add(jmi_news);
			jmb_menu.add(jm_add);
			jm_add.add(jmi_addfri);
			jm_add.add(jmi_addchat);
			jm_add.add(jmi_upd);
			jm_add.add(jmi_logout);
			jm_add.add(jmi_exit);
			
			// ���â
			jp_list.setVisible(true);
			jp_list.setBackground(Color.YELLOW);
			jl_no.setIcon(new ImageIcon(noname + "�˼�����2.png"));
			jl_no.setText("���̸�");
			jp_my.add(jl_my);
			jp_fri.add(jl_fri);
			jp_my.setBackground(Color.CYAN);
			jp_fri.setBackground(Color.PINK);
			jp_chat.setBackground(Color.orange);
			jp_news.setBackground(Color.green);
			jp_calender.setBackground(Color.yellow);

			
			
			//��Ŀ�� ������ �߰�(�α��� â - ���̵�, ��й�ȣ)
			jtf_id.addFocusListener(this);
			jtf_pw.addFocusListener(this);
			
		//�������ڿ� �޼ҵ�
			//�α���â
			/*jtf_id.addKeyListener(new KeyAdapter() {
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
							System.out.println("������ ���ڿ��Դϴ�.");
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
*/			
			//����â
/*			jtf_gid.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(jta_gerror.getText() != " " && jta_gerror.getText() != "/" && jta_error.getText() != "-"
							&& jta_gerror.getText() != "=" && jta_gerror.getText() != "|" 
							&& jta_gerror.getText() != "," && jta_gerror.getText() != "."
							&& jta_gerror.getText() != "[" && jta_gerror.getText() != "]"
							&& jta_gerror.getText() != "`" ) {
						jta_gerror.setVisible(false);
					}
					super.keyPressed(e);
					System.out.println("Key Released: "+ e.getKeyCode());
					switch(e.getKeyCode()) {
					case 32:
						try {
							System.out.println("NUMPAD0: "+ e.getKeyCode());
							System.out.println("������ ���ڿ��Դϴ�.");
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
						jta_gerror.setVisible(true);
						break;
					case 44:
						jta_gerror.setVisible(true);
						break;
					case 45:
						jta_gerror.setVisible(true);
						break;
					case 46:
						jta_gerror.setVisible(true);
						break;
					case 47:
						jta_gerror.setVisible(true);
						break;
					case 61:
						jta_gerror.setVisible(true);
						break;
					case 91:
						jta_gerror.setVisible(true);
						break;
					case 92:
						jta_gerror.setVisible(true);
						break;
					case 93:
						jta_gerror.setVisible(true);
						break;
					case 192:
						jta_gerror.setVisible(true);
						break;
					default:
					break;
					}///////////// end switch
				}///////////// end keyPressed
			});/////////// end addKeyListener
*/			
			//���콺 �˾� ���̾ƿ�
			popup = new JPopupMenu();
			popup.add(jmi_popfile);
//			popup.add(jmi_popdel);
			popup.add(jmi_popchat);
			friendTable.add(popup);
			//�˾����콺 �׼Ǹ�����
			friendTable.addMouseListener(new MouseAdapter() {
				   @Override
				   public void mousePressed(MouseEvent e) {
				    // ������ ��ư Ŭ�� �� ...
				    if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {
				      System.out.println("����");
				     popup.show(friendTable, e.getX(), e.getY());
				    }
				   }
				  });
			jmi_popfile.addActionListener(this);
//			jmi_popdel.addActionListener(this);
			jmi_popchat.addActionListener(this);
			
		}///////////// end initDisplay
		
	//ģ����� �г�â ���� �޼ҵ�//////////////
		/**
		 * �� ������ �����ִ� ���̺��� �����Ѵ�.
		 * @param mVO : �� ����
		 */
		//������ ���̺� ���ΰ�ħ
		public synchronized void refreshMyTable(MemberVO mVO) {
			if(myTableModel.getRowCount() > 0)
				myTableModel.removeRow(0);
			myTableModel.addRow(mVO);
		}
		/**
		 * ģ�� ��� ���̺��� �����Ѵ�.
		 * @param mVOs : ģ�� ����Ʈ
		 */
		//ģ������ ���̺� ���ΰ�ħ
		public synchronized void refreshFriendTable(ArrayList<MemberVO> mVOs) {
			while(friendTableModel.getRowCount() > 0)
				friendTableModel.removeRow(0);
			for(MemberVO mVO : mVOs)
				friendTableModel.addRow(mVO);
		}
		
		/**
		 * ģ�� �� ���� ���̺�𵨿� �߰��Ѵ�.
		 * @param mVO
		 */
		//ģ�� ��Ͽ� �߰��ϱ�
		public synchronized void addFriendRow(MemberVO mVO) {
			friendTableModel.addRow(mVO);
		}
		
		/**
		 * ���̺� �𵨿��� ģ�� �� ���� �����Ѵ�.
		 * @param mVO
		 */
		//ģ�� ��Ͽ��� �����ϱ�
		public synchronized void removeFriendRow(MemberVO mVO) {
			for(int i=0;i<friendTableModel.getRowCount();i++) {
				MemberVO f_mVO = friendTableModel.getValueAt(i, 0);
				if(f_mVO.getMem_no() == mVO.getMem_no()) {
					friendTableModel.removeRow(i);
					return;
				}
			}
		}
	//ģ����� �г�â ���� �޼ҵ� ��//////////////
		
	//��ȭ�� ��� �г�â ���� �޼ҵ�//////////////
		//��ȭ�� ��� ���ΰ�ħ
		public void refreshRoomList(ArrayList<RoomVO> rVOList) {
			clientData.refreshRoomList(rVOList);
			
			dtm.setRowCount(0);
			for(RoomVO rVO : rVOList) {
				dtm.addRow(new String[] {Integer.toString(rVO.getRoom_no()), rVO.getRoom_title()});
			}
		}
	//��ȭ�� ��� �г�â ���� �޼ҵ� ��/////////////		
		
		//���� �ڽ�
		public String getGender() {
			if("��".equals(jtf_ggender.getSelectedItem())) return "1";
			else return "2";
		}
		public void setGender(String gender) {
		if("��".equals(gender)) jtf_ggender.setSelectedItem("��");
		else jtf_ggender.setSelectedItem("��");
		}////////////// end Gender

		public ClientData getClientData() {
			return clientData;
		}
		
	//���θ޼ҵ�
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClientFrame ui = new ClientFrame();
		ui.initDisplay();
	}////////////// end main
	
		
		//��ư �׼Ǻ�
		@Override
		public void actionPerformed(ActionEvent e) {
			//�α���â �̺�Ʈ
			if(e.getSource()==jmi_gaip) {
				//System.out.println(jp_card);
				//card.next(jp_card);
				card.show(jp_card,"����â");
			} else if(e.getActionCommand().equals("�α���")) {
				if(clientData.login(jtf_id.getText(), jtf_pw.getText())) {
					//�α��� ������ ��������� �����Ͽ� ģ������Ʈ��������, ä�ü����� �����Ͽ� �� ����Ʈ ��������, �̸�Ƽ�ܼ����� �����Ͽ� �̸�Ƽ�� �޾ƿ��� ����
					clientData.getEmoticonFromServer();
					refreshMyTable(clientData.getMyData());
					clientData.actionFriendList();
					clientData.initChat();
					card.show(jp_card,"ģ�����");
					jmb_menu.setVisible(true);
				}
				else {
					JOptionPane.showMessageDialog(null, "����", "�α��� ����", JOptionPane.ERROR_MESSAGE);
				}
			} else if(e.getActionCommand().equals("������")) {
				System.exit(0);
			}
			
			// ȸ������ �̺�Ʈ
			if(e.getSource()==jbtn_bok) {
				//System.out.println(jtf_id.getText());
				jb.Gumsa();
			}
			if(e.getSource()==jmi_get) {
				System.out.println("���Թ�ư");
				if(jb.answer == 1) {
					System.out.println("���Լ���");
				}
				else {
					JOptionPane.showMessageDialog(jp_gaip, "���̵� �ߺ��˻縦 ���ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
				}
				if(jtf_gpw.getText()!=jtf_gpw_re.getText()) {
					JOptionPane.showMessageDialog(jp_gaip, "��й�ȣ�� ���� �ʽ��ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);		
				} else {return;}
				
			} else if(e.getSource()==jmi_back) {
				card.show(jp_card,"�α���â"); 
			}
			
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
				new SearchDialog(this);
			} else if (e.getActionCommand().equals("���ο� ä��")) {
				new CreateRoomDialog(this);
			} else if (e.getActionCommand().equals("ȸ����������")) {
				new UpdateDialog(this);
			} else if (e.getActionCommand().equals("�α׾ƿ�")) {
				card.show(jp_card,"�α���â"); 
				jmb_menu.setVisible(false);
			} else if (e.getActionCommand().equals("����")) {
				System.exit(0);
			}
			
			//���콺 �˾� �̺�Ʈ
			if(e.getActionCommand().equals("�����ʺ���")) {
				ProfileDialog pd;
			} else if(e.getActionCommand().equals("��ȭ�ϱ�")) {
				
			} else if(e.getActionCommand().equals("�����ϱ�")) {
				
			}
		}
			


		@Override
		public void focusGained(FocusEvent e) {
			if(e.getSource() == jtf_id) {
				if(jtf_id.getText().equals("���̵�"))
					jtf_id.setText("");
			}
			else if(e.getSource() == jtf_pw) {
				if(jtf_pw.getText().equals("��й�ȣ"))
					jtf_pw.setText("");
			}
			
		}


		@Override
		public void focusLost(FocusEvent e) {
			if(e.getSource() == jtf_id) {
				if(jtf_id.getText().length() == 0)
					jtf_id.setText("���̵�");
			}
			else if(e.getSource() == jtf_pw) {
				if(jtf_pw.getText().length() == 0)
					jtf_pw.setText("��й�ȣ");
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==16) {
				delay=1;
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int Vs = 0;
		    Vs = e.getKeyCode();
		  
			if(delay==1) {
				switch(Vs) {
				case 19: case 49: case 50: case 51: case 52: case 53: case 54: case 55: case 56: case 57:
				case 45: case 248: case 61:  
					try {
						jtf_id.setText(jtf_id.getText().substring(0, jtf_id.getText().length()-1));
						jtf_gid.setText(jtf_gid.getText().substring(0, jtf_gid.getText().length()-1));
					} catch (StringIndexOutOfBoundsException e2) {
						e2.printStackTrace();
			
					}
					JOptionPane.showMessageDialog(this, "Ư�����ڸ� �Է��Ͽ����ϴ�.", "����", JOptionPane.CLOSED_OPTION);
					delay=0;
					break;
				}
			}
			    switch(Vs) {
			    case 44: case 46: case 47: case 59: case 222: case 91: case 93:
			    case 107: case 111: case 106: case 109: case 110: 
			    	try {
						jtf_id.setText(jtf_id.getText().substring(0, jtf_id.getText().length()-1));
						jtf_gid.setText(jtf_gid.getText().substring(0, jtf_gid.getText().length()-1));
					} catch (StringIndexOutOfBoundsException e2) {
						e2.printStackTrace();
			
					}
					JOptionPane.showMessageDialog(this, "Ư�����ڸ� �Է��Ͽ����ϴ�.", "����", JOptionPane.CLOSED_OPTION);
					delay=0;
					break;
			    }
		}

		@Override
		public void keyTyped(KeyEvent e) {}
}


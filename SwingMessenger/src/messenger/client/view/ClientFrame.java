package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	//�����
		Joongbok 	jb			= new Joongbok(this);
		ClientData	clientData	= new ClientData(this);
		int			answer		= 0;
		
		//�α��� �г�
		CardLayout 		card		= new CardLayout();
		JPanel 			jp_card		= new JPanel();
		JPanel 			jp_login 	= new JPanel();
		JLabel			jp_img 		= new JLabel();
		JLabel			Rweback		= new JLabel();
		JLabel			Lweback		= new JLabel();
		JTextField 		jtf_id   	= new JTextField(20);
		JPasswordField  jtf_pw		= new JPasswordField(20);
		JButton 		jbtn_log 	= new JButton("�α���");
		JMenuItem		jmi_gaip	= new JMenuItem("ȸ������");
		JMenuItem		jmi_system	= new JMenuItem("������");
		
		//����â �г�
		JPanel 				jp_gaip		= new JPanel();
		JLabel 				jl_gid  	= new JLabel("���̵� :");
		JLabel 				jl_gpw 		= new JLabel("��й�ȣ           :");
		JLabel 				jl_gpw_re 	= new JLabel("��й�ȣ Ȯ�� :");
		JLabel 				jl_gname 	= new JLabel("�̸� :");
		JLabel 				jl_ggender  = new JLabel("���� :");
		JLabel 				jl_ghp		= new JLabel("�ڵ�����ȣ :");
		JTextField  		jtf_gid		= new JTextField(20);
		JPasswordField 		jtf_gpw		= new JPasswordField(20);
		JPasswordField 		jtf_gpw_re	= new JPasswordField(20);
		JTextField  		jtf_gname 	= new JTextField(20);
		JTextField			jtf_ghp 	= new JTextField(20);
		String[]			genderList  = {"��","��"};
		JComboBox<String>	jtf_ggender = new JComboBox<String>(genderList);
		JMenuItem			jmi_get		= new JMenuItem("   ȸ     ��     ��     ��");
		JMenuItem			jmi_back	= new JMenuItem("   ��     ��     ��     ��");
		JButton				jbtn_bok	= new JButton("�ߺ��˻�");
		
		// �޴���
		JMenuBar  jmb_menu 		= new JMenuBar();
		JMenuItem jmi_fri 		= new JMenuItem("ģ�����");
		JMenuItem jmi_chat 		= new JMenuItem("��ȭ���");
		JMenu 	  jm_add 		= new JMenu("�޴�");
		JMenuItem jmi_addfri 	= new JMenuItem("���ο� ģ��");
		JMenuItem jmi_addchat   = new JMenuItem("���ο� ä��");
		JMenuItem jmi_upd 		= new JMenuItem("ȸ����������");
		JMenuItem jmi_exit 		= new JMenuItem("����");
		
		//�̽��Ϳ��� ���̾�α�â
		
		
		//ģ����� �г�
		JPanel				jp_List			= new JPanel();
		MemberVOTableModel	myTableModel 	= new MemberVOTableModel("��");
		MemberVOTableModel	friendTableModel= new MemberVOTableModel("ģ�� ����Ʈ");
		MemberVOTable		myTable			= new MemberVOTable();
		MemberVOTable		friendTable		= new MemberVOTable();		
		JScrollPane			jsp_friendTable = new JScrollPane(friendTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
																	 	 , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		//��ȭ���� �г�
		JPanel				jp_chat 	= new JPanel();
		DefaultTableModel 	dtm_room  	= new DefaultTableModel(new String[] {"��ȣ", "����"}, 0) {
													//�� ��� ����
												 	@Override
												 	public boolean isCellEditable(int row, int column) {
												 		return false;
												 	}
				 						};
		JTable				jt_room 	= new JTable(dtm_room);
		JScrollPane 		jsp_room 	= new JScrollPane(jt_room, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
																 , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		
		//���콺 �˾� �޴�
		JPopupMenu popup 	   = new JPopupMenu();
		JMenuItem  jmi_popprof = new JMenuItem("�����ʺ���");
		JMenuItem  jmi_popdel  = new JMenuItem("�����ϱ�");
		JMenuItem  jmi_popfile = new JMenuItem("����÷��");
		JMenuItem  jmi_popcall = new JMenuItem("��ȭ�ϱ�");
		
		// �ӽ� �̹��� �ҽ�
		String noname = "/messenger/client/images/";
		
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
			jmi_addfri.addActionListener(this);
			jmi_addchat.addActionListener(this);
			jmi_upd.addActionListener(this);
			jmi_exit.addActionListener(this);
			
			////////////Ű ������ �߰�//////////////
			//���ڼ� ����(���̵�,��й�ȣ, �̸�, ���¸޼���, ��ȭ��ȣ)
			//���ͽ� ��� ����(�α��ο��� ��й�ȣ�Է� �� ����, ä�ù� ���̾�α׿��� ���ͽ� ����)
			jtf_id.addKeyListener(this);
			jtf_gid.addKeyListener(this);
			jtf_pw.addKeyListener(this);
			jtf_gpw.addKeyListener(this);
			jtf_gpw_re.addKeyListener(this);
			jtf_gname.addKeyListener(this);
			jtf_ghp.addKeyListener(this);
			
			////////////Ű ������ �߰� ��////////////
			
			//��Ŀ�� ������ �߰�(�α��� â - ���̵�, ��й�ȣ)
			jtf_id.addFocusListener(this);
			jtf_pw.addFocusListener(this);
			
			//ī�� �г�
			jp_card.setLayout(card);
			jp_card.add(jp_login,"�α���â");
			jp_card.add(jp_gaip,"����â");
			jp_card.add(jp_List, "ģ�����");
			jp_card.add(jp_chat, "��ȭ���");
			//������
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setTitle("���뺸����");
			this.setSize(380, 550);
			this.setVisible(true);
			this.add(jp_card, BorderLayout.CENTER);
			char c = '*';
			
		//�α���â�г�
			//�α���â
			jp_login.add(jp_img);
			jp_login.add(Rweback);
			jp_login.add(Lweback);
			jp_login.add(jtf_id);
			jp_login.add(jtf_pw);
			jp_login.add(jbtn_log);
			jp_login.add(jmi_gaip);
			jp_login.add(jmi_system);
			jp_login.setLayout(null);
			jp_login.setBackground(new Color(126, 195, 237));
			jp_img.setBounds(70, 10, 250, 260);
			jp_img.setIcon(new ImageIcon(ClientFrame.class.getResource(noname+"bonobono2.jpg")));
			//Rweback.setBounds(0,0,380,550);
			//Lweback.setBounds(50,0,10,10);
			//Rweback.setIcon(new ImageIcon(ClientFrame.class.getResource(noname+"����(��).png")));
			//Lweback.setIcon(new ImageIcon(ClientFrame.class.getResource(noname+"Lweback.jpg")));
			jtf_id.setBounds(60, 260, 250, 30);
			jtf_id.setText("");
			jtf_pw.setBounds(60, 290, 250, 30);
			jtf_pw.setEchoChar(c);
			jtf_pw.setText("��й�ȣ");
			jbtn_log.setBounds(60, 340, 250, 30);
			jbtn_log.setBackground(new Color(126, 195, 237));
			jmi_gaip.setBounds(90, 460, 100, 20);
			jmi_gaip.setHorizontalTextPosition(0);
			jmi_gaip.setBackground(new Color(126, 195, 237));
			jmi_system.setBounds(210, 460, 100, 20);
			jmi_system.setHorizontalTextPosition(0);
			jmi_system.setBackground(new Color(126, 195, 237));
			
			
		//����â �г�
			jp_gaip.setLayout(null);
			jp_gaip.add(jmi_get);
			jp_gaip.add(jmi_back);
			jp_gaip.add(jbtn_bok);
			jp_gaip.add(jl_gid);
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
			jbtn_bok.setBounds(160, 110, 160, 20);
			jbtn_bok.setBackground(new Color(126, 195, 237));
			jl_gpw.setBounds(40, 150, 100, 20);
			jl_gpw_re.setBounds(40, 190, 100, 20);
			jl_gname.setBounds(40, 230, 80, 20);
			jl_ggender.setBounds(40, 270, 80, 20);
			jl_ghp.setBounds(40, 310, 80, 20);
			jtf_gid.setBounds(160, 70, 160, 20);
			jtf_gpw.setBounds(160, 150, 160, 20);
			jtf_gpw.setEchoChar(c);
			jtf_gpw_re.setBounds(160, 190, 160, 20);
			jtf_gname.setBounds(160, 230, 160, 20);
			jtf_ggender.setBounds(160, 270, 160, 20);
			jtf_ghp.setBounds(160, 310, 160, 20);
			jmi_get.setBounds(40, 430, 140, 30);
			jmi_get.setBackground(new Color(126, 195, 237));
			jmi_back.setBounds(180, 430, 140, 30);
			jmi_back.setBackground(new Color(126, 195, 237));
		
			//ģ�� ��� �г� ����//////////
			jp_List.setLayout(new BorderLayout());
			myTable.setModel(myTableModel);
			friendTable.setModel(friendTableModel);
			jp_List.add("North", myTable);
			jp_List.add("Center", jsp_friendTable);
			//ģ�� ��� �г� ���� ��/////
			
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
			//�ڱ� ������ ����
			myTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() == 2) {
						System.out.println("�� ������ ����");
						int row = myTable.getSelectedRow();
						MemberVO memVO = myTableModel.getValueAt(row, 0);
						new ProfileDialog(memVO);
						
					}
				}
			});
			jp_chat.add("Center", jsp_room);
			////�� ��� �г� ���� ��////////////////////////////////
		// ����ȭ��
			this.add("North", jmb_menu);
			jmb_menu.setVisible(false);
			jmb_menu.setBackground(new Color(126, 195, 237));
			// ���̵� �޴���
			jmb_menu.setLayout(new GridLayout(1, 5));
			jmb_menu.add(jmi_fri);
			jmb_menu.add(jmi_chat);
			jmb_menu.add(jm_add);
			jm_add.add(jmi_addfri);
			jm_add.add(jmi_addchat);
			jm_add.add(jmi_upd);
			jm_add.add(jmi_exit);
			jm_add.setBackground(new Color(126, 195, 237));
			jmi_fri.setBackground(new Color(126, 195, 237));
			jmi_chat.setBackground(new Color(126, 195, 237));
			jmi_addfri.setBackground(new Color(126, 195, 237));
			jmi_addchat.setBackground(new Color(126, 195, 237));
			jmi_upd.setBackground(new Color(126, 195, 237));
			jmi_exit.setBackground(new Color(126, 195, 237));
			
			//���콺 �˾� ���̾ƿ�
			popup = new JPopupMenu();
			popup.add(jmi_popprof);
			popup.add(jmi_popdel);
			popup.add(jmi_popfile);
			popup.add(jmi_popcall);
			friendTable.add(popup);
			//�˾����콺 �׼Ǹ�����
			friendTable.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					// ������ ��ư Ŭ�� �� ...
					if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					    popup.show(friendTable, e.getX(), e.getY());
					}
				}
			});
			jmi_popprof.addActionListener(this);
			jmi_popdel.addActionListener(this);
			jmi_popcall.addActionListener(this);
			jmi_popfile.addActionListener(this);
			
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
		 * ģ�� ��� ���̺��� ����
		 * @return ģ����� ���̺�
		 */
		public MemberVOTableModel getFriendTableModel() {
			return friendTableModel;
		}
	//ģ����� �г�â ���� �޼ҵ� ��//////////////
		
	//��ȭ�� ��� �г�â ���� �޼ҵ�//////////////
		//��ȭ�� ��� ���ΰ�ħ
		public synchronized void refreshRoomList(ArrayList<RoomVO> rVOList) {
			clientData.refreshRoomList(rVOList);
			
			dtm_room.setRowCount(0);
			for(RoomVO rVO : rVOList) {
				dtm_room.addRow(new String[] {Integer.toString(rVO.getRoom_no()), rVO.getRoom_title()});
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
			if(e.getActionCommand().equals("ȸ������")) {
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
					//�α��� ���н� �޼��� ���̾�α׸� ����.
					JOptionPane.showMessageDialog(null, "����", "�α��� ����", JOptionPane.ERROR_MESSAGE);
				}
			} else if(e.getActionCommand().equals("������")) {
				System.exit(0);
			}
			
			// ȸ������ �̺�Ʈ
			//�ߺ��˻�
			else if(e.getActionCommand().equals("�ߺ��˻�")) {
				answer = jb.Gumsa();
			}
			if(e.getSource()==jmi_get) {
				if(jb.answer != 1){
					JOptionPane.showMessageDialog(jp_gaip, "���̵� �ߺ��˻縦 ���ּ���.", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(jb.answer == 1) {
					if(!jtf_gpw.getText().equals(jtf_gpw_re.getText())) {
						JOptionPane.showMessageDialog(jp_gaip, "��й�ȣ�� ���� �ʽ��ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
						ClientJoin cj = new ClientJoin();
						cj.setID(jtf_gid.getText());
						cj.setPW(jtf_gpw.getText());
						cj.setName(jtf_gname.getText());
						cj.setGender((String)jtf_ggender.getSelectedItem());
						cj.setHP(jtf_ghp.getText());
						cj.result =	cj.Join(cj);
						System.out.println("�׼Ǹ����� cj.result"+cj.result);
						if(cj.result == 1) {
							System.out.println("���Լ���");
							JOptionPane.showMessageDialog(jp_gaip, "���ԵǾ����ϴ�.", "���Լ���", JOptionPane.DEFAULT_OPTION);
						}
						else {
							JOptionPane.showMessageDialog(jp_gaip, "���Կ� �����Ͽ����ϴ�.", "���Խ���", JOptionPane.DEFAULT_OPTION);
						}
					}	
				}
			} else if(e.getSource()==jmi_back) {
				card.show(jp_card,"�α���â"); 
			}
			
			// ����ȭ�� �̺�Ʈ
			if (e.getActionCommand().equals("ģ�����")) {
				card.show(jp_card, "ģ�����");
			} else if (e.getActionCommand().equals("��ȭ���")) {
				card.show(jp_card, "��ȭ���");
			} 
			
			// �޴��� �̺�Ʈ
			if (e.getActionCommand().equals("���ο� ģ��")) {
				new SearchDialog(this);
			} else if (e.getActionCommand().equals("���ο� ä��")) {
				new CreateRoomDialog(this);
			} else if (e.getActionCommand().equals("ȸ����������")) {
				new UpdateDialog(this);
			} else if (e.getActionCommand().equals("����")) {
				System.exit(0);
			}
			
			//���콺 �˾� �̺�Ʈ
			if(e.getActionCommand().equals("�����ʺ���")) {
				int row = friendTable.getSelectedRow();
				if(row == -1)
					return;
				MemberVO memVO = friendTableModel.getValueAt(row, 0);
				new ProfileDialog(memVO);
			} else if(e.getActionCommand().equals("�����ϱ�")) {
				int row = friendTable.getSelectedRow();
				if(row == -1)
					return;
				MemberVO f_memVO = friendTableModel.getValueAt(row, 0);
				String f_id = f_memVO.getMem_id();
				System.out.println("�����ϱ�. ģ�� ���̵� : " + f_id);
				clientData.actionDeleteFriend(f_id);
			}
			else if(e.getActionCommand().equals("��ȭ�ϱ�")) {
				JOptionPane.showMessageDialog(null, "�׳�... ī�徲���� �� �̰ɽ��...", "Easter Egg", 1);
				System.out.println("�׳�.. ī�徲����...");
				JOptionPane.showMessageDialog(null, "��!! �Ӿ���!!? ��ȭ ������ �Դϴ�.", "Easter Egg", 2);
				JOptionPane.showMessageDialog(null, "��~ �ǼӾ�����~ �̰� �������� ī����^_^", "Easter Egg", 3);
				
			}
			else if(e.getActionCommand().equals("����÷��")) {
				JOptionPane.showInputDialog(null, "���ϰ�θ� �Է����ּ���!!", "Easter Egg", 1);
				System.out.println("�׳�.. ī�徲����...");
				JOptionPane.showMessageDialog(null, "�׳�... ī�徲���� �� �̰ɽ��...", "Easter Egg", 1);
				
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
		public void keyPressed(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {}

		@Override
		public void keyTyped(KeyEvent e) {
			if(e.getSource() == jtf_id) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_id.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gid) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gid.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_pw) {
				if(e.getKeyChar()=='\n') {
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
						//�α��� ���н� �޼��� ���̾�α׸� ����.
						JOptionPane.showMessageDialog(null, "����", "�α��� ����", JOptionPane.ERROR_MESSAGE);
					}
				}
				else if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_pw.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gpw) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gpw.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gpw_re) {
				if(Character.isLetterOrDigit(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gpw_re.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_gname) {
				if(Character.isLetter(e.getKeyChar()) == false)
					e.consume();
				else if(jtf_gname.getText().length()>=20)
					e.consume();
			}
			else if(e.getSource() == jtf_ghp) {
				if(jtf_ghp.getText().length()>=20)
					e.consume();
			}
		}
}


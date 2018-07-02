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
	///////�����
	//������â
	JDialog				jd_profile = new JDialog();
	JPanel				jp_profile = new JPanel();
	JLabel				jl_profile = new JLabel();
	
	//ģ���˻�â
	JDialog 	jd_shfri  = new JDialog();
	JTextField  jtf_shfri = new JTextField();
	JButton 	jbtn_gum  = new JButton("�˻�");
	
	//ä��â
	JDialog 			jd_chat	   = new JDialog();
	JTextPane 			jtf_South  = new JTextPane();
	JTextPane 			jtp_Center = new JTextPane();
	JPanel				jp_North   = new JPanel();
	JPanel				jp_South   = new JPanel();
	JToolBar			jtb_North  = new JToolBar();
	JButton				jbtn_jun   = new JButton("����");
	JButton				jbtn_inv   = new JButton("ģ���ʴ�");
	JButton				jbtn_exit  = new JButton("ä�ù泪����");
	JButton				jbtn_close = new JButton("â�ݱ�");
	JButton				jbtn_emti  = new JButton("�̸�Ƽ��");
	JScrollPane 		jsp_South  = new JScrollPane(jtf_South,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane 		jsp_Center = new JScrollPane(jtp_Center,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JScrollPane 		jsp_North  = new JScrollPane(jtb_North);
	
	//ģ���ʴ�â
	JDialog				jd_inv	   = new JDialog();
	JTable				jt_inv	   = new JTable();
	JTextField			jtf_inv    = new JTextField();
	JButton				jbtn_ok    = new JButton("Ȯ��");
	JButton				jbtn_cancel= new JButton("���");
	JScrollPane 		jsp_inv    = new JScrollPane(jt_inv);
	
	//�̸�Ƽ��â
	JDialog				jd_emti    = new JDialog();
	JLabel				jl_emti    = new JLabel();
	String				img_path   = "C:\\Users\\516\\Desktop\\tales_emoticon\\";
	
	//ȸ������ ����â
	CardLayout 	card		= new CardLayout();
	JDialog		jd_upd		= new JDialog();
	JPanel		jp_card		= new JPanel();
	JPanel		jp_cert		= new JPanel();
	JPanel		jp_upd		= new JPanel();
	JLabel 		jl_unick  	= new JLabel("�г��� :");
	JLabel 		jl_upw 		= new JLabel("��й�ȣ :");
	JLabel 		jl_uname 	= new JLabel("�̸� :");
	JLabel 		jl_ugender  = new JLabel("���� :");
	JLabel 		jl_uhp		= new JLabel("�ڵ�����ȣ :");
	JTextField  jtf_cert 	= new JTextField(20);
	JTextField  jtf_unick	= new JTextField(20);
	JTextField  jtf_upw		= new JTextField(20);
	JTextField  jtf_uname 	= new JTextField(20);
	JTextField  jtf_uhp 	= new JTextField(20);
	String[] 	genderList  = {"����","����"};
	JComboBox<String>   jtf_ugender = new JComboBox<String>(genderList);
	JButton		jbtn_cert	= new JButton("�����ϱ�");
	JButton		jbtn_upd	= new JButton("����");
	JButton		jbtn_back	= new JButton("�ڷΰ���");	
	
	//������â ���̾�α�
	public void Profile() {
		jd_profile.setSize(350,500);
		jd_profile.setVisible(true);
		jd_profile.setTitle("������");
	}
	
	//ģ���˻� ���̾�α�
	public void addFriends() {
		jbtn_gum.addActionListener(this);
		jbtn_jun.addActionListener(this);
		jd_shfri.setTitle("ģ���˻�");
		jd_shfri.setSize(300, 200);
		jd_shfri.setVisible(true);
		jd_shfri.setLayout(null);
		jd_shfri.add(jtf_shfri);
		jtf_shfri.setVisible(true);
		jd_shfri.add(jbtn_gum);
		Font f = new Font("����", Font.CENTER_BASELINE, 10);
		jbtn_gum.setFont(f);
		;
		jbtn_gum.setBounds(120, 100, 60, 30);

	}
	
	//�� ä�� ���̾�α�
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
	
	//ģ���ʴ�â
	public void Invited() {
		jbtn_ok.addActionListener(this);
		jbtn_cancel.addActionListener(this);
		jd_inv.setTitle("ģ���ʴ�");
		jd_inv.setSize(400, 300);
		jd_inv.setVisible(true);
		jd_inv.setLayout(new GridLayout(4,1,0,0));
		jd_inv.add(jtf_inv);
		jd_inv.add(jbtn_ok);
		jd_inv.add(jbtn_cancel);
		Font f = new Font("����",Font.CENTER_BASELINE,10);
		jbtn_ok.setFont(f);
		jbtn_cancel.setFont(f);
		jt_inv.setVisible(true);
		jsp_inv.setVisible(true);
	}
	
	//�̸�Ƽ�� ���̾�α�â
	public void Emoticon() {
		jd_emti.setTitle("�̸�Ƽ��");
		jd_emti.setVisible(true);
		jd_emti.setSize(400,300);
		jd_emti.add(jl_emti);
		jl_emti.setVisible(true);
		ArrayList<JLabel> list = new ArrayList<JLabel>();
		for(int i=0;i<list.size();i++) {
			jl_emti.setIcon(new ImageIcon(img_path+list.get(i)));
			
		}
	}
	
	//ȸ������ ���� ���̾�α�â
	public void UpdateInfo() {
		jbtn_upd.addActionListener(this);
		jd_upd.setTitle("ȸ����������");
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
	
	//��ư �׼Ǻ�
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
				e2.printStackTrace();//����� �޼��� �̷°���
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
		if(e.getActionCommand().equals("�˻�")) {
			System.out.println("�˻�����");
			try {
				// �˻� ��û Ŭ���̾�Ʈ
				// �����ϸ�
				answer = JOptionPane.showConfirmDialog(jd_shfri, jtf_shfri.getText() + "���� �߰��Ͻðڽ��ϱ�?", "ģ���߰�",
						JOptionPane.YES_NO_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					try {
						// ģ�� �߰� Ŭ���̾�Ʈ
						// �߰��ϸ�
						JOptionPane.showMessageDialog(jd_shfri, jtf_shfri.getText() + "���� �߰��Ǿ����ϴ�. \n ����� �����մϴ�.",
								"ģ���߰�", JOptionPane.OK_OPTION);
					} catch (Exception fe2) {
						// TODO: handle exception
						JOptionPane.showMessageDialog(jd_shfri, "ģ���߰��� �����Ͽ����ϴ�.", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else if (answer == JOptionPane.NO_OPTION) {
					jd_shfri.dispose();
				}
			} catch (Exception fe) {
				JOptionPane.showMessageDialog(jd_shfri, jtf_shfri.getText() + "���� ã�� �� �����ϴ�.", "�˻�����",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		//Emoticon
		if(e.getActionCommand().equals("����")) {
			System.out.println("���۽���");
		}
		if(e.getActionCommand().equals("�̸�Ƽ��")) {
			System.out.println("�̸�Ƽ�ܽ���");
			Emoticon();
		}
		//Invited
		if(e.getActionCommand().equals("ģ���ʴ�")) {
			System.out.println("�ʴ����");
			Invited();
		}
		if(e.getActionCommand().equals("Ȯ��")) {
			System.out.println("Ȯ�ν���");		
		}
		else if(e.getActionCommand().equals("���")) {
			System.out.println("��ҽ���");
			System.exit(0);
		}
		//ȸ������ ����
		if(e.getActionCommand().equals("�����ϱ�")) {
			card.show(jp_card, "����â");
		}
		else if(e.getActionCommand().equals("����")) {
			System.out.println("MemberUpdate Ŭ���� ȣ��");
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

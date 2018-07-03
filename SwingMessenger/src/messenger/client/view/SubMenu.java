package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
	JFrame		clientFrame;
	
	//������â
	JDialog	jd_profile;
	JPanel	jp_profile;
	JLabel	jl_profile;
	
	//ģ���˻�â
	JDialog 	jd_shfri;
	JTextField  jtf_shfri;
	JButton 	jbtn_gum;
	
	//ģ���ʴ�â
	JDialog				jd_inv	   = new JDialog();
	JTable				jt_inv	   = new JTable();
	JTextField			jtf_inv    = new JTextField();
	JButton				jbtn_ok    = new JButton("Ȯ��");
	JButton				jbtn_cancel= new JButton("���");
	JScrollPane 		jsp_inv    = new JScrollPane(jt_inv);
	
	
	//ȸ������ ����â
//	CardLayout 	card		= new CardLayout();
	JDialog				jd_upd;
	JPanel				jp_card;
	JPanel				jp_cert;
	JPanel				jp_upd;
	JLabel 				jl_unick;
	JLabel 				jl_upw;
	JLabel 				jl_uname;
	JLabel 				jl_ugender;
	JLabel 				jl_uhp;
	JTextField  		jtf_cert;
	JTextField  		jtf_unick;
	JTextField  		jtf_upw;
	JTextField  		jtf_uname;
	JTextField  		jtf_uhp;
	String[] 			genderList;
	JComboBox<String>   jtf_ugender;
	JButton				jbtn_cert;
	JButton				jbtn_upd;
	JButton				jbtn_back;
	
	SubMenu(JFrame clientFrame) {
		this.clientFrame = clientFrame;
	}
	
	//������â ���̾�α�
//	public void Profile() {
//		jd_profile = new JDialog(clientFrame, true);
//		jp_profile = new JPanel();
//		jl_profile = new JLabel();
//		
//		jd_profile.setSize(350,500);
//		jd_profile.setVisible(true);
//		jd_profile.setTitle("������");
//	}
	
	//ģ���˻� ���̾�α�
//	public void addFriends() {
//		jd_shfri  = new JDialog(clientFrame, true);
//		jtf_shfri = new JTextField();
//		jbtn_gum  = new JButton("�˻�");
//		
//		jbtn_gum.addActionListener(this);
//		jd_shfri.setTitle("ģ���˻�");
//		jd_shfri.setLayout(null);
//		jd_shfri.setSize(300, 200);
//		jd_shfri.add(jtf_shfri);
//		jd_shfri.add(jbtn_gum);
//		
//		Font f = new Font("����", Font.CENTER_BASELINE, 10);
//		jbtn_gum.setFont(f);
//		jbtn_gum.setBounds(120, 100, 60, 30);
//
//		jd_shfri.setVisible(true);
//	}
	
	
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
	
	//ȸ������ ���� ���̾�α�â
//	public void UpdateInfo() {
//		jd_upd		= new JDialog(clientFrame, true);
//		jp_card		= new JPanel();
//		jp_cert		= new JPanel();
//		jp_upd		= new JPanel();
//		jl_unick  	= new JLabel("���¸޽��� :");
//		jl_upw 		= new JLabel("��й�ȣ :");
//		jl_uname 	= new JLabel("�̸� :");
//		jl_ugender  = new JLabel("���� :");
//		jl_uhp		= new JLabel("�ڵ�����ȣ :");
//		jtf_cert 	= new JTextField(20);
//		jtf_unick	= new JTextField(20);
//		jtf_upw		= new JTextField(20);
//		jtf_uname 	= new JTextField(20);
//		jtf_uhp 	= new JTextField(20);
//		genderList  = new String[]{"����","����"};
//		jtf_ugender = new JComboBox<String>(genderList);
//		jbtn_cert	= new JButton("�����ϱ�");
//		jbtn_upd	= new JButton("����");
//		jbtn_back	= new JButton("�ڷΰ���");	
//		
//		jbtn_upd.addActionListener(this);
//		jd_upd.setTitle("ȸ����������");
//		jd_upd.setLayout(null);
//		jd_upd.setSize(350,400);
//		jd_upd.setBackground(Color.YELLOW);
//		jd_upd.add(jbtn_upd);
//		jd_upd.add(jbtn_back);
//		jd_upd.add(jl_unick);
//		jd_upd.add(jl_upw);
//		jd_upd.add(jl_uname);
//		jd_upd.add(jl_uhp);
//		jd_upd.add(jl_ugender);
//		jd_upd.add(jtf_unick);
//		jd_upd.add(jtf_upw);
//		jd_upd.add(jtf_uname);
//		jd_upd.add(jtf_ugender); 
//		jd_upd.add(jtf_uhp);
//		jd_upd.setBackground(new Color(126, 195, 237));
//		jl_unick.setBounds(30, 30, 80, 20);
//		jl_upw.setBounds(30, 70, 80, 20);
//		jl_uname.setBounds(30, 110, 80, 20);
//		jl_ugender.setBounds(30, 150, 80, 20);
//		jl_uhp.setBounds(30, 190, 80, 20);
//		jl_uhp.setBounds(30, 190, 80, 20);
//		jtf_unick.setBounds(120, 30, 180, 20);
//		jtf_upw.setBounds(120, 70, 180, 20);
//		jtf_uname.setBounds(120, 110, 180, 20);
//		jtf_ugender.setBounds(120, 150, 180, 20);
//		jtf_uhp.setBounds(120, 190, 180, 20);
//		jbtn_upd.setBounds(90, 250, 180, 30);
//		jbtn_back.setBounds(90, 280, 180, 30);
//		jd_upd.setVisible(true);
//	}
	
	//��ư �׼Ǻ�
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int answer = 0;
		Object obj = e.getSource();
		
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
		if(e.getActionCommand().equals("Ȯ��")) {
			System.out.println("Ȯ�ν���");		
		}
		else if(e.getActionCommand().equals("���")) {
			System.out.println("��ҽ���");
			System.exit(0);
		}
		//ȸ������ ����
		if(e.getActionCommand().equals("�����ϱ�")) {
//			card.show(jp_card, "����â");
		}
		else if(e.getActionCommand().equals("����")) {
			System.out.println("MemberUpdate Ŭ���� ȣ��");
//			MemberUpdate mupd = new MemberUpdate();
//			mupd.Update();
		}
	}
	
	public static void main(String[] args) {
		ClientFrame f = new ClientFrame();
		f.initDisplay();
		SubMenu ad = new SubMenu(f);
		//ad.UpdateInfo();
		//ad.Profile();
		ad.Invited();
		
	}
}

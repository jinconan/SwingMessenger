package messenger.client.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.CardLayout;

public class Friends implements ActionListener {
	CardLayout  card 		= new CardLayout();
	JFrame 		jf_m	 	= new JFrame();
	//�޴���
	JMenuBar 	jm_menu 	= new JMenuBar();
	JMenu 		jm_fri 		= new JMenu("ģ�����");
	JMenu 		jm_chat 	= new JMenu("��ȭ���");
	JMenu 		jm_ 		= new JMenu("����");
	JMenu 		jm_news 	= new JMenu("����");
	//����â
	JPanel 		jp_card = new JPanel();
	JPanel 		jp_my 	= new JPanel();
	JLabel 		jl_my 	= new JLabel("��â");
	JTextField 	jtf_my 	= new JTextField();
	//�̹����� �гη� ������? �̹����� ���� �ٸ� ����� �ֳ�?
	JPanel 		jp_fri	= new JPanel();
	JLabel 		jl_fri 	= new JLabel("ģ��â");
	JTextField 	jtf_fri = new JTextField(); 
	JScrollPane jsp_list= new JScrollPane(jp_card);
	 
	public void initDisplay() {
		jf_m.setSize(500, 600);
		jf_m.setVisible(true);
		jf_m.add("West", jm_menu);
		//�޴���
		jm_menu.setLayout(new GridLayout(4,1,0,0));
		jm_menu.add(jm_fri);
		jm_menu.add(jm_chat);
		jm_menu.add(jm_);
		jm_menu.add(jm_news);
		//ī��
		jp_card.setLayout(card);
		jp_card.add(jp_my);
		jp_card.add(jp_fri);
		//���â
		jf_m.add("Center",jp_card);
		jp_card.setLayout(new GridLayout(5,1,0,0));
		jp_card.add(jp_my);
		jp_my.setBackground(Color.CYAN);
		jp_my.add(jl_my);
		jp_card.add(jp_fri);
		jp_fri.add(jl_fri);
		jp_fri.setBackground(Color.PINK);
		//���̺� �߰��� ���� �߰� �г�
		
				
	}
	public static void main(String[] args) {
		Friends fr = new Friends();
		fr.initDisplay();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

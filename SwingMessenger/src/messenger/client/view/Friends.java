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
	//메뉴바
	JMenuBar 	jm_menu 	= new JMenuBar();
	JMenu 		jm_fri 		= new JMenu("친구목록");
	JMenu 		jm_chat 	= new JMenu("대화목록");
	JMenu 		jm_ 		= new JMenu("뭐지");
	JMenu 		jm_news 	= new JMenu("뉴스");
	//상태창
	JPanel 		jp_card = new JPanel();
	JPanel 		jp_my 	= new JPanel();
	JLabel 		jl_my 	= new JLabel("내창");
	JTextField 	jtf_my 	= new JTextField();
	//이미지를 패널로 넣을까? 이미지를 넣을 다른 기능이 있나?
	JPanel 		jp_fri	= new JPanel();
	JLabel 		jl_fri 	= new JLabel("친구창");
	JTextField 	jtf_fri = new JTextField(); 
	JScrollPane jsp_list= new JScrollPane(jp_card);
	 
	public void initDisplay() {
		jf_m.setSize(500, 600);
		jf_m.setVisible(true);
		jf_m.add("West", jm_menu);
		//메뉴바
		jm_menu.setLayout(new GridLayout(4,1,0,0));
		jm_menu.add(jm_fri);
		jm_menu.add(jm_chat);
		jm_menu.add(jm_);
		jm_menu.add(jm_news);
		//카드
		jp_card.setLayout(card);
		jp_card.add(jp_my);
		jp_card.add(jp_fri);
		//목록창
		jf_m.add("Center",jp_card);
		jp_card.setLayout(new GridLayout(5,1,0,0));
		jp_card.add(jp_my);
		jp_my.setBackground(Color.CYAN);
		jp_my.add(jl_my);
		jp_card.add(jp_fri);
		jp_fri.add(jl_fri);
		jp_fri.setBackground(Color.PINK);
		//테이블 추가에 따른 추가 패널
		
				
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

package SemiProjectChat;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class display extends JFrame implements ActionListener,MouseListener{
	public display() {
		
	}

	JPanel         contentPane;
	JList          friend_list                = null;
	JList          Message_display            = new JList();
	JTextField     Message_input              = new JTextField();
	JButton        jbt_Exit                   = new JButton("");
	JLabel         lblNewLabel_1              = new JLabel("New label");
	JButton        jbt_friend                 = new JButton();
	JButton        jbt_Message                = new JButton();
	JPanel         panel                      = new JPanel();
	JButton        jbt_others                 = new JButton();
	JLabel         lblNewLabel                = new JLabel("New label");
	CardLayout     card                       = new CardLayout(5, 5);  
    JPanel         jPanel                     = new JPanel();
    String         ID                         = null;
    String         NickName                   = null;
    DAO            dao                        = new DAO();
	MemberDTO      dto                        = new MemberDTO();
	FriendDTO      f_dto                      = new FriendDTO();
	FriendDTO      f_dto1                     = new FriendDTO();
	TalkDTO        t_dto                      = new TalkDTO();
	Vector         list                       = null;

	
	
	
	JTextField User_no = new JTextField();
	JTextField Room_title = new JTextField();
	JLabel Room_Jcreate = new JLabel("방 만들기");
	JLabel Room_JTitle = new JLabel("제목");
	JLabel Room_JFriend = new JLabel("친구 초대");
	JComboBox comboBox = new JComboBox();
	JButton Room_Jadd = new JButton("추가");
	JList Room_list = new JList();
	JLabel User_Jno = new JLabel("번호");
	JButton User_jbtadd = new JButton("추가");
	JLabel User_Jadd = new JLabel("친구추가");
	
	
    public display(String text, String idPw) {
		this.ID=text;
        this.NickName = idPw;
        System.out.println("display :"+NickName);
		initdisplay();
	
	}


 


	public void initdisplay(){
		
	
		      //    여백을 설정한 CardLayout을 만든다.
		list = new Vector();
		dto.setMEM_ID(ID);
		list=dao.friend(dto);
			
		friend_list = new JList(list);
		
		friend_list.addMouseListener(this);
		jPanel.setLayout(card);
		jbt_others.addActionListener(this);
		jbt_friend.addActionListener(this);
		jbt_Exit.addActionListener(this);
		jbt_Message.addActionListener(this);
		User_jbtadd.addActionListener(this);
		Room_Jadd.addActionListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 358, 699);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		jPanel.setBounds(32, 171, 278, 411);
		jPanel.add("first",friend_list);
		contentPane.add(jPanel);
		
		jPanel.add("second",Message_display);
		contentPane.add(jPanel);
	
		jPanel.add("three",panel);
		contentPane.add(jPanel);
		panel.setBackground(Color.white);
        panel.setLayout(null);
		
		User_Jadd.setBounds(14, 12, 62, 18);
		panel.add(User_Jadd);
		
	
		
		User_jbtadd.setBackground(Color.WHITE);
		User_jbtadd.setBounds(14, 125, 235, 27);
		panel.add(User_jbtadd);

		
		User_Jno.setBounds(14, 83, 62, 18);
		panel.add(User_Jno);
		
		User_no = new JTextField();
		User_no.setBounds(102, 80, 147, 24);
		panel.add(User_no);
		User_no.setColumns(10);
		
		
		Room_Jcreate.setBounds(14, 164, 62, 18);
		panel.add(Room_Jcreate);
		
		Room_JTitle.setBounds(14, 194, 62, 18);
		panel.add(Room_JTitle);
		
		Room_title = new JTextField();
		Room_title.setBounds(102, 191, 147, 24);
		panel.add(Room_title);
		Room_title.setColumns(10);
		
		
	
		Room_JFriend.setBounds(14, 234, 62, 18);
		panel.add(Room_JFriend);
		
		
		comboBox.setBounds(102, 231, 147, 24);
		panel.add(comboBox);
		
		
		
		Room_Jadd.setBackground(Color.WHITE);
		Room_Jadd.setBounds(14, 376, 235, 27);
		panel.add(Room_Jadd);
		
	
		Room_list.setBounds(14, 264, 235, 102);
		panel.add(Room_list);
	//
		jbt_Exit.setBackground(Color.BLACK);
		jbt_Exit.setBounds(121, 615, 97, 23);
		contentPane.add(jbt_Exit);
		
		
		lblNewLabel_1.setIcon(new ImageIcon("D:\\\\ojdbc6\\\\Semiproject\\\\src\\\\images\\\\a.png"));
		lblNewLabel_1.setBounds(32, 77, 278, 34);
		contentPane.add(lblNewLabel_1);
		
		
		jbt_friend.setIcon(new ImageIcon("D:\\\\ojdbc6\\\\Semiproject\\\\src\\\\images\\\\south.png"));
		jbt_friend.setBounds(32, 114, 50, 44);
		contentPane.add(jbt_friend);
		
	
		jbt_Message.setIcon(new ImageIcon("D:\\\\ojdbc6\\\\Semiproject\\\\src\\\\images\\\\b.png"));
		jbt_Message.setBounds(94, 114, 50, 44);
		contentPane.add(jbt_Message);
		
		
		jbt_others.setIcon(new ImageIcon("D:\\ojdbc6\\Semiproject\\src\\images\\c.png"));
		jbt_others.setBounds(156, 114, 50, 44);
		contentPane.add(jbt_others);
		
		
		lblNewLabel.setIcon(new ImageIcon("D:\\ojdbc6\\Semiproject\\src\\images\\d.jpg"));
		lblNewLabel.setBounds(-32, 0, 374, 664);
		contentPane.add(lblNewLabel);
		this.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	
		if(arg0.getSource()==jbt_friend) {
			
			card.show(jPanel, "first");
	
		}
		else if(arg0.getSource()==jbt_Message) {
			
			card.show(jPanel, "second");
			System.out.println("btnNewButton");
		}
      
        else if(arg0.getSource()==jbt_others) {
        	card.show(jPanel, "three");
        	
        }
        else if(arg0.getSource()==User_jbtadd) {
        	System.out.println("ggg");
        	
        	dto.setMEM_ID(ID);
            Vector User  = dao.friend_add_select(dto);
            dto.setMEM_HP(User_no.getText());
            Vector User2 =  dao.friend_add_select2(dto);
          

            boolean isok = true;
            try {
            	
                  	f_dto.setFRIEND_NAME    ((String) User2.elementAt(0));
                	f_dto.setFRIEND      ((int)    User2.elementAt(1));
                	f_dto.setFRIEND_HIREDATE((String) User2.elementAt(2));
                	f_dto.setMEM_NO         ((int)    User.elementAt(1));
                	f_dto1.setFRIEND_NAME   ((String) User.elementAt(0));
                	f_dto1.setFRIEND     ((int)    User.elementAt(1));
                	f_dto1.setFRIEND_HIREDATE((String)User.elementAt(2));
                	f_dto1.setMEM_NO        ((int)    User2.elementAt(1));
                	dao.friend_add(f_dto,f_dto1);
                
                	
                	list = new Vector();
         
                	dto.setMEM_ID(ID);
            		list=dao.friend(dto);
            		
           
            		friend_list.setListData(list);
            	
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "번호를 제대로 입력하세요", "알림", JOptionPane.ERROR_MESSAGE);
			}
     
        }
        else if(arg0.getSource()==Room_Jadd) {
        	System.out.println("firend_add");
        }
        else if(arg0.getSource()==jbt_Exit) {
        	this.dispose();
        }
       
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if(e.getSource()!=null) {
			System.out.println(friend_list.getSelectedValue());
			

			System.out.println("1");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()!=null) {
			System.out.println(friend_list.getSelectedValue()+"Preassed");
		
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()!=null) {
			System.out.println(friend_list.getSelectedValue()+"Released");
		
		   
		}
	}

}

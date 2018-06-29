package messenger.server.view;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import messenger.protocol.Message;
import messenger.server.chat.ChatServer;
import messenger.server.chat.ChatServerThread;
import messenger.server.chat.ChatServerThreadList;
import messenger.server.emoticon.EmoticonServer;
import messenger.server.friend.FriendServer;
import messenger.server.login.LoginServer;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerView extends JFrame {
	//서버 소켓
	private LoginServer loginServer;
	private ChatServer	chatServer;
	private	FriendServer friendServer;
	private EmoticonServer emoticonServer;
	
	private JPanel contentPane;
	private JScrollPane jsp_loginlog;
	private JScrollPane jsp_chatlog;
	private JScrollPane jsp_friendlog;
	private JTextArea jta_loginlog;
	private JTextArea jta_chatlog;
	private JTextArea jta_friendlog;
	private JLabel jlb_loginlog;
	private JLabel jlb_chatlog;
	private JLabel jlb_friendlog;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//DefaultTableModel 잠시 제거.
	private JTable jtb_member;
	private JTable jtb_room;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerView frame = new ServerView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ServerView() {
		setResizable(false);
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		int width = (int)screenSize.getWidth();
		int height = (int)screenSize.getHeight();
		setBounds(width/8 , height/8, width * 3/4, height* 3/4);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel jp_north = new JPanel();
		contentPane.add(jp_north, BorderLayout.NORTH);
		jp_north.setLayout(new GridLayout(1, 4, 0, 0));
		
		jlb_loginlog = new JLabel("\uB85C\uADF8\uC778 \uB85C\uADF8");
		jp_north.add(jlb_loginlog);
		jlb_loginlog.setHorizontalAlignment(SwingConstants.CENTER);
		
		jlb_chatlog = new JLabel("\uCC44\uD305 \uB85C\uADF8");

		jp_north.add(jlb_chatlog);
		jlb_chatlog.setHorizontalAlignment(SwingConstants.CENTER);
		
		jlb_friendlog = new JLabel("\uCE5C\uAD6C \uB85C\uADF8");
		jp_north.add(jlb_friendlog);
		jlb_friendlog.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		JLabel jlb_emoticonlog = new JLabel("\uC774\uBAA8\uD2F0\uCF58 \uB85C\uADF8");
		jp_north.add(jlb_emoticonlog);
		jlb_emoticonlog.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel jp_center = new JPanel();
		contentPane.add(jp_center, BorderLayout.CENTER);
		jp_center.setLayout(new GridLayout(1, 4, 0, 0));
		
		jsp_loginlog = new JScrollPane();
		jp_center.add(jsp_loginlog);
		jsp_loginlog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		jta_loginlog = new JTextArea();
		jta_loginlog.setEditable(false);
		jta_loginlog.setLineWrap(true);
		jsp_loginlog.setViewportView(jta_loginlog);
		
		JPanel jp_chat = new JPanel();
		jp_center.add(jp_chat);
		jp_chat.setLayout(new GridLayout(3, 1, 0, 0));
		
		jsp_chatlog = new JScrollPane();
		jp_chat.add(jsp_chatlog);
		jsp_chatlog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		jta_chatlog = new JTextArea();
		jta_chatlog.setEditable(false);
		jta_chatlog.setLineWrap(true);
		jsp_chatlog.setViewportView(jta_chatlog);
		
		JScrollPane jsp_room = new JScrollPane();
		jp_chat.add(jsp_room);
		
		jtb_room = new JTable();

		
		jsp_room.setViewportView(jtb_room);
		
//		refreshRoomList();
//		dtm_room = new DefaultTableModel();
//		dtm_room.setColumnIdentifiers(cols_room);
//		dtm_room.addRow(new String[] {"0","0명"});
//		jtb_room.setModel(dtm_room);
		
		
		
		JScrollPane jsp_member = new JScrollPane();
		jp_chat.add(jsp_member);
		
		jtb_member = new JTable();
		jsp_member.setViewportView(jtb_member);
		
		
		jsp_friendlog = new JScrollPane();
		jp_center.add(jsp_friendlog);
		jsp_friendlog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		jta_friendlog = new JTextArea();
		jta_friendlog.setEditable(false);
		jta_friendlog.setLineWrap(true);
		jsp_friendlog.setViewportView(jta_friendlog);
		
		JScrollPane jsp_emoticonlog = new JScrollPane();
		jp_center.add(jsp_emoticonlog);
		jsp_emoticonlog.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTextArea jta_emoticonlog = new JTextArea();
		jta_emoticonlog.setLineWrap(true);
		jta_emoticonlog.setEditable(false);
		jsp_emoticonlog.setViewportView(jta_emoticonlog);
		
		JPanel jp_south = new JPanel();
		contentPane.add(jp_south, BorderLayout.SOUTH);
		jp_south.setLayout(new GridLayout(1, 4, 0, 0));
		
		JButton jbtn_login = new JButton("\uC2DC\uC791");
		jp_south.add(jbtn_login);
		
		JButton jbtn_chat = new JButton("\uC2DC\uC791");
		jp_south.add(jbtn_chat);
		
		JButton jbtn_friend = new JButton("\uC2DC\uC791");
		jp_south.add(jbtn_friend);
		
		JButton jbtn_emoticon = new JButton("\uC2DC\uC791");
		jp_south.add(jbtn_emoticon);
		jbtn_emoticon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SwingWorker<Object, Object>() {
					@Override
					protected Object doInBackground() throws Exception {
						emoticonServer = new EmoticonServer(jta_emoticonlog);
						emoticonServer.run();
						return null;
					}
				}.execute();
			}
		});
		jbtn_friend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Object, Object>() {
					@Override
					protected Object doInBackground() throws Exception {
						friendServer = new FriendServer(jta_friendlog);
						friendServer.run();
						return null;
					}
				}.execute();
			}
		});
		jbtn_chat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Object, Object>() {
					@Override
					protected Object doInBackground() throws Exception {
						chatServer = new ChatServer(jta_chatlog);
						chatServer.run();
						return null;
					}
				}.execute();
			}
		});
		jbtn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new SwingWorker<Object, Object>() {
					@Override
					protected Object doInBackground() throws Exception {
						loginServer = new LoginServer(jta_loginlog);
						loginServer.run();
						return null;
					}
				}.execute();
			}
		});
		
	}

	
	public JTextArea getJta_loginlog() {
		return jta_loginlog;
	}

	public JTextArea getJta_chatlog() {
		return jta_chatlog;
	}

	public JTextArea getJta_friendlog() {
		return jta_friendlog;
	}
	
}

package messenger.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import messenger._db.DBConnection;
import messenger._db.dao.LoginDAO;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.DefaultComboBoxModel;

public class ChatTest extends JFrame {
	private Socket socket;
	private JPanel contentPane;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private MemberVO mem = null;
	private HashMap<Integer, RoomDialog> roomList = new HashMap<Integer, RoomDialog>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatTest frame = new ChatTest();
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
	public ChatTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		SwingWorker<Object,Object> worker = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				getMember(); //회원정보 가져오기
				System.out.println(mem);
				socket = new Socket("localhost", Port.CHAT);
				oos = new ObjectOutputStream(socket.getOutputStream());
				
				ArrayList<ChatVO> request = new ArrayList<ChatVO>();
				request.add(new ChatVO(0, 0, null, null, mem.getMem_no(),mem.getMem_name()));
				
				Message<ChatVO> msg = new Message<ChatVO>(Message.CHATROOM_LOAD,request,null);
				oos.writeObject(msg);
				oos.flush();
				
				ois = new ObjectInputStream(socket.getInputStream());
				msg = (Message<ChatVO>)ois.readObject();
				
				ArrayList<ChatVO> response = (ArrayList<ChatVO>)msg.getResponse();
				for(ChatVO chat : response) {
					roomList.put(chat.getRoom_no(), new RoomDialog(mem,new RoomVO(chat.getRoom_no(),"hi")));
				}
				Thread th = new Thread(new testRunnable());
				th.start();
				
				return null;
			}
		};
		
		worker.execute();
		
	}

	void getMember() {
		DBConnection dbCon = new DBConnection();
		String sql = "SELECT mem_no, mem_id, mem_name, mem_nick, mem_gender, mem_pw FROM member WHERE mem_no=4";
		try(
				Connection con = dbCon.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();
				) {
			if(rs.next()) {
				int mem_no = rs.getInt("mem_no");
				String mem_id = rs.getString("mem_id");
				String mem_name = rs.getString("mem_name");
				String mem_nick = rs.getString("mem_nick");
				String mem_gender = rs.getString("mem_gender");
				String mem_pw = rs.getString("mem_pw");

				mem = new MemberVO(mem_no, mem_id, mem_name, mem_nick, mem_gender, mem_pw,null,null,null);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void printChat(ChatVO chat) {

		if(roomList.containsKey(chat.getRoom_no())) {
			//방 리스트 해쉬맵에 해당 방 번호를 갖는 다이얼로그가 있으면 그 다이얼로그에 받은 메시지 출력
			if(chat.getMem_no() == mem.getMem_no())
				roomList.get(chat.getRoom_no()).append("나> "+chat.getChat_content());
			else
				roomList.get(chat.getRoom_no()).append(chat.getRoom_no() + "> "+chat.getChat_content());
		}
	}
	
	class testRunnable implements Runnable {
		@Override
		public void run() {
			while(true) {
				try {
					Message<ChatVO> msg = (Message<ChatVO>)ois.readObject();
					ArrayList<ChatVO> response = (ArrayList<ChatVO>) msg.getResponse();
					ChatVO chatVO = response.get(0);
					System.out.println(chatVO.getRoom_no()+" | " + chatVO.getChat_content());
					printChat(chatVO);
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
	class RoomDialog extends JDialog {
		RoomVO	roomVO;
		MemberVO memberVO;
		JTextArea jta = new JTextArea();
		JTextField jtf = new JTextField();
		JScrollPane jsp = new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD hh:mm:ss");
		RoomDialog(MemberVO memberVO, RoomVO roomVO) {
			this.memberVO = memberVO;
			this.roomVO = roomVO;
			getContentPane().setLayout(new BorderLayout());
			this.setSize(300, 300);
			this.setResizable(false);
			this.setTitle(memberVO.getMem_name()+ " | 방:" + Integer.toString(roomVO.getRoom_no()));
			jta.setLineWrap(true);
			jtf.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if(e.getKeyChar()=='\n') {
						ArrayList<ChatVO> request = new ArrayList<ChatVO>();
						ChatVO chat = new ChatVO(0, roomVO.getRoom_no(), jtf.getText(), format.format(new Date()), memberVO.getMem_no(), memberVO.getMem_name());
						request.add(chat);
						Message<ChatVO> msg = new Message<ChatVO>(Message.CHAT_SEND,request,null);
						
						try {
							oos.writeObject(msg);
							oos.flush();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						jtf.setText("");
					}
				}
			});
			getContentPane().add(jsp,BorderLayout.CENTER);
			getContentPane().add(jtf, BorderLayout.SOUTH);
			this.setVisible(true);
		}
		
		void append(String s) {
			jta.append(s+"\n");
			jta.revalidate();
		}
	}
	
}

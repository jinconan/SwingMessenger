package messenger.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.protocol.Port;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ChatTest extends JFrame {
	private Socket socket;
	private JPanel contentPane;
	private JTextField jtf_room;
	private JTextField jtf_content;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private MemberVO mem = new MemberVO(2, "test1", "테스트", "호구", "남", "1111", "010-1234-5678", null, null);
	private JComboBox<Integer> jcb_type;
	private JTextArea jta_log;
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
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		jta_log = new JTextArea();
		jta_log.setLineWrap(true);
		jta_log.setEditable(false);
		scrollPane.setViewportView(jta_log);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		jcb_type = new JComboBox<Integer>();
		jcb_type.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {Message.CHAT_SEND, Message.CHATROOM_LOAD}));
		panel.add(jcb_type);
		
		jtf_room = new JTextField();
		panel.add(jtf_room);
		jtf_room.setColumns(5);
		
		jtf_content = new JTextField();
		panel.add(jtf_content);
		jtf_content.setColumns(10);
		
		SwingWorker<Object,Object> worker = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				socket = new Socket("localhost", Port.CHAT);
				ois = new ObjectInputStream(socket.getInputStream());
				oos = new ObjectOutputStream(socket.getOutputStream());
				
				return null;
			}
		};
		
		JButton jbtn_send = new JButton("send");
		jbtn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//임의로 타입, 방 번호, 내용을 설정하고, 정해진 회원번호를 메시지에 포함시켜서 서버에 전달.
				ChatVO chat = new ChatVO();
				int room_no = Integer.parseInt(jtf_room.getText());
				String chat_content = jtf_content.getText();
				int mem_no = mem.getMem_no();
				
				chat.setRoom_no(room_no);
				chat.setChat_content(chat_content);
				chat.setMem_no(mem_no);
				
				ArrayList<ChatVO> request = new ArrayList<ChatVO>();
				request.add(chat);
				Message<ChatVO> msg = new Message<ChatVO>();
				
				msg.setType((Integer)jcb_type.getSelectedItem());
				msg.setRequest(request);
				
				try {
					oos.writeObject(msg);
					oos.flush();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		panel.add(jbtn_send);
		
		worker.execute();
	}

	class testRunnable implements Runnable {
		@Override
		public void run() {
			while(true) {
				try {
					Message<ChatVO> msg = (Message<ChatVO>)ois.readObject();
					ArrayList<ChatVO> response = (ArrayList<ChatVO>) msg.getResponse();
					ChatVO chatVO = response.get(0);
					int no = chatVO.getMem_no();
					String content = chatVO.getChat_content();
					int rno = chatVO.getRoom_no();
					
					jta_log.append(rno+" | " + no + " | " + content+"\n");
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
	}
	
}

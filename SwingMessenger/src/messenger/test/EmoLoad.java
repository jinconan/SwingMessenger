package messenger.test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

import messenger.protocol.Message;
import messenger.protocol.Port;

import java.awt.GridLayout;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class EmoLoad extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmoLoad frame = new EmoLoad();
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
	public EmoLoad() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(6, 6, 0, 0));
		contentPane.addContainerListener(new ContainerListener() {
			
			@Override
			public void componentRemoved(ContainerEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentAdded(ContainerEvent e) {
				contentPane.revalidate();
			}
		});
	
		SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {

			@Override
			protected Object doInBackground() throws Exception {
				Socket socket = new Socket("localhost",Port.EMOTICON);
				
				Message<JLabel> msg = new Message<JLabel>();
				msg.setType(Message.EMOTICON_LOAD);
				
				try (
					OutputStream os = socket.getOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(os); 
				) {
					oos.writeObject(msg);
					oos.flush();
					
					try (InputStream is = socket.getInputStream();
							BufferedInputStream bis = new BufferedInputStream(is);
							ObjectInputStream ois = new ObjectInputStream(bis);
					) {
						msg = (Message<JLabel>)ois.readObject();
						ArrayList<JLabel> response = (ArrayList<JLabel>)msg.getResponse();
						for(JLabel label : response) 
							contentPane.add(label);

					} catch(Exception e) {
						e.printStackTrace();
					}
					
				} catch(Exception e) {
					e.printStackTrace();
				}
				return null;
			}

		};
		worker.execute();;
	}

}

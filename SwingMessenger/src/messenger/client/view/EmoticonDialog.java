package messenger.client.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class EmoticonDialog extends JDialog{
	private ClientData clientData;
	JPanel	jp_emoticon;
	JScrollPane jsp_emoticon = new JScrollPane();
	ChatDialog chatDialog;
	
	public EmoticonDialog(ClientData clientData, ChatDialog chatDialog) {
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.clientData = clientData;
		this.chatDialog = chatDialog;
		
		jp_emoticon = clientData.getEmoticonPanel(chatDialog.jtf_chat);
		
		jsp_emoticon.setViewportView(jp_emoticon);
		this.setLayout(new BorderLayout());
		this.add("Center",jsp_emoticon);
		this.pack();
		this.setVisible(true);
	}
}

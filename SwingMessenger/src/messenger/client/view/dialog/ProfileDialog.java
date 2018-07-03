package messenger.client.view.dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ProfileDialog extends JDialog{
	JFrame	clientFrame;
	JPanel	jp_profile = new JPanel();
	JLabel	jl_profile = new JLabel();
	
	public ProfileDialog(JFrame clientFrame) {
		super(clientFrame, "ÇÁ·ÎÇÊ", true);
		this.clientFrame = clientFrame;
		this.setSize(350,500);
		this.setVisible(true);
	}
	
	
	
	
}

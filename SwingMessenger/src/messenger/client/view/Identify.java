package messenger.client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Identify implements ActionListener {
	JDialog 	jd_idf 		= new JDialog();
	JDialog		jd_status   = new JDialog();
	JLabel 		jl_img 		= new JLabel();
	JTextArea 	jta_idf		= new JTextArea();
	JTextField 	jtf_idf  	= new JTextField();
	JButton 	jbtn_idf 	= new JButton();
	JButton 	jbtn_fri 	= new JButton();
	JButton 	jbtn_chat	= new JButton();
	
	public void initDisplay() {
		jd_idf.setTitle("상태창");
		jd_idf.setVisible(true);
		jd_idf.add("Center",jta_idf);
		jd_idf.add(jbtn_fri);
		jd_idf.add(jbtn_chat);
	}
	
	public void Status() {
		jd_status.setTitle("상태메세지");
		jd_status.setVisible(true);
		jd_status.setSize(200,30);
		jd_status.add("Center", jtf_idf);
		jd_status.add("East", jbtn_idf);
		jtf_idf.setVisible(true);
		jtf_idf.setSize(170,30);
		jbtn_idf.setVisible(true);
		jbtn_idf.setSize(30,30);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals(jbtn_idf)) {
			jta_idf.setText(jtf_idf.getText());
		}
		if(e.getActionCommand().equals(jtf_idf)) {
			Status();
		}
	}
}

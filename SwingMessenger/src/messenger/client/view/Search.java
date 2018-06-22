package messenger.client.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Search {
	JDialog jd_gum = new JDialog();
	JPanel jp_gum = new JPanel();
	JButton jbtn_gum = new JButton();
	JButton jbtn_add = new JButton();
	JLabel jl_sid = new JLabel();
	JTextField jtf_gum = new JTextField();
	DefaultTableModel dftm_list = new DefaultTableModel();
	JTable jt_gum = new JTable(dftm_list);
	
	public void initDisplay() {
		jd_gum.setSize(400, 300);
		jd_gum.setVisible(true);
		jd_gum.add("Center",jt_gum);
		jd_gum.add("South", jp_gum);
		jp_gum.add(jl_sid);
		jp_gum.add(jtf_gum);
		jp_gum.add(jbtn_gum);
		jp_gum.add(jbtn_add);
		jp_gum.setBounds(null);
		jp_gum.setVisible(true);
		jl_sid.setBounds(30,250,20,20);
		jtf_gum.setBounds(70,250,100,20);
		jbtn_gum.setBounds(250,245,30,30);
		jbtn_add.setBounds(300,245,30,30);
		
		
	}
}

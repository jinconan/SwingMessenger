package messenger.client.view.dialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CreateRoomDialog extends JDialog implements ActionListener{
	
	private		  JFrame			   clientFrame;
	private final JPanel               contentPanel        = new JPanel();
	private       JTextField           jtf_title           = new JTextField();
	
	
	private       String[]             Head_friend_list    = {"친구리스트"};
	private       String[]             Head_Room_add_list  = {"대화할친구"};
	private       String[][]           data_friend_list    = new String[0][1];
	private       String[][]           data_Room_add_list  = new String[0][1];
	
	private       DefaultTableModel    model_friend_list   = new DefaultTableModel(Head_friend_list,0);
	private       DefaultTableModel    model_Room_add_list = new DefaultTableModel(Head_Room_add_list,0);
	
	private       JTable               friend_list         = new JTable(model_friend_list);
	private       JTable               Room_add_list       = new JTable(model_Room_add_list);
	
	private       JScrollPane          Jsc_friend_list     = new JScrollPane(friend_list);
	private       JScrollPane          Jsc_Room_add_list   = new JScrollPane(Room_add_list);
	
	private       JLabel               lblNewLabel         = new JLabel("방만들기");
	private       JLabel               lblNewLabel_1       = new JLabel("제목");
	private       JPanel               buttonPane          = new JPanel();
	private       JButton              jbt_add             = new JButton("Add");
	private       JButton              jbt_del             = new JButton("Del");
	private       JButton              jbt_ok              = new JButton("OK");
	private       JButton              jbt_Cancel          = new JButton("Cancel");
	
	private       Vector               list                = null;
	private       Vector               V_Room_add_list     = new Vector();
	
	public CreateRoomDialog(JFrame clientFrame) {
		this.clientFrame = clientFrame;
		init();
	}
	
	public void Test() {
		list = new Vector();
		list.add("김재현");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("이정렬");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("이진");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("최재영");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("최운철");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("김훈태");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("하하");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("유재석");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("강호동");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("신동렵");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("김종국");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("송지효");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("이석민");
		model_friend_list.addRow(list);
		list = new Vector();
		list.add("유석민");
		model_friend_list.addRow(list);
	}
	
	public void event() {
		
		jbt_add   .addActionListener(this);
		jbt_del   .addActionListener(this);
		jbt_ok    .addActionListener(this);
		jbt_Cancel.addActionListener(this);
		
	}
	
	public void init() {
		
		event();
		Test();
		setBounds(100, 100, 450, 315);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		lblNewLabel      .setBounds(12, 10, 57, 15);
		lblNewLabel_1    .setBounds(12, 33, 57, 15);
		jtf_title        .setBounds(72, 30, 146, 21);
		jbt_add          .setBounds(182, 88, 64, 23);
		jbt_del          .setBounds(182, 182, 64, 23);
		Jsc_friend_list  .setBounds(12, 73, 157, 160);
		Jsc_Room_add_list.setBounds(258, 73, 164, 160);
		
		jtf_title.setColumns(10);
		Jsc_friend_list.setViewportView(friend_list);
		Jsc_Room_add_list.setViewportView(Room_add_list);
		
		contentPanel.add(lblNewLabel);
		contentPanel.add(lblNewLabel_1);
		contentPanel.add(jtf_title);
		contentPanel.add(jbt_add);
		contentPanel.add(jbt_del);
		contentPanel.add(Jsc_friend_list);
		contentPanel.add(Jsc_Room_add_list);
		
		buttonPane.add(jbt_Cancel);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		buttonPane.add(jbt_ok);
		
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		getRootPane()   .setDefaultButton(jbt_ok);
		
		jbt_ok    .setActionCommand("OK");
		jbt_Cancel.setActionCommand("Cancel");
		this.setVisible(true);
		
	}
	public static void main(String[] args) {
		new CreateRoomDialog(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		   
		if(e.getSource()==jbt_add) {
			
			list = new Vector();
			list.add(model_friend_list.getValueAt(friend_list.getSelectedRow(), 0));
            for(int i=0; i<model_Room_add_list.getRowCount();i++) {
            	if(model_Room_add_list.getValueAt(i, 0).equals(model_friend_list.getValueAt(friend_list.getSelectedRow(), 0))){
            	
            		JOptionPane.showMessageDialog(this, "이미 추가하셨습니다.", "알림", JOptionPane.CLOSED_OPTION);
            		return;
            	}
            	
            }
            model_Room_add_list.addRow(list);
            V_Room_add_list.add(list);
			
		}
		else if(e.getSource()==jbt_del) {
			
			model_Room_add_list.removeRow(Room_add_list.getSelectedRow());
			V_Room_add_list    .remove(Room_add_list.getSelectedRow()+1);
			
		}
        else if(e.getSource()==jbt_ok) {
        
        	
        	if(jtf_title.getText()==null||jtf_title.getText().equals("")) {
        		JOptionPane.showMessageDialog(this, "방제목을 추가해주세요", "알림", JOptionPane.CLOSED_OPTION);
        		return;
        	}
        	V_Room_add_list.add(jtf_title.getText());
        	for(int i = 0; i<V_Room_add_list.size();i++) {
        		System.out.println(V_Room_add_list.elementAt(i));
        	}
        	
        	
        	this.dispose();
        }
        else if(e.getSource()==jbt_Cancel) {
        	System.exit(1);
        }
	}
}

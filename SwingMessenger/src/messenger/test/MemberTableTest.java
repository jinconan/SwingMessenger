package messenger.test;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import messenger._db.vo.MemberVO;
import messenger.client.member_table.MemberVOTable;
import messenger.client.member_table.MemberVOTableModel;

public class MemberTableTest extends JFrame{

	JPanel jp = new JPanel(new BorderLayout());
	MemberVOTable table0 = new MemberVOTable();
	MemberVOTable table = new MemberVOTable();
	
	public MemberTableTest() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		MemberVOTableModel model0 = new MemberVOTableModel(getList0(),"나");
		MemberVOTableModel model = new MemberVOTableModel(getList(),"친구");
		
		table.setModel(model);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				
				if(e.getButton() == 3) {
					JDialog jd = new JDialog();
						
					
				}
			}
		});
		table.setRowHeight(85);
		table0.setModel(model0);
		table0.setRowHeight(85);
	
		jp.add(table0,BorderLayout.NORTH);
		jp.add(new JScrollPane(table), BorderLayout.CENTER);
		add(jp,BorderLayout.CENTER);
		pack();
		setVisible(true);
	}
	
	List<MemberVO> getList0() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		ImageIcon icon = new ImageIcon("src//messenger//server//images//profile//profile_0.png");
		ImageIcon bicon = new ImageIcon("src//messenger//server//images//background//background_0.jpg");
		list.add(new MemberVO(0, "나", null, null, null, null, null, new JLabel(icon), new JLabel(bicon)));
		return list;
	}
	
	List<MemberVO> getList() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		ImageIcon picon = new ImageIcon("src//messenger//server//images//profile//profile_0.png");
		ImageIcon bicon = new ImageIcon("src//messenger//server//images//background//background_0.jpg");
		list.add(new MemberVO(0, "bin", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin2", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin2", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin3", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin4", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin5", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin6", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin7", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin8", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		list.add(new MemberVO(0, "bin9", null, null, null, null, null, new JLabel(picon), new JLabel(bicon)));
		
		
		
		return list;
	}
	
	public static void main(String[] args) {
		new MemberTableTest();

	}

	class profileDialog extends JDialog {
		JLabel background;
	}
}

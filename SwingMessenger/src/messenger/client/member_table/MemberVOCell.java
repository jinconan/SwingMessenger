package messenger.client.member_table;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import messenger._db.vo.MemberVO;

public class MemberVOCell extends AbstractCellEditor implements TableCellRenderer, TableCellEditor{
	private MemberVO mem;

	private JPanel jp_mem = new JPanel();
	
	private JLabel jl_prof = new JLabel();
	private JLabel jl_id = new JLabel();
	private JLabel jl_nick = new JLabel();
	
	public MemberVOCell() {
		jp_mem.setLayout(new FlowLayout(FlowLayout.LEADING));
		jp_mem.add(jl_prof);
		jp_mem.add(jl_id);
		jp_mem.add(jl_nick);
	}
	
	private void updateData(MemberVO mem, boolean isSelected, JTable table) {
		this.mem = mem;
		
		if(mem.getMem_profile() != null)
			jl_prof.setIcon(mem.getMem_profile().getIcon());
		jl_id.setText(mem.getMem_id());
		jl_nick.setText(mem.getMem_nick());
		
		if (isSelected) {
			jp_mem.setBackground(table.getSelectionBackground());
		} else {
			jp_mem.setBackground(table.getBackground());
		}
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		MemberVO mem = (MemberVO) value;
		updateData(mem, isSelected, table);
		return jp_mem;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		MemberVO mem = (MemberVO) value;
		updateData(mem, isSelected, table);
		return jp_mem;
	}
	
}

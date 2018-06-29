package messenger.test;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class JTreeTest extends JFrame{
	JTree tree;
	JTreeTest() {
		DefaultMutableTreeNode dmtn_root 	= new DefaultMutableTreeNode("사람목록");
		DefaultMutableTreeNode dmtn_me 		= new DefaultMutableTreeNode("나");
		DefaultMutableTreeNode dmtn_friend 	= new DefaultMutableTreeNode("친구목록");
		
		String name = "이름 : 육백만불 대화명 :";
		String com = "안녕하세요";
		DefaultMutableTreeNode f0 	= new DefaultMutableTreeNode("이진");
		DefaultMutableTreeNode f1 	= new DefaultMutableTreeNode("이름 : "+name+" "+"대화명 : "+com);
		DefaultMutableTreeNode f2 	= new DefaultMutableTreeNode("김유신");
		DefaultMutableTreeNode f3 	= new DefaultMutableTreeNode("양만춘");
		
		
		dmtn_root.add(dmtn_me);
		dmtn_root.add(dmtn_friend);
		dmtn_me.add(f0);
		dmtn_friend.add(f1);
		dmtn_friend.add(f2);
		dmtn_friend.add(f3);
		tree = new JTree(dmtn_root);
//		tree.expandRow(1);
		tree.setRowHeight(50);
		
		DefaultTreeCellRenderer dt = new DefaultTreeCellRenderer();
		dt.setOpenIcon(new ImageIcon());
		dt.setClosedIcon(new ImageIcon());
		dt.setLeafIcon(new ImageIcon());
		
		tree.setCellRenderer(dt);
		
		JScrollPane js = new JScrollPane(tree);
		add("Center", js);
		setSize(350,500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	public static void main(String[] args) {
		new JTreeTest();

	}

}

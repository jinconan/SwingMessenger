package messenger.client.view;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Indentify extends JFrame {
	JLabel jl_status = new JLabel();
	JLabel jl2 = new JLabel();
	JLabel jl3 = new JLabel();
	JLabel jl4 = new JLabel();
	JLabel jl5 = new JLabel();
	JLabel jl6 = new JLabel();
	
	public void initDisplay() {
		this.setTitle("�� �г� ����");
		this.setSize(500,500);
		this.setLayout(null);
		this.ArrayList();
		//this.setContentPane(contentPane);
		this.setLayout(new GridLayout(6,1));
		
		/*this.add(jl_status);
		this.add(jl2);
		this.add(jl3);
		this.add(jl4);
		this.add(jl5);
		this.add(jl6);*///�̰� �ϳ��� ���� �� �ִ� �����..!
		
		
		this.setVisible(true);
		ArrayList<JLabel> list= new ArrayList<JLabel>();
		list.add(0, jl_status);
		list.add(1, jl2);
				
		
	}
	public void ArrayList() {
		ArrayList<JLabel> Plist= new ArrayList<JLabel>();
		Plist.add(jl_status);
		Plist.add(jl2);
		Plist.add(jl3);
		jl_status.setIcon(new ImageIcon("E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\\uC54C\uC218\uC5C6\uC74C.PNG"));
		//contentPane.add(lblNewLabel, BorderLayout.SOUTH);
		jl_status.setVisible(true);
		jl_status.setText("1��");
		jl2.setIcon(new ImageIcon("E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\\uC54C\uC218\uC5C6\uC74C.PNG"));
		jl2.setText("2��");
		jl3.setIcon(new ImageIcon("E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\\uC54C\uC218\uC5C6\uC74C.PNG"));
		jl3.setText("3��");
		jl4.setIcon(new ImageIcon("E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\\uC54C\uC218\uC5C6\uC74C.PNG"));
		jl4.setText("4��");
		jl5.setIcon(new ImageIcon("E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\\uC54C\uC218\uC5C6\uC74C.PNG"));
		jl5.setText("5��");
		jl6.setIcon(new ImageIcon("E:\\dev_kosmo201804\\dev_java\\src\\com\\image\\\uC54C\uC218\uC5C6\uC74C.PNG"));
		jl6.setText("6��");
		
		for(int i=0;i<Plist.size();i++) {
			if(Plist.get(i) instanceof JLabel) {
				JLabel temp = (JLabel)Plist.get(i);
				Plist.add(i, null);
				System.out.println(temp);
				System.out.println("�� �ȳ��ñ�?");
				//Plist�� �������� -> for���� �ȵ��ư�
			}
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Indentify idf = new Indentify();
		idf.ArrayList();
		idf.initDisplay();
	}

}

package messenger.client.view;

import javax.swing.JOptionPane;

public class Thread1 {
	public static void main(String[] args) throws Exception {
		Thread_1 th1 = new Thread_1();
		th1.start();
		String input = JOptionPane.showInputDialog("�ƹ� ���̳� �Է��ϼ���.");
		System.out.println("�Է��Ͻ� ���� " + input + "�Դϴ�.");
		
		for(int i=10;i>0;i--) {
			System.out.println(i);
			try {
				Thread.sleep(1000); //1�ʰ� �ð��� ������Ų��.
				
			}catch(Exception e) {
				
			}
		}
	}
}
class Thread_1 extends Thread{
	public void run() {
		for(int i=10;i>0;i--) {
			System.out.println(i);
			try {
				Thread.sleep(1000); //1�ʰ� �ð��� ������Ų��.
			}catch(Exception e) {
			
			}
		}
	}
}

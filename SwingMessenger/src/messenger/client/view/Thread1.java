package messenger.client.view;

import javax.swing.JOptionPane;

public class Thread1 {
	public static void main(String[] args) throws Exception {
		Thread_1 th1 = new Thread_1();
		th1.start();
		String input = JOptionPane.showInputDialog("아무 값이나 입력하세요.");
		System.out.println("입력하신 값은 " + input + "입니다.");
		
		for(int i=10;i>0;i--) {
			System.out.println(i);
			try {
				Thread.sleep(1000); //1초간 시간을 지연시킨다.
				
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
				Thread.sleep(1000); //1초간 시간을 지연시킨다.
			}catch(Exception e) {
			
			}
		}
	}
}

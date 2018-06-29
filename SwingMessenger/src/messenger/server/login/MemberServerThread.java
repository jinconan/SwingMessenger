package messenger.server.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import messenger._db.vo.MemberVO;
import messenger.protocol.Message;
import messenger.server.chat.ChatServerThreadList;
/*******************************
 * 2018.06.20 ������ Ŭ���� �ڵ� ��ž.
 * 	 - ���� ����� �������� ���߱� ������ �̴�� �ڵ� �� ��� ����δ��� ŭ.
 *   - ��Ƽ�����忡���� �����ϱ� 
 * 2018.06.28 ������ ������ �ؼ� �ٽ� ����� �ڵ��ϱ�
 *  ! �н� ��ǥ
 *    - ��Ƽ�����忡 ���� �����ϰ� ������ �� �ִ�. 
 * 
 *******************************/
public class MemberServerThread implements Runnable{
	Socket					socket;
	JTextArea 				jta_log;
	MemberMenu 				mm 			= new MemberMenu();

	public MemberServerThread(JTextArea jta_log, Socket socket) {
		this.jta_log = jta_log;
		this.socket = socket;
	}

	@Override
	public synchronized void run() {
		try (	
				InputStream			in 	= socket.getInputStream();
				BufferedInputStream bin = new BufferedInputStream(in);
				ObjectInputStream	oin = new ObjectInputStream(bin);

				){

			Message<MemberVO> msg = (Message<MemberVO>)oin.readObject();
			msg.getType();//�޼���������Ʈ �ȿ� ����ִ� �޼��� Ÿ���� �ҷ���
			if(jta_log != null)
				jta_log.append("��û : " + socket.getInetAddress().toString() + ", " + socket.getPort() + "\n");
			/*private List<T> request;	//Ŭ���̾�Ʈ�� �����͸� ��� �κ�
				private List<T> response;	//������ �����͸� ��� �κ�
			 */				
			try(
					OutputStream			out		= socket.getOutputStream();
					BufferedOutputStream	bout	= new BufferedOutputStream(out);
					ObjectOutputStream		oout	= new ObjectOutputStream(bout);
					){
				ArrayList<MemberVO> request = (ArrayList<MemberVO>) msg.getRequest();
				ArrayList<MemberVO> response = new ArrayList<MemberVO>();
				MemberVO mvo = request.get(0);//Ŭ���̾�Ʈ���� ���� ������ mvo�� ����.
				switch(msg.getType()) {
				case Message.MEMBER_JOIN://ȸ������
					mm.MemberInsert(request, Message.MEMBER_JOIN);
					response.add(mvo);
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.MEMBER_MODIFY://ȸ������ ����.
					mm.MemberUpdate(request, Message.MEMBER_MODIFY);
					request.get(0).getMem_background();//
					request.get(0).getMem_profile();
					response.add(mvo);
					oout.writeObject(msg);
					oout.flush();
					break;
				case Message.MEMBER_IDCHECK:
					mm.MemberOverlap(request);
					if(mm.MemberOverlap(request)==1) {
						response=null;
						oout.writeObject(msg);
						oout.flush();
					}
					else {
						response.add(mvo);
						oout.writeObject(msg);
						oout.flush();
					}
				}
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}

}


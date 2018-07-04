package messenger.client.Emoticon;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;

import messenger._protocol.Message;
import messenger._protocol.Port;
import messenger._protocol.Server;

public class GetEmoticon {
	// 회원이 로그인에 성공해서 서버에 접속하는 순간 이모티콘을 불러오면됨
	HashMap<String, JLabel> list = new HashMap<String, JLabel>();

	public HashMap<String, JLabel> method() {
		// TODO Auto-generated constructor stub

		try {
			Socket socket = new Socket(Server.IP, Port.EMOTICON);
			try (
				OutputStream os = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(os));
			) {
				Message<JLabel> msg = new Message<JLabel>(Message.EMOTICON_LOAD, null, null);
				oos.writeObject(msg);
				oos.flush();

				try (
					InputStream is = socket.getInputStream();
					ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(is));
				) {
					msg = (Message<JLabel>) ois.readObject();
					ArrayList<JLabel> response = (ArrayList<JLabel>) msg.getResponse();
					if (response != null) {
						for (JLabel label : response) {
							list.put(label.getText(), label);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}

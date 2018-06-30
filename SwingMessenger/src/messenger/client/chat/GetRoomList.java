package messenger.client.chat;

import java.util.ArrayList;

import messenger._db.vo.ChatVO;
import messenger._db.vo.RoomVO;
import messenger.client.view.ClientFrame;
import messenger.protocol.Message;

public class GetRoomList {
	
	public void method(Message<ChatVO> msg, ClientFrame clientFrame) {
		ArrayList<ChatVO> response = (ArrayList<ChatVO>)msg.getResponse();
		ArrayList<RoomVO> rVOList = new ArrayList<RoomVO>();
		
		//ChatVO�� ���� ����Ʈ�� RoomVo�� ����Ʈ�� ��ȯ�Ѵ�.
		if(response != null) {
			for(int i=0; i<response.size();i++) {
				RoomVO roomVO = response.get(i).getRoomVO();
				rVOList.add(roomVO);
			}
		}
		
		//RoomVo�� ����Ʈ�� ������ �� ����� ���ΰ�ħ�Ѵ�..
		clientFrame.getRoomPanel().refreshRoomList(rVOList);
	}
}

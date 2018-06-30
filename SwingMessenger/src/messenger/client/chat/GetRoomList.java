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
		
		//ChatVO로 받은 리스트를 RoomVo의 리스트로 변환한다.
		if(response != null) {
			for(int i=0; i<response.size();i++) {
				RoomVO roomVO = response.get(i).getRoomVO();
				rVOList.add(roomVO);
			}
		}
		
		//RoomVo의 리스트를 가지고 방 목록을 새로고침한다..
		clientFrame.getRoomPanel().refreshRoomList(rVOList);
	}
}

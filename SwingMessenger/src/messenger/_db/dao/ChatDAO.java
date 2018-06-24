package messenger._db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import messenger._db.DBConnection;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomMemberVO;

public class ChatDAO {
	private DBConnection dbCon = new DBConnection();
	
	private ChatDAO() {}
	
	private static class LazyHolder {
		private static final ChatDAO INSTANCE = new ChatDAO();
	}
	
	/**
	 * 
	 * @return 인스턴스
	 */
 	public static ChatDAO getInstance() {
		return LazyHolder.INSTANCE;
	}

 	/**
	 * 
	 * @param chat : 한 클라이언트가 전달한 대화로그
	 * @return : 성공시 param과 같은 값, 실패시 null
	 */
	public synchronized ChatVO insertChat(ChatVO chat) {
		StringBuilder sql = new StringBuilder("INSERT INTO chat(chat_no, room_no, chat_content, chat_time, mem_no)");
		sql.append(" VALUES(seq_chat.nextval, ?, ?, ?, ?)");

		int out = 0;
		ChatVO chatVO = null;
		try (
			Connection con = dbCon.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql.toString());
		){
			int i = 1;
			SimpleDateFormat chat_time_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String chat_time = chat_time_format.format(new Date());
			pstmt.setInt(i++, chat.getRoom_no());
			pstmt.setString(i++, chat.getChat_content());
			pstmt.setString(i++, chat_time);
			pstmt.setInt(i++, chat.getMem_no());
			
			out = pstmt.executeUpdate();
			if(out != 0) {
				chatVO = chat;
				int chat_no = 0; //오라클 함수나 프로시저를 이용하면 이것을 바로 받아올 수 있으나 아직 미구현. 클라이언트의 입장에선 필요없는 필드라서 아무 값이나 넣어도 무방.
				chatVO.setChat_no(chat_no);
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
		return chatVO;
	}

	/**
	 * 접속 후 클라이언트가 참여 중인 방 리스트를 얻음.
	 * @param mem_no : 방 참여 현황을 알고자 하는 클라이언트의 정보(회원번호만)
	 * @return : 방 번호가 들어있는 ChatVO의 리스트
	 */
	public synchronized ArrayList<ChatVO> selectRoomList(int mem_no) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT room_no FROM room_member WHERE mem_no = ?");
		ArrayList<ChatVO> list = new ArrayList<ChatVO>(); 
		try(
			Connection con = dbCon.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql.toString());
		){
			int i = 1;
			
			pstmt.setInt(i++,mem_no);
			try (
				ResultSet rs = pstmt.executeQuery();
			){
				while(rs.next()) {
					ChatVO chatVO = new ChatVO();
					int room_no = rs.getInt("room_no");
					chatVO.setRoom_no(room_no);
					list.add(chatVO);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}

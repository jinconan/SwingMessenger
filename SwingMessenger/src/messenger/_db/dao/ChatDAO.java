package messenger._db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import messenger._db.DBConnection;
import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
import messenger._db.vo.RoomMemberVO;
import messenger._db.vo.RoomVO;

/**
 * DB의 chat테이블에 대한 DML 처리를 하는 DAO Class
 * <담당하는 메시지 타입>
 * 	CHAT_SEND
 * 	CHATROOM_LOAD
 * 	CHATROOM_EXIT
 * @author Jin Lee
 */
public class ChatDAO {
	private DBConnection dbCon = new DBConnection();
	private final SimpleDateFormat chat_time_format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private ChatDAO() {}
	
	private static class LazyHolder {
		private static final ChatDAO INSTANCE = new ChatDAO();
	}
	
	/**
	 * ChatDAO의 인스턴스를 얻는다.
	 * @return ChatDAO의 인스턴스
	 */
 	public static ChatDAO getInstance() {
		return LazyHolder.INSTANCE;
	}

 	/**
	 * Chat테이블에 채팅로그를 추가한다.
	 * @param chat : 한 클라이언트가 전달한 대화로그
	 * @return : 성공시 1, 실패시 0
	 */
	public synchronized int insertChat(ChatVO chat) {
		StringBuilder sql = new StringBuilder("INSERT INTO chat(chat_no, room_no, chat_content, chat_time, mem_no) ");
		sql.append(" VALUES(seq_chat.nextval, ?, ?, ?, ?)");

		int		out		= 0;
		ChatVO	chatVO	= null;
		try (
			Connection 			con		= dbCon.getConnection();
			PreparedStatement 	pstmt	= con.prepareStatement(sql.toString());
		){
			int i = 1;
			
			int 	room_no 		= (chat.getRoomVO() != null) ? chat.getRoomVO().getRoom_no() : 0;
			String	chat_content	= chat.getChat_content();
			String	chat_time 		= chat_time_format.format(new Date());
			int		mem_no 			= (chat.getMemVO() != null) ? chat.getMemVO().getMem_no() : 0;
			pstmt.setInt(i++, room_no);
			pstmt.setString(i++, chat_content);
			pstmt.setString(i++, chat_time);
			pstmt.setInt(i++, mem_no);
			
			out = pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * 참여한 모든 방 리스트를 select한다.
	 * @param mem_no : 클라이언트의 회원번호
	 * @return : 방 번호의 ArrayList<ChatVO>. 방이 없으면 ArrayList의 size는 0이다.
	 */
	public synchronized ArrayList<ChatVO> selectRoomList(int mem_no) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT room_no, room_starttime FROM room WHERE room_no IN ");
		sql.append("(SELECT room_no FROM room_member WHERE mem_no = ?)");
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
					String room_starttime = rs.getString("room_starttime");
					RoomVO roomVO = new RoomVO(room_no, room_starttime);
					chatVO.setRoomVO(roomVO);
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

	/**
	 * room_member테이블 delete 수행 메소드. 
	 * @param mem_no : 회원 번호.
	 * @param room_no : 방 번호
	 * @return : delete 성공시 1, 실패시 0
	 */
	public synchronized int deleteRoomMember(int mem_no, int room_no) {
		StringBuilder sql = new StringBuilder();
		int out = 0;
		sql.append("DELETE FROM room_member WHERE mem_no=? AND room_no=?");
		try(
				Connection con = dbCon.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql.toString());
		){
				int i = 1;
				
				pstmt.setInt(i++,mem_no);
				pstmt.setInt(i++,room_no);
				out = pstmt.executeUpdate();
			} 
		catch(SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	/**
	 * room_member테이블 insert 수행 메소드. 새 방을 생성할 시 room 테이블에서 먼저 insert를 수행한다.
	 * @param request : 방 안에 참여할 참가자 리스트(기존 방에 초대시에는 초대할 멤버만)
	 * @return insert 수행 결과
	 */
	public synchronized int insertRoomMember(ArrayList<ChatVO> request) {
		int result = 0;
		
		return result;
	}
}

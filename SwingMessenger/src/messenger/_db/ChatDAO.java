package messenger._db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import messenger._db.vo.ChatVO;
import messenger._db.vo.MemberVO;
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
	private DBConnection dbCon = DBConnection.getInstance();
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

		int	out	= 0;
		try (
			Connection 			con		= dbCon.getConnection();
			PreparedStatement 	pstmt	= con.prepareStatement(sql.toString());
		){
			int i = 1;
			
			int 	room_no 		= (chat.getRoomVO() != null) ? chat.getRoomVO().getRoom_no() : 0;
			String	chat_content	= chat.getChat_content();
			String	chat_time 		= chat_time_format.format(new Date());
			int		mem_no 			= (chat.getMemVO() != null) ? chat.getMemVO().getMem_no() : 0;
			chat.setChat_time(chat_time);
			
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
		StringBuilder sql = new StringBuilder("SELECT room_no, room_starttime, room_title FROM room ");
		sql.append("WHERE room_no IN ");
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
					String room_title = rs.getString("room_title");
					RoomVO roomVO = new RoomVO(room_no, room_starttime,room_title);
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
		sql.append("DELETE FROM room_member WHERE mem_no=? AND room_no=?");
		int out = 0;
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
	 * 친구 초대 또는 방 개설 메소드.
	 * 새 방을 생성할 시 room테이블에 insert를 수행하고 생성된 방 번호를 리턴하는 프로시저를 먼저 실행한다.
	 * 그 후 공통적으로 선택된 멤버들을 원하는 방에 insert를 수행한다.
	 * @param request : 방 안에 참여할 참가자 리스트(기존 방에 초대시에는 초대할 멤버만)
	 * @param roomVO : 참여할 방(null인 경우 새로운 방 개설) -> 방 생성시 제목을 받아야하므로 null대신 방 번호가 0인 경우로 변경
	 * @return 최종적으로 방에 참가한 인원을 ChatVO에 담아서 리스트로 리턴.
	 */
	public synchronized ArrayList<ChatVO> insertRoomMember(ArrayList<ChatVO> request, RoomVO roomVO) {
		ArrayList<ChatVO> out = new ArrayList<ChatVO>();
		StringBuilder sql = null;
		int room_no = 0;
		try (
			Connection con = dbCon.getConnection();
		){
			//roomVO가 없거나 방 번호가 없는 경우 새로운 방을 먼저 db에 추가한다.
			if(roomVO == null || roomVO.getRoom_no() == 0) {
				try (CallableStatement cstmt = con.prepareCall("{call proc_room_create(?,?)}");){
					String room_title = (roomVO == null) ? null : roomVO.getRoom_title();
					if(room_title == null || room_title.trim()=="")
						room_title="제목없음";
					cstmt.setString(1, room_title);
					cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
					cstmt.executeUpdate();
					
					room_no = cstmt.getInt(2);
				}catch(Exception e) {
					e.printStackTrace();
					return out;
				}
			}
			else
				room_no = roomVO.getRoom_no();
			//방 안에 멤버를 추가.
			sql = new StringBuilder("INSERT INTO room_member(room_seq, room_no, mem_no) VALUES(seq_room_member.nextval, ?, ?)");
			try (
				PreparedStatement pstmt = con.prepareStatement(sql.toString())
			) {
				int result = 0;
				roomVO = new RoomVO(room_no, null, null);
				for(ChatVO chatVO : request) {
					MemberVO memVO = chatVO.getMemVO();
					if(memVO == null)
						continue;
					pstmt.setInt(1, room_no);
					pstmt.setInt(2,  memVO.getMem_no());
					result = pstmt.executeUpdate();
					if(result != 0) {
						chatVO.setRoomVO(roomVO);
						out.add(chatVO);
					}
					pstmt.clearParameters();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	public static void main(String[] args) {
		ChatDAO dao = new ChatDAO();
		ArrayList<ChatVO> request = new ArrayList<ChatVO>();
		RoomVO roomVO = null;
		
		MemberVO mem = new MemberVO();
		mem.setMem_no(25);
		request.add(new ChatVO(0, null, null, null, mem));
		mem = new MemberVO();
		mem.setMem_no(26);
		request.add(new ChatVO(0, null, null, null, mem));
		ArrayList<ChatVO> out= dao.insertRoomMember(request, roomVO);
		
		for(ChatVO c : out) {
			System.out.println(c.getMemVO().getMem_no());
		}
	}
}

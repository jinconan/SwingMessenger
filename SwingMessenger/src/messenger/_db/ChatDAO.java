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
 * DB�� chat���̺� ���� DML ó���� �ϴ� DAO Class
 * <����ϴ� �޽��� Ÿ��>
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
	 * ChatDAO�� �ν��Ͻ��� ��´�.
	 * @return ChatDAO�� �ν��Ͻ�
	 */
 	public static ChatDAO getInstance() {
		return LazyHolder.INSTANCE;
	}

 	/**
	 * Chat���̺� ä�÷α׸� �߰��Ѵ�.
	 * @param chat : �� Ŭ���̾�Ʈ�� ������ ��ȭ�α�
	 * @return : ������ 1, ���н� 0
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
	 * ������ ��� �� ����Ʈ�� select�Ѵ�.
	 * @param mem_no : Ŭ���̾�Ʈ�� ȸ����ȣ
	 * @return : �� ��ȣ�� ArrayList<ChatVO>. ���� ������ ArrayList�� size�� 0�̴�.
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
	 * room_member���̺� delete ���� �޼ҵ�. 
	 * @param mem_no : ȸ�� ��ȣ.
	 * @param room_no : �� ��ȣ
	 * @return : delete ������ 1, ���н� 0
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
	 * ģ�� �ʴ� �Ǵ� �� ���� �޼ҵ�.
	 * �� ���� ������ �� room���̺� insert�� �����ϰ� ������ �� ��ȣ�� �����ϴ� ���ν����� ���� �����Ѵ�.
	 * �� �� ���������� ���õ� ������� ���ϴ� �濡 insert�� �����Ѵ�.
	 * @param request : �� �ȿ� ������ ������ ����Ʈ(���� �濡 �ʴ�ÿ��� �ʴ��� �����)
	 * @param roomVO : ������ ��(null�� ��� ���ο� �� ����) -> �� ������ ������ �޾ƾ��ϹǷ� null��� �� ��ȣ�� 0�� ���� ����
	 * @return ���������� �濡 ������ �ο��� ChatVO�� ��Ƽ� ����Ʈ�� ����.
	 */
	public synchronized ArrayList<ChatVO> insertRoomMember(ArrayList<ChatVO> request, RoomVO roomVO) {
		ArrayList<ChatVO> out = new ArrayList<ChatVO>();
		StringBuilder sql = null;
		int room_no = 0;
		try (
			Connection con = dbCon.getConnection();
		){
			//roomVO�� ���ų� �� ��ȣ�� ���� ��� ���ο� ���� ���� db�� �߰��Ѵ�.
			try (CallableStatement cstmt = con.prepareCall("{call proc_room_create(?,?)}");){
				String room_title = (roomVO == null) ? null : roomVO.getRoom_title();
				if(room_title == null || room_title.trim()=="")
					room_title="�������";
				cstmt.setString(1, room_title);
				cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
				cstmt.executeUpdate();
				
				room_no = cstmt.getInt(2);
			}catch(Exception e) {
				e.printStackTrace();
				return out;
			}

			//�� �ȿ� ����� �߰�.
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

}

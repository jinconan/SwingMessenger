package messenger.client.view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messenger._db.vo.MemberVO;
import messenger._protocol.Message;
import messenger._protocol.Port;

public class ClientJoin {

	Message<MemberVO>  message  = new Message<MemberVO>();
	List   <MemberVO>  User     = null;
	MemberVO           memberVo = null;
	Socket             socket   = null;
	ObjectInputStream  ois      = null;
	ObjectOutputStream oos      = null;
	
	String             ID       = null;
	String             PW       = null;
	String             name     = null;
	String             Gender   = null;
	String             HP       = null;
	
	int result = 0;
	
	//회원 가입 완료 되면 1 반환 
	//가입 안돼면 0 반환 
	
	public List messages(ClientJoin vo) {
		
		User     = new ArrayList<MemberVO>();
		memberVo = new MemberVO();
		memberVo.setMem_id(vo.getID());
		memberVo.setMem_hp(vo.getHP());
		memberVo.setMem_gender(vo.getGender());
		memberVo.setMem_background(null);
		memberVo.setMem_name(vo.getName());
		memberVo.setMem_nick(null);
		memberVo.setMem_pw(vo.getPW());
		User.add(memberVo);
		
		return User;
		
	}
	public int Join(ClientJoin vo) {
		
		int result = 0;
		
		try {
			socket = new Socket("192.168.0.235",Port.MEMBER);
			oos    = new ObjectOutputStream(socket.getOutputStream());
			message.setType(message.MEMBER_JOIN);
			message.setRequest(messages(vo));
			oos.writeObject(message);
			ois     = new ObjectInputStream(socket.getInputStream());
			message = (Message<MemberVO>) ois.readObject();
			
			if(message.getResponse().size()>0) {
				result=1;
				System.out.println("서버로부터 받은 result"+result);
				oos.close();
				ois.close();
				socket.close();
				return result;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
		
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		this.ID = iD;
	}
	public String getPW() {
		return PW;
	}
	public void setPW(String pW) {
		this.PW = pW;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		this.Gender = gender;
	}
	public String getHP() {
		return HP;
	}
	public void setHP(String hP) {
		this.HP = hP;
	}
	
}

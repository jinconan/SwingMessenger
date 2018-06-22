package messenger.client.view;

import java.util.ArrayList;
import java.util.List;

import com.ch7.Sonata;

public class ArrayListTest {
	ArrayList<Object> al = new ArrayList<Object>();//제네릭이 들어있지 않아 경고창이 뜸
	ArrayList<String> al5 = new ArrayList<String>();
	//ArrayList<DeptVO> al6 = new ArrayList<DeptVO>();
	List al2 = new ArrayList();
	List<String> al3 = new ArrayList<String>();
	List<Sonata> al4 = new ArrayList<Sonata>();
	//List<T> al5	= new ArrayList<T>();
	//al 변수는 제네릭이 없으니깐 어떤 타입이든 담을 수 있어요.
	//제네릭이 있는데 서로 다른 타입을 담고 싶어요 - 
	//그런데 꺼내 쓸때는 어떻게 타입을 알 수 있는걸까?
	
	
	public void setList() {
		al.add("하하");
		al.add(new Sonata());
		al.add(0);
	}////////////////////////end of setList
	
	public void setList2() {
		al.add("하하");
		al.add(new Sonata());
		al.add(0);
	}////////////////////////end of setList
	
	public void getList() {
		for(int i=0;i<al.size();i++) {
			if(al.get(i) instanceof String) {
				String temp = (String)al.get(i);
				System.out.println(temp);
			}
			else if(al.get(i) instanceof Sonata) {
				Sonata myCar = (Sonata)al.get(i);
				System.out.println(myCar);
			}
			else if(al.get(i) instanceof Integer) {
				Integer it = (Integer)al.get(i);
				System.out.println(it);
			}
		}
	}
		
	public static void main(String[] args) {
		/*ArrayList<Integer> list = new ArrayList();
		
		//빈공간
		if(list.isEmpty()) {
			System.out.println("ArrayList 비었음");
		}
		
		//리스트 추가
		list.add(4);
		list.add(19);
		list.add(20);
		list.add(55);
		System.out.println("add 호출 후");
		System.out.println(list);
		
		//get&set
		final int getIndex = 3;
		final int set = 8;
		if(list.get(getIndex) !=null) {
			System.out.println(getIndex + "번 위치에" + list.get(getIndex) + "있다.");
			list.set(getIndex, set);
			System.out.println("값 변경 후");
			System.out.println(list);*/
		}
		
	}
}

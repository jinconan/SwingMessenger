package messenger.client.view;

import java.util.ArrayList;
import java.util.List;

import com.ch7.Sonata;

public class ArrayListTest {
	ArrayList<Object> al = new ArrayList<Object>();//���׸��� ������� �ʾ� ���â�� ��
	ArrayList<String> al5 = new ArrayList<String>();
	//ArrayList<DeptVO> al6 = new ArrayList<DeptVO>();
	List al2 = new ArrayList();
	List<String> al3 = new ArrayList<String>();
	List<Sonata> al4 = new ArrayList<Sonata>();
	//List<T> al5	= new ArrayList<T>();
	//al ������ ���׸��� �����ϱ� � Ÿ���̵� ���� �� �־��.
	//���׸��� �ִµ� ���� �ٸ� Ÿ���� ��� �;�� - 
	//�׷��� ���� ������ ��� Ÿ���� �� �� �ִ°ɱ�?
	
	
	public void setList() {
		al.add("����");
		al.add(new Sonata());
		al.add(0);
	}////////////////////////end of setList
	
	public void setList2() {
		al.add("����");
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
		
		//�����
		if(list.isEmpty()) {
			System.out.println("ArrayList �����");
		}
		
		//����Ʈ �߰�
		list.add(4);
		list.add(19);
		list.add(20);
		list.add(55);
		System.out.println("add ȣ�� ��");
		System.out.println(list);
		
		//get&set
		final int getIndex = 3;
		final int set = 8;
		if(list.get(getIndex) !=null) {
			System.out.println(getIndex + "�� ��ġ��" + list.get(getIndex) + "�ִ�.");
			list.set(getIndex, set);
			System.out.println("�� ���� ��");
			System.out.println(list);*/
		}
		
	}
}

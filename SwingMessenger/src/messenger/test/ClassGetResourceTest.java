package messenger.test;

//������ ��θ� ��� �׽�Ʈ
//�̸�Ƽ�� ��ġ �ľ��� ����
public class ClassGetResourceTest {

	public static void main(String[] args) {
		Class<ClassGetResourceTest> c = ClassGetResourceTest.class;
		ClassLoader loader = c.getClassLoader();
		_ToDoList inst = new _ToDoList();
		System.out.println(ClassGetResourceTest.class.getResource("."));
		System.out.println(inst.getClass().getResource("."));
		System.out.println(c.getResource("."));
		System.out.println(loader.getResource("."));

	}

}

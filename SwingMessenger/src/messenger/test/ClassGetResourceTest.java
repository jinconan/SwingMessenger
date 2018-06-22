package messenger.test;

//파일의 경로를 얻는 테스트
//이모티콘 위치 파악을 위해
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

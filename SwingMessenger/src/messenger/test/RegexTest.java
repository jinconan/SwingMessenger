package messenger.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args) {
//		Pattern p = Pattern.compile("^[��-�R]*.*$");
//		String s = "�߾߾߾�_�ٺ�";
//		Matcher m = p.matcher(s);
//		if(m.find())
//			System.out.println("ã��");
		
		String s = "������������� (�ȳ�_��¥)         �������������� (�ȳ�_����)";
		
		Pattern p  = Pattern.compile("^.*\\([��-�R]*\\_[��-�R]*\\).*$");
		Matcher m = p.matcher(s);
		System.out.println(m.matches() );

	}

}

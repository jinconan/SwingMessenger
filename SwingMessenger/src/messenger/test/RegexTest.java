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
		
		String s = "([4195]-[4209]) + ([4196]+[4210])";
		
		Pattern p  = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(s);
		while(m.find()) {
			System.out.println(m.group(1));
		}
	}

}

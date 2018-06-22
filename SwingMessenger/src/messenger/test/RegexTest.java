package messenger.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args) {
//		Pattern p = Pattern.compile("^[啊-芌]*.*$");
//		String s = "具具具具_官焊";
//		Matcher m = p.matcher(s);
//		if(m.find())
//			System.out.println("茫澜");
		
		String s = "([4195]-[4209]) + ([4196]+[4210])";
		
		Pattern p  = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(s);
		while(m.find()) {
			System.out.println(m.group(1));
		}
	}

}

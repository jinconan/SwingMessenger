package messenger.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTest {

	public static void main(String[] args) {
//		Pattern p = Pattern.compile("^[°¡-ÆR]*.*$");
//		String s = "¾ß¾ß¾ß¾ß_¹Ùº¸";
//		Matcher m = p.matcher(s);
//		if(m.find())
//			System.out.println("Ã£À½");
		
		String s = "¤±¤·¤«¤¤·ÁÀç¤Á (¾È³ç_ÁøÂ¥)         ¤¤¤©¤¤¤·¤©¤¤¤© (¾È³ç_±¸¶ó)";
		
		Pattern p  = Pattern.compile("^.*\\([°¡-ÆR]*\\_[°¡-ÆR]*\\).*$");
		Matcher m = p.matcher(s);
		System.out.println(m.matches() );

	}

}

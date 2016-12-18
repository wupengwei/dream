package com.wpw.dream.core.reg.filter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegFilter {
	
	public static void main(String[] args) {

    	String subjectString = "我佛圣诞：www.cctv.com";
    	String a = RegFilter.removeTag(subjectString);
    	System.out.println(a);
    	
	}
	
	 public static String removeTag(String htmlStr) {
	      //  String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // script
	       // String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // style
	        //String regEx_html = "<[^>]+>"; // HTML tag
	        //String regEx_space = "<a[^>]+>|</a>";// other characters
		 	//String regEx_space = "^((https?|ftp|news):\\/\\/)?([a-z]([a-z0-9\\-]*[\\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\\/[a-z0-9_\\-\\.~]+)*(\\/([a-z0-9_\\-\\.]*)(\\?[a-z0-9+_\\-\\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$";
		 String regEx_space = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";
	        Pattern p_space = Pattern
	                .compile(regEx_space, Pattern.CASE_INSENSITIVE);
	        Matcher m_space = p_space.matcher(htmlStr);
	        htmlStr = m_space.replaceAll("");

	        return htmlStr;

	    }

}

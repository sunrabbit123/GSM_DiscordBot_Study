package com.github.sunrabbit123.Liechu_bot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String pattern = "+[0-9](老 第|林 第|崔 第|岿 第)";
		
		Pattern p = Pattern.compile("([0-9])(老 第|林 第|崔 第|岿 第)");
		String val = "具惯大惯 1老 第 大大大";
		Matcher m = p.matcher(val);
		
		if(m.find()) {
			System.out.println(true);
		}else {
			System.out.println(false);
		}
	}

}

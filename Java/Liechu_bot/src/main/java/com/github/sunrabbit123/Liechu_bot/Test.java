package com.github.sunrabbit123.Liechu_bot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String pattern = "+[0-9](�� ��|�� ��|�� ��|�� ��)";
		
		Pattern p = Pattern.compile("([0-9])(�� ��|�� ��|�� ��|�� ��)");
		String val = "�߹ߴ�� 1�� �� ����";
		Matcher m = p.matcher(val);
		
		if(m.find()) {
			System.out.println(true);
		}else {
			System.out.println(false);
		}
	}

}

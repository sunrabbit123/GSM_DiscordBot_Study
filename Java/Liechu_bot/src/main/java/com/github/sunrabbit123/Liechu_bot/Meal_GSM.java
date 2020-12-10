package com.github.sunrabbit123.Liechu_bot;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


class DateConverter{
	private Date YMD;
	private String fymd;
	private String[][] dateExp = {
			{"그끄저께", "-3"},
			{"그끄제", "-3"},
			{"그저께", "-2"},
			{"그제", "-2"},
			{"어제", "-1"},
			{"오늘", "0"},
			{"내일", "1"},
			{"모레", "2"},
			{"글피", "3"},
			{"그글피", "4"}
	};
	
	DateConverter(String content){
		SimpleDateFormat fNow = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String text = content.replace("라이츄 ", "");
		
		if(Pattern.compile("([0-9])+(일 뒤)").matcher(text).find()){
			int plus = Integer.parseInt(text.replaceAll("[^0-9]", ""));
			cal.add(cal.DATE, plus);
		}
		
		if(Pattern.compile("([0-9])+(주 뒤)").matcher(text).find()){
			int plus = Integer.parseInt(text.replaceAll("[^0-9]", ""));
			cal.add(cal.DATE, plus*7);
		}
		
		if(Pattern.compile("([0-9])+(달 뒤|월 뒤)").matcher(text).find()) {
			int plus = Integer.parseInt(text.replaceAll("[^0-9]", ""));
			cal.add(cal.MONTH, plus);
		}
		
		for(int i = 0; i < dateExp.length; i++) {
			if(text.contains(dateExp[i][0])) {
				int plus = Integer.parseInt(dateExp[i][1]);
				cal.add(cal.DATE, plus);
				break;
			}
		}
		
		if(Pattern.compile("(주)").matcher(text).find()) {
			int length = getCount("[^전저지다]", text);

			if(Pattern.compile("(다)").matcher(text).find()) {
				cal.add(cal.DATE, length*7);
			} else {
				cal.add(cal.DATE, length*-7);
			}
		}
		if(Pattern.compile("(달)").matcher(text).find()) {
			int length = getCount("[^전저지다]", text);
			if(Pattern.compile("(다)").matcher(text).find()) {
				cal.add(cal.MONTH, length*7);
			} else {
				cal.add(cal.MONTH, length*-7);
			}
		}
		if(Pattern.compile("(해|년)").matcher(text).find()) {
			int length = getCount("[^전저지다]", text);
			if(Pattern.compile("(다)").matcher(text).find()) {
				cal.add(cal.YEAR, length*7);
			} else {
				cal.add(cal.YEAR, length*-7);
			}
		}

		Matcher matches = Pattern.compile("(.요일)").matcher(text);
		if(matches.find()) {
			String mch = matches.group(0).replaceAll("요일", "");
			
			String[] week = {"일", "월", "화", "수", "목", "금", "토"};
			for(int i = 0; i < week.length; i++) {
				if(mch.equals(week[i])) {
					cal.add(cal.DATE, (cal.get(Calendar.DAY_OF_WEEK) * -1) + i + 1 );
					break;
				}
				
			}
			
		}
		
		this.YMD = new Date(cal.getTimeInMillis());
		this.fymd = fNow.format( this.YMD );
	}
	public int getCount(String pattern, String text) { return text.replaceAll(pattern, "").length(); }
	public String getYMD() { return this.fymd; }
	public String getKYMD() {
		SimpleDateFormat fNow = new SimpleDateFormat("yyyy년 MM월 dd일 E요일");
		return fNow.format(this.YMD);
	}
}
public class Meal_GSM {
	final static String URL = "https://open.neis.go.kr/hub/mealServiceDietInfo?"
			+ "Type=json"
			+ "&KEY=bfa95730b1b84b07b2db733b2138d9aa&pIndex=1&pSize=100"
			+ "&ATPT_OFCDC_SC_CODE=F10"
			+ "&SD_SCHUL_CODE=7380292";
	
	private EmbedBuilder embed = new EmbedBuilder();
	
	Meal_GSM(String userName, Icon userIcon) {
		this.embed.setColor(Color.RED)
		.setFooter(userName, userIcon)
		.setTimestamp(new Date().toInstant());
	}

	public EmbedBuilder getEmbed() {return this.embed;}
	
	public void getMeal(String content) {
		DateConverter MLSVYMD = new DateConverter(content);
		String urls = URL + "&MLSV_YMD=" + MLSVYMD.getYMD();
		System.out.println(MLSVYMD.getYMD());
		this.embed.setTitle(MLSVYMD.getKYMD());
		
		String doctext = StcFunc.parseText(urls);
		
		JSONParser p = new JSONParser();
		JSONObject obj = null;
		
		try {
			obj = (JSONObject)p.parse(doctext);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		System.out.println(urls);
		int mealCount = 0;
		try {
			mealCount = Integer.parseInt((
					(JSONObject)(
							(JSONArray)(
									(JSONObject)(
											(JSONArray)obj.get("mealServiceDietInfo")
											).get(0)
									).get("head")
							).get(0)
					).get("list_total_count").toString());
		} catch(java.lang.NullPointerException e) {
			System.out.println("없는급식");
		}
		
		
		System.out.println(mealCount + "개의 급식이 검색되었습니다.");
		if(mealCount != 0) {
			JSONArray info = (JSONArray)(
					(JSONObject)(
							(JSONArray)obj.get("mealServiceDietInfo")
							).get(1)
					).get("row");
			int mealTarget = content.contains("조식") || content.contains("아침") ? 0 :
								(content.contains("중식") || content.contains("점심")? 1 : 
								(content.contains("석식") || content.contains("저녁") ? 2 : 3));
			
			if(mealTarget == 3) {
				for(int i = 0; i < mealCount; i++) {
					JSONObject mealType = (JSONObject) info.get(i);
					
					String fieldTitle = mealType.get("MMEAL_SC_NM").toString();
					String fieldDesc = mealType.get("DDISH_NM").toString();
					fieldDesc = fieldDesc.replaceAll("[*0-9./]", "");
					fieldDesc = fieldDesc.replaceAll(" ", "\n");
					embed.addInlineField(fieldTitle, fieldDesc);
				}
			}else {
				JSONObject mealType = (JSONObject) info.get(mealTarget);
				String fieldTitle = mealType.get("MMEAL_SC_NM").toString();
				String fieldDesc = mealType.get("DDISH_NM").toString();
				fieldDesc = fieldDesc.replaceAll("[*0-9./]", "");
				fieldDesc = fieldDesc.replaceAll(" ", "\n");
				embed.addInlineField(fieldTitle, fieldDesc);
			}
		} else {
			embed.addInlineField("오류", "급식이 없습니다.");
		}

	}
}

package com.github.sunrabbit123.Liechu_bot;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	private String ymd;
	
	DateConverter(String content){
		SimpleDateFormat fNow = new SimpleDateFormat("yyyyMMdd");
		Date tNow = new Date();
		this.YMD = tNow;
		this.ymd = fNow.format( tNow );
	}
	
	public String getYMD() { return this.ymd; }
	public String getKYMD() {
		SimpleDateFormat fNow = new SimpleDateFormat("yyyy년 MM월 dd일");
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
		String urls = URL + "&MLSV_YMD=" + "20201125";//MLSVYMD.getYMD();
		this.embed.setTitle(MLSVYMD.getKYMD());
		
		Document doc = null;
		try {
			doc = Jsoup.connect(urls).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String doctext = doc.text();
		JSONParser p = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject)p.parse(doctext);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println(urls);
		int mealCount = Integer.parseInt((
				(JSONObject)(
						(JSONArray)(
								(JSONObject)(
										(JSONArray)obj.get("mealServiceDietInfo")
										).get(0)
								).get("head")
						).get(0)
				).get("list_total_count").toString());
		
		JSONArray info = (JSONArray)(
							(JSONObject)(
									(JSONArray)obj.get("mealServiceDietInfo")
									).get(1)
							).get("row");
		
		System.out.println(mealCount + "개의 급식이 검색되었습니다.");
		System.out.println(info.toJSONString());
		for(int i = 0; i < mealCount; i++) {
			JSONObject mealType = (JSONObject) info.get(i);
			
			String fieldTitle = mealType.get("MMEAL_SC_NM").toString();
			String fieldDesc = mealType.get("DDISH_NM").toString();
			fieldDesc = fieldDesc.replaceAll("[*0-9./]", "");
			fieldDesc = fieldDesc.replaceAll(" ", "\n");
			embed.addInlineField(fieldTitle, fieldDesc);
		}

	}
}

package com.github.sunrabbit123.Liechu_bot;

import java.awt.Color;
import java.util.Date;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

class Date_converter{
	
}
public class Meal_GSM {
	final static String URL = "https://open.neis.go.kr/hub/mealServiceDietInfo?"
			+ "KEY=bfa95730b1b84b07b2db733b2138d9aa&pIndex=1&pSize=100" + "&ATPT_OFCDC_SC_CODE=F10"
			+ "&SD_SCHUL_CODE=7380292";
	
	private EmbedBuilder embed = new EmbedBuilder();
	
	Meal_GSM(String userName, Icon userIcon) {
		this.embed.setColor(Color.RED)
		.setFooter(userName, userIcon)
		.setTimestamp(new Date().toInstant());
	}
	
	
	public EmbedBuilder getEmbed() {return this.embed;}
	
	public void getMeal() {
		
	}
}

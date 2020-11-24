package com.github.sunrabbit123.Liechu;

import java.awt.Color;
import java.util.Date;

import org.javacord.api.entity.Icon;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Meal_GSM {
	private EmbedBuilder embed = new EmbedBuilder();
	
	Meal_GSM(String userName, Icon userIcon) {
		this.embed.setColor(Color.RED)
		.setFooter(userName, userIcon)
		.setTimestamp(new Date().toInstant());
	}
	
	
	public EmbedBuilder getEmbed() {return embed;}
	
	public void setDescription() {
		
	}
}

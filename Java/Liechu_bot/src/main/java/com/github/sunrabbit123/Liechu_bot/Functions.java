package com.github.sunrabbit123.Liechu_bot;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;


class StcFunc{
	public static void logPrint( String content ) {
		SimpleDateFormat fNow = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
		Date tNow = new Date();
		
		String ftNow = fNow.format( tNow );
		
		System.out.println("[LOG " + ftNow + "] : " + content);
	}
	
	public static void chatPrint( String content ) {
		SimpleDateFormat fNow = new SimpleDateFormat("yyyy:MM:dd hh:mm:ss");
		Date tNow = new Date();
		
		String ftNow = fNow.format( tNow );
		
		System.out.println("[Chat " + ftNow + "] : " + content);
	}
}

public class Functions implements MessageCreateListener{

	final static String prefix = "라이츄";
	
	@Override
	public void onMessageCreate(MessageCreateEvent ev) {
		Message msg = ev.getMessage();
		
		if( msg.getAuthor().isBotUser() ){ return; }

		String content = msg.getContent();
		StcFunc.chatPrint(ev.getMessageAuthor().getName() + " : " + content);
		
		if( !content.startsWith(prefix)) { return; }
		else {
			content = content.replace("라이츄 ", "");
			if( content.contains("굴러") ) {
				roll(msg);
			}else if( content.contains("골라")) {
				choice(msg);
			}else if( content.contains("급식") ||
					content.contains("아침") ||
					content.contains("조식") ||
					content.contains("점심") ||
					content.contains("중식") ||
					content.contains("저녁") ||
					content.contains("석식") ||
					content.contains("밥") ||
					content.contains("배고파") ||
					content.contains("헝그리")){
				meal(ev);
			}else if( content.contains("주사위") ) {
				dice(msg);
			}
			
		
		}
	}
	private static int getRand(int end) {
		return (int)(Math.random()*end);
	}
	private static int getRand(int start, int end) {
		return (int)(Math.random()*end) + start;
	}
	private static void roll(Message msg) {
		switch( getRand(1, 3)){
		case 1: msg.getChannel().sendMessage("데구르르 쾅!");
		case 2: msg.getChannel().sendMessage("데구르르.. 꽈당!");
		case 3: msg.getChannel().sendMessage("데구르르... 데굴");
		}
	}
	private static void dice(Message msg) {
		msg.getChannel().sendMessage("데구르르....");
		msg.getChannel().sendMessage(getRand(5)+1 +" 이(가) 나왔습니다!");
		
	}
	private static void meal(MessageCreateEvent ev) {
		Meal_GSM mealMsg = new Meal_GSM(ev.getMessageAuthor().getName(), ev.getMessageAuthor().getAvatar());
		mealMsg.getMeal(ev.getMessageContent());
		ev.getMessage().getChannel().sendMessage(mealMsg.getEmbed());
	}
	private static void choice(Message msg) {
		String[] text = msg.getContent().replaceAll("골라 ", "").split(",");
		String choiced = text[getRand(text.length)];
		msg.getChannel().sendMessage("제가 고른건.... " + choiced + "입니다!");
	}
}



package com.github.sunrabbit123.Liechu;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Functions implements MessageCreateListener{

	final static String prefix = "라이츄";
	
	@Override
	public void onMessageCreate(MessageCreateEvent ev) {
		Message msg = ev.getMessage();
		
		if( msg.getAuthor().isBotUser() ){ return; }

		String content = msg.getContent();
		System.out.println("야호");
		if( !content.startsWith(prefix)) { return; }
		
		if( content.contains("굴러") ) {
			roll(msg);
		}else if( content.contains("급식") ){
			
		}else if( content.contains("주사위") ) {
			dice(msg);
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
		msg.getChannel().sendMessage(getRand(5)+1 +"이(가) 나왔습니다!");
		
	}

}


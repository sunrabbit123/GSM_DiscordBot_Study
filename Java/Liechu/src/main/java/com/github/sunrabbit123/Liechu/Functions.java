package com.github.sunrabbit123.Liechu;

import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class Functions implements MessageCreateListener{

	final static String prefix = "������";
	
	@Override
	public void onMessageCreate(MessageCreateEvent ev) {
		Message msg = ev.getMessage();
		
		if( msg.getAuthor().isBotUser() ){ return; }

		String content = msg.getContent();
		System.out.println("��ȣ");
		if( !content.startsWith(prefix)) { return; }
		
		if( content.contains("����") ) {
			roll(msg);
		}else if( content.contains("�޽�") ){
			
		}else if( content.contains("�ֻ���") ) {
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
		case 1: msg.getChannel().sendMessage("�������� ��!");
		case 2: msg.getChannel().sendMessage("��������.. �ʴ�!");
		case 3: msg.getChannel().sendMessage("��������... ����");
	}
	}
	private static void dice(Message msg) {
		msg.getChannel().sendMessage("��������....");
		msg.getChannel().sendMessage(getRand(5)+1 +"��(��) ���Խ��ϴ�!");
		
	}

}


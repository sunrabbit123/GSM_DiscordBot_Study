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

	final static String prefix = "������";
	
	@Override
	public void onMessageCreate(MessageCreateEvent ev) {
		Message msg = ev.getMessage();
		
		if( msg.getAuthor().isBotUser() ){ return; }

		String content = msg.getContent();
		StcFunc.chatPrint(ev.getMessageAuthor().getName() + " : " + content);
		
		if( !content.startsWith(prefix)) { return; }
		else {
			content = content.replace("������ ", "");
			if( content.contains("����") ) {
				roll(msg);
			}else if( content.contains("���")) {
				choice(msg);
			}else if( content.contains("�޽�") ||
					content.contains("��ħ") ||
					content.contains("����") ||
					content.contains("����") ||
					content.contains("�߽�") ||
					content.contains("����") ||
					content.contains("����") ||
					content.contains("��") ||
					content.contains("�����") ||
					content.contains("��׸�")){
				meal(ev);
			}else if( content.contains("�ֻ���") ) {
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
		case 1: msg.getChannel().sendMessage("�������� ��!");
		case 2: msg.getChannel().sendMessage("��������.. �ʴ�!");
		case 3: msg.getChannel().sendMessage("��������... ����");
		}
	}
	private static void dice(Message msg) {
		msg.getChannel().sendMessage("��������....");
		msg.getChannel().sendMessage(getRand(5)+1 +" ��(��) ���Խ��ϴ�!");
		
	}
	private static void meal(MessageCreateEvent ev) {
		Meal_GSM mealMsg = new Meal_GSM(ev.getMessageAuthor().getName(), ev.getMessageAuthor().getAvatar());
		mealMsg.getMeal(ev.getMessageContent());
		ev.getMessage().getChannel().sendMessage(mealMsg.getEmbed());
	}
	private static void choice(Message msg) {
		String[] text = msg.getContent().replaceAll("��� ", "").split(",");
		String choiced = text[getRand(text.length)];
		msg.getChannel().sendMessage("���� ����.... " + choiced + "�Դϴ�!");
	}
}



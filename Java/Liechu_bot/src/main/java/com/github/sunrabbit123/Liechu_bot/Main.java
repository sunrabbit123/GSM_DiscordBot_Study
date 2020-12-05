package com.github.sunrabbit123.Liechu_bot;

import java.io.BufferedReader;
import java.io.FileReader;


import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;


class Token {
	static String getToken(String path) {
		String prv = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			prv = br.readLine();
			br.close();
		} catch (Exception e) {
			System.out.println("��ū�� �̻��ѵ�");
		}
		
		return prv;
		
	}
}


public class Main {

	public static void main(String[] args) {
		String path = "./token.txt";
		String token = Token.getToken(path);
		
		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
		
//		api.addMessageCreateListener(ev -> {
//			ev.getChannel().sendMessage("����,,,?");
//		});
//		
		api.addMessageCreateListener( new Functions() );
				
		api.addServerJoinListener( ev -> { StcFunc.logPrint( ev.getServer().getName() + " ������ �ʴ�Ǿ����ϴ�."); });
		api.addServerLeaveListener( ev -> { StcFunc.logPrint( ev.getServer().getName() + " ������ ������Ͽ����ϴ�."); });

		
		System.out.println("You can invite the bot by the using the follow url : " + api.createBotInvite());
		
	}

}

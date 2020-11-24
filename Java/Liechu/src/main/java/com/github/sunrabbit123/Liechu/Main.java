package com.github.sunrabbit123.Liechu;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);
	
	public static void main(String[] args) {
		String path = "C:\\Users\\user\\Desktop\\GSM_DiscordBot_Study\\Java\\Liechu\\token.txt";
		String token = Token.get_token(path);
		DiscordApi api = new DiscordApiBuilder()
				.setToken(token)
				.login().join();
		
		logger.info("You can invite me by using the followwing url : " + api.createBotInvite());

		api.addListener( new Functions() );
		
		api.addServerJoinListener( ev -> { logger.info( ev.getServer().getName() + " ������ �ʴ�Ǿ����ϴ�."); });
		api.addServerLeaveListener(ev -> { logger.info(ev.getServer().getName() + " ������ ������Ͽ����ϴ�."); });
		
		
	}
	

}

class Token {
	static String get_token(String path) {
		String prv = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			prv = br.readLine();
			br.close();
		} catch (Exception e) {
			System.out.println("��ū�� �̻��ѵ�");
		}
		
		String token = prv;
		
		
		return token;
	}
}


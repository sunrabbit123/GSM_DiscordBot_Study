package com.github.sunrabbit123.Liechu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.event.server.ServerJoinEvent;
import org.javacord.api.listener.server.ServerJoinListener;

public class Main {
	
	public static void main(String[] args) {
		String path = "C:\\Users\\user\\Desktop\\GSM_DiscordBot_Study\\Java\\Liechu\\token.txt";
		String token = Token.get_token(path);
		DiscordApi api = new DiscordApiBuilder().
				setToken(token)
				.setWaitForServersOnStartup(false)
				.login().join();
		
		printLog("봇 작동 시작");
		
		api.addServerJoinListener( new ServerJoinListener() {
			public void onServerJoin(ServerJoinEvent ev) {
			    printLog(ev.getServer().getName() + "서버에 초대되었습니다.");
			}
		} );
		api.addMessageCreateListener( new Functions() );
		
		
	}
	private static void printLog( String content ) {
		SimpleDateFormat Fnow = new SimpleDateFormat ("yyyy.MM.dd ahh:mm:ss");
		Date Dnow = new Date();
		String TFnow = Fnow.format(Dnow);
		
		System.out.println("Log[" + TFnow + "] : " + content );
		
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
			System.out.println("토큰이 이상한디");
		}
		
		String token = prv;
		
		
		return token;
	}
}


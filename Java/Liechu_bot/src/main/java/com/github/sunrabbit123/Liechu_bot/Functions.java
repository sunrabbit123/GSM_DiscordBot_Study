package com.github.sunrabbit123.Liechu_bot;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;




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
	public static String parseText( String url ) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url)
					.userAgent("Mozilla")
					.get();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return doc.text();
		
	}
	public static Document parseDoc( String url ) { 
		System.out.println(url);
		Document doc = null;
		try {
			doc = Jsoup.connect(url)
					.userAgent("Mozilla")
					.get();
		} catch (IOException e) {
			doc = Jsoup.parse("<h1>값이 없습니다.</h1>");
			e.printStackTrace();
		}
		return doc;
		
	}
}

public class Functions implements MessageCreateListener{

	final static String prefix = "라이츄 ";
	final static String musicPrefix = "츄 ";
	@Override
	public void onMessageCreate(MessageCreateEvent ev) {
		Message msg = ev.getMessage();
		
		if( msg.getAuthor().isBotUser() ){ return; }

		String content = msg.getContent();
		StcFunc.chatPrint(ev.getMessageAuthor().getName() + " : " + content);
		if( content.startsWith(musicPrefix)) {
			String keyword = content.split(" ")[1];
			music(keyword, ev);
			
			
		} else if( !content.startsWith(prefix)) { 
			String res = ((new CommandManager()).SelectCommand(content));
			if(res.startsWith("먀아,,,?") || res.startsWith("이츄,,,?")) {
				return;
			} else {
				msg.getChannel().sendMessage(res);
			}
		} else {
			content = content.replace("라이츄 ", "");
			if( content.contains("굴러") ) {
				roll(msg);
			}else if( content.contains("골라")) {
				choice(msg);
			}else if( content.contains("잊어") ) {
				forget(msg);
			}else if( content.contains("배워") ) {
				learn(msg);
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
			}else {
				customCommand(msg);
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
		String[] text = msg.getContent().replaceAll("라이츄 골라 ", "").split(",");
		String choiced = text[getRand(text.length)];
		msg.getChannel().sendMessage("제가 고른건.... " + choiced + "입니다!");
	}
	private static void learn(Message msg) {
		String[] content = msg.getContent().replaceAll("라이츄 배워 ", "").split(":");
		String key = content[0];
		String value = content[1];
		String server = msg.getServer().toString().replaceAll("Optional\\[", "").replaceAll("\\]", "");
		String publisher = msg.getUserAuthor().toString().replaceAll("Optional\\[", "").replaceAll("\\]", "");
		
		msg.getChannel().sendMessage((new CommandManager()).InsertCommand(server, key, value, publisher));
		
	}
	private static void forget(Message msg) {
		String key = msg.getContent().replaceAll("라이츄 잊어 ", "");
		
		msg.getChannel().sendMessage((new CommandManager()).DeleteCommand(key));
	}
	private static void customCommand(Message msg) {
		String key = msg.getContent().replaceAll("라이츄 ", "");
		String res = ((new CommandManager()).SelectCommand(key));
		msg.getChannel().sendMessage(res);
	}
	private static void musicPlay(DiscordApi api, AudioConnection aC, String contents) {
		
		AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        AudioPlayer player = playerManager.createPlayer();
        
        AudioSource source = new LavaplayerAudioSource(api, player);
        aC.setAudioSource(source);

        playerManager.loadItem(contents, new AudioLoadResultHandler() {
            
            @Override
            public void trackLoaded(AudioTrack track) {
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (AudioTrack track : playlist.getTracks()) {
                    player.playTrack(track);
                }
            }

            @Override
            public void noMatches() {
                // Notify the user that we've got nothing
            }

            @Override
            public void loadFailed(FriendlyException throwable) {
                // Notify the user that everything exploded
            }
        });
	}
	private static void music(String keyword, MessageCreateEvent ev) {
		if(keyword.equals("들어와") || keyword.equals("ㄷ")) {
			ev.getMessageAuthor().getConnectedVoiceChannel().ifPresent(voice -> voice.connect().thenAccept(aC -> {
				ev.getChannel().sendMessage(new EmbedBuilder()
						.setDescription(aC.getChannel().getName() + "에 연결했어요!")
						.setColor(Color.GREEN));
			}).exceptionally(e -> {
				ev.getChannel().sendMessage("아고--고 채널에 먼저 연결해주세요");
				e.printStackTrace();
				return null;
			}));
		} else if(keyword.equals("나가") || keyword.equals("퇴출") || keyword.equals("ㅌ") || keyword.equals("ㄴ") || keyword.equals("퇴장")) {
			ev.getServer().get().getAudioConnection().get().close();
			ev.getChannel().sendMessage(new EmbedBuilder()  
					.setDescription("안녕히 계세요 여러분~\n 전 이 세상의 모든 굴레와 속박을\n벗어던지고 제 행복을 찾아 떠납니다~")
					.setColor(Color.RED));
				
		} else if(keyword.equals("재생") || keyword.equals("ㅈ")){
			String text = ev.getMessageContent();
			String link = text.split(" ")[2];
			DiscordApi api = ev.getApi();
			Optional<AudioConnection>aC = ev.getServer().get().getAudioConnection();
			aC.ifPresentOrElse(voice -> musicPlay(api, voice, link),
								() -> ev.getMessageAuthor().getConnectedVoiceChannel().ifPresentOrElse(
									voice -> voice.connect().thenAccept(audioConnect ->musicPlay(api, audioConnect, link)),
									() -> ev.getChannel().sendMessage(new EmbedBuilder().setDescription("먼저 채널에 입장해주세요!"))
									));
					
			
		}
		
		
	}
}




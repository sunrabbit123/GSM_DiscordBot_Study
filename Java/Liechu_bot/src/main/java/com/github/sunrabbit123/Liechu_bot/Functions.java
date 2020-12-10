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
			doc = Jsoup.parse("<h1>���� �����ϴ�.</h1>");
			e.printStackTrace();
		}
		return doc;
		
	}
}

public class Functions implements MessageCreateListener{

	final static String prefix = "������ ";
	final static String musicPrefix = "�� ";
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
			if(res.startsWith("�Ͼ�,,,?") || res.startsWith("����,,,?")) {
				return;
			} else {
				msg.getChannel().sendMessage(res);
			}
		} else {
			content = content.replace("������ ", "");
			if( content.contains("����") ) {
				roll(msg);
			}else if( content.contains("���")) {
				choice(msg);
			}else if( content.contains("�ؾ�") ) {
				forget(msg);
			}else if( content.contains("���") ) {
				learn(msg);
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
		String[] text = msg.getContent().replaceAll("������ ��� ", "").split(",");
		String choiced = text[getRand(text.length)];
		msg.getChannel().sendMessage("���� ����.... " + choiced + "�Դϴ�!");
	}
	private static void learn(Message msg) {
		String[] content = msg.getContent().replaceAll("������ ��� ", "").split(":");
		String key = content[0];
		String value = content[1];
		String server = msg.getServer().toString().replaceAll("Optional\\[", "").replaceAll("\\]", "");
		String publisher = msg.getUserAuthor().toString().replaceAll("Optional\\[", "").replaceAll("\\]", "");
		
		msg.getChannel().sendMessage((new CommandManager()).InsertCommand(server, key, value, publisher));
		
	}
	private static void forget(Message msg) {
		String key = msg.getContent().replaceAll("������ �ؾ� ", "");
		
		msg.getChannel().sendMessage((new CommandManager()).DeleteCommand(key));
	}
	private static void customCommand(Message msg) {
		String key = msg.getContent().replaceAll("������ ", "");
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
		if(keyword.equals("����") || keyword.equals("��")) {
			ev.getMessageAuthor().getConnectedVoiceChannel().ifPresent(voice -> voice.connect().thenAccept(aC -> {
				ev.getChannel().sendMessage(new EmbedBuilder()
						.setDescription(aC.getChannel().getName() + "�� �����߾��!")
						.setColor(Color.GREEN));
			}).exceptionally(e -> {
				ev.getChannel().sendMessage("�ư�--�� ä�ο� ���� �������ּ���");
				e.printStackTrace();
				return null;
			}));
		} else if(keyword.equals("����") || keyword.equals("����") || keyword.equals("��") || keyword.equals("��") || keyword.equals("����")) {
			ev.getServer().get().getAudioConnection().get().close();
			ev.getChannel().sendMessage(new EmbedBuilder()  
					.setDescription("�ȳ��� �輼�� ������~\n �� �� ������ ��� ������ �ӹ���\n��������� �� �ູ�� ã�� �����ϴ�~")
					.setColor(Color.RED));
				
		} else if(keyword.equals("���") || keyword.equals("��")){
			String text = ev.getMessageContent();
			String link = text.split(" ")[2];
			DiscordApi api = ev.getApi();
			Optional<AudioConnection>aC = ev.getServer().get().getAudioConnection();
			aC.ifPresentOrElse(voice -> musicPlay(api, voice, link),
								() -> ev.getMessageAuthor().getConnectedVoiceChannel().ifPresentOrElse(
									voice -> voice.connect().thenAccept(audioConnect ->musicPlay(api, audioConnect, link)),
									() -> ev.getChannel().sendMessage(new EmbedBuilder().setDescription("���� ä�ο� �������ּ���!"))
									));
					
			
		}
		
		
	}
}




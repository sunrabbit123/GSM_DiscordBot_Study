package com.github.sunrabbit123.JavaBot;

import java.awt.Color;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class Main {

	final static String token = "NzAwNjExMzg0OTk5NjczOTM2.XpldMg.EZ5a6LC0pdftpCdq2fH1ePyHl94";
	final static String prefix = "라이츄";
	final static String URL = "https://open.neis.go.kr/hub/mealServiceDietInfo?"
			+ "KEY=bfa95730b1b84b07b2db733b2138d9aa&pIndex=1&pSize=100" + "&ATPT_OFCDC_SC_CODE=F10"
			+ "&SD_SCHUL_CODE=7380292";

	public static void main(String[] args) {
		DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

		CheckTime ckt = new CheckTime(api);
		Thread th = new Thread(ckt);
		th.start();

		System.out.println("Logged in!");
		api.updateActivity("라이츄츄츄츄릅");

		api.addMessageCreateListener(ev -> {

			EmbedBuilder embed = new EmbedBuilder();// 임베드 생성

			String msg = ev.getMessage().getContent();
			String userName = ev.getMessageAuthor().getName();
			TextChannel Ch = ev.getChannel();

			if (msg.startsWith(prefix)) {
				if (msg.contains("fkdlcb")) {
					Ch.sendMessage("라이츄는 한글밖에 모르는걸요!");
				} else if (msg.contains("굴러")) {
					int RandomNum = (int) (Math.random() * 100);
					switch (RandomNum % 3) {
					case 1:
						Ch.sendMessage("데구르르 데굴");
						break;
					case 2:
						Ch.sendMessage("데구르르 데구르");
						break;
					case 3:
						Ch.sendMessage("라이츄를 불러오는 중입니다....\n로딩중....");
						break;
					}
				} else if (msg.contains("주사위")) {
					int Rnum = (int) (Math.random() * 10) % 7;
					Ch.sendMessage("데구르르.... ");
					Ch.sendMessage("또르르르....");
					Ch.sendMessage(Rnum + "입니다..!");

				} else if (msg.contains("급식") || msg.contains("아침") || msg.contains("조식") || msg.contains("점심")
						|| msg.contains("중식") || msg.contains("석식") || msg.contains("저녁")) {
					String Type = msg.contains("아침") || msg.contains("조식") ? "조식"
							: msg.contains("점심") || msg.contains("중식") ? "중식"
									: msg.contains("석식") || msg.contains("저녁") ? "석식" : "급식";
					System.out.println(Type);
					Document doc = null;
					Calendar cal = Calendar.getInstance();
					cal.setTime(new Date());

					int plus_date = 0;

					if (msg.contains("어제")) {
						plus_date--;
					}
					if (msg.contains("내일")) {
						plus_date++;
					}
					if (msg.contains("모레") || msg.contains("모래")) {
						plus_date += 2;
					}
					if (msg.contains("글피")) {
						plus_date += 3;
					}
					if (msg.contains("다음주")) {
						plus_date += 7;
					}
					if (msg.contains("다음달")) {
						cal.add(Calendar.MONTH, 1);
					}
					cal.add(Calendar.DATE, plus_date);
					int year = cal.get(cal.YEAR);
					int month = cal.get(cal.MONTH) + 1;
					int date = cal.get(cal.DATE);

					String YMD = "&MLSV_YMD=";

					if (month < 10) {
						YMD += year + "0" + month;
					} else {
						YMD += year + month;
					}

					if (date < 10) {
						YMD += "0" + date;
					} else {
						YMD += date;
					}

					String URLS = URL + YMD;
					// URL 계산

					try {
						doc = Jsoup.connect(URLS).get();
					} catch (IOException e) {
						e.printStackTrace();
					}
					String doctext = doc.text();
					doctext = doctext.replace(
							"쌀 : 국내산<br/>김치류 : 국내산<br/>고춧가루(김치류) : 국내산<br/>쇠고기(종류) : 국내산(한우)<br/>돼지고기 : 국내산<br/>닭고기 : 국내산<br/>오리고기 : 국내산<br/>쇠고기 식육가공품 : 국내산<br/>돼지고기 식육가공품 : 국내산<br/>닭고기 식육가공품 : 국내산<br/>오리고기 가공품 : 국내산<br/>낙지 : 국내산<br/>고등어 : 국내산<br/>갈치 : 국내산<br/>오징어 : 국내산<br/>꽃게 : 국내산<br/>참조기 : 국내산<br/>콩 : 국내산 ",
							"");
					String mill = null;

					if (!Type.equals("급식")) {
						mill = doctext.substring(doctext.lastIndexOf(Type));
					} else {
						mill = doctext.substring(doctext.lastIndexOf("조식"));
					}
					//필터
					mill = mill.replaceAll("[0-9a-zA-Z.(g):/]", "");
					mill = mill.replaceAll("[*]", "");
					mill = mill.replace("광주광역시교육청  광주소프트웨어마이스터고등학교", "");
					mill = mill.replaceAll("탄수화물  <>단백질  <>지방  <>비타민  <>티아민  <>리보플라빈  <>비타민  <>칼슘  <>철분   ", "");
					mill = mill.replace("     ", "");
					mill = mill.replace("    ", "");

					String[] millList = mill.split("   ");
					if (!Type.equals("급식")) {
						mill = millList[1].replaceAll("<>", "\n");
						embed.addField(Type, mill);
					} else {
						try {
							for (int i = 0; i < 6; i += 2) {
								String type = millList[i];// 조식, 중식, 석식
								mill = millList[i + 1].replaceAll("<>", "\n"); // 메뉴 내용

								embed.addField(type, mill, true);
							}
						} catch (Exception e) {
							System.out.println(e);
						}

					}

					embed.setTitle(year + "년 " + month + "월 " + date + "일").setColor(Color.RED)
							.setFooter(userName, ev.getMessageAuthor().getAvatar())
							.setTimestamp(new Date().toInstant());

					Ch.sendMessage(embed);
					System.out.println(Arrays.toString(millList));
					System.out.println(URLS);
				}

			}
		});
	};

}

class CheckTime implements Runnable {
	DiscordApi api;
	boolean checked = false;
	String targetTime = "16:33:00";

	public CheckTime(DiscordApi api) {
		this.api = api;
	}

	public void run() {
		while (true) {
			SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			Date time = new Date();
			String tm = format.format(time);

//            if (tm.equals(targetTime)) {
//                api.getTextChannelById("719449629963452449").get().sendMessage("테스트인거예요! " + tm + "이 되어서 메시지를 보냈어요!");
//            }
		}
	}
}

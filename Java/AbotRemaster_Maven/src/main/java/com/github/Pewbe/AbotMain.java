package com.github.Pewbe;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AbotMain {
    public static void main(String[] args) {
        String token = "NzIwMTk2MjAxMTQ3OTI0NDkw.XuCc-g.RkysOxDS1NzT5KhKJjap3FEkc0M";
        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        System.out.println("디스코드 로그인에 성공했어요!");

        api.addMessageCreateListener(ev -> {
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            long startTime = System.currentTimeMillis();
            String msg = ev.getMessage().getContent();
            String userName = ev.getMessageAuthor().getName();
            int rand = (int)(Math.random()*6);
            int noCommand = (int)(Math.random()*5);
            int roll = (int)(Math.random()*4);
            EmbedBuilder embed = new EmbedBuilder();

            if( msg.startsWith("에이야") ) {
                System.out.println(startTime + "ms에 저를 호출한 메시지가 도착했어요.");
                if( msg.contains("배워") ) {
                    try {
                        BufferedWriter bw = new BufferedWriter(new FileWriter("D:\\somthing I made\\AbotRemaster_Maven\\CustomCommand.txt", true) );
                        final PrintWriter pw = new PrintWriter( bw, true );
                        String m;
                        System.out.println("배우라는 말을 들었어요.");

                        m = msg.replace("에이야 배워 ", "");
                        if( m.contains(":") ) {
                            String path = "D:\\somthing I made\\AbotRemaster_Maven\\CustomCommand.txt";
                            BufferedReader br = new BufferedReader(new FileReader(path));
                            String buff;
                            boolean isAlready = false;

                            while( ( buff = br.readLine() ) != null ) {
                                if (buff.equals(m))
                                    isAlready = true;
                                System.out.println("데이터베이스를 읽으며 중복이 없는지 확인 중이에요....");
                            }

                            if( isAlready == true )
                                ev.getChannel().sendMessage("음.. 그 말은 이미 할 줄 아는걸요?");
                            else {
                                pw.write( m + "\n" );
                                pw.flush();
                                ev.getChannel().sendMessage("알았어요! 다음부턴 이렇게 말하면 되죠?");
                                pw.close();
                                System.out.println("말을 배웠어요. 데이터베이스에 새로 기록까지 하고, 파일 닫는 것까지 성공!");
                            }
                        } else
                            ev.getChannel().sendMessage("음..명령어를 제대로 입력해 주지 않으면 알아들을 수 없는걸요?");
                    } catch (Exception e) { e.printStackTrace(); }
                }
                if( msg.contains("굴러") ) {
                    switch( roll ){
                        case 1: ev.getChannel().sendMessage("데구르르 쾅!"); break;
                        case 2: ev.getChannel().sendMessage("데구르르 퉤에엣"); break;
                        case 3: ev.getChannel().sendMessage("데구르르..쿠당탕탕!"); break;
                    }
                }
                else if (msg.contains("안녕") || msg.contains("안뇽") || msg.contains("하이") || msg.contains("ㅎㅇ") || msg.contains("하로") || msg.contains("안녀엉")) {
                    SimpleDateFormat format = new SimpleDateFormat ( "HH");
                    Date time = new Date();
                    String tm= format.format(time);
                    String tmDig = null;

                    if( Integer.parseInt(tm) <= 03 )
                        tmDig = "안녕하세요! " + userName + " 씨! 늦은 시간인데 아직 안 주무시는 건가요?";
                    else if( Integer.parseInt(tm) >= 04 && Integer.parseInt(tm) <= 06 )
                        tmDig = userName + " 씨, 좋은 아침이예요! 일찍 일어나셨네요?";
                    else if( Integer.parseInt(tm) >= 07 && Integer.parseInt(tm) <= 11 )
                        tmDig = "좋은 아침이네요, " + userName + " 씨!";
                    else if( Integer.parseInt(tm) >= 12 && Integer.parseInt(tm) <= 14 )
                        tmDig = "안녕하세요! " + userName + " 씨, 점심은 드셨나요?";
                    else if( Integer.parseInt(tm) >= 15 && Integer.parseInt(tm) <= 17 )
                        tmDig = "좋은 낮이네요, " + userName + " 씨!";
                    else if( Integer.parseInt(tm) >= 18 && Integer.parseInt(tm) <= 21 )
                        tmDig = "안녕하세요! " + userName + " 씨, 저녁은 맛있게 드셨나요?";
                    else if( Integer.parseInt(tm) >= 22 && Integer.parseInt(tm) <= 24 )
                        tmDig = "벌써 이런 시간이네요? " + userName + " 씨. 오늘도 좋은 꿈 꾸세요!";

                    switch( rand ){
                        case 1: ev.getChannel().sendMessage("안녕하세요, " + userName + " 씨!"); break;
                        case 2: ev.getChannel().sendMessage("오셨나요, " + userName + " 씨?"); break;
                        case 3: ev.getChannel().sendMessage(userName + " 씨, 반가워요!"); break;
                        case 4: ev.getChannel().sendMessage(userName + " 씨, 안녕하세요!"); break;
                        case 5: ev.getChannel().sendMessage(tmDig); break;
                    }
                }
                else if( msg.contains("핑") ){
                    System.out.println(System.currentTimeMillis() + "ms에 제가 메시지에 답장을 보냈어요.");
                    ev.getChannel().sendMessage("답장을 보내기까지 27...아니라구요? 아..\n.....퐁!");
                }
                else if( msg.contains("조용") ){
                    ev.getChannel().sendMessage("............");
                    try {
                        Thread.sleep(10000);
                    }
                    catch ( InterruptedException e) {e.printStackTrace(); }
                }
                else if( msg.contains("죽어") || msg.contains("주거") || msg.contains("꺼져") ){
                    ev.getChannel().sendMessage("아, 네....\n[접속을 종료합니다.]");
                    api.disconnect();
                    System.out.println("접속을 종료했어요. 가라면 가야죠 뭐. 에휴..");
                }
                else if( msg.contains("도움말") ){
                    embed.setColor( Color.getColor("caf2bb") );
                    embed.setTitle("에이봇 리마스터");
                    embed.setDescription("원래는 만든놈이 자캐씹덕질 하려고 만들었던 봇이지만 지나치게\n씹덕같다는 이유로 버려지고 재탄생한 아마도 대화봇.");
                    embed.addField("에이야 [커맨드]", "호출 시의 위의 키워드를 앞에 붙혀서 호출해야 에이가\n정상적으로 반응합니다. 커맨드에 대한 내용은 아래에 적혀 있습니다.");
                    embed.addField("안녕", "에이가 인사해 줍니다. 인삿말은 랜덤입니다.");
                    embed.addField("굴러", "모든 봇의 버릴 수 없는 정체성. 데구르르 데굴 굴러줍니다.");
                    embed.addField("핑", "원래는 메시지에 답장을 보내기까지 걸리는 시간을 ms단위로\n보내 줄 예정이었지만, 어째선지 작동을 안해서\n더욱 고차원적으로 퐁! 이라고 답해줍니다.");
                    embed.addField("조용", "에이를 10초간 닥치게 합니다. 도배방지 커맨드입니다. ※미완성 커맨드※");
                    embed.addField("배워 [커맨드]:[반응]", "말을 가르칩니다. 구분자가 :이기 때문에 커맨드와 반응에\n공백이든 특수문자든 뭐든 막 때려박아도 되긴 하지만,\n봇이 터질 가능성이 있기에 권장드리진 않습니다.");
                    embed.addField("도움말", "현재 보고 있는 도움말 창을 보냅니다.");
                    embed.addField("죽어", "에이를 죽입니다.\n※정말로 서버 연결이 끊기니 주의해주세요※");
                    embed.addField("계산 [식]", "식을 계산해 줍니다. 일부 인식하지 못하는 수식이 존재합니다.\nex)팩토리얼 등");
                    embed.setFooter("업뎃 문의는 퓨브#4783로");
                    ev.getChannel().sendMessage( embed );
                }
                else if(msg.contains("계산")) {
                    try {
                        String exp;

                        exp = msg.replace("에이야 계산 ", "");
                        ev.getChannel().sendMessage( exp + " 의 계산 결과는 " + engine.eval(exp) + "♪" );
                    } catch (ScriptException e) { e.printStackTrace(); }
                }
                else {
                    try {
                        System.out.println("해당되는 커맨드가 없어서, 배운 말들 중에 있는지 확인하러 왔어요.");
                        String path = "D:\\somthing I made\\AbotRemaster_Maven\\CustomCommand.txt";
                        BufferedReader br = new BufferedReader(new FileReader(path));
                        String buff;
                        String[] splitedArr;
                        List<String> list = new ArrayList<String>();
                        int cnt=0, rep;

                        while( ( buff = br.readLine() ) != null ) {
                            splitedArr = buff.split(":");
                            if( splitedArr[0].equals( msg.replace("에이야 ", "") ) ) {
                                list.add( splitedArr[1] );
                                cnt++;
                            }
                            System.out.println("배운 말들 중에 커맨드가 있는지 확인 중이예요........");
                        }
                        if( list.size() != 0 ) {
                            rep = (int)(Math.random()*(cnt));
                            ev.getChannel().sendMessage( list.get(rep) );
                            System.out.println("배운 말이 있네요! 메시지를 보내는 데까지 완료했어요!");
                        } else {
                            switch ( noCommand ) {
                                case 1: ev.getChannel().sendMessage("흐음.."); break;
                                case 2: ev.getChannel().sendMessage("(딴짓)"); break;
                                case 3: ev.getChannel().sendMessage("부르셨나요?"); break;
                                case 4: ev.getChannel().sendMessage("♬♪~"); break;
                            }
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                }
                }
            });

        // Print the invite url of your bot
        System.out.println("서버 초대 링크는 여기 있어요!: " + api.createBotInvite());
    }
}
package com.github.Pewbe;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AbotMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        String token = "NzIwMTk2MjAxMTQ3OTI0NDkw.XuCc-g.9rso2ajKg-Glro2oLIBWAZPFqAs";
        DiscordApi api = new DiscordApiBuilder()
                .setToken(token)
                .login()
                .join();
        ActivityUpdate actUp = new ActivityUpdate( api );//원래 시간체크하는 스레드였는데 상태 업데이트 스레드로 바꿈
        Birthday bitday = new Birthday( api );
        Thread th = new Thread( actUp );
        Thread th2 = new Thread( bitday );
        //Thread th3 = new Thread( love );

        th.setDaemon( true );
        th2.setDaemon( true );
        //th3.setDaemon( true );

        th.start();
        th2.start();
        //th3.start();

        System.out.println("디스코드 로그인에 성공했어요!");

        //api.getMessageById("757961797641699408", api.getTextChannelById("751074192740581458").get() ).get().delete();

        //api.getUserById("668072089302990908").get().sendMessage("🎉<@" + "668072089302990908" + ">" + "님, 생일 축하드려요! 오늘은 정말 좋은 하루 되시길 바랄게요!🎉");
        //api.getUserById("682556804927979523").get().sendMessage("🎉<@" + "682556804927979523" + ">" + "님, 생일 축하드려요! 오늘은 정말 좋은 하루 되시길 바랄게요!🎉");

        /*
        EmbedBuilder em = new EmbedBuilder();

        em.setTitle("회의언제할까");
        em.setDescription("1️⃣일요일부터\n2️⃣월요일부터");
        long id = api.getTextChannelById("737611769694126133").get().sendMessage(em).get().getId();

        Message.addReaction( api, Long.parseLong("737611769694126133"), id, "1️⃣" );
        Message.addReaction( api, Long.parseLong("737611769694126133"), id, "2️⃣" );
         */

        //api.updateActivity("\"에이야\" 라고 불러주세요!");

        api.addMessageCreateListener(ev -> {
            Message message = ev.getMessage();
            TextChannel channel = ev.getChannel();

            /*
            if( message.getAuthor().getIdAsString().equals("360435715781165067") ){
                message.addReaction("cka:756109959787184138");
            }
             */// -참-

            if( message.getAuthor().isBotUser() ){ return; }

            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            long startTime = System.currentTimeMillis();
            String msg = message.getContent();
            String userName = ev.getMessageAuthor().getName();
            String reply;
            int noCommand = (int)(Math.random()*7);
            int roll = (int)(Math.random()*4);
            EmbedBuilder embed = new EmbedBuilder();

            if( msg.equals("dpdldi rnffj") ) {
                channel.sendMessage("어... 한글로 해 주시면...?" + "\n`💗+2`");
                loveUp(2, message.getAuthor().getId());
            }
            else if( msg.equals("dpdldi dlfgo") )
                channel.sendMessage("으음...그러니까 한글로.....");

            if( !message.getContent().startsWith("에이야") ) { return; }
            System.out.println(Instant.now() + "에 [" + ev.getServer().get().getName() + "] 에서 [" + message.getAuthor().getName() + "] 님이 저를 호출한 메시지를 보냈어요.");

            if( msg.contains("배워") ) {
                learn( msg, ev );//배워 메소드
            }
            else if( msg.contains("잊어") ){
                forget( msg, ev );//잊어 메소드
            }
            else if( msg.contains("굴러") ) {
                switch( roll ){
                    case 1: channel.sendMessage("데구르르 쾅!" + "\n`💗+4`"); break;
                    case 2: channel.sendMessage("데구르르 퉤에엣" + "\n`💗+4`"); break;
                    case 3: channel.sendMessage("데구르르..쿠당탕탕!" + "\n`💗+4`"); break;
                }
                loveUp(4, message.getAuthor().getId());

                System.out.println("굴렀어요!");
            }
            else if (msg.contains("안녕") || msg.contains("안뇽") || msg.contains("하이") || msg.contains("ㅎㅇ") || msg.contains("하로") || msg.contains("안냥")) {
                channel.sendMessage( hello( msg, ev, userName ) );//인사 메소드
            }
            else if( msg.contains("핑") ){
                System.out.println(System.currentTimeMillis() + "ms에 제가 메시지에 답장을 보냈어요.");
                channel.sendMessage("답장을 보내기까지 27...아니라구요? 아..\n.....퐁!");
            }
            else if( msg.contains("심심해") ){
                try {
                    long msgId = channel.sendMessage("관심 없거든요?").get().getId();

                    Thread.sleep(500);
                    Message.edit( api, channel.getId(), msgId, "..아니, 심심하셨나요?" );
                }catch( Exception e ){ e.printStackTrace(); }
            }
            /*
            else if( msg.contains("죽어") || msg.contains("주거") || msg.contains("꺼져") ){
                channel.sendMessage("아, 네....\n[접속을 종료합니다.]");
                api.disconnect();
                System.out.println("접속을 종료했어요. 가라면 가야죠 뭐. 에휴..");
            }
             */
            else if( msg.contains("도움말") || msg.contains("도와줘") || msg.contains("help") ){
                String replaced = msg.replace("에이야 도움말 ", "");
                Color c = new Color( 196, 230, 145 );

                embed.setColor( c );

                if( replaced.equals("대화") ){
                    embed.setTitle("에이봇과 대화")
                         .addField("`에이야 안녕`", "에이가 인사해 줍니다. `인삿말은 랜덤입니다.`")
                         .addField("`에이야 굴러`", "모든 봇의 버릴 수 없는 정체성. 데구르르 데굴 굴러줍니다.")
                         .addField("`에이야 핑`", "원래는 메시지에 답장을 보내기까지 걸리는 시간을 ms단위로\n보내 줄 예정이었지만, 어째선지 작동을 안해서\n더욱 고차원적으로 퐁! 이라고 답해줍니다.")
                         .addField("`에이야 밥`", "오늘의 식사 메뉴를 추천해 드립니다.")
                         .addField("`에이야 참참참`", "참참참을 합니다. 아마도...?");
                }else if( replaced.equals("기능") ){
                    embed.setTitle("에이봇의 기능")
                         //.addField("`에이야 조용`", "에이를 10초간 닥치게 합니다. 도배방지 커맨드입니다. `※미완성 커맨드※`")
                         .addField("`에이야 죽어`", "에이를 죽입니다. `※만든놈의 귀차니즘으로 삭제된 커맨드※`")
                         .addField("`에이야 계산 [식]`", "식을 계산해 줍니다. `일부 인식하지 못하는 수식이 존재합니다.`\nex)팩토리얼 등")
                         .addField("`에이야 생일 [MM.dd]`", "에이에게 생일을 알려줍니다. 등록하고 나면 나중에 생일을 축하해줍니다.\n`한 자릿수는 꼭 앞에 0을 붙혀 두 자릿수로 만들어 넣어주세요.`\n```ex) 2.1(X)  02.01(O)\n※한 번 등록한 생일은 변경할 수 없습니다.※\n생일이 잘못 입력된 것 같으면 퓨브#2222로 문의해주세요.```")
                         .addField("`에이야 프사 [멘션]`", "맨션된 사람의 프로필 사진을 보여줍니다.");
                }else if( msg.contains("가르치기") ){
                    embed.setTitle("에이봇에게 말 가르치기")
                         .addField("`에이야 배워 [커맨드]:[반응]`", "말을 가르칩니다.")
                         .addField("`에이야 잊어 [커맨드]`", "가르친 말을 잊게 합니다. 중복된 커맨드에 다른 대사 여러 개가\n동시에 DB에 존재하는 경우, `함께 전부 지워버리니` 주의해주세요.")
                         .addField("키워드", "반응 문자열에 넣으면 해당 문자열로 치환하는 키워드에 대한 설명입니다.\nex) 안녕하세요 $u님! => 안녕하세요 퓨브님!")
                         .addField("$u", "메시지를 보낸 유저의 이름이 들어갑니다.")
                         .addField("$t", "현재 시간이 오전/오후HH:mm 형식으로 들어갑니다.")
                         .addField("$f", "임의의 음식 이름이 들어갑니다.")
                         .addField("$a", "임의의 동물 이름이 들어갑니다.")
                         .addField("$c", "임의의 국가 이름이 들어갑니다.");
                }else{
                    embed.setTitle("에이봇 리마스터")
                         .setDescription("원본 에이봇을 없애버리고 원본이 된 에이봇 리마스터")
                         .addField("`에이야 [커맨드]`", "호출 시의 위의 키워드를 앞에 붙혀서 호출해야 에이가\n정상적으로 반응합니다. 커맨드에 대한 내용은 아래에 적혀 있습니다.")
                         .addField("`에이야 도움말 대화`", "에이와 잡담을 나눌 때의 도움말을 보여줍니다.")
                         .addField("`에이야 도움말 기능`", "에이가 할 수 있는 기능들에 대한 도움말을 보여 드립니다.")
                         .addField("`에이야 도움말 가르치기`", "명령어 가르치기에 관한 도움말을 보여 드립니다.")
                         .addField("`도움말`", "현재 보고 있는 도움말 창을 보냅니다.");
                }

                embed.setFooter("가끔 명령어가 씹하는 건 고질병. 업뎃문의는 퓨브#2222으로", message.getAuthor().getAvatar())
                     .setTimestamp( Instant.now() );
                channel.sendMessage( embed );
            }
            else if(msg.contains("계산")) {
                try {
                    String exp;

                    exp = msg.replace("에이야 계산 ", "");
                    channel.sendMessage( exp + " 의 계산 결과는 " + engine.eval(exp) + "♪" );
                } catch (ScriptException e) { e.printStackTrace(); }
            }
            else if( msg.contains("뭐하냐") ){
                try {
                    long msgId = channel.sendMessage("(알아서 뭐 하게...)").get().getId();

                    Thread.sleep(500);
                    Message.edit( api, channel.getId(), msgId, "..아무것도 안 하고 있어요!" );
                }catch( Exception e ){ e.printStackTrace(); }
            }
            else if( msg.contains("뒤질래") ){
                try {
                    long msgId = channel.sendMessage("**뭐?**").get().getId();

                    Thread.sleep(300);
                    Message.edit( api, channel.getId(), msgId, ".....네에?" );
                }catch( Exception e ){ e.printStackTrace(); }
            }
            else if( msg.contains("참참참") ){
                try {
                    long msgId = channel.sendMessage("먼저 할게요! 자~ 참참ㅊ.......").get().getId();

                    Thread.sleep(500);
                    Message.edit( api, channel.getId(), msgId, "[알 수 없는 사용자에 의해 참수당했습니다.]" );
                }catch( Exception e ){ e.printStackTrace(); }
            }
            else if( msg.contains("유령") ){
                message.addReaction("👻");
                channel.sendMessage("유령이예요~!");
            }
            else if( msg.contains("밥") ){
                channel.sendMessage( "오늘은 " + getRandomFood( msg, ev ) + " 어떠신가요?" );
            }
            else if( msg.contains("호감도") ){
                Color c = new Color( 196, 230, 145 );
                embed.setColor( c );

                try {
                    if( msg.contains("랭킹") ){
                        channel.sendMessage( showLoveRank( ev, channel, embed, api ) );
                    }
                    else {
                        channel.sendMessage("아, 아직.. 만드는 중이예요!");
                    }
                } catch( Exception e ){ e.printStackTrace(); }
            }
            else if( msg.contains("프사") ){
                String replaced;
                Color c = new Color( 196, 230, 145 );

                embed.setColor( c );

                try {
                    replaced = msg.replace("에이야 프사", "");
                    embed.setImage( api.getUserById(replaced.replaceAll("\\W", "")).get().getAvatar() );
                    embed.setFooter(userName, message.getAuthor().getAvatar());
                    embed.setTimestamp(Instant.now());
                } catch( Exception e ){ e.printStackTrace(); }

                channel.sendMessage( embed );
            }
            else if( msg.contains("생일") ){
                if( msg.endsWith("생일") ){
                    try {
                        String path = "D:\\somthing I made\\AbotRemaster_Maven\\Birthday.txt";
                        BufferedReader br = new BufferedReader(new FileReader(path));
                        String buff;
                        String[] splited;
                        boolean isExist = false;

                        while( (buff = br.readLine()) != null ){
                            if( buff.contains( message.getAuthor().getId() + "" ) ) {
                                splited = buff.split("#");
                                channel.sendMessage( userName + " 씨의 생일은 " + splited[0] + "(으)로 등록되어 있어요.\n만약 생일이 잘못 등록된 것 같으시면, \"퓨브#2222\"로 부탁드려요!" );
                                isExist = true;
                            }
                        }
                        if( !isExist )
                            channel.sendMessage(userName + " 씨의 생일은 등록되어 있지 않은 것 같네요..? 생일에 관한 내용은 \"에이야 도움말 기능\" 을 참고해주세요.");
                    } catch( Exception e ) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        String path = "D:\\somthing I made\\AbotRemaster_Maven\\Birthday.txt";
                        BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
                        final PrintWriter pw = new PrintWriter(bw, true);
                        String birthdayDate;

                        birthdayDate = msg.replace("에이야 생일 ", "");

                        if (dateCheck(birthdayDate, "MM.dd")) {
                            BufferedReader br = new BufferedReader(new FileReader(path));
                            String buff;
                            boolean isAlready = false;

                            while ((buff = br.readLine()) != null) {
                                if (buff.contains(message.getAuthor().getIdAsString()))
                                    isAlready = true;
                                System.out.print(".");
                            }

                            if (isAlready)
                                channel.sendMessage(userName + " 씨의 생일은 이미 등록되어 있네요! \"에이야 생일\" 로 확인해 주세요.");
                            else {
                                pw.write(birthdayDate + "#" + message.getAuthor().getId() + "\n");
                                pw.flush();
                                channel.sendMessage(birthdayDate + "...알겠어요! 잘 기억해 둘게요♬");
                                pw.close();
                                System.out.println(userName + " 씨의 생일을 데이터베이스에 저장했어요.");
                            }
                        } else
                            ev.getChannel().sendMessage("에..그 날짜는 없는 날짜 같은데요..?");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                try {
                    System.out.println("해당되는 커맨드가 없어서, 배운 말들 중에 있는지 확인하러 왔어요.");

                    String path = "D:\\somthing I made\\AbotRemaster_Maven\\CustomCommand.txt";
                    BufferedReader br = new BufferedReader(new FileReader(path));
                    String buff;
                    String[] splitedArr, replacedAns;
                    List<String> list = new ArrayList<String>();
                    SimpleDateFormat format = new SimpleDateFormat ("a hh:mm");
                    Date time = new Date();
                    String tm= format.format(time);
                    int cnt=0, rep;

                        /*
                        System.out.println( Integer.parseInt( tm.substring(0, 2) ) );
                        if( Integer.parseInt( tm.substring(0, 2) ) > 12 ) {
                            tm = "오후" + (Integer.parseInt(tm.substring(0, 2)) - 12) + tm.substring(2, 5);
                            System.out.println( "24시간 형식을 12시간 형식으로 바꾸러 왔어요." );
                        }else
                            tm = "오전" + tm;
                        */

                    while( ( buff = br.readLine() ) != null ) {
                        splitedArr = buff.split(":");
                        if( splitedArr[0].equals( msg.replace("에이야 ", "") ) ) {
                            list.add( splitedArr[1] );
                            cnt++;
                        }
                        System.out.print(".");
                    }
                    if( list.size() != 0 ) {
                        rep = (int)(Math.random()*(cnt));
                        replacedAns = list.get(rep).split("#");//replacedAns[0] = 대답   replacedAns[1] = 유저ID

                        replacedAns[0] = replacedAns[0].replace("$u", userName);
                        replacedAns[0] = replacedAns[0].replace("$t", tm);
                        replacedAns[0] = replacedAns[0].replace("$f", getRandomFood( msg, ev ));
                        replacedAns[0] = replacedAns[0].replace("$a", getRandomAnimal( msg, ev ));
                        replacedAns[0] = replacedAns[0].replace("$c", getRandomCountry( msg, ev ));

                        System.out.println("보낼 문자열: " + replacedAns[0]);

                        if( replacedAns[1].equals("682556804927979523") )
                            channel.sendMessage( replacedAns[0] );
                        else
                            channel.sendMessage( replacedAns[0]); // 작성자 이름 나오게 하는 코드:  + "\n`by." + api.getUserById( replacedAns[1] ).get().getName() + "`"  replacedA

                        System.out.println("배운 말이 있네요! 메시지를 보내는 데까지 완료했어요!");
                    } else {
                        switch ( noCommand ) {
                            case 1: channel.sendMessage("흐음.."); break;
                            case 2: channel.sendMessage("(딴짓)"); break;
                            case 3: channel.sendMessage("부르셨나요?"); break;
                            case 4: channel.sendMessage("♬♪~"); break;
                            case 5: channel.sendMessage(".....?"); break;
                            case 6: channel.sendMessage("가르치기 기능은 이걸 위해서 있는 걸지도..?"); break;
                        }
                    }
                } catch (Exception e) { e.printStackTrace(); }
            }
        });

        // Print the invite url of your bot
        System.out.println("서버 초대 링크는 여기 있어요!: " + api.createBotInvite());
    }

    private static void loveUp( int love, long userId ){
        try {
            if( love > 0 ) {
                String path = "D:\\somthing I made\\AbotRemaster_Maven\\LovePoint.txt";
                BufferedReader br = new BufferedReader(new FileReader(path));
                BufferedWriter bw = new BufferedWriter(new FileWriter(path));
                final PrintWriter pw = new PrintWriter(bw, true);
                String buff;
                String[] splited;
                boolean successfulyUpdated = false;

                System.out.println("증가될 호감도가 있네요.");
                while ((buff = br.readLine()) != null) {
                    System.out.println("누구에게 증가시키면 되는지 찾는 중이예요...");
                    splited = buff.split("#");//0: [호감도], 1: [유저아이디]
                    if( userId == Long.parseLong( splited[1] ) ){
                        System.out.println("호감도를 증가시킬 유저를 찾았어요!");
                        splited[0] += love;
                        bw.write(splited[0] + "#" + splited[1] + "\r\n");

                        System.out.println("성공적으로 호감도를 증가시켰어요. 해당 유저의 최종 호감도: " + splited[0]);
                        System.out.println("수정된 문자열: " + splited[0] + "#" + splited[1]);

                        successfulyUpdated = true;
                    }
                }
                if( !successfulyUpdated ){
                    System.out.println("이 유저는 호감도 증가가 처음인가 봐요.");
                    pw.write(love + "#" + userId + "\r\n");
                    System.out.println("추가된 문자열: " + love + "#" + userId);
                }

                bw.close();
                pw.close();
                br.close();

                System.out.println("호감도를 성공적으로 증가했어요!");
            } else {
                System.out.println("어라..? 증가할 호감도가 없는걸요.. 오류일까요?");
            }
        } catch ( Exception e ){ e.printStackTrace(); }
    }

    private static EmbedBuilder showLoveRank(MessageCreateEvent ev, TextChannel channel, EmbedBuilder embed, DiscordApi api) {
        try {
            String path = "D:\\somthing I made\\AbotRemaster_Maven\\LovePoint.txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String buff;
            String[] splited;
            List<Love> list = new ArrayList<>();

            while( ( buff = br.readLine() ) != null ){
                splited = buff.split("#");//0: 호감도, 1: 유저 ID
                list.add( new Love( splited[0], splited[1] ) );
            }

            Collections.sort( list );

            embed.setTitle("🏆호감도 랭킹🏆");
            embed.setDescription("🥇: " + api.getUserById( list.get(0).getUserId() ).get().getName() + "  " + list.get(0).getLove() + "💗\n"
                                + "🥈: " + api.getUserById( list.get(1).getUserId() ).get().getName() + "  " + list.get(1).getLove() + "💗\n"
                                + "🥉: " + api.getUserById( list.get(2).getUserId() ).get().getName() + "  " + list.get(2).getLove() + "💗");

        } catch ( Exception e ){ e.printStackTrace(); }

        return embed;
    }

    public static boolean dateCheck(String date, String format) {
        SimpleDateFormat dateFormatParser = new SimpleDateFormat(format, Locale.KOREA);
        dateFormatParser.setLenient(false);
        try {
            dateFormatParser.parse(date);
            return true;
        } catch (Exception Ex) {
            return false;
        }
    }

    private static String getRandomAnimal(String msg, MessageCreateEvent ev) {
        int animcnt=0;
        String selectedAnim = null;

        try {
            List<String> anim = new ArrayList<String>();
            String path = "D:\\somthing I made\\AbotRemaster_Maven\\animals.txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String buff;

            while( ( buff = br.readLine() ) != null ) {
                anim.add(buff);
                animcnt++;
                System.out.print(".");
            }

            System.out.println(animcnt + "개의 항목을 리스트에 담는 데 성공했어요!");

            selectedAnim = anim.get( (int)(Math.random()*(animcnt)) );
        } catch ( Exception e ){ e.printStackTrace(); }

        return selectedAnim;
    }

    private static String getRandomCountry(String msg, MessageCreateEvent ev) {
        int councnt=0;
        String selectedCoun = null;

        try {
            List<String> coun = new ArrayList<String>();
            String path = "D:\\somthing I made\\AbotRemaster_Maven\\Countrys.txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String buff;

            while( ( buff = br.readLine() ) != null ) {
                coun.add(buff);
                councnt++;
                System.out.print(".");
            }

            System.out.println(councnt + "개의 항목을 리스트에 담는 데 성공했어요!");

            selectedCoun = coun.get( (int)(Math.random()*(councnt)) );
        } catch ( Exception e ){ e.printStackTrace(); }

        return selectedCoun;
    }

    private static String getRandomFood(String msg, MessageCreateEvent ev) {
        int foodcnt=0;
        String selectedFood = null;

        try {
            List<String> food = new ArrayList<String>();
            String path = "D:\\somthing I made\\AbotRemaster_Maven\\Foods.txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            String buff;

            while( ( buff = br.readLine() ) != null ) {
                food.add(buff);
                foodcnt++;
                System.out.print(".");
            }

            System.out.println(foodcnt + "개의 항목을 리스트에 담는 데 성공했어요!");
            
            selectedFood = food.get( (int)(Math.random()*(foodcnt)) );
        } catch ( Exception e ){ e.printStackTrace(); }

        return selectedFood;
    }

    private static String hello(String msg, MessageCreateEvent ev, String userName) {
        SimpleDateFormat format = new SimpleDateFormat ( "HH");
        Date time = new Date();
        String tm= format.format(time);
        String tmDig = null;
        int rand = (int)(Math.random()*6);

        if( Integer.parseInt(tm) <= 03 )
            tmDig = "안녕하세요, " + userName + " 씨! 늦은 시간인데 아직 안 주무시는 건가요?";
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
            case 1: return "안녕하세요, " + userName + " 씨!";
            case 2: return "오셨나요, " + userName + " 씨?";
            case 3: return userName + " 씨, 반가워요!";
            case 4: return userName + " 씨, 안녕하세요!";
            case 5: return tmDig;
        }
        return null;
    }

    private static void forget(String msg, MessageCreateEvent ev) {//에이야 잊어
        String sumFilePath = "D:\\somthing I made\\AbotRemaster_Maven\\CustomCommand.txt";
        File file = new File(sumFilePath);
        String dummy = "";

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            //삭제하고자 하는 position 이전까지는 이동하며 dummy에 저장
            String line, m, delData;
            String[] buff;
            boolean isDeleteSuccess = false;

            System.out.println("잊으라는 말을 들었어요.");

            m = msg.replace("에이야 잊어 ", "");

            while ((line = br.readLine()) != null) {//line = [커맨드:대답#가르친 유저 ID] 형식의 문자열이 들어가 있음
                buff = line.split(":");//buff[0]=[커맨드] buff[1]=[대답#가르친 유저 ID] 형식으로 들어가 있음

                if (m.equals(buff[0])) {
                    if( buff[1].contains( ev.getMessage().getAuthor().getId() + "" ) ) {
                        delData = br.readLine();
                        System.out.println("다음 데이터를 데이터베이스에서 삭제했어요!: " + delData);
                        isDeleteSuccess = true;
                    }
                    else
                        dummy += (line + "\r\n");
                } else
                    dummy += (line + "\r\n");

                System.out.print(".");
            }

            if (isDeleteSuccess) {
                FileWriter fw = new FileWriter(sumFilePath);
                fw.write(dummy);

                fw.close();
                br.close();
                ev.getChannel().sendMessage("네! " + "\"" + msg.replace("에이야 잊어 ", "") + "\"" + " 커맨드를 삭제했어요.");
                System.out.println("배운 말을 성공적으로 데이터베이스에서 삭제했어요. 내용을 덮어씌우고, 파일을 닫기까지 완료했어요!");
            } else
                ev.getChannel().sendMessage(ev.getMessage().getAuthor().getName() + " 씨에게 그런 걸 배운 기억은 없는걸요?"
                                                + "\n※커맨드는 본인이 가르친 커맨드만 삭제할 수 있어요.※" );
        } catch (Exception e) { e.printStackTrace(); }
    }

    private static void learn(String msg, MessageCreateEvent ev){//에이야 배워
        try {
            String path = "D:\\somthing I made\\AbotRemaster_Maven\\CustomCommand.txt";
            BufferedWriter bw = new BufferedWriter(new FileWriter(path, true));
            final PrintWriter pw = new PrintWriter(bw, true);
            String m;
            System.out.println("배우라는 말을 들었어요.");

            m = msg.replace("에이야 배워 ", "");
            if (m.contains(":")) {
                BufferedReader br = new BufferedReader(new FileReader(path));
                String buff;
                boolean isAlready = false;

                while ((buff = br.readLine()) != null) {
                    if (buff.equals(m))
                        isAlready = true;
                    System.out.print(".");
                }

                if ( isAlready )
                    ev.getChannel().sendMessage("음.. 그 말은 이미 할 줄 아는걸요?");
                else {
                    pw.write(m + "#" + ev.getMessage().getAuthor().getId() + "\n");
                    pw.flush();
                    ev.getChannel().sendMessage("알았어요! 다음부턴 이렇게 말하면 되죠?");
                    pw.close();
                    System.out.println("말을 배웠어요. 데이터베이스에 새로 기록까지 하고, 파일 닫는 것까지 성공!");
                }
            } else
                ev.getChannel().sendMessage("음..명령어를 제대로 입력해 주지 않으면 알아들을 수 없는걸요?");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.github.Pewbe;

import org.javacord.api.DiscordApi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Birthday implements Runnable{
    DiscordApi api;

    public Birthday(DiscordApi api){
        this.api = api;
    }
    public void run() {
        try {
            String path = "D:\\somthing I made\\AbotRemaster_Maven\\Birthday.txt";
            BufferedReader br = new BufferedReader(new FileReader(path));
            boolean chk = false;

            while (true) {
                SimpleDateFormat format = new SimpleDateFormat ("MM.dd");
                Date time = new Date();
                String tm= format.format(time);// MM.dd 형태의 현재 날짜 문자열
                String birthdayDate;

                while( (birthdayDate = br.readLine()) != null ){
                    String[] splited = birthdayDate.split("#");

                    if( tm.equals(splited[0]) && !chk ){
                        api.getUserById(splited[1]).get().sendMessage("🎉<@" + splited[1] + ">" + "님, 생일 축하드려요! 오늘은 정말 좋은 하루 되시길 바랄게요!🎉");
                        System.out.println("유저님의 생일을 축하해 드렸어요!");
                        chk = true;
                    }
                    System.out.println("날짜 비교 중이예요......");
                }
                Thread.sleep(86400000 );
            }
        } catch( Exception e ){
            e.printStackTrace();
        }
    }
}
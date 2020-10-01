package com.github.Pewbe;

import org.javacord.api.DiscordApi;

class ActivityUpdate implements Runnable {
    DiscordApi api;

    public ActivityUpdate(DiscordApi api) {
        this.api = api;
    }

    public void run(){
        while( true ){
            try {
                api.updateActivity("\"에이야 안녕\" 이라고 해 보세요!");
                Thread.sleep(4000);
                api.updateActivity("\"에이야 도움말\" 이라고 해 보세요!");
                Thread.sleep(4000);
                api.updateActivity("\"에이야 밥\" 이라고 해 보세요!");
                Thread.sleep(4000);
                api.updateActivity("\"에이야 굴러\" 라고 해 보세요!");
                Thread.sleep(4000);
                api.updateActivity("\"에이야 참참참\" 이라고 해 보세요!");
                Thread.sleep(4000);
            } catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
/*
시간체크(였던것)

    boolean checked = false;
    String targetTime = "16:33:00";

    public CheckTime( DiscordApi api ) {
        this.api = api;
    }
    public void run() {
        while( true ) {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            Date time = new Date();
            String tm = format.format(time);

            if (tm.equals(targetTime)) {
                //api.getTextChannelById("719449629963452449").get().sendMessage("테스트인거예요! " + tm + "이 되어서 메시지를 보냈어요!");
            }
        }
    }
 */
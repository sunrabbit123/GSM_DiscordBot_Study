const fs = require('fs');

const Discord = require("discord.js");
const client = new Discord.Client();
const hook = new Discord.WebhookClient('746336318166138981', 'IhpONhZszvbvQEhium9Y6Xdo2jH_IVPRtK8JS4OU_O9wThBLJdoXMxjvbDur33X1_ePU');
const prefix = "띵동 ";

const mdbConn = require('./mariaDBconn.js');
const express = require('express');
const app = express();

const _config = require('./config.js');


client.on("ready", () => {
    console.log(`${client.user.tag}님이 봇에 로그인하였습니다.`);
    //hook.send("공지사항"); 공지 용
});

client.commands = new Discord.Collection();
client.aliases = new Discord.Collection();

fs.readdirSync("./Commands/").forEach(dir => {
    const Filter = fs.readdirSync(`./Commands/${dir}`).filter(f => f.endsWith(".js"));
    Filter.forEach(file =>{
        const cmd = require(`./Commands/${dir}/${file}`);
        client.commands.set(cmd.config.name, cmd);
        for(var alias of cmd.config.aliases){
            client.aliases.set(alias, cmd.config.name);
        }
    });
});
function runCommand(command,msg,args,prefix){
    if (client.commands.get(command) || client.aliases.get(command)){
        const cmd = client.commands.get(command) || client.commands.get(client.aliases.get(command));
        if (cmd){cmd.run(client, msg, args, prefix);}
        return;
    }
}

client.on("message", msg => {
    if(msg.author.bot){return;}//봇이면 되돌리기
    else if (msg.content.indexOf(prefix) !== 0){return;}//프리픽스 없으면 되돌리기

    var args = msg.content.slice(prefix.length).trim().split(/ +/g);//슬라이싱
    var command = args.shift().toLowerCase();//여기까지 프리픽스 빼고 나머지

    try{
        runCommand(command,msg,args,prefix);
    } catch(err){
        console.error(err);
    }
});


// 웹훅
// https://discordapp.com/api/webhooks/746336318166138981/IhpONhZszvbvQEhium9Y6Xdo2jH_IVPRtK8JS4OU_O9wThBLJdoXMxjvbDur33X1_ePU
client.login(_config.BotToken());// run bot
/*
if (command === `임베드`) {
        var embed = new Discord.MessageEmbed()
            .setTitle("여기는 대표 타이틀!") // 여기는 임베드에서 타이틀로 사용됩니다!
            .setDescription("여기는 대표 설명!") // 여기는 타이틀을 설명해주는 걸로 사용됩니다!
            .setColor("RED") // 여기는 색상을 설정하는 공간인데 HEX값을 넣으셔도 됩니다! (#7239DA) "red" 말고 다른것들도 있어요! 맨 밑에다가 적어놓을테니 확인해주세요!
            .setFooter("여기는 말머리?") // 여기는 임베드의 밑부분에서 말머리로 사용됩니다!
            .setThumbnail("http://blogfiles.naver.net/20151023_23/shin_0305_1445573936921jrPRT_JPEG/%BD%E6%B3%D7%C0%CF%BF%B9%BD%C3.jpg") // 여기는 임베드에서 썸네일로 불려옵니다! (URL를 넣어가 경로를 기입하면 그 경로에 있는 이미지를 불러와 썸네일로 이용되요!)
            .setImage("http://blogfiles.naver.net/MjAxODA4MjNfMjQ0/MDAxNTM1MDE5ODk1Njc3.c5p_E9tLPEXGnXPAkpOuhpEOm7VLqopETMTfJ9C8CWYg.6FCsIDtjWnd19lSzmw_z1oHm9E7fd39s1RmRPeBOF3Ag.JPEG.dlawldbs20/VD-poem-20150915-01.jpg") // 여기는 임베드에서 이미지로 사용되는 곳입니다. // 위에 설명이랑 같아요

            .setTimestamp() // 여기는 타임스탬프를 설정하는 공간인데 비워두면 현재시각, 여기에 타임스탬프를 넣으시면 그 값에 맞는 시간으로 변환됩니다!
            .addField("여기는 소제목", "여기는 소설명(??)") // 첫번째 칸은 임베드의 소제목, 두번째 칸은 임베드의 소제목의 설명하는 공간입니다! 세번째 칸은 INLINE으로 사용되는데 TRUE 하면 라인에 들어가는거고 FALSE 하면 밑라인으로 내려가게 됩니다.
        msg.reply(embed) // EMBED를 REPLY로 답변합시다!
    }
    */

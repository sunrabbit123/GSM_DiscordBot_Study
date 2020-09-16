require('dotenv').config();

const Token = process.env.Token;
const Discord = require("discord.js");

const client = new Discord.Client();
const prefix = "레무링 ";
const version = process.env.version;

client.once("ready", () => {
    console.log(`v.${version} / ${client.user.tag}봇작동중...`);
    client.user
      .setActivity(`${prefix} 도와줘`, {type: "PLAYING"})
      .then(presence =>
        console.log(
          `Activity set to ${presence.game ? presence.game.name : "none"}`
        )
    )
});
client.on('guildMemberAdd', member => {
    // 서버에서 지정된 채널에 메시지 보내기:
    const channel = member.guild.channels.cache.find(ch => ch.name === '접속접속');
    // 만약 아무 채널도 발견되지 않았다면 보내지 않기
    if (!channel) return
    // 유저를 맨션하며, 메세지를 전송
    channel.send(`어서오세요. ${member}님!`);
});
client.on("message", msg => {

    if (!msg.guild) return;

    if (msg.author.bot) return;

    if (msg.content.indexOf(prefix) !== 0) return;

    if (msg.type == 'GUILD_MEMBER_JOIN') { // 길드에 새로운 인원이 왔을때
        msg.reply("안녕하세요!");
        msg.react('👋');
    }

    let args = msg.content.slice(prefix.length).trim().split(/ +/g);
    let command = args.shift().toLowerCase();

    function input_output (input,output) { //기본적인 입,출력 함수
        if (command === input) {
            msg.reply(output);
        }
    }

    //도움말 임베드 생성

    let help = new Discord.MessageEmbed()

        .setTitle("Rem") // 여기는 임베드에서 타이틀로 사용됩니다!
        .setAuthor(`: 도움말`)
        .setDescription("딩코딩코딩러 굴개가 만든 2번째 디스코드봇") // 여기는 타이틀을 설명해주는 걸로 사용됩니다!
        .setColor("GREEN") // 여기는 색상을 설정하는 공간인데 HEX값을 넣으셔도 됩니다! (#7239DA) "red" 말고 다른것들도 있어요! 맨 밑에다가 적어놓을테니 확인해주세요!
        .setFooter(`v.${version}`) // 여기는 임베드의 밑부분에서 말머리로 사용됩니다.
        // 여기는 임베드에서 썸네일로 불려옵니다! (URL를 넣어가 경로를 기입하면 그 경로에 있는 이미지를 불러와 썸네일로 이용돼요!)
        //.setImage("http://blogfiles.naver.net/MjAxODA4MjNfMjQ0/MDAxNTM1MDE5ODk1Njc3.c5p_E9tLPEXGnXPAkpOuhpEOm7VLqopETMTfJ9C8CWYg.6FCsIDtjWnd19lSzmw_z1oHm9E7fd39s1RmRPeBOF3Ag.JPEG.dlawldbs20/VD-poem-20150915-01.jpg") // 여기는 임베드에서 이미지로 사용되는 곳입니다. // 위에 설명이랑 같아요​
        .setTimestamp() // 여기는 타임스탬프를 설정하는 공간인데 비워두면 현재시각, 여기에 타임스탬프를 넣으시면 그 값에 맞는 시간으로 변환됩니다!
        .addField("레무링", "렘의 접두사입니다. 이후 나오는 커맨드는 모두 레무링을 붙여야 실행됩니다.") // 첫번째 칸은 임베드의 소제목, 두번째 칸은 임베드의 소제목의 설명하는 공간입니다! 세번째 칸은 INLINE으로 사용되는데 TRUE 하면 라인에 들어가는거고 FALSE 하면 밑라인으로 내려가게 됩니다.
        .addField("핑", "퐁이라고 답할줄 알았겠지만 제 봇은 고차원적이기에 봇의 반응속도를 알려줍니다.")
        .addField("굴러", "봇의 교양커맨드, 말이 필요없다.")
        .addField("꽈배기", "금기어 아직 기능은 넣지 않았지만 갠디 테러를 기획 중이다.")
        
    /*** 커맨드 목록들 ***/

    input_output(`도와줘`, help)

    input_output(`핑`,`${client.ws.ping}ms가 제가 당신께 도착하는 시간입니다.`)

    input_output(`안녕`,`${msg.author.tag}님 안녕하세요.`)

    input_output(`굴러`,`데구르르 데ㄱ...꽝!!!`)

    input_output(`샤키봇`,`선배님이십니다.`)

    input_output(`꽈배기`,`ㄴ...죽인다`)
    
    input_output(`굴개`, `제 개발자세요.`)
    
    input_output(`ㅋ`, `왜 웃어요..?`)
    
    input_output(`인마`, `네?`)
    
    input_output(`좋은아침`, "좋은아침입니다.")

    input_output(`버전`, `v.${version} 입니다.`)

})


client.login(Token);
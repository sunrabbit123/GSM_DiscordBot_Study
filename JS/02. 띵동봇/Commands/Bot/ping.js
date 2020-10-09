exports.run = async(client, msg, args, prefix) => {
    msg.reply(`님의 핑은  ${client.ws.ping}ms입니당`);
}

exports.config = {
    name : '핑',
    aliases: ['핑','vld','botping'],
    category: ['bot'],
    des: ['봇의 디스코드 웹소켓 지연시간을 알려드립니다.'],
    use: ['띵동 핑']
}
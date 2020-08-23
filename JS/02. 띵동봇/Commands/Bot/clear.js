exports.run = async(client, msg, args, prefix) => {
    if (!args[0]){return msg.reply("띵동 청소할 값을 정수로 넣어주세요");}
    else if (!Number(args[0])){ return msg.reply("띵동 메세지는 지울 값은 숫자로!");}
    else if (args[0] < 1){ return msg.reply("띵동 메세지를 지울 값은 1보다 크게!");}
    else if (args[0] > 100){ return msg.reply("띵동 메세지를 지울 값이 100보다 크면 안지워져유");}
    else{
        msg.channel.bulkDelete(args[0]).then(msg.reply(`성공적으로 ${args[0]}만큼 값을 삭제하였습니다!`));
    }
}

exports.config = {
    name:"청소",
    aliases : ['clear','clean'],
    category: ['Bot'],
    des: ['bulkDelete'],
    use: ['띵동 청소 <메세지 갯수>']
}
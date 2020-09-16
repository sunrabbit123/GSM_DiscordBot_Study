exports.run = async(client, msg, args, prefix)=>{
    for(var i = 0; i<5; i++){
        msg.channel.send("<@610787845359271966>");
    }

}

exports.config = {
    name:"띵동",
    aliases: ["Eldehd","띵동"],
    category: ['bot'],
    des: ["봇의 핑을 찍고 도망갑니다"],
    use : ["띵동 띵동"]
}
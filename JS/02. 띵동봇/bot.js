const Discord = require("discord.js");
const client = new Discord.Client;
const prefix = "띵동 ";

const mdbConn = require('./mariaDBconn.js');
const express = require('express');
const app = express();


client.on("ready", () =>{
    console.log(`${client.user.tag}님이 봇에 로그인하였습니다.`);
});

client.on("ready", msg =>{
    if(msg.author.bot){return;}
    else if (msg.content.indexOf(prefix) !== 0) return;
    var args = msg.content.slice(prefix.length).trim().split(/ +/g);
    var command = args.shift().toLowerCase();

    if(command === `핑`){msg.reply(`님의 핑은 ${client.ws.ping}ms입니당`);}

    else if(command === `띵동`){
        for(var i = 0; i<5; i++){
            msg.reply("띵동");}}
    
});

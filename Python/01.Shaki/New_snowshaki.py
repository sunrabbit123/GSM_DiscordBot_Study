# region import
import discord
from discord.ext import commands

import asyncio
import random
import time
import datetime

import operator
import os
import re

from functools import partial
from const import Docs,Strings
from web_find import SearchWord
from funcs import print_time, set_embed, basic_command, custom_command
# endregion

#from db_manger import  dbmanger

# region command
def command_find(message,prefixed = True):
    diction = getattr(Strings,'command_prefixes' if prefixed else 'commands')
    for command, string in diction.items():
        if message in string:
            return command
# endregion



class ShakiBot(commands.Bot):
    def __init__(self,*,debug = False, admin : str = '508788780002443284'):
        self.debug = debug
        #self.dbmanger = dbmanger()
        self.prefix =["샤키야","참수진","수진아","Shaki","shaki"]
        self.prefixed = 0
        self.admin = admin
        


        super().__init__(command_prefix = None, help_command=None)

    async def on_ready(self):
        activity = discord.Activity(name='"샤키야 도움말" 이라고 해보지 않으련?', type=discord.ActivityType.playing)
        await self.change_presence(activity=activity)
        print("야생의 샤키가 나타났다!")

   
    @print_time
    async def on_message(self, message : discord.Message):
        await self.wait_until_ready()
        if not message.author.bot:
            command = message.content.lower().split()
            try:    
                prefixed = 1 if (command[0] in self.prefix) else  0
                command = command[prefixed]
            except IndexError:
                return
            
            func = getattr(basic_command
                            if not command in Strings.command_prefixes['custom']
                            else custom_command,
                            "command_%s"%command_find(command, prefixed=prefixed),
                            None)

            try:
                print("%s : %s : %s" % (message.author,message.channel.name,message.content ))
            except UnicodeEncodeError:
                pass#유니코드 에러는 스킵
            if func:
                await func(message)
            else:
                if prefixed == False:
                    return
                else:
                    with open("./funcs/snow_shaki_bot.txt","r",encoding='utf-8') as f:
                        commands = list()
                        saying = message.content[2:]
                        
                        lines = f.readlines()
                        print(lines)
                        for line in lines:
                            line_key = line.split(':;')[0]
                            if line_key in saying:
                                commands.append(line.split(':;')[1])

                        if len(commands) >= 1:
                            send_msg = random.choice(commands)
                            await message.channel.send(send_msg)
                            del commands
                            return
                        else:
                            print("%s는 명령어가 아닙니다.(User : %s)\n" %(command,message.content))
                            return    
    


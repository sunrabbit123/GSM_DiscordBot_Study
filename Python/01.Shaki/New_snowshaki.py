# region import
import discord
from discord.ext import commands

import asyncio
import random
import time
import datetime
from functools import partial

import operator
import os
import re

import pymongo
from const import Docs,Strings
from web_find import SearchWord
from funcs import print_time, set_embed, basic_command, custom_command
from model import custom_command as custom_db
# endregion

# region command
def command_find(message,prefixed = True):
    diction = getattr(Strings,'command_prefixes' if prefixed else 'commands')
    for command, string in diction.items():
        if message in string:
            return command
# endregion



class ShakiBot(commands.Bot):
    def __init__(self, db, *,debug = False, admin : str = '508788780002443284'):
        self.debug = debug
        #self.dbmanger = dbmanger()
        self.prefix =["샤키야","참수진","수진아","Shaki","shaki"]
        self.prefixed = 0
        self.admin = admin
        self.db = custom_db(db)

        


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
            
            finded_command = command_find(command, prefixed = prefixed)
            command_type = finded_command not in Strings.custom
            # True == basic_command
            # False == custom_command

            extension = basic_command if command_type else custom_command
            
            func = None
            try:
                func = getattr(extension, f"command_{finded_command}")
                print("%s : %s : %s" % (message.author,message.channel.name,message.content ))
            except (UnicodeEncodeError, AttributeError):
                pass#유니코드 에러는 스킵, 해당 클래스에 해당 함수가 없어도 스킵
            if func:
                if command_type:
                    await func(message)
                else:
                    await func(message, self.db)
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
    


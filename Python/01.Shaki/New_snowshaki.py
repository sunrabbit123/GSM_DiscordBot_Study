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
from custom_manger import command_manger
from func import print_time, set_embed
# endregion

#from db_manger import  dbmanger


def command_find(message,prefixed = True):
    diction = getattr(Strings,'command_prefixes' if prefixed else 'commands')
    for command, string in diction.items():
        if message in string:
            return command




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
            
            func = getattr(self, "command_%s"%command_find(command, prefixed=prefixed),None)

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
                    with open("snow_shaki_bot.txt","r",encoding='utf-8') as f:
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
                
    async def command_help(self, message):
        emb = set_embed(message, title='깔롤랭은 국룰입니다.', description = Docs.help)
        await message.channel.send(embed = emb)

    async def command_choice(self, message):
        chlist = message.content.split()[2:]
        await message.channel.send(f"내가 뽑은건...!\n{random.choice(chlist)}입니당!")
        
    
    async def command_잊어(self,message):#샤키야 key커맨드
        forget_word = message.content[6:]
        with open('snow_shaki_bot.txt','w', encoding='utf-8') as save_word:
            with open('snow_shaki_bot.txt','r', encoding='utf-8') as read_word:
                lines = read_word.readlines()
                for i in range(len(lines)):
                    if lines[i] in forget_word:
                        try:
                            lines = lines[:i] + lines[i+1:]
                        except IndexError:
                            pass
                save_word.writelines(lines)
        


        await message.channel.send("?\n형신이세요? 알려주고 잊으라하네ㅔ,,,")
        return
        
    

        

    async def command_배워(self,message) :#샤키야 key커맨드 value커맨드
        word = message.content[7:].split(":")
        with open('snow_shaki_bot.txt', 'a', encoding= 'utf-8') as ff:
            ff.write('%s:;%s\n' % (word[0], word[1]))
        
        await message.channel.send("야랄 왜 나한테...")

    
    async def command_구글검색(self, message):
        findg = " ".join(message.content.split()[2:])
        image = SearchWord().get_image(findg)

        if image is None:
            await message.channel.send("이미지 불러오기를 실패했습니다")

        else:
            em = set_embed(message, title = f"{findg}의 이미지 검색 결과")
            em.set_image(url = image)
        await message.channel.send(embed = em)
        
        
            
    async def command_사전검색(self, message):
        findn = " ".join(message.content.split()[2:])
        findit = SearchWord().get_dic(findn)

        if findit is None:
            await message.channel.send("사전검색이 실패했습니다.")

        else:
            em = set_embed(message,
                            title = f"{findn}의 네이버 사전검색 결과",
                            description = findit)
            await message.channel.send(embed = em)
    
    async def command_굴러(self,message):
        await message.channel.send(random.choice(["데구르르 꽝","꽝 데구르르","데구르르 뎅강","ㄷㄱㄹㄹ ㄷㄱ","야랄,,, 너나 구르세요"]))
    
    


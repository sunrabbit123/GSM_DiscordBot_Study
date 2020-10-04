import discord
from discord.ext import commands


import asyncio
import random
import time
import datetime

import operator
import os
import re

from .embed import set_embed
from const import Docs, Strings
from web_find import SearchWord
class basic_command:
    @staticmethod
    async def command_help(message):
        emb = set_embed(message, title='깔롤랭은 국룰입니다.', description = Docs.help)
        await message.channel.send(embed = emb)

    @staticmethod
    async def command_choice(message):
        chlist = message.content.split()[2:]
        await message.channel.send(f"내가 뽑은건...!\n{random.choice(chlist)}입니당!")
    
    @staticmethod
    async def command_굴러(message):
        await message.channel.send(Strings.roll)
    
    @staticmethod
    async def command_구글검색(message):
        findg = " ".join(message.content.split()[2:])
        image = SearchWord().get_image(findg)

        if image is None:
            await message.channel.send("이미지 불러오기를 실패했습니다")

        else:
            em = set_embed(message, title = f"{findg}의 이미지 검색 결과")
            em.set_image(url = image)
        await message.channel.send(embed = em)
    
    @staticmethod
    async def command_사전검색(message):
        findn = " ".join(message.content.split()[2:])
        findit = SearchWord().get_dic(findn)

        if findit is None:
            await message.channel.send("사전검색이 실패했습니다.")

        else:
            em = set_embed(message,
                            title = f"{findn}의 네이버 사전검색 결과",
                            description = findit)
            await message.channel.send(embed = em)
    
class custom_command:
    @staticmethod
    async def command_잊어(message):#샤키야 key커맨드
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
        
    
    @staticmethod
    async def command_배워(message) :#샤키야 key커맨드 value커맨드
        word = message.content[7:].split(":")
        with open('snow_shaki_bot.txt', 'a', encoding= 'utf-8') as ff:
            ff.write('%s:;%s\n' % (word[0], word[1]))
        
        await message.channel.send("야랄 왜 나한테...")

    


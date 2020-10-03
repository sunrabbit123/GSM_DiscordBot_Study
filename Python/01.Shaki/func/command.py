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
    async def command_굴러(self,message):
        await message.channel.send(Strings.roll)
    
    
    
    


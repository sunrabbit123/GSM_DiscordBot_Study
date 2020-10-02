import discord
import datetime

def set_embed(ext : discord.Message):
    embed = discord.Embed(colour = 0x7ACDF4, timestamp=datetime.datetime.now())
    embed.set_footer(text = ext.author.name, icon_url= ext.author.avatar_url)
    return embed
import datetime
import discord
import re

def pattern_Comparison(pattern, text) -> bool:
    comparison = re.search(pattern, text)
    if comparison:
        return True
    else:
        return False

def set_date(text : str, YMWD : str, date : datetime.date, val : int = 1):
    if(pattern_Comparison(re.compile(r'(전|저|지)'), text)):
        date -= datetime.timedelta(days = 365 * val) if YMWD == 'Y' else \
               datetime.timedelta(month = val) if YMWD == 'M' else \
               datetime.timedelta(weeks = val) if YMWD == 'W' else \
               datetime.timedelta(days = val)
    elif(pattern_Comparison(re.compile(r'(후|뒤|다)'), text)):
        date += datetime.timedelta(days = 365 * val) if YMWD == 'Y' else \
               datetime.timedelta(month = val) if YMWD == 'M' else \
               datetime.timedelta(weeks = val) if YMWD == 'W' else \
               datetime.timedelta(days = val)
    else:
        date += datetime.timedelta(days = 365 * val) if YMWD == 'Y' else \
               datetime.timedelta(month = val) if YMWD == 'M' else \
               datetime.timedelta(weeks = val) if YMWD == 'W' else \
               datetime.timedelta(days = val)
def set_Fixed_Date(val, YMWD, date : datetime.date):
    if YMWD == 'Y':
        
    






class get_date:
    def __init__(self, message : discord.Message ):
        text = ' '.join(message.content.split()[1:])f
        self.date = datetime.date.today()

        if pattern_Comparison(re.compile(r'(일|월|달)'), text) and\
            pattern_Comparison(re.compile(r'[0-9]'), text):
            
    

    def format_date():
        formatted = re.sub('[0*9',' ', self.date.isformat)
        formatted = re.sub(
        return formatted
    
        
        

import datetime
import discord
import re

def pattern_Comparison(pattern, text) -> bool:
    comparison = re.search(pattern, text)
    if comparison:
        return True
    else:
        return False
def plus_minus_date(date : datetime.datetime, YMWD, value : int):
    if YMWD == 'Y':
        if value > 0:
            date += datetime.timedelta(days = 365 * value)
        else :
            value *= value
            date -= datetime.timedelta(days = 365 * value)
    elif YMWD == "M":
        if value > 0:
            date += datetime.timedelta(month = value)
        else :
            value *= value
            date -= datetime.timedelta(month = value)
    elif YMWD == "W"
        if value > 0:
            date += datetime.timedelta(weeks = value)
        else :
            value *= value
            date -= datetime.timedelta(weeks = value)
    else :
        if value > 0:
            date += datetime.timedelta(weeks = value)
        else :
            value *= value
            date -= datetime.timedelta(weeks = value)  

def set_date(text : str, YMWD : str, date : datetime.datetime, val : int = 1):
    if(pattern_Comparison(re.compile(r'(전|저|지)'), text)):
        date -= plus_minus_date(date, YMWD, val)
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
    






class get_date:
    def __init__(self, message : discord.Message ):
        text = ' '.join(message.content.split()[1:])
        self.date = datetime.datetime.now()

        if pattern_Comparison(re.compile(r'(일|월|달)'), text) and\
            pattern_Comparison(re.compile(r'[0-9]'), text):
            print(1)
    

    def format_date(self):
        formatted = re.sub('[^0-9]','', self.date.__str__.split("T")[0])
        return formatted
    
        
        

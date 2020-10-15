import datetime
import discord
import re
from const import Strings
def pattern_Comparison(pattern, text) -> bool:
    comparison = re.search(pattern, text)
    if comparison:
        return True
    else:
        return False

def plus_minus_date(date : datetime.datetime, YMWD, value : int) -> datetime.datetime:
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
    elif YMWD == "W":
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
    return date

def set_date(text : str, YMWD : str, date : datetime.datetime, val : int = 1) -> datetime.datetime:
    if(pattern_Comparison(re.compile(r'(전|저|지)'), text)):
        plus_minus_date(date, YMWD, val * -1)
    elif(pattern_Comparison(re.compile(r'(후|뒤|다)'), text)):
        plus_minus_date(date, YMWD, val)
    else:
        plus_minus_date(date, YMWD, val)
    return date
    

def set_Fixed_Date(value : int, YMWD : str, date : datetime.datetime) -> datetime.datetime:
    if YMWD == 'W':
        date.replace(day = value * 7)
    elif YMWD == 'D':
        date.replace(day = value)
    elif YMWD == 'M':
        date.replace(month = value)
    elif YMWD == 'Y':
        date.replace(year = value)
    return date

    
class get_date:
    

    def __init__(self, message : discord.Message ):
        text = ' '.join(message.content.split()[1:])
        Date_Dict = {   "주" : 'W',
                        "달" : 'M',
                        "해" : 'Y',
                        "년" : 'Y'}
        Days_Dict = { "열흘" : "10",
                      "스무날" : "20",
                      "보름" : "15",
                      "그믐" : "30"}
        self.date = datetime.datetime.now()

        if pattern_Comparison(re.compile('[일|월|달|주]'), text) and\
            pattern_Comparison(re.compile('[0-9]'), text):
            YMWD = 'M' if pattern_Comparison(re.compile('[월|달]'), text) else\
                   ('W' if pattern_Comparison(re.compile('[주]'), text) else 'D')
                   
            val = text.replace('[^0-9]', "")
            self.date = set_date(text, YMWD, self.date, int(val))

        length = re.sub('[^다|저|지]', "", text)
        is_DMY = re.sub('[^주|달|해|년]', "", text)
        is_Days_Dict = re.sub('[^열흘|스무날|보름|그믐]', "", text)

        if is_DMY:
            self.date = set_date(text, Date_Dict[is_DMY[0]], self.date, length)
        elif is_Days_Dict:
            self.date = set_date(text, Days_Dict[is_Days_Dict[0]], self.date, length)
        else:
            for i, j, k in zip(Strings.dateCentury, Strings.dateCenturyAbbr, range(0,10)):
                if pattern_Comparison(re.compile(f'[{i}|{j}]'), text):
                    if pattern_Comparison(re.compile('[열]'), text):
                        self.date = set_Fixed_Date(k + 11, 'D', self.date)
                    elif pattern_Comparison(re.compile('[스무]'), text):
                        self.date = set_Fixed_Date(k + 21, 'D', self.date)
                    else:
                        self.date = set_Fixed_Date(k + 1, 'D', self.date)
        for week, num in zip(Strings.week, range(0,7)):
            if pattern_Comparison(re.compile(f'[{week}요일]'), text):
                self.date = set_Fixed_Date(int(self.date.day) - self.date.weekday() + num, 'D', self.date)

                
                






    def format_date(self):
        formatted = re.sub('[^0-9]', '', str(self.date.__str__()))[2:8]
        return formatted
    
        
        

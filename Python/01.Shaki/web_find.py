import aiohttp
import requests
from bs4 import BeautifulSoup

import datetime
import random

import asyncio
import re


class HTMLGetter:
    def __init__(self,url):
        self.url = url

    async def get_html(self):
        async with aiohttp.ClientSession() as cs:
            return (await cs.get(self.url))
        
    async def get_soup(self):
        html= await self.get_html()
        soup = BeautifulSoup(html,'html.parser')
        return await soup

class SearchWord:
    async def get_dic(self,keyword):
        soup = await HTMLGetter("https://terms.naver.com/search.nhn?query=%s&searchType=&dicType=&subject="%keyword).get_soup()
        try :
            expl = soup.find('div' , class_ = "info_area").text

            return expl
        except Exception as e:
            print("검색불가")
            print(e)
            return None
        
    async def get_image(self,keyword):
        soup = await HTMLGetter("https://www.google.co.kr/search?hl=en&tbm=isch&q=%s" % keyword).get_soup()

        try :
            info = soup.find_all("img")
            index = random.randint(1, len(info))
            return info[index]["src"]
        except:
            return None

    async def get_meal(self, plus_date = 0):
        # region URL
        URL = "https://open.neis.go.kr/hub/mealServiceDietInfo?"\
            + "Type=json"\
			+ "&KEY=bfa95730b1b84b07b2db733b2138d9aa"\
            + "&pIndex=1"\
            + "&pSize=100"\
			+ "&ATPT_OFCDC_SC_CODE=F10"\
			+ "&SD_SCHUL_CODE=7380292"
        date : str = str(datetime.datetime.now()).split()[0]
        URL += "&MLSV_YMD" + re.sub("-", "", date)
        # endregion
        soup = await HTMLGetter(URL).get_soup()

        print(soup)
        


if __name__ == "__main__":
    pass

            
        
        

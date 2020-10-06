import aiohttp
import requests
from bs4 import BeautifulSoup
from urllib.parse import quote_plus

import datetime
import random

import json
import asyncio
import re


class HTMLGetter:
    def __init__(self, url):
        self.url = url

    async def get_html(self):
        async with aiohttp.ClientSession() as cs:
            return (await cs.get(self.url))
        
    async def get_soup(self):
        html = await self.get_html()
        return BeautifulSoup(await html.read(),'html.parser')
         

class SearchWord:
    async def get_dic(self, keyword):
        soup = await HTMLGetter("https://terms.naver.com/search.nhn?query=%s&searchType=&dicType=&subject="%keyword).get_soup()
        try :
            expl = soup.find('div' , class_ = "info_area").text

            return expl
        except Exception as e:
            print("검색불가")
            print(e)
            return None

    async def get_image(self, keyword):
        soup = await HTMLGetter(f"https://www.google.co.kr/search?q={keyword}&source=lnms&tbm=isch").get_soup()
        # https://www.google.co.kr/search?q=%EB%9D%BC%EC%9D%B4%EC%B8%84
        print(soup)
        try :
            info = soup.find_all("img")
            print(info)
            print(len(info))
            index = random.randint(1, len(info))
            return info[index]["src"]
        except Exception as e:
            print(e)
            return None

    @staticmethod
    async def get_meal(plus_date = 0):
        # region URL
        URL = "https://open.neis.go.kr/hub/mealServiceDietInfo?"\
            + "Type=json"\
			+ "&KEY=bfa95730b1b84b07b2db733b2138d9aa"\
            + "&pIndex=1"\
            + "&pSize=100"\
			+ "&ATPT_OFCDC_SC_CODE=F10"\
			+ "&SD_SCHUL_CODE=7380292"
        date : str = str(datetime.datetime.now()).split()[0]
        URL += "&MLSV_YMD=" + re.sub("-", "", date)
        # endregion
        print(URL)
        data = json.loads(await(await HTMLGetter(URL).get_html()).read())
        return data["mealServiceDietInfo"][1]['row']
        


if __name__ == "__main__":
    pass

            
        
        

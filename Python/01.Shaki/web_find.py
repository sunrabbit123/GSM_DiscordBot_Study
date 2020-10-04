import requests
from bs4 import BeautifulSoup
import datetime
import random
class HTMLGetter:
    def __init__(self,url):
        self.url = url

    def get_html(self):
        try:
            response = requests.get(self.url)
        except requests.exceptions.ConnectionError:
            return None
        html = response.text
        return html

    def get_soup(self):
        html= self.get_html()
        soup = BeautifulSoup(html,'html.parser')
        return soup

class SearchWord:
    def get_dic(self,keyword):
        soup = HTMLGetter("https://terms.naver.com/search.nhn?query=%s&searchType=&dicType=&subject="%keyword).get_soup()
        try :
            expl = soup.find('div' , class_ = "info_area").text

            return expl
        except :
            print("검색불가")
            return None
        
    def get_image(self,keyword):
        soup = HTMLGetter("https://www.google.co.kr/search?hl=en&tbm=isch&q=%s" % keyword).get_soup()

        try :
            info = soup.find_all("img")
            index = random.randint(1, len(info))
            return info[index]["src"]
        except:
            return None

    def get_meal(self, keyword = "오늘" ):
        days = datetime.datetime.now()
        if keyword == "내일":
            days += datetime.timedelta(1)



if __name__ == "__main__":
    pass

            
        
        

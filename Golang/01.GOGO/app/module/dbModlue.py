import pymysql

class Database():
    def __init__(self):
        self.db = pymysql.connect(host='localhost', user='root', password='붹', db='discord_bot', charset='utf8')
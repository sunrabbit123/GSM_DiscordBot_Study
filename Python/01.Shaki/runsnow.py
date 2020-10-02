from New_snowshaki import ShakiBot
from configparser import ConfigParser

config = ConfigParser()
config.read('config.ini')

token = config.get('default', 'token')
admin = config.get('default', 'admin')

print("와ㅏㅏ 실행된다ㅏㅏ")
ShakiBot(admin = admin).run(token)


print("샤키가 사라졌다")


from New_snowshaki import ShakiBot
import os
import pymongo

# token = os.environ["TOKEN"]
# admin = os.environ['admin']
# db_path = os.environ['db']

from configparser import ConfigParser

config = ConfigParser()
config.read("config.ini")
token = config.get("default", "TOKEN")
admin = config.get("default", "ADMIN")
db_path = config.get("default", "DB")

if __name__ == "__main__":
    client = pymongo.MongoClient(db_path)
    db = client.get_database("Shaki").Shaki_command
    print("complete connect db")
    ShakiBot(db, admin=admin).run(token)

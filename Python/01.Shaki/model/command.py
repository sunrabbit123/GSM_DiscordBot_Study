import pymongo

import asyncio

class custom_command:
    def __init__(self, db):
        # db : mongodb database
        self.collect = db
    
    def command_insert(self, key, value, server, user) -> bool :
        self.collect.insert({"key-command" : key,
                            "value-command" : value,
                            "server" : server,
                            "user" : user})
        return True
    
    def command_select(self, key) -> str:
        result = self.collect.find({
            "key-command" : key
        })
        print(result)
    
    def command_delete(self, key) -> bool :
        self.collect.remove({
            "key-command" : key
        })
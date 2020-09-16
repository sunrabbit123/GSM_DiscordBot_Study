from flask import Flask

app = Flask(__name__)

from app.main.index import main as main
    
app.register_blueprint(main)
    
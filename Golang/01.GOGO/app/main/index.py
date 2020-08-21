from flask import Blueprint, request, render_template, flash, redirect, url_for
from flask import current_app as current_app

main = Blueprint('main', __name__, url_prefix='/')

@main.route('/main', method=['GET'])
def index():
    return render_template('/templates/index.html')
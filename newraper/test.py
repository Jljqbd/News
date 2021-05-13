from lxml import etree
import numpy as np
import requests
import json
from openpyxl import Workbook
import time
import hashlib
import os
import datetime
import xlrd
import xlrd
import MySQLdb
filepath = "E:/VSC_project/VSC py/newraper/tt/"
conn = MySQLdb.Connect(host = '127.0.0.1',
                       port = 3306,
                       user = 'root',
                       passwd = 'root',
                       db = 'news',
                       charset='utf8')
cur = conn.cursor()
def toutiao_getdata(url):
    r = requests.get(url)
    webstr = r.text
    html = etree.HTML(webstr)
    newsid = 'tt' + ((url[:-1]).split('/'))[-1]
    authorid = 'ttxw'
    newstitle =  html.xpath("/html/body/div/div/div/h1")
    theme = '头条新闻'
    hidename = '0'
    newscollect = '0'
    newsauthor = "头条新闻"
    content_list = html.xpath("/html/body/div/div/div/div/article/p")
    content_str = []
    for line in content_list:
        if line.text!=None:
            conten_str.append(line.text)
    newscontent = "".join(content_str)
    news_content_path = filepath + newsid + ".txt"
    newslike = '0'
    news_time_list = html.xpath("/html/body/div/div/div/div/div/span")
    for line in news_time_list:
        if ":" in line and "-" in line:
            news_time = line
            break
    newsviewsnum = '0'
    news_imageurl = ",".join(html.xpath("/html/body/div/div/div/div/article/img/@src"))
    with open(news_content_path,'w', encoding='utf-8') as f: # 如果filename不存在会自动创建， 'w'表示写数据，写之前会清空文件中的原有数据！
        f.write(newscontent)
    f.close()
    sql="INSERT INTO newspaper(newsid,authorid, newstitle, newstheme, hidename, newscollect," \
    +"username, news_content_path, newslike, newsOurl, newsviewsnum, imageurl) VALUES" \
    + "('%s', '%s','%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');" \
    % (newsid,authorid, newstitle, theme, hidename, newscollect, newsauthor, news_content_path, \
        newslike, url, newsviewsnum, news_imageurl)
    cur.execute(sql)
    conn.commit()
excel_path = "E:/VSC_project/VSC py/newraper/result/result-2020-06-01-07-06.xlsx"

worksheet = xlrd.open_workbook(excel_path)
sheet_names= worksheet.sheet_names()
print(sheet_names)
sheet = worksheet.sheet_by_name('data')
rows = sheet.nrows # 获取行数
cols = sheet.ncols # 获取列数，尽管没用到
all_content = []
for i in range(rows-1):
    cell = sheet.cell_value(i+1, 1) # 取第二列数据
    all_content.append(cell)
# cols = sheet.col_values(2) # 获取第二列内容 但是整数会处理成float
print(all_content)
os.makedirs(filepath)
for i in range(len(all_content)):
    toutiao_getdata(all_content[i])
    print("进度： "+str(i)+"/"+str(len(all_content)))
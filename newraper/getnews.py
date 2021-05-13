import numpy as np
import requests
import json
from openpyxl import Workbook
import time
import hashlib
import os
import datetime
import MySQLdb
from lxml import etree
from xpinyin import Pinyin
p = Pinyin()
crawling_num = 0
'''
网易: https://3g.163.com
新闻：/touch/reconstruct/article/list/BBM54PGAwangning/0-10.html
娱乐：/touch/reconstruct/article/list/BA10TA81wangning/0-10.html
体育：/touch/reconstruct/article/list\/BA8E6OEOwangning/0-10.html
财经：/touch/reconstruct/article/list/BA8EE5GMwangning/0-10.html
军事：/touch/reconstruct/article/list/BAI67OGGwangning/0-10.html
科技：/touch/reconstruct/article/list/BA8D4A3Rwangning/0-10.html
手机：/touch/reconstruct/article/list/BAI6I0O5wangning/0-10.html
数码：/touch/reconstruct/article/list/BAI6JOD9wangning/0-10.html
时尚：/touch/reconstruct/article/list/BA8F6ICNwangning/0-10.html
游戏：/touch/reconstruct/article/list/BAI6RHDKwangning/0-10.html
教育：/touch/reconstruct/article/list/BA8FF5PRwangning/0-10.html
健康：/touch/reconstruct/article/list/BDC4QSV3wangning/0-10.html
旅游：/touch/reconstruct/article/list/BEO4GINLwangning/0-10.html
视频：/touch/nc/api/video/recommend/Video_Recom/0-10.do?callback=videoList
'''
baseurl = "https://3g.163.com"
modelurl = {'新闻':'/touch/reconstruct/article/list/BBM54PGAwangning/0-10.html',
            '娱乐':'/touch/reconstruct/article/list/BA10TA81wangning/0-10.html',
            '体育':'/touch/reconstruct/article/list/BA8E6OEOwangning/0-10.html',
            '财经':'/touch/reconstruct/article/list/BA8EE5GMwangning/0-10.html',
            '军事':'/touch/reconstruct/article/list/BAI67OGGwangning/0-10.html',
            '科技':'/touch/reconstruct/article/list/BA8D4A3Rwangning/0-10.html',
            '手机':'/touch/reconstruct/article/list/BAI6I0O5wangning/0-10.html',
            '数码':'/touch/reconstruct/article/list/BAI6JOD9wangning/0-10.html',
            '时尚':'/touch/reconstruct/article/list/BA8F6ICNwangning/0-10.html',
            '游戏':'/touch/reconstruct/article/list/BAI6RHDKwangning/0-10.html',
            '教育':'/touch/reconstruct/article/list/BA8FF5PRwangning/0-10.html',
            '健康':'/touch/reconstruct/article/list/BDC4QSV3wangning/0-10.html',
            '旅游':'/touch/reconstruct/article/list/BEO4GINLwangning/0-10.html',
            #'视频':'/touch/nc/api/video/recommend/Video_Recom/0-10.do?callback=videoList'
            }
headers = {
    'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'Accept-Encoding': 'gzip, deflate, br',
    'Accept-Language': 'zh,en-US;q=0.9,en;q=0.8',
    'Cache-Control': 'max-age=0',
    'Connection': 'keep-alive',
    'Cookie': '_ntes_nnid=d819f4b9bcbac0d664fd25498982956a,1590120922170; _ntes_nuid=d819f4b9bcbac0d664fd25498982956a; _ntes_newsapp_install=false; NNSSPID=6eb062a944e145998fd8613cc17660e6',
    'Host': '3g.163.com',
    'Sec-Fetch-Dest': 'document',
    'Sec-Fetch-Mode': 'navigate',
    'Sec-Fetch-Site': 'none',
    'Sec-Fetch-User': '?1',
    'Upgrade-Insecure-Requests': '1',
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36 Edg/83.0.478.37'
}
conn = MySQLdb.Connect(host = '127.0.0.1',
                       port = 3306,
                       user = 'root',
                       passwd = 'root',
                       db = 'news',
                       charset='utf8')
cur = conn.cursor()
def news_data(url, theme, model, filepath):
    global crawling_num
    r = requests.get(url)
    webstr = r.text
    html = etree.HTML(webstr)
    newsid = 'wy'+ model + ((url.split('/')[-1]).split('.'))[0]
    check_isexist_sql = "select * from newspaper where newsid = '%s'" %(newsid)
    sqlnum  = cur.execute(check_isexist_sql)
    if sqlnum != 0:
        return 
    #print(sqlnum)
    #print('时间静止大法')
    authorid = 'wyxw'
    # + ((url.split('/')[-1]).split('.'))[0]
    newstitle = "".join(html.xpath("/html/head/meta[@property='og:title']/@content"))
    if newstitle=='' or newstitle==None:
        return
    #theme
    hidename ='0'
    newscollect = '0'
    newsauthor = '网易'
    # + theme +'/'+ "".join(html.xpath("/html/head/meta[@property='article:author']/@content"))
    html_data = html.xpath("/html/body/main/article/div/div/div/p")
    new_html_data = []
    for line in html_data:
        if line.text!=None:
            new_html_data.append(line.text)
    #newscontent="".join([i.text for i in html_data])
    newscontent="".join(new_html_data)
    news_content_path = filepath + newsid + '.txt'
    newslike = '0'
    news_time = "".join(html.xpath("/html/head/meta[@property='article:published_time']/@content"))
    #url
    newsviewsnum = '0'
    #图片网址用“，”隔开
    news_imageurl = ",".join(html.xpath("/html/head/meta[@property='og:image']/@content"))
    with open(filepath + newsid + '.txt','w', encoding='utf-8') as f: # 如果filename不存在会自动创建， 'w'表示写数据，写之前会清空文件中的原有数据！
        f.write(newscontent)
    f.close()
    sql="INSERT INTO newspaper(newsid,authorid, newstitle, newstheme, hidename, newscollect," \
    +"username, newstime, news_content_path, newslike, newsOurl, newsviewsnum, imageurl) VALUES" \
    + "('%s', '%s','%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');" \
    % (newsid, authorid, newstitle, theme, hidename, newscollect, newsauthor, news_time, news_content_path, \
        newslike, url, newsviewsnum, news_imageurl)
    cur.execute(sql)
    conn.commit()
    crawling_num += 1
def pinyin(text):
    return p.get_initials(text,'').lower()
#data_list = []
for key in modelurl:
    print('正在爬取'+key+'模块...')
    filepath = 'E:/VSC_project/VSC py/newraper/wy/'+key+'/'
    if  not os.path.exists(filepath):
        os.makedirs(filepath)
    r = requests.get(baseurl + modelurl[key], headers = headers)
    #print(url)
    index = r.text.find("(")
    t1 = r.text[index+1:-1]
    #t1 = ("".join(t1[1]))[:-1]
    json_data = json.loads(t1)
    data_list_i = json_data["".join(json_data.keys())]
    for j in range(len(data_list_i)):
        if 'http' not in data_list_i[j]['url']:
            continue
        news_data(data_list_i[j]['url'], key, pinyin(key), filepath)
    #data_list.append(json_data)
conn.close()
print('完成, 一共新添加了 '+str(crawling_num)+" 个新闻")


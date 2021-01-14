#imports
import imdb
import datetime
import mysql.connector
import time    
from datetime import date
import requests
import gzip

#functions
def DBtoArray(db,arrayName):
    cursr = db.cursor(dictionary=True)
    cursr.execute("SELECT * FROM " + arrayName)
    rslt = cursr.fetchall()
    ray = []
    for idx in rslt:
        ray += [idx[list(idx.keys())[0]]]
    return ray

#execution
def addMovie(iID):
    country = DBtoArray(bilheteira,"country")
    genre = DBtoArray(bilheteira,"genre")
    actor = DBtoArray(bilheteira,"actor")    
    month = {"Jan":1,"Feb":2,"Mar":3,"Apr":4,"May":5,"Jun":6,"Jul":7,"Aug":8,"Sep":9,"Oct":10,"Nov":11,"Dec":12}
    global moviesDB
    global btraCursor
    dbCommitCounter = 0
    
    # get a movie
    movie = moviesDB.get_movie(iID[2:])
    try:
        for key in ['year','runtimes','imdbID','director','rating','cover url','genres','cast']:
            if(key not in movie.keys()):
                print("ERR-" + key)
                return
        country_key = 'country'
        try:
            country_key = 'country'
        except:
            country_key = 'countries'
        year = movie['year']
        imdbID = movie['imdbID']
        aired = date(int(year),1,1)
        try:
            aired = movie['original air date'].split(" ")
            aired = date(int(aired[2]),int(month[aired[1]]),int(aired[0]))
        except:
            aired = date(int(year),12,12)
        plot = ""
        try:
            plot = movie['plot outline']
        except:
            plot = movie['plot'][0]
        
        runtime = movie['runtimes'][0]
        director = movie['director'][0]["name"]
        rating = movie['rating']
        pic = movie['cover url']
        sql = """INSERT INTO film
            (title,movie_id,year,released,plot,runtime,director,rating,picture)
            VALUES
            (%(title)s,%(movie_id)s,%(year)s,%(released)s,%(plot)s,%(runtime)s,%(director)s,%(rating)s,%(picture)s)"""
        val = { 'title':movie['title'],
                'movie_id':imdbID,
                'year':year,
                'released':aired,
                'plot':plot,
                'runtime':runtime,
                'director':director,
                'rating':rating,
                'picture':pic
                }
        btraCursor.execute(sql,val)
        dbCommitCounter += 1
        print("IN-Country")
        for c in movie[country_key]:
            if c not in country:
                sql = "INSERT INTO country (country_name) VALUES (%(countryname)s)"
                val = {'countryname':c}
                btraCursor.execute(sql,val)
                dbCommitCounter += 1
            sql = "INSERT INTO filmbycountry (country_name,film) VALUES (%(country)s,%(film)s)"
            val = {'country':c,'film':imdbID}
            btraCursor.execute(sql,val)
            dbCommitCounter += 1
            country += [c]
        print("IN-Genres")
        for g in movie['genres']:
            if g not in genre:
                sql = "INSERT INTO genre (genre_name) VALUES (%(genre_name)s)"
                val = {'genre_name':g}
                btraCursor.execute(sql,val)
                dbCommitCounter += 1
            sql = "INSERT INTO filmbygenre (genre_name,film) VALUES (%(genre_name)s,%(film)s)"
            val = {'genre_name':g,'film':imdbID}
            btraCursor.execute(sql,val)
            dbCommitCounter += 1
            genre += [g]
        print("IN-Cast")
        for a in movie['cast']:
            actorName = a['name']
            actorRole = str(a.currentRole)
            if actorName not in actor:
                sql = "INSERT INTO actor (actor_name) VALUES (%(actor_name)s)"
                val = {'actor_name':actorName}
                btraCursor.execute(sql,val)
                dbCommitCounter += 1
            sql = "INSERT INTO starredin (actor,film,personage) VALUES (%(actor)s,%(film)s,%(personage)s)"
            val = {'actor':actorName,'film':imdbID,'personage':actorRole}
            btraCursor.execute(sql,val)
            dbCommitCounter += 1
            actor += [actorName]
        if(dbCommitCounter >= 25):
            dbCommitCounter = 0
            bilheteira.commit()
    except Exception as e:
        print("ERR:")
        print(e)
        traceback.print_tb(e.__traceback__)
        
    bilheteira.commit()
        
#global variables
bilheteira = mysql.connector.connect(
  host="localhost",
  user="root",
  password="123456",
  database="bilheteira"
)


moviesDB = imdb.IMDb()
btraCursor = bilheteira.cursor()
now = datetime.datetime.now()

print("Downloading most recent imdb ids.")
url = 'https://datasets.imdbws.com/title.basics.tsv.gz'
r = requests.get(url, allow_redirects=True)
print("Loading dataset")
f_in = gzip.decompress(r.content)
with open('data.tsv', 'wb') as f_out:
    f_out.write(f_in)
with open('data.tsv', 'r', encoding="utf8") as file:
    print(file.readline())
    while(file.newlines):
        line = file.readline()
        try:
            arr = line.split("\t")
            imdbID,tipo,title,orgTitle,isadult,year,end,runtime,genres = arr
            if(isadult != "0" or tipo != "movie" or int(year) <= 2019 or "Documentary" in genres or runtime == "\\N"):
                continue
            print(imdbID,tipo,year)
            addMovie(imdbID)
        except:
            continue



end = datetime.datetime.now()
print(f'Start: {now}\nEnded: {end}')

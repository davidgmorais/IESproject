#imports
import imdb
import datetime
import mysql.connector
import time    
from datetime import date

#functions
def DBtoArray(db,arrayName):
    cursr = db.cursor(dictionary=True)
    cursr.execute("SELECT * FROM " + arrayName)
    rslt = cursr.fetchall()
    ray = []
    for idx in rslt:
        ray += [idx[list(idx.keys())[0]]]
    return ray
#global variables
bilheteira = mysql.connector.connect(
  host="localhost",
  user="root",
  password="123456",
  database="bilheteira"
)
btraCursor = bilheteira.cursor()

ids = mysql.connector.connect(
  host="localhost",
  user="root",
  password="123456",
  database="scriptIds"
)
idsCursor = ids.cursor()
moviesDB = imdb.IMDb()

movieIDNF = DBtoArray(ids,"movieIDNF")
movieIDAA = DBtoArray(ids,"movieIDAA")
movieIDNW = DBtoArray(ids,"movieIDNW")
        
#execution
now = datetime.datetime.now()
month = {"Jan":1,"Feb":2,"Mar":3,"Apr":4,"May":5,"Jun":6,"Jul":7,"Aug":8,"Sep":9,"Oct":10,"Nov":11,"Dec":12}
country = DBtoArray(bilheteira,"country")
genre = DBtoArray(bilheteira,"genre")
actor = DBtoArray(bilheteira,"actor")
movieIDsTBA = [983946,972544]
print(f'Searching for movies with year >= {now.year-2}')
dbCommitCounter = 0
for i in movieIDsTBA:
    if(str(i) in movieIDNW or str(i) in movieIDAA):
        continue
    try:
        # get a movie
        movie = moviesDB.get_movie(str(i))
        year = movie['year']
        if(year >= (now.year-2)):
            imdbID = movie['imdbID']
            aired = movie['original air date'].split(" ")
            aired = date(int(aired[2]),int(month[aired[1]]),int(aired[0]))
            sql = """INSERT INTO film
                    (title,movie_id,year,released,plot,runtime,director,rating,picture)
                    VALUES
                    (%(title)s,%(movie_id)s,%(year)s,%(released)s,%(plot)s,%(runtime)s,%(director)s,%(rating)s,%(picture)s)"""
            val = { 'title':movie['title'],
                            'movie_id':imdbID,
                            'year':year,
                            'released':aired,
                            'plot':movie['plot outline'],
                            'runtime':movie['runtime'][0],
                            'director':movie['director'][0]["name"],
                            'rating':movie['rating'],
                            'picture':movie['cover url']
                    }
            btraCursor.execute(sql,val)
            dbCommitCounter += 1
            for c in movie['country']:
                if c not in country:
                    sql = "INSERT INTO country (countryname) VALUES (%(countryname)s)"
                    val = {'countryname':c}
                    btraCursor.execute(sql,val)
                    dbCommitCounter += 1
                    sql = "INSERT INTO filmbycountry (country,film) VALUES (%(country)s,%(film)s)"
                    val = {'country':c,'film':imdbID}
                    btraCursor.execute(sql,val)
                    dbCommitCounter += 1
                    country += [c]
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
            for a in movie['cast']:
                actorName = a['name']
                if actorName not in country:
                    actorRole = str(a.currentRole)
                    sql = "INSERT INTO actor (actor_name) VALUES (%(actor_name)s)"
                    val = {'actor_name':actorName}
                    btraCursor.execute(sql,val)
                    dbCommitCounter += 1
                    sql = "INSERT INTO starredin (actor,film,personage) VALUES (%(actor)s,%(film)s,%(personage)s)"
                    val = {'actor':actorName,'film':imdbID,'personage':actorRole}
                    btraCursor.execute(sql,val)
                    dbCommitCounter += 1
                    actor += [actorName]
            sql = "INSERT INTO movieIDAA (idx) VALUES (%s)"
            val = (str(i),)
            idsCursor.execute(sql,val)
            dbCommitCounter += 1
            if(dbCommitCounter >= 25):
                dbCommitCounter = 0
                ids.commit()
                bilheteira.commit()
        else:
            sql = "INSERT INTO movieIDNW (idx) VALUES (%s)"
            val = (str(i),)
            idsCursor.execute(sql,val)
            dbCommitCounter += 1
    except Exception as e:
        if(str(i) not in movieIDNF):
            sql = "INSERT INTO movieIDNF (idx) VALUES (%s)"
            val = (str(i),)
            idsCursor.execute(sql,val)
            dbCommitCounter += 1
#        print(e)
                
ids.commit()
bilheteira.commit()

end = datetime.datetime.now()
print(f'Start: {now}\nEnded: {end}')

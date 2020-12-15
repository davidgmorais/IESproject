#imports
import mysql.connector

#functions
def DBtoArray(db,arrayName):
    cursr = db.cursor(dictionary=True)
    cursr.execute("SELECT * FROM " + arrayName)
    rslt = cursr.fetchall()
    ray = []
    for idx in rslt:
        ray += [idx[list(idx.keys())[0]]]
    return ray
def getMovieFromDB(db, idx):
    cursr = db.cursor(dictionary=True)
    cursr.execute("SELECT * FROM film WHERE movie_id=" + idx)
    rslt = cursr.fetchall()
    return rslt[0]
    
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

movieIDNF = DBtoArray(ids,"movieIDNF")
movieIDAA = DBtoArray(ids,"movieIDAA")
movieIDNW = DBtoArray(ids,"movieIDNW")
country = DBtoArray(bilheteira,"country")
genre = DBtoArray(bilheteira,"genre")
actor = DBtoArray(bilheteira,"actor")
print(movieIDNF,movieIDAA,movieIDNW,country,genre,actor)

moviesIDAA = DBtoArray(ids,"movieIDAA")
dbCommitCounter = 0
for movieid in moviesIDAA:
    movie = getMovieFromDB(bilheteira,movieid)
    if(movie['year'] < 2020):
        sql = "delete from filmbycountry where film=" + movie['movie_id']
        btraCursor.execute(sql)
        dbCommitCounter += 1
        sql = "delete from filmbygenre where film=" + movie['movie_id']
        btraCursor.execute(sql)
        dbCommitCounter += 1
        sql = "delete from starredin where film=" + movie['movie_id']
        btraCursor.execute(sql)
        dbCommitCounter += 1
        sql = "delete from film where movie_id=" + movie['movie_id']
        btraCursor.execute(sql)
        dbCommitCounter += 1
        sql = "delete from movieIDAA where idx=" + movieid
        idsCursor.execute(sql)
        sql = "INSERT INTO movieIDNW (idx) VALUES (%s)"
        val = (movieid,)
        idsCursor.execute(sql,val)
        dbCommitCounter += 1
    if(dbCommitCounter >= 25):
        dbCommitCounter = 0
        ids.commit()
        bilheteira.commit()

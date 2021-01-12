#imports
import datetime
import mysql.connector
import time    
import random

#functions
def DBtoArray(db,arrayName,getAttribute,where):
    cursr = db.cursor(dictionary=True)
    cursr.execute("SELECT " + getAttribute +  " FROM " + arrayName + " " + where)
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
now = datetime.datetime.now()

comments_templates = [
    """By the end I just have no idea what to think. I just think it would have
worked much better if the ideas were done justice in their own film, rather than
ham-fistedly trying to ram them into a film to try and please fans
and make box-office.""",
    """In some ways its excellent!""",
    """Let's not talk about this please...""",
    """Horrible.""",
    """...feels like it could (and should) have been so much better.""",
    """It's as if they were shuttling from idea to idea, image to image,
and leaving the viewer to latch on as best they can to this meandering train
of thought."""]

commentLikesSql = """
    UPDATE comment
    SET likes = %(likes)s
    WHERE id = %(id)s
    """

newCommentSQL = """
    INSERT INTO comment
    (parent_id,author,cinema,film,premier,replyto,content,created)
    VALUES
    (0,-1,0,%(film)s,0,0,%(content)s,%(created)s)
    """

newNotificationSQL = """
    INSERT INTO notification
    (sender,receiver,created,message,title,data,type)
    VALUES
    (%(sender)s,%(receiver)s,%(created)s,%(message)s,"Someone liked your comment.",%(data)s,"comment")
    """

likesChangingSql = """
    UPDATE film
    SET likes = %(likes)s
    WHERE movie_id = %(id)s
    """

insertUser = """
    insert into user
    (user_name,user_email,password,role)
    values
    (%(name)s, %(mail)s,"e10adc3949ba59abbe56e057f20f883e",0)
"""
btraCursor.execute(insertUser,{"name":"simUser1","mail":"simMail1@sim.simulation"})
btraCursor.execute(insertUser,{"name":"simUser2","mail":"simMail2@sim.simulation"})
bilheteira.commit()
user_keys = DBtoArray(bilheteira,"user","id","")
iterator = 0
endWhile = 0
while(endWhile != 1000):
    iterator += 1
    film_keys = DBtoArray(bilheteira,"film","movie_id","")
    comment_keys = DBtoArray(bilheteira,"comment","id","")

    curr_id = film_keys[random.randint(0, len(film_keys)-1)]
    curr_likes = DBtoArray(bilheteira,"film","likes","where movie_id = " + curr_id)[0]
    curr_likes = int(curr_likes)
    min_likes = curr_likes - random.randint(curr_likes-2, curr_likes-1)
    if(min_likes < 0):
        min_likes = 0
    max_likes = curr_likes + random.randint(curr_likes+1, curr_likes+2)
    if max_likes <= min_likes:
        min_likes = max_likes-1
    val = {"likes":random.randint(min_likes, max_likes),
           "id":curr_id}
    btraCursor.execute(likesChangingSql,val)
    if(iterator == 1000):
        iterator = 0
        created = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        film = film_keys[random.randint(0, len(film_keys)-1)]
        val = {"film":film ,
               "content": comments_templates[random.randint(0, len(comments_templates)-1)],
               "created":created}
        btraCursor.execute(newCommentSQL,val)
        bilheteira.commit()
        comment_keys = DBtoArray(bilheteira,"comment","id","where likes = 0 and film = " + film)
        created = datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
        #like comments and create notif of liked
        val = {"likes":1,
               "id":comment_keys[0]}
        btraCursor.execute(commentLikesSql,val)
        val = {"sender":user_keys[0],
               "receiver":user_keys[1],
               "created":created,
               "message":"",
               "data":comment_keys[0]}
        btraCursor.execute(newNotificationSQL,val)

        print("New comment added to: ", film)
        endWhile += 1
    bilheteira.commit() 
end = datetime.datetime.now()
print(f'Start: {now}\nEnded: {end}')

import time
import datetime
import glob
import MySQLdb
from time import strftime

temp_sensor = './serial_c'
 
# Variables for MySQL
db = MySQLdb.connect(host="localhost", user="root",passwd="jsy123", db="db1")
cur = db.cursor()
device = '/dev/ttyACM0'

def tempRead():
    t = open(temp_sensor, 'r')
    lines = t.readlines()
    t.close()

    temp_output = lines[1].find('t=')
    if temp_output != -1:
        temp_string = lines[1].strip()[temp_output+2:]
        temp_c = float(temp_string)/1000.0
    return round(temp_c,1)

try:
    print "Trying...",device
    arduino = serial.Serial(device, 9600) 
except:
    print "Failed to connect on",device  
 
while True:
    temp = arduino.readline()  #read the data from the arduino
    pieces = temp.split("\t")  #split the data by the tab
    print temp
    datetimeWrite = (time.strftime("%Y-%m-%d ") + time.strftime("%H:%M:%S"))
    print datetimeWrite
    sql = ("""INSERT INTO tb6 (datetime,gas_val) VALUES (%s,%s)""",(datetimeWrite,temp))
    try:
        print "Writing to database..."
        # Execute the SQL command
        cur.execute(*sql)
        # Commit your changes in the database
        db.commit()
        print "Write Complete"
 
    except:
        # Rollback in case there is any error
        db.rollback()
        print "Failed writing to database"

    cur.close()
    db.close()
    break

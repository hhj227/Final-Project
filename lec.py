#!/usr/bin/env python

import socket
import serial
import time
import subprocess

try:
    ser = serial.Serial(dev.strip(),9600)
    print "Arduino Connected"
except:
    print "Arduino not connected"

def server():
    global ser
    while True:
        conn, addr = s.accept()
        print 'Connection address:', addr
        data = conn.recv(BUFFER_SIZE)
        if not data: continue
        print "received data:", data
        if data != 0:
            conn.send(data)
            conn.close()
            ser.write('1')
            time.sleep(1)

        elif data == '7':
            conn.send('bye bye')
            conn.close()
            ser.close()
            exit(0)

        else :
            ser.write(data)
            aa =  ser.readline()
            time.sleep(0.1)
            print aa
            conn.send(aa)
            conn.close()


TCP_IP = '192.168.123.152'
TCP_PORT = 5005
BUFFER_SIZE = 20  # Normally 1024, but we want fast response

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.setsockopt(socket.SOL_SOCKET,socket.SO_REUSEADDR,1)
s.bind((TCP_IP, TCP_PORT))
s.listen(5)

print 'server started'
server()  







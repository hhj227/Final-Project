#!/usr/bin/env python

import socket
import sys


TCP_IP = '192.168.137.17'
TCP_PORT = 5005
BUFFER_SIZE = 1024
MESSAGE = str(sys.argv[1])

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((TCP_IP, TCP_PORT))
s.send(MESSAGE)
data = s.recv(BUFFER_SIZE)
s.close()

print "received data:", data



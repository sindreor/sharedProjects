import socket
import sys
 
HOST = ''   # Symbolic name, meaning all available interfaces
PORT = 9090 # Arbitrary non-privileged port

fram=False
bak=False
hoyre=False
venstre=False

 
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
print ('Socket created')
 
#Bind socket to local host and port
try:
    s.bind((HOST, PORT))
except socket.error as msg:
    print ('Bind failed. Error Code : ' + str(msg[0]) + ' Message ' + msg[1])
    sys.exit()
     
print ('Socket bind complete')
 
#Start listening on socket
s.listen(10)
print ('Socket now listening')
 
#now keep talking with the client
while 1:
    #wait to accept a connection - blocking call
    conn, addr = s.accept()
    print ('Connected with ' + addr[0] + ':' + str(addr[1]))
    mottatt=conn.recv(1024).decode()[0]
    
    if(mottatt=="h"):
        hoyre=True
    elif(mottatt=="v"):
        venstre=True
    elif(mottatt=="f"):
        fram=True
    elif(mottatt=="b"):
        bak=True
    elif(mottatt=="d"):
        hoyre=False
    elif(mottatt=="a"):
        venstre=False
    elif(mottatt=="s"):
        bak=False
    elif(mottatt=="w"):
        fram=False
    
#The prints should be changed with commads for stearing the motors according to the direction specified by the signal
    if(hoyre==True and fram==True):
        print("Framover mot høyre")
    elif(hoyre==True and bak==True):
        print("Bakover mot høyre")
    elif(venstre==True and fram==True):
        print("Framover mot venstre")
    elif(venstre==True and bak==True):
        print("Bakover mot venstre")
    elif(venstre==True):
        print("Spinn venstre")
    elif(hoyre==True):
        print("Spinn høyre")
    elif(fram==True):
        print("Rett framover")
    elif(bak==True):
        print("Rett bakover")
    else:
        print("Stopp")
         
        
s.close()

Duc Anh Tran
trananhduc@gatech.edu
902928522
CS 3251: Networking I
February 11 2016
Programming assignmet I: Socket programming.

files provided:
    - Clients and servers: dbengineTCP.java, dbengineUDP.java, dbclientTCP.java, dbclientTCP.py, dbclientUDP.java, dbclientTCP.py
    - Helper files: StreamHelper.java, InputHandler.java
    - Data file: db.csv
    - Compilation file: Makefile
    - Output sample: sample.txt
Running instruction
1. Compile java files: $make
2. Run server:
    For TCP: java dbengineTCP [optional port]
    For UDP: java dbengineUDP [optinal port]
    If not specified, the default port would be 8591
3. Run client (must open a different terminal in order for server and client communicate):
    For java:
        For TCP: java dbengineTCP <Server_IP_address>:<Server_port> <Student_ID> <column(s)_name>
        For UDP: java dbengineUDP <Server_IP_address>:<Server_port> <Student_ID> <column(s)_name>
    For python:
         python dbclientTCP.py <Server_IP_address>:<Server_port> <Student_ID> <column(s)_name>
         python dbclientUDP.py <Server_IP_address>:<Server_port> <Student_ID> <column(s)_name>
    Client must specified server IP and port, student Id and one or more column to queries.


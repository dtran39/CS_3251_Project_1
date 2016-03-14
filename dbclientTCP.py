import socket, sys, struct
#Helper function to receive an integer from java server
def recvall(sock, size):
    msg = ''
    while len(msg) < size:
        part = sock.recv(size-len(msg))
        if part == '':
            break
        msg += part
    return msg
def main():
    #Precheck for the number of arguments
    if len(sys.argv) < 4:
        print "Not enough arguments to query"
        return
    '''
        Initialization step: extracting host IP address and port number,
                            initialize socket, connect to the server
                            generating request from the arguments
    '''
    host = sys.argv[1].split(":")
    hostAddress, port = host[0], int(host[1])
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        sock.connect((hostAddress, port))
    except socket.error:
        print "No server with that address and port. Bye bye."
        return
    request = ""
    for i in range(1, len(sys.argv)):
        request += sys.argv[i] + "   "
    #Send the request message size, then send the message in array of bytes
    sock.sendall(struct.pack(">i", len(request)))
    sock.sendall(bytes(request))
    # Receive the response message size, then receive the response message
    message_size = struct.unpack('>i', recvall(sock, 4))[0]
    message = ""
    for i in range(message_size):
        message += sock.recv(1)
    print message
    sock.close()
if __name__ == "__main__":
    main()
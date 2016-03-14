import socket, sys
#This function implements the ping functionality with 3 trials for the client.
def ping(sock, request, hostAddress, port, trialsLeft):
    #Try to stream the request to server 4 times. If getting timeout exception for all of the 4, exits.
    try:
        sock.sendto(bytes(request), (hostAddress, port))
        response = sock.recv(65507)
        print response
    except socket.timeout:
        if trialsLeft > 0:
            print "The server has not answered in the last two seconds.\n" + str(trialsLeft - 1) + " trials left.\nretrying ..."
            ping(sock, request, hostAddress, port, trialsLeft - 1)
        else:
            print "No response from the server. Bye Bye"
    finally:
        sock.close()
def main():
    #Precheck for the number of arguments
    if len(sys.argv) < 4:
        print "Not enough arguments to query"
        return
    '''
        Initialization step: extracting host IP address and port number,
                            initialize socket (with timeout),  generating request from the arguments
    '''
    host = sys.argv[1].split(":")
    hostAddress, port = host[0], int(host[1])
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.settimeout(2)
    request = ""
    for i in range(2, len(sys.argv)):
        request += sys.argv[i] + "   "
    ping(sock, request, hostAddress, port, 4)
if __name__ == "__main__":
    main()
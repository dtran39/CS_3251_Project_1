import java.io.IOException;
import java.net.Socket;
import java.net.ConnectException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * This class implement a TCP client
 */
public class dbclientTCP {
    public static void main(String[] args) throws IOException{
        // Precheck for the number of arguments
        if (args.length < 3) {
            System.out.println("Not enough arguments to query");
            return;
        }
        /*
            Initialization step: extracting host IP address and port number, create socket object for connection,
                                streamHelper to provide code for streaming input and output using TCP
                                Generating request
         */
        String[] host = args[0].split(":", 2);
        String hostAdress = host[0];
        int port = Integer.parseInt(host[1]);
        Socket socket;
        try {
            socket = new Socket(hostAdress, port);
        } catch (ConnectException e){
            System.out.println("No server with that address and port. Bye Bye.");
            return;
        }
        String request = "";
        for (String arg : args)           request += arg + "   ";
        // Send request to the server
        StreamHelper.streamTCPOut(socket, request.trim());
        // Print the response got back form the server, close the socket
        System.out.println(StreamHelper.streamTCPIn(socket));
        socket.close();
    }
}
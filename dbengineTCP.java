import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
/**
 * This class implement a TCP server
 */
public class dbengineTCP {
    public static void main(String[] args) throws IOException {
        /*
            Initialization step: assigning port number, create socket object for connection,
                                inputHandler to process request from client and generate response from server
                                streamHelper to provide code for streaming input and output using TCP
         */
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8591;
        ServerSocket serverSocket = new ServerSocket(port);
        // The server runs continuously
        while(true) {
            // Accepting request from a client, print notification
            Socket clientSocket = serverSocket.accept();
//            System.out.println("Got a request from " +
//                    clientSocket.getInetAddress().getHostAddress() + ":" +
//                    clientSocket.getPort() + "\nSending response ...");
            // Get the request from the client
            String[] request = StreamHelper.streamTCPIn(clientSocket).split("   ");
            // Extract the query part from the request
            String[] query = new String[request.length - 1];
            for (int i = 1; i < request.length; i++) query[i - 1] = request[i];
            // Generate response based on the request
            String response = InputHandler.handleArgs(query);
            // Send the response back to the client, close the connection
            StreamHelper.streamTCPOut(clientSocket, response);
            clientSocket.close();
        }
    }
}
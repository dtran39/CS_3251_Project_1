import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class implement a UDP server
 */
public class dbengineUDP {
    public static void main(String[] args) throws IOException{
        /*
            Initialization step: assigning port number, create socket object for connection,
                                inputHandler to process request from client and generate response from server
                                streamHelper to provide code for streaming input and output using UDP
         */
        int port = (args.length > 0) ? Integer.parseInt(args[0]) : 8591;
        DatagramSocket socket = new DatagramSocket(port);
        // The server runs continuously
        while (true){
            // Get the request from the client, wrapped outside by a hashmap object
            HashMap<String, String> request = StreamHelper.streamUDPIn(socket);
            // Extract the details from the request
            String query = request.get("message");
            InetAddress clientAddress = InetAddress.getByName(request.get("senderAddress").substring(1));
            int clientPort = Integer.parseInt(request.get("senderPort"));
            // Generate response based on the request
            String response = InputHandler.handleArgs(query.trim().split("   "));
            // Send the response back to the client
            StreamHelper.streamUDPOut(socket, response, clientAddress, clientPort);
        }
    }
}
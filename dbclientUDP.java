import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.io.IOException;
/**
 * This class implement a UDP client
 */
public class dbclientUDP {
    private static final int DATAGRAM_MAX_SIZE = 65507; //maximum size of datagram
    private static DatagramPacket receivePacket = new DatagramPacket(new byte[DATAGRAM_MAX_SIZE], DATAGRAM_MAX_SIZE);
    public static void main(String[] args) throws IOException {
        // Precheck for the number of arguments
        if (args.length < 3) {
            System.out.println("Not enough arguments");
            return;
        }
        /*
            Initialization step: extracting host IP address and port number,
                                create socket object for connection (with 2 seconds timeout)
                                streamHelper to provide code for streaming input and output using TCP
                                Generating request
         */
        String[] host = args[0].split(":", 2);
        InetAddress hostAddress = InetAddress.getByName(host[0]);
        int port = Integer.parseInt(host[1]);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(2000);
        String request = "";
        for (int i = 1; i < args.length; i++)   request += args[i] + "   ";
        // Ping the server to send the request. If timeout, try 3 more times.
        ping(socket, request, receivePacket, hostAddress, port, 4);
        // If success, print the response form the server.
        System.out.println(new String(receivePacket.getData(), 0, receivePacket.getLength()));
        socket.close();
    }
    // This function implements the ping functionality with 3 trials for the client.
    private static void ping(DatagramSocket socket, String request, DatagramPacket receivePacket, InetAddress addressList, int port, int trialLeft) throws IOException {
        // Use stream helper to encapsulate all of the streaming implementation
        // Try to stream the request to server 4 times. If getting timeout exception for all of the 4, exits.
        try {
            StreamHelper.streamUDPOut(socket, request, addressList, port);
            socket.receive(receivePacket);
        }
        catch (SocketTimeoutException e) {
            if (trialLeft > 0) {
                System.out.println("The server has not answered in the last two seconds.\n"
                                    + (trialLeft - 1) + " trials left.\nretrying ...");
                ping(socket, request, receivePacket, addressList, port, trialLeft - 1);
            } else {
                System.out.println("No response from the server. Bye bye");
            }
        }
    }
}
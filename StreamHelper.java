import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

/**
 * This class implements all of the streaming in and out functionalities through both TCP and UDP.
 */
public class StreamHelper {
    private static final int DATAGRAM_MAX_SIZE = 65507; //maximum size of a datagram
    /*
        This funciton implements streaming in through TCP functionality given a socket
     */
    public static String streamTCPIn(Socket senderSocket)throws IOException{
        // Create an input stream from the given socket
        DataInputStream inputStream = new DataInputStream(senderSocket.getInputStream());
        // Get the number of bytes of the input.
        int numberOfBytes = inputStream.readInt();
        // Declare array of bytes and stream input to the array
        byte[] bytes = new byte[numberOfBytes];
        for (int i = 0; i <numberOfBytes; i++){
            bytes[i] = inputStream.readByte();
        }
        // Return the string version of the byte array.
        String message = new String(bytes);
        return message;
    }
    /*
        This funciton implements streaming out through TCP functionality given a socket
     */
    public static void streamTCPOut(Socket receiverSocket, String message) throws IOException {
        // Create an output stream from the given socket
        DataOutputStream outputStream = new DataOutputStream(receiverSocket.getOutputStream());
        // Stream out the message size in bytes and stream the messages through bytes
        outputStream.writeInt(message.length());
        outputStream.writeBytes(message);
    }
    /*
        This funciton implements streaming in through UDP functionality given a socket
     */
    public static HashMap<String, String> streamUDPIn(DatagramSocket socket) throws IOException {
        // Declare a packet for receiving, stream input to the packet
        DatagramPacket receivePacket = new DatagramPacket(new byte[DATAGRAM_MAX_SIZE], DATAGRAM_MAX_SIZE);
        socket.receive(receivePacket);
        // Convert the data got from packet to a HashTable object, return that objects
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("message", new String(receivePacket.getData(), 0, receivePacket.getLength()));
        data.put("senderAddress", receivePacket.getAddress().toString());
        data.put("senderPort", Integer.toString(receivePacket.getPort()));
        return data;
    }
    /*
        This funciton implements streaming out through UDP functionality given a socket
     */
    public static void streamUDPOut(DatagramSocket socket, String message, InetAddress receiverAddress, int receiverPort)
            throws IOException {
        // Create an output packet
        byte[] messageBytes = message.trim().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(messageBytes,
                messageBytes.length, receiverAddress, receiverPort);
        // Use the socket to send that packet
        socket.send(sendPacket);
    }
}
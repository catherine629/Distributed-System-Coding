import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This is a UDP client socket for city lookup. This client will use socket to 
 * send message to the UDP server. The message will contain the city name and 
 * abbreviation of state. Then the server will look up the city and return its
 * coordinates back to this client. At last, the client will show the coordinates
 * to the user.
 * 
 * @author cathe
 */
public class UDPClient {
    public static void main(String args[]){
        DatagramSocket aSocket = null;
        try{
            // set the server IP address and port
            InetAddress aHost = InetAddress.getLocalHost();
            int serverPort = 33333;
            // create a datagram socket
            aSocket = new DatagramSocket();
            // the message to be transmitted
            String message = null;
            Scanner sc = new Scanner(System.in);
            // prompt the user to enter message
            System.out.println("Enter city name and state abbreviation, and we will find its coordinates (e.g. Pittsburgh,PA)");
            message = sc.nextLine();
            // transfer message string to bytes for transmission
            byte[] m = message.getBytes();
            // transmit the message
            DatagramPacket request = new DatagramPacket(m, m.length, aHost, serverPort);
            aSocket.send(request);
                
            // receive response from server
            byte[] buffer = new byte[1000];
            DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
            aSocket.receive(reply);
            String result = new String(reply.getData(),0,reply.getLength());
            // if result is null, tell the user
            if(result.equals("")){
                System.out.println("Could not resolve "+message);
            }
            // represent reply to user
            else{
                System.out.println(result);
            }                          
        } catch (SocketException e){
            System.out.println("Client Socket problem: "+e.getMessage());
        } catch (IOException e){
            System.out.println("Input/Output problem: "+e.getMessage());
        } finally{
            // close the socket after operation
            if(aSocket!=null)
                aSocket.close();
            }
    }
}

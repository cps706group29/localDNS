import java.io.*;
import java.net.*;

public class LocalDNS{
  public static void main(String argv[]) throws Exception{
    DatagramSocket serverSocket = new DatagramSocket(40281);
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];

    // LocalDNS runs infinitely, listens for any incoming packets
    while(true){
      // incoming packet arrives at the socket
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);

      String requestURL = new String(receivePacket.getData());
      System.out.println("Incoming Message: " + requestURL);

      // Prepare a response
      InetAddress IPAddress = receivePacket.getAddress();
      int port = receivePacket.getPort();

      ///////////////////////// Take requestURL from UDP message, and resolve it ///////////////////////
      

      //////////////////////////////////////////////////////////////////////////////////////
      String response = requestURL;
      sendData = response.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

      // Send the Response
      serverSocket.send(sendPacket);
    }
  }
}

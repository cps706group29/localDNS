import java.io.*;
import java.net.*;

public class DummyClient{
  public static void main(String argv[]) throws Exception{
    DatagramSocket clientSocket = new DatagramSocket();

    InetAddress IPAddress = InetAddress.getByName("127.0.0.1");
     byte[] sendData = new byte[1024];
     byte[] receiveData = new byte[1024];

     String sentence = "video.hiscinema.com";
     sendData = sentence.getBytes();

     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 40281);

     clientSocket.send(sendPacket);

     DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

     clientSocket.receive(receivePacket);

     String serverResponse = new String(receivePacket.getData());

     System.out.println("FROM SERVER: " + serverResponse);
     clientSocket.close();


  }
}

import java.io.*;
import java.net.*;
import java.util.*;

public class LocalDNS{
  public static final String  HIS_CINEMA_NS_IP    = "127.0.0.2";
  public static final int     HIS_CINEMA_NS_PORT  = 40282;
  public static final String  HER_CDN_NS_IP       = "127.0.0.3";
  public static final int     HER_CDN_NS_PORT     = 40283;
  public static final int     LOCAL_DNS_LISTENING_PORT = 40281;

  public static ArrayList<ResourceRecord> records;
  public static DatagramSocket serverSocket;

  public static void main(String argv[]) throws Exception{
    records =  new ArrayList<ResourceRecord>();
    records.add(new ResourceRecord("herCDN.com",      "ns.herCDN.com",     "NS"));
    records.add(new ResourceRecord("ns.herCDN.com",    HER_CDN_NS_IP,      "A"));
    records.add(new ResourceRecord("hisCinema.com",   "ns.hiscinema.com",  "NS"));
    records.add(new ResourceRecord("ns.hiscinema.com", HIS_CINEMA_NS_IP,   "A"));

    // Create RECEIVING socket
    serverSocket = new DatagramSocket(LOCAL_DNS_LISTENING_PORT);
    byte[] receiveData = new byte[1024];
    byte[] sendData = new byte[1024];

    // LocalDNS runs infinitely, listens for any incoming DNS queries
    System.out.println("Listening for Requests...");
    while(true){
      // incoming packet arrives at the socket
      DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receivePacket);

      String requestURL = new String(receivePacket.getData());
			System.out.println("--------------------------------------------");
      System.out.println("Incoming Message: " + requestURL);

      // Prepare a response
      InetAddress returnIPAddress = receivePacket.getAddress();
      int returnPort = receivePacket.getPort();

      ///////////////////////// Take requestURL from UDP message, and resolve it ///////////////////////
      String response = resolve(requestURL);
			System.out.println("--------------------------------------------");
      //////////////////////////////////////////////////////////////////////////////////////
      sendData = response.getBytes();
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, returnIPAddress, returnPort);

      // Send the Response
      serverSocket.send(sendPacket);
    }
  }


  /**

  **/
  public static String resolve(String url) throws Exception{
    String hisCinemaNSurl 	= url.trim();
		String hisCinemaNS 			= "ns." + hisCinemaNSurl.substring(hisCinemaNSurl.indexOf(".")+1);
		String hisCinemaNSip 		= getNSip(hisCinemaNS);
		int 	 hisCinemaNSport  = HIS_CINEMA_NS_PORT;
		String hisCinemaNSres 	= requestToNameServer(hisCinemaNSurl, hisCinemaNS, hisCinemaNSip, hisCinemaNSport);
		String herCDNNSurl 			= hisCinemaNSres;
		String herCDNNS 				= "ns." + hisCinemaNSres;
		String herCDNNSip 		  = getNSip(herCDNNS);
		int 	 herCDNNSport 		= HER_CDN_NS_PORT;
		String herCDNNSres 			= requestToNameServer(herCDNNSurl, herCDNNS, herCDNNSip, herCDNNSport);
    return herCDNNSres;
  }

  public static String requestToNameServer(String url, String ns, String nsIP, int nsPort) throws Exception{
    byte[] sendData = new byte[1024];
    byte[] receiveData = new byte[1024];
    sendData = url.getBytes();
    InetAddress NSIPAddress = InetAddress.getByName(nsIP);
    DatagramPacket request = new DatagramPacket(sendData, sendData.length, NSIPAddress, nsPort);
    System.out.println("Sending request to " + ns + " @ "+ nsIP + ":" + nsPort + " = " + url);
    serverSocket.send(request);
		DatagramPacket response = new DatagramPacket(receiveData, receiveData.length);
    serverSocket.receive(response);
		String nsResponse = new String(response.getData());
		System.out.println("Response received from " + ns + " @ " + nsIP + ":" + nsPort + " = " + nsResponse);
    return nsResponse;
  }

	public static String getNSip(String NSurl){
    String current = NSurl.trim();
		for(int i = 0; i < records.size(); i++){
			ResourceRecord record = records.get(i);
			if(current.equals(record.name)){

				current = record.value;
				if(record.type.equals("A")){
					return current;
				}
				i = i - i - 1;
			}
		}
		return "";
	}

}

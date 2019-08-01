import java.io.ObjectOutputStream;
import java.net.Socket;


public class TA {

	// subset of secret keys
	byte[] S = ("123456789101").getBytes();
	byte[] Si = ("123456").getBytes();
	byte[] Sj = ("789101").getBytes();
	
	String serverName = "localhost";
	int port = 6066;
	int port2 = 5067;
	int port3 = 4444;
	
	public static void main(String[] args) throws Exception 
	{
		TA t = new TA();
		t.run();
		
		
	}
	
	
	public void run() throws Exception{
		
		Socket client = new Socket(serverName, port);
		// send S to the aggregator
		ObjectOutputStream out = new ObjectOutputStream (client.getOutputStream());
		out.writeObject(S);
		
		
		
		
        Socket client2 = new Socket(serverName, port2);
		// send Si to user 1
		ObjectOutputStream out2 = new ObjectOutputStream (client2.getOutputStream());
		out2.writeObject(Si);
		
		
		
		
		
        Socket client3 = new Socket(serverName, port3);
		// send Sj to user 2
		ObjectOutputStream out3 = new ObjectOutputStream (client3.getOutputStream());
		out3.writeObject(Sj);
		
		
		
		client.close();
		client2.close();
		client3.close();
		
		
	}
	
	
}

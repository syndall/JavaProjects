
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class user2 extends Thread{
	
	private byte[] key1;
	private byte [] key2;
	String serverName = "localhost";
	int port = 6066;
	private byte [] Si;
	private int interval = 0;
	private BigInteger user2_data = new BigInteger("15");
	BigInteger ki, c;
	private int n =3;
	public static void main(String[] args) throws Exception 
	{
		Thread t = new user2();
		t.start();
		
		
	}
	
	
	void compute() throws Exception{
		
		int bitLength = 260;
	  	SecureRandom rnd = new SecureRandom();
	  	
	  	// Generate M
	  	BigInteger M = BigInteger.probablePrime(bitLength, rnd);
		
		// Secret keys in subset
		key1 = Arrays.copyOfRange(Si,0,2);
		key2 = Arrays.copyOfRange(Si,3,5);
		
		// increase interval
		interval++;
		
		String i = Integer.toString(interval);
		
		// Peform HMAC with key
		SecretKeySpec macKey = new SecretKeySpec(key1, "HmacSHA256");  	 
		Mac mac1 = Mac.getInstance("HMACSHA256");
		mac1.init(macKey);
		byte [] hmac_dgiest1 = mac1.doFinal((i).getBytes());	
		
		// Compute hash
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		byte [] hash1 = sha256.digest(hmac_dgiest1);
		
		// Peform HMAC with key
		SecretKeySpec macKey2 = new SecretKeySpec(key2, "HmacSHA256");  	 
		Mac mac2 = Mac.getInstance("HMACSHA256");
		mac2.init(macKey2);
		byte [] hmac_dgiest2 = mac2.doFinal((i).getBytes());	
		
		// Compute digest
		MessageDigest sha2 = MessageDigest.getInstance("SHA-256");
		byte [] hash2 = sha2.digest(hmac_dgiest2);
		
		// Hash to integer
		BigInteger h1 = new BigInteger(hash1);
		BigInteger h2 = new BigInteger(hash2);
		
	//	System.out.println("Hash3 " + h1);
	//	System.out.println("Hash4 " + h2);
		//System.out.println("M " + M);
		
		// Add hashes together
		BigInteger hash_sum1 = h1.add(h2);
		// positive integer 
		hash_sum1 = hash_sum1.abs();
		
	//	System.out.println("hash sum " + hash_sum1);
		
		// Produce Ki
		ki = hash_sum1.mod(M);
	//	System.out.println("Ki: "+ ki);
		
		
		// Produce Cipher text
		c = (ki.add(user2_data)).mod(M);
		
		System.out.println("Cipher-Text " + c);
		
		// increase user data by 5
		user2_data = user2_data.add(new BigInteger("5"));
		
	}
	
	
	public void run(){
		
		
try {		
		
	ServerSocket server = new ServerSocket(4444);	
	Socket sock = server.accept();
	
	
	// Get secret key subset from TA
	ObjectInputStream OIS = new ObjectInputStream(sock.getInputStream());
	Si = (byte []) OIS.readObject();
	if(Si != null) System.out.println("I know have a Subset of secret keys.");
	
	
	
	for(int i =0; i<n; i++){
		Socket client = new Socket(serverName, port);
		compute();
         
		
		ObjectOutputStream out = new ObjectOutputStream (client.getOutputStream());
		out.writeObject(c);
		client.close();
		sleep(300);
	}	
	
	
	server.close(); 
	
}	

catch(Exception ex)
{
	ex.printStackTrace();
}	
	
	}
	

}

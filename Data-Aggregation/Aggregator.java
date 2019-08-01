import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Aggregator extends Thread {

	private byte[] S;
	private BigInteger k0, c_sum, sum, c1, c2;
	private int interval = 0;
	private ServerSocket serverSocket;
    Socket server;
    private boolean run = true;
    private int n = 3;
    
    public Aggregator(int port) throws Exception
    {
       serverSocket = new ServerSocket(port);
       serverSocket.setSoTimeout(180000);
    }
    
    
    
	
	
void compute() throws Exception{
		
	    
	    byte[] key1 = Arrays.copyOfRange(S,0,2);
	    byte [] key2 = Arrays.copyOfRange(S,3,5);
	    byte [] key3 = Arrays.copyOfRange(S,6,8);
	    byte [] key4 = Arrays.copyOfRange(S,9,11);
	     
	
		int bitLength = 260;
	  	SecureRandom rnd = new SecureRandom();
	  	
	  	// Generate M
	  	BigInteger M = BigInteger.probablePrime(bitLength, rnd);
		
	
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
		
		// Peform HMAC with key
		SecretKeySpec macKey3 = new SecretKeySpec(key3, "HmacSHA256");  
		Mac mac3 = Mac.getInstance("HMACSHA256");
		mac3.init(macKey3);
		byte [] hmac_dgiest3 = mac3.doFinal((i).getBytes());	
			
			
		// Compute digest
		MessageDigest sha3 = MessageDigest.getInstance("SHA-256");
		byte [] hash3 = sha3.digest(hmac_dgiest3);
			
		
		// Peform HMAC with key
		SecretKeySpec macKey4 = new SecretKeySpec(key4, "HmacSHA256");  
		Mac mac4 = Mac.getInstance("HMACSHA256");
		mac4.init(macKey4);
		byte [] hmac_dgiest4 = mac4.doFinal((i).getBytes());	
			
			
		// Compute digest
		MessageDigest sha4 = MessageDigest.getInstance("SHA-256");
		byte [] hash4 = sha4.digest(hmac_dgiest4);
		
		
		
		
		// Hash to integer
		BigInteger h1 = new BigInteger(hash1);
		BigInteger h2 = new BigInteger(hash2);
		BigInteger h3 = new BigInteger(hash3);
		BigInteger h4 = new BigInteger(hash4);
		
		
	//	System.out.println("Hash1 " + h1);
	//	System.out.println("Hash2 " + h2);
	//	System.out.println("Hash3 " + h3);
	//	System.out.println("Hash4 " + h4);
		
		BigInteger addup1 = h1.add(h2);
		addup1 = addup1.abs();
		BigInteger addup2 = h3.add(h4);
		addup2 = addup2.abs();
		
		
		BigInteger hash_sum = addup1.add(addup2);
		
	//	System.out.println("hash_sum " + hash_sum);
		
		
		k0 = hash_sum.mod(M);
		c_sum = c1.add(c2);
		
	//	System.out.println("cipher sum " + c_sum);
		sum = c_sum.subtract(k0).mod(M);
		
		
		
	   System.out.println("The sum is " + sum);
	   
	   if(interval == n) run = false;
		
	}
	
	
	
	
	public void run(){
		
  while(run)
  {
     try{
	 
     server = serverSocket.accept();
    
    if(S == null){  // Get secret key subset from TA
 	ObjectInputStream OIS = new ObjectInputStream(server.getInputStream());
 	S = (byte []) OIS.readObject();
 	if(S != null) System.out.println("I know have the entire Subset of secret keys.");
    }
    
   //----------------------------------------------------------

   
 	ObjectInputStream cipher = new ObjectInputStream(server.getInputStream());
 	BigInteger c = (BigInteger) cipher.readObject();
 	
 	System.out.println("Cipher-Text " + c);
 	
 	if(c != null)
 	{
 		if(c1 == null)
 		    c1 = c;
 		else
 			c2 = c;
 	}
 	
 
 	if(c1!=null && c2!=null)
 	{
 		compute();
 		c1 = null;
 		c2 = null;
 	}
 	
 	    
 	
 	
 	//--------------------------------------------------------------------

  
   }	
	 catch(Exception ex)
	{
		
	}
     
     
     
  }
}
	
	
	public static void main(String[] args) throws Exception 
	{
		Thread t = new Aggregator(6066);
		t.start();
		
		
	}
	
	
	
	
	
}

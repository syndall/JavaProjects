import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class Client  {
	
	String serverName = "localhost";
    int port = 6066;
    PrivateKey Myprivatekey;
	public PublicKey Alice_publickey;
	private PublicKey B_publickey;
	private byte[] Key = ("1234567812345678").getBytes() ;
	private byte[] Key2;
	SecretKeySpec secret = new SecretKeySpec(Key, "AES");
	private byte[] cipherText, cipherText2;
	private byte[] cipherKey, cipherKey2;
	private byte[] signature;
	private byte[] digest, hmacdigest, hashcheck;
	private byte[] pliantext;
	private BigInteger DiffKey;
	private BigInteger p,g,b,a,r;
	// 2000 byte message 
	private String message ="aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
     		+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; 
	

	
	public static void main(String[] args) throws Exception 
	{
		Client c = new Client();
		c.run();
		
		
	}
//-----------------------------------------------------------------------------	
	void alogrithm() throws Exception{

		
		// Compute digest
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		digest = sha256.digest((message).getBytes());
		
		
		// Compute signature
		Signature instance = Signature.getInstance("SHA256withRSA");
		instance.initSign(Myprivatekey);
		instance.update(digest);
		signature = instance.sign();


		// Encrypt message
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secret);
		cipherText = cipher.doFinal(message.getBytes());
		
	
				
	}
	
//--------------------------------------------------------------------------
	void makeA(){
		// random value for a
		int bitLength = 128;
		SecureRandom rnd = new SecureRandom();
	    a = BigInteger.probablePrime(bitLength, rnd);
	    
	    // compute r = g^a mod p
	    r = g.modPow(a,p);
	   
	}
	
//-----------------------------------------------------------------------------
	
		void DiffKey(){
			// compute Diffe-Hellman Key = g^ab mod p
			DiffKey = b.modPow(a, p);
			System.out.println("Alice Diff Key is: " + DiffKey);
			
		}
	
//----------------------------------------------------------------------------
void encryptKey() throws Exception{
		// RSA encryption using bob's public key
	    Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, B_publickey);
		cipherKey = cipher.doFinal(secret.getEncoded());	
		
		
	}
//------------------------------------------------------------------------------
void decryptKey() throws Exception{
		// RSA decryption using Alice's private key
	    Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, Myprivatekey);
		Key2 = cipher.doFinal(cipherKey2);	
		
		System.out.println("Secret key from Bob "+ new String(Key2));
	}
//--------------------------------------------------------------------------------
void decryptmsg() throws Exception {
	
	byte[] key = DiffKey.toByteArray();
	key = Arrays.copyOf(key, 16); // use only first 128 bit
	
    // Diffe-Hellman Key
	SecretKeySpec sdk = new SecretKeySpec(key, "AES");
	
	// Decrypt message
	Cipher cipher = Cipher.getInstance("AES");
	cipher.init(Cipher.DECRYPT_MODE, sdk);
	pliantext = cipher.doFinal(cipherText2);
	
	System.out.println("Message: "+new String(pliantext));
	
	// compute hmac of the message
	SecretKeySpec macKey = new SecretKeySpec(Key2, "HmacSHA256");
	Mac mac = Mac.getInstance("HMACSHA256");
    mac.init(macKey);
    hashcheck = mac.doFinal(pliantext);
			
	    
	  
}
//--------------------------------------------------------------------------
boolean verfyhmac() throws Exception{
			
	       // check if computed and received digests matches
	       if(Arrays.equals(hashcheck, hmacdigest))
	       {
	    	   return true;
	       }
	       else
	       {
	    	   return false;
	       }
			
					
		}


//----------------------------------------------------------------------------
void RSAKeys() throws Exception
{
	// Create Key Pair using RSA.
	KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
	Myprivatekey = keyPair.getPrivate();
	Alice_publickey = keyPair.getPublic();
	
}
	
	
//---------------------------------------------------------------------------
	public void run() throws Exception{
		
		Socket client = new Socket(serverName, port);
		
		
		ObjectOutputStream out = new ObjectOutputStream (client.getOutputStream());
		// create keys
		RSAKeys();
		// Send Public Key	
		out.writeObject(Alice_publickey);
		out.flush();
		
		// Get Bob public Key
		ObjectInputStream OIS = new ObjectInputStream(client.getInputStream());
		B_publickey = (PublicKey) OIS.readObject();
		if(B_publickey  != null) System.out.println("I know have Bob's public key.");
	    // encrypt key
		encryptKey();

		
	   //Send cipherText of Key
		ObjectOutputStream bos = new ObjectOutputStream (client.getOutputStream());
		bos.writeObject(cipherKey);
		bos.flush();
		System.out.println("Sent shared key to Bob "+ new String(Key));
       
       //Perfom alogrithm (Signature, Hash, encryption)
       alogrithm();
       
       //Send Signed Hash to Bob
       ObjectOutputStream sos = new ObjectOutputStream (client.getOutputStream());
       sos.writeObject(signature);
       sos.flush();
       
       //Send encrypted message to Bob
       ObjectOutputStream mos = new ObjectOutputStream (client.getOutputStream());
       mos.writeObject(cipherText);
       mos.flush();
       System.out.println("Message sent.");
       
       // receive p and g
       
       ObjectInputStream pis = new ObjectInputStream(client.getInputStream());
       p = (BigInteger) pis.readObject();
        
       
       ObjectInputStream gis = new ObjectInputStream(client.getInputStream());
       g = (BigInteger) gis.readObject();
       
       
       //Send a = g^a mod p to bob
       makeA();
       ObjectOutputStream aos = new ObjectOutputStream (client.getOutputStream());
       aos.writeObject(r);
       aos.flush();
       // receive bob result
       ObjectInputStream bis = new ObjectInputStream(client.getInputStream());
       b = (BigInteger) bis.readObject();
       
       DiffKey();
       
       // receive encrpyted key from bob for integrity
       ObjectInputStream kis = new ObjectInputStream(client.getInputStream());
       cipherKey2= (byte []) kis.readObject();
       decryptKey();
       
       // receive hmac from Bob
       ObjectInputStream his = new ObjectInputStream(client.getInputStream());
       hmacdigest= (byte []) his.readObject();
       
       //receive message from Bob
       ObjectInputStream mis = new ObjectInputStream(client.getInputStream());
       cipherText2= (byte []) mis.readObject();
       System.out.println("Incoming message.......");
       
       // decrypt message
       decryptmsg();
       
       if(verfyhmac())
       {
    	   System.out.println("Message is Verifed!");
       }
       else
       {
    	   System.out.println("Message is not Verifed!");
       }
       
   
       client.close();
       
	}
	
	
	
}

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
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


public class Server {

	PrivateKey Myprivatekey;
	public  PublicKey Bob_publickey;
	PublicKey A_PublicKey;
	private byte[] cipherKey, cipherKey2;
	byte[] Key;
	byte[] IKey = ("8765432198765432").getBytes();
	SecretKeySpec macKey = new SecretKeySpec(IKey, "HmacSHA256");
	private byte[] signature;
	private byte[] digest, digest2;
	private byte[] cipherText,cipherText2;
	private byte[] pliantext;
	private BigInteger DiffKey;
	private BigInteger p,g,b,a,r; 
	//1000 byte message
	private String message ="bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
    		+ "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";
	
  
	public static void main(String[] args) throws Exception 
	{
		Server s = new Server();
		s.run();
		
		
	}
//-----------------------------------------------------------------------------	
		void alogrithm() throws Exception{

			byte[] key = DiffKey.toByteArray();
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			
            // Diffe Key
			SecretKeySpec sdk = new SecretKeySpec(key, "AES");
			
			// compute hmac
			Mac mac = Mac.getInstance("HMACSHA256");
		    mac.init(macKey);
		    digest2 = mac.doFinal(message.getBytes());	
		     
		    
		    // Encrypt message
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, sdk);
			cipherText2 = cipher.doFinal(message.getBytes());
			
			
		
					
		}	
//-----------------------------------------------------------------------------	
  boolean verfymsg() throws Exception{
			
	        // verify signature using Alice's public key
			Signature sig = Signature.getInstance("SHA256withRSA");
			sig.initVerify(A_PublicKey);
			sig.update(digest);
			
			return sig.verify(signature);
			
					
		}
//-------------------------------------------------------------------------------
  
  void createPandG(){
	  // create p and g
	  int bitLength = 128;
	  SecureRandom rnd = new SecureRandom();
	  p = BigInteger.probablePrime(bitLength, rnd);
	  g = BigInteger.probablePrime(bitLength, rnd);
	  
	  
  }
//--------------------------------------------------------------------------
	void makeB(){
		// create random value for b
		int bitLength = 128;
		SecureRandom rnd = new SecureRandom();
	    b = BigInteger.probablePrime(bitLength, rnd);
	
	    // compute r = g^b mod p
		r=g.modPow(b,p);
		
	}
//-----------------------------------------------------------------------------------
	void DiffKey(){
		// compute Diffe-Hellman Key = g^ab mod p
		DiffKey = a.modPow(b, p);
		System.out.println("Bob Diffie-Hellman Key is: " + DiffKey);
		
	}
	
//----------------------------------------------------------------------------
	void encryptKey() throws Exception{
			// RSA encryption using Alice's public key
		    Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, A_PublicKey);
			cipherKey2 = cipher.doFinal(macKey.getEncoded());	
			
		} 
 
  
//--------------------------------------------------------------------------------
  void decryptmsg() throws Exception {
	  
	     SecretKeySpec secret = new SecretKeySpec(Key, "AES");
	  
	        // Decrypt message
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secret);
			pliantext = cipher.doFinal(cipherText);
			
			System.out.println("Message: "+new String(pliantext));
			
			// Compute digest
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			digest = sha256.digest(pliantext);
			
	    
	  
  }
  	
//------------------------------------------------------------------------------
	
void decryptKey() throws Exception{
		// RSA decryption using Bob's private key
	    Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, Myprivatekey);
		Key = cipher.doFinal(cipherKey);	
		
		System.out.println("Secret key from Alice "+ new String(Key));
	}
//----------------------------------------------------------------------------
void RSAKeys() throws Exception
{
	// Create Key Pair using RSA.
	KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
	Myprivatekey = keyPair.getPrivate();
	Bob_publickey = keyPair.getPublic();
	
}


	
//------------------------------------------------------------------------------	
	public void run() throws Exception{
		
		ServerSocket server = new ServerSocket(6066);
		server.setSoTimeout(30000);
		Socket sock = server.accept();
		
		// Get Alice public Key
		ObjectInputStream OIS = new ObjectInputStream(sock.getInputStream());
        A_PublicKey = (PublicKey) OIS.readObject();
		if(A_PublicKey != null) System.out.println("I know have Alice public key.");
        
        // Send Bob public key to alice
        ObjectOutputStream out = new ObjectOutputStream (sock.getOutputStream());
		// create keys
		RSAKeys();
		// Send Public Key	
		out.writeObject(Bob_publickey);
		out.flush();
		
        
		// receive cipher of secret key
        ObjectInputStream bis = new ObjectInputStream(sock.getInputStream());
        cipherKey= (byte []) bis.readObject();
        decryptKey(); // decrypt Key
        
       
    	// receive signed hash
        ObjectInputStream sis = new ObjectInputStream(sock.getInputStream());
        signature= (byte []) sis.readObject();
    	
    	//receive message from alice
        ObjectInputStream mis = new ObjectInputStream(sock.getInputStream());
        cipherText= (byte []) mis.readObject();
        System.out.println("Incoming message.......");
        
        // decrypt and compute hash
        decryptmsg();
        // verify signature on hash
        boolean verify = verfymsg();
        
        if(verify)
        {
        	System.out.println("Message is Verified!");
        }
        else
        {
        	System.out.println("Message not Verified!");
        }
		
        // create p and g 
        createPandG();
        makeB(); // B = g^b mod p
        
        // send p and g to alice
        ObjectOutputStream pos = new ObjectOutputStream (sock.getOutputStream());
		pos.writeObject(p);
		pos.flush();
		
		ObjectOutputStream gos = new ObjectOutputStream (sock.getOutputStream());
		gos.writeObject(g);
		gos.flush();
				
		// receive alice result
		ObjectInputStream ais = new ObjectInputStream(sock.getInputStream());
        a = (BigInteger) ais.readObject();
        
        // send B = g^b mod p to Bob
        ObjectOutputStream bos = new ObjectOutputStream (sock.getOutputStream());
		bos.writeObject(r);
		bos.flush();
        
		DiffKey();
		
	    // send encrypted Key to alice for integrity
		encryptKey();
		ObjectOutputStream kos = new ObjectOutputStream (sock.getOutputStream());
		kos.writeObject(cipherKey2);
		kos.flush();
		System.out.println("Sent shared key to Alice "+ new String(IKey));
		
		// compute hmac and encrypted message
		alogrithm();
        
		//Send hmac to Alice
	    ObjectOutputStream hos = new ObjectOutputStream (sock.getOutputStream());
	    hos.writeObject(digest2);
	    hos.flush();
	       
	    //Send encrypted message to Alice
	    ObjectOutputStream mos = new ObjectOutputStream (sock.getOutputStream());
	    mos.writeObject(cipherText2);
	    mos.flush();
	    System.out.println("Message sent.");
	    
	 server.close();
	     	
	}
	
}

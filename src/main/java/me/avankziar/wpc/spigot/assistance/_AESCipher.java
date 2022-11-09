package main.java.me.avankziar.wpc.spigot.assistance;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*
 * @co-author https://github.com/chaudhuri-ab/CrossPlatformCiphers/blob/master/Java_CIPHER/src/ciphers/Java_AES_Cipher.java
 */
public class _AESCipher
{
	private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private static int CIPHER_KEY_LEN = 16; //16 = 128 bits
    
    /*public static void main(String[] args)
	{
		String data = "Test 1611771877468";
		String text = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, "
    			+ "sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, "
    			+ "sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. "
    			+ "Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.";
		String key = generateKey();
		String iv = generateIv();
		System.out.println("Data: "+text);
		System.out.println("Iv: "+iv);
		System.out.println("Key: "+key);
		
		String encrypt = encrypt(key, iv, text);
		System.out.println("Ecrypt: "+encrypt);
		
		String decrypt = decrypt(key, encrypt);
		System.out.println("Decrypt: "+decrypt);
	}*/
    
    public static String generateIv()
    {
    	String i = "";
    	while (i.length() != 16)
    	{
    		byte[] iv = new byte[16];
        	SecureRandom sr = new SecureRandom();
        	sr.nextBytes(iv);
        	IvParameterSpec ivspec = new IvParameterSpec(iv);
        	String ivs = Base64.getEncoder().encodeToString(ivspec.getIV());        	
        	//String ivs = bytesToHex(ivspec.getIV());
        	//System.out.println("IvGenerator: "+ivs);
        	i = ivs.substring(0, 16);
    	}
    	
    	return i;
    }
    
    public static String generateKey()
    {
    	String k = "";
    	while(k.length() != 16)
    	{
    		KeyGenerator kgen = null;
    		try
    		{
    			kgen = KeyGenerator.getInstance("AES");
    		} catch (NoSuchAlgorithmException e)
    		{
    			e.printStackTrace();
    		}
        	SecretKey skey = kgen.generateKey();
        	String key = Base64.getEncoder().encodeToString(skey.getEncoded());
        	//System.out.println("Key: "+key);
        	//System.out.println("Key.substring: "+key.substring(0, 16));
        	k = key.substring(0, 16);
    	}
    	
    	return k;
    }
    
    final protected static char[] hexArray = "0123456789abcdef".toCharArray();
    
    public static String bytesToHex(byte[] bytes) 
    {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Encrypt data using AES Cipher (CBC) with 128 bit key
     * 
     * 
     * @param key  - key to use should be 16 bytes long (128 bits)
     * @param iv - initialization vector
     * @param data - data to encrypt
     * @return encryptedData data in base64 encoding with iv attached at end after a :
     */
    public static String encrypt(String key, String iv, String data) {
        try {
            if (key.length() < _AESCipher.CIPHER_KEY_LEN) {
                int numPad = _AESCipher.CIPHER_KEY_LEN - key.length();
                
                for(int i = 0; i < numPad; i++){
                    key += "0"; //0 pad to len 16 bytes
                }
                
            } else if (key.length() > _AESCipher.CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }
            
            
            IvParameterSpec initVector = new IvParameterSpec(iv.getBytes("ISO-8859-1"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(_AESCipher.CIPHER_NAME);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, initVector);

            byte[] encryptedData = cipher.doFinal((data.getBytes()));
            
            String base64_EncryptedData = Base64.getEncoder().encodeToString(encryptedData);
            String base64_IV = Base64.getEncoder().encodeToString(iv.getBytes("ISO-8859-1"));
            
            return base64_EncryptedData + ":" + base64_IV;
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypt data using AES Cipher (CBC) with 128 bit key
     * 
     * @param key - key to use should be 16 bytes long (128 bits)
     * @param data - encrypted data with iv at the end separate by :
     * @return decrypted data string
     */
    
    public static String decrypt(String key, String data) {
        try {
            if (key.length() < _AESCipher.CIPHER_KEY_LEN) {
                int numPad = _AESCipher.CIPHER_KEY_LEN - key.length();
                
                for(int i = 0; i < numPad; i++){
                    key += "0"; //0 pad to len 16 bytes
                }
                
            } else if (key.length() > _AESCipher.CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }
            
            String[] parts = data.split(":");
            
            IvParameterSpec iv = new IvParameterSpec(Base64.getDecoder().decode(parts[1]));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("ISO-8859-1"), "AES");

            Cipher cipher = Cipher.getInstance(_AESCipher.CIPHER_NAME);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] decodedEncryptedData = Base64.getDecoder().decode(parts[0]);

            byte[] original = cipher.doFinal(decodedEncryptedData);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}

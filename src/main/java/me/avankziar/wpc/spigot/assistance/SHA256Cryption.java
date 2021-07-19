package main.java.me.avankziar.wpc.spigot.assistance;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.spec.IvParameterSpec;

public class SHA256Cryption
{
	public static String encryptSHA256(String input)
	        throws NoSuchAlgorithmException 
	{
	    MessageDigest mDigest = MessageDigest.getInstance("SHA-256");

	    byte[] shaByteArr = mDigest.digest(input.getBytes(Charset.forName("UTF-8")));
	    StringBuilder hexStrBuilder = new StringBuilder();
	    for (int i = 0; i < shaByteArr.length; i++) {
	    	hexStrBuilder.append(String.format("%02x", shaByteArr[i]));
	    }

	    return hexStrBuilder.toString();
	}
	
	/*public static String generateWebAuthKey()
    {
    	byte[] iv = new byte[42];
    	SecureRandom sr = new SecureRandom();
    	sr.nextBytes(iv);
    	IvParameterSpec ivspec = new IvParameterSpec(iv);
    	String wak = Base64.getEncoder().encodeToString(ivspec.getIV());
    	return wak;
    }*/
	
	public static String generateSalt()
    {
    	byte[] iv = new byte[42];
    	SecureRandom sr = new SecureRandom();
    	sr.nextBytes(iv);
    	IvParameterSpec ivspec = new IvParameterSpec(iv);
    	String wak = Base64.getEncoder().encodeToString(ivspec.getIV());
    	return wak;
    }

	// Salt: XcGAeNgL7YJqJ5r2f5ZTdX3J56Xm5dQ2+r/nlVmIvq81l49NsIeBg1kD
    public static void main(String[] args) throws NoSuchAlgorithmException 
    {
    	String salt = "XcGAeNgL7YJqJ5r2f5ZTdX3J56Xm5dQ2+r/nlVmIvq81l49NsIeBg1kD";
    	String pw = "987654321";
    	String hash = encryptSHA256(pw+salt);
        System.out.println("Java SHA256 = " + hash);
    }
}

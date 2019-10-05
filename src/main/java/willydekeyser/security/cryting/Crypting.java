package willydekeyser.security.cryting;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

@Service
public class Crypting {

	private static Cipher ecipher;
    private static Cipher dcipher;
    private static SecretKey key;
    
    private static SecretKeySpec secretKey;
    private static byte[] key2;
       
	public Crypting() {
		
		final String secretKey = "Het gaat u geen reet aan, dwaze potstamper!";
	     
	    String originalString = "Willy De Keyser";
	    String encryptedString = encrypt(originalString, secretKey) ;
	    String decryptedString = decrypt(encryptedString, secretKey) ;
	    
	    System.out.println("Key: " + secretKey);
	    System.out.println("String: " + originalString);
	    System.out.println("Encrypted: " + encryptedString);
	    System.out.println("String: " + decryptedString);
		
		try {
			key = KeyGenerator.getInstance("DES").generateKey();
            ecipher = Cipher.getInstance("DES");
            dcipher = Cipher.getInstance("DES");
            ecipher.init(Cipher.ENCRYPT_MODE, key);
            dcipher.init(Cipher.DECRYPT_MODE, key);
            String encrypted = Encrypting("This is a classified message!");
            String decrypted = Decrypting(encrypted);
            System.out.println("Decrypted: " + decrypted + " - " + encrypted + " - " + key.getEncoded());
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("No Such Algorithm:" + e.getMessage());
            return;
        }
        catch (NoSuchPaddingException e) {
            System.out.println("No Such Padding:" + e.getMessage());
            return;
        }
        catch (InvalidKeyException e) {
            System.out.println("Invalid Key:" + e.getMessage());
            return;
        }
	}

	public String Encrypting(String data) {
		try {
			byte[] utf8 = data.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			enc = BASE64EncoderStream.encode(enc);
			return new String(enc);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String Decrypting(String data) {
		
		try {
			byte[] dec = BASE64DecoderStream.decode(data.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);
			return new String(utf8, "UTF8");
			
		}
		catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}
	
	public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key2 = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key2 = sha.digest(key2);
            key2 = Arrays.copyOf(key2, 16);
            secretKey = new SecretKeySpec(key2, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
	
	public static String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
	
	public static String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}

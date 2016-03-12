package diavololoop.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import diavololoop.exception.WrongRSAKeyException;


public class SecureSocket {
	
	public static final int RSA_KEYSIZE = 2048; //bit
	public static final int AES_KEYSIZE = 16; //byte
	public static final String RSA_TRANSFORMATION = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
	public static final String AES_TRANSFORMATION = "AES/CFB8/NoPadding";
	
	private Socket socket;
	private CipherOutputStream output;
	private CipherInputStream input;
	
	private Key key;
	
	public SecureSocket(Socket socket){
		this.socket = socket;
	}
	
	public OutputStream getOutputStream(){
		return output;
	}
	public InputStream getInputStream(){
		return input;
	}
	
	public void init(KeyPair keyPair) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		RSAPublicKeySpec publicRSA = KeyFactory.getInstance("RSA").getKeySpec(keyPair.getPublic(), RSAPublicKeySpec.class);
		
		byte[] publicRSAmodulus = publicRSA.getModulus().toByteArray();
		byte[] publicRSAexponent = publicRSA.getPublicExponent().toByteArray();

		DataOutputStream unsecureOutput = new DataOutputStream(socket.getOutputStream());
		DataInputStream  unsecureInput	= new DataInputStream(socket.getInputStream());
		
		unsecureOutput.writeInt(publicRSAmodulus.length);
		unsecureOutput.write(publicRSAmodulus);
		unsecureOutput.writeInt(publicRSAexponent.length);
		unsecureOutput.write(publicRSAexponent);
		
		byte[] key = new byte[SecureSocket.RSA_KEYSIZE/8];
		byte[] iv = new byte[SecureSocket.AES_KEYSIZE];
		
		
		
		unsecureInput.readFully(key);
		unsecureInput.readFully(iv);
		
		Cipher cipher = Cipher.getInstance( SecureSocket.RSA_TRANSFORMATION );
		cipher.init(Cipher.DECRYPT_MODE, keyPair.getPrivate());
		key = cipher.doFinal(key);
		
		
		
		Cipher cipherAESEncrypt = Cipher.getInstance( SecureSocket.AES_TRANSFORMATION );
		Cipher cipherAESDecrypt = Cipher.getInstance( SecureSocket.AES_TRANSFORMATION );
		
		cipherAESEncrypt.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
		cipherAESDecrypt.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
		
		this.input  = new CipherInputStream (unsecureInput,  cipherAESDecrypt);
		this.output = new CipherOutputStream(unsecureOutput, cipherAESEncrypt);

	}
	
	public void waitOnInit() throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, 
					IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException{
		
		try {
			waitOnInit(null);
		} catch (WrongRSAKeyException e) {
			e.printStackTrace();
		}
	}
	
	public void waitOnInit(RSAPublicKeySpec rsa) throws InvalidKeyException, InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, 
					IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, IOException, WrongRSAKeyException{

		SecureRandom random = new SecureRandom();

		byte[] key = new byte[SecureSocket.AES_KEYSIZE];
		byte[] iv  = new byte[SecureSocket.AES_KEYSIZE];

		random.nextBytes( key );
		random.nextBytes( iv  );

		waitOnInit(key, iv, rsa);
	}
	
	public void waitOnInit(byte[] key, byte[] iv, RSAPublicKeySpec rsa) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, 
					NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, 
					WrongRSAKeyException{
		DataOutputStream unsecureOutput = new DataOutputStream(socket.getOutputStream());
		DataInputStream  unsecureInput	= new DataInputStream(socket.getInputStream());
		
		RSAPublicKeySpec receiveRSAKeySpec = receiveRSAKey(unsecureInput);
		
		if( rsa != null && !(	receiveRSAKeySpec.getModulus().equals(rsa.getModulus()) && 
				receiveRSAKeySpec.getPublicExponent().equals(rsa.getPublicExponent())) ){
			
			throw new WrongRSAKeyException();
			
		}
		
		Key publicRSAkey = KeyFactory.getInstance("RSA").generatePublic(receiveRSAKeySpec);

		Cipher cipher = Cipher.getInstance( SecureSocket.RSA_TRANSFORMATION );
		cipher.init(Cipher.ENCRYPT_MODE, publicRSAkey);
		byte[] cipherData = cipher.doFinal(key);
		
		unsecureOutput.write(cipherData);
		unsecureOutput.write(iv);
		
		Cipher cipherAESEncrypt = Cipher.getInstance( SecureSocket.AES_TRANSFORMATION );
		Cipher cipherAESDecrypt = Cipher.getInstance( SecureSocket.AES_TRANSFORMATION );
		
		cipherAESEncrypt.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
		cipherAESDecrypt.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
		
		this.input  = new CipherInputStream (unsecureInput,  cipherAESDecrypt);
		this.output = new CipherOutputStream(unsecureOutput, cipherAESEncrypt);
	}
	
	public RSAPublicKeySpec receiveRSAKey(DataInputStream input) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException{
		byte[] publicRSAmodulus = new byte[input.readInt()];
		input.readFully(publicRSAmodulus);
		
		byte[] publicRSAexponent = new byte[input.readInt()];
		input.readFully(publicRSAexponent);
		
		
		RSAPublicKeySpec publicRSAkeySpec = new RSAPublicKeySpec(new BigInteger(publicRSAmodulus), new BigInteger(publicRSAexponent));
		return publicRSAkeySpec;
	}
	
}

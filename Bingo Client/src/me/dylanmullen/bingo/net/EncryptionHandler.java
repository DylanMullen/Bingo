package me.dylanmullen.bingo.net;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class EncryptionHandler
{

	private KeyPair keyPair;
	private boolean loaded;

	private String publicKey;
	private String aesKey;

	public EncryptionHandler()
	{
		this.loaded = load();
	}

	private boolean load()
	{
		try
		{
			this.keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
			return true;
		} catch (NoSuchAlgorithmException e)
		{
			return false;
		}
	}

	public String decyrpt(String input)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
			cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());

			byte[] data = cipher.doFinal(DatatypeConverter.parseBase64Binary(input));
			return new String(data);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public String encrypt(String input)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, getAESKey());
			byte[] encrypted = cipher.doFinal(input.getBytes());
			return DatatypeConverter.printBase64Binary(encrypted);
		} catch (Exception e)
		{
			return null;
		}
	}

	public String getBase64PublicKey()
	{
		if (this.publicKey == null)
			this.publicKey = Base64.getEncoder().encodeToString(getPublicKey().getEncoded());
		return this.publicKey;
	}

	private SecretKeySpec getAESKey()
	{
		return new SecretKeySpec(DatatypeConverter.parseBase64Binary(aesKey), "AES");
	}

	public RSAPublicKey getPublicKey()
	{
		return ((RSAPublicKey) keyPair.getPublic());
	}

	public RSAPrivateKey getPrivateKey()
	{
		return ((RSAPrivateKey) keyPair.getPrivate());
	}

	public void setAesKey(String aesKey)
	{
		this.aesKey = aesKey;
	}

	public boolean isLoaded()
	{
		return loaded;
	}

}

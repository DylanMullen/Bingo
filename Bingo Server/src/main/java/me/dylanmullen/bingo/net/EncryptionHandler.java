package me.dylanmullen.bingo.net;

import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;

public class EncryptionHandler
{

	public static String decrypt(Client client, String input)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, client.getAESKey());
			byte[] data = cipher.doFinal(DatatypeConverter.parseBase64Binary(input));
			return new String(data);
		} catch (Exception e)
		{
			return null;
		}
	}

	public static String encryptAES(Client client, String input)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, client.getAESKey());
			byte[] encrypted = cipher.doFinal(input.getBytes());
			return DatatypeConverter.printBase64Binary(encrypted);
		} catch (Exception e)
		{
			return null;
		}
	}

	public static String encryptRSA(Client client, String input)
	{
		try
		{
			Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, client.getRsaPublicKey());
			byte[] encrypted = cipher.doFinal(input.getBytes());
			return DatatypeConverter.printBase64Binary(encrypted);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

}

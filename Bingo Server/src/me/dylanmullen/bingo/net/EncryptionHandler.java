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

}

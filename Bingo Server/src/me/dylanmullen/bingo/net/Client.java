package me.dylanmullen.bingo.net;

import java.net.InetAddress;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;

public class Client
{

	private InetAddress address;
	private int port;

	private String rsaPublicKey;
	private SecretKey aesKey;

	public Client(String rsaPublicKey, InetAddress address, int port)
	{
		this.rsaPublicKey = rsaPublicKey;
		this.address = address;
		this.port = port;
		generateAESKey();
	}

	private void generateAESKey()
	{
		try
		{
			KeyGenerator gen = KeyGenerator.getInstance("AES");
			gen.init(256);
			this.aesKey = gen.generateKey();
		} catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
	}

	public String getBase64AESKey()
	{
		return DatatypeConverter.printBase64Binary(aesKey.getEncoded());
	}
	
	public SecretKey getAESKey()
	{
		return aesKey;
	}
	
	public String getRsaPublicKey()
	{
		return rsaPublicKey;
	}

	public InetAddress getAddress()
	{
		return address;
	}

	public int getPort()
	{
		return port;
	}

}

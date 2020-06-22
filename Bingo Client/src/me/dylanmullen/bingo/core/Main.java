package me.dylanmullen.bingo.core;

import me.dylanmullen.bingo.net.handlers.EncryptionHandler;

/**
 * @author Dylan
 * @date 17 Jun 2020
 * @project Bingo Client
 */
public class Main
{

	/**
	 * Main method for launching the application.
	 * 
	 * @param args Arguments passed through the console.
	 */
	public static void main(String[] args)
	{
		new BingoApp();
		EncryptionHandler en = new EncryptionHandler();

		System.out.println(
				"{\"packetInformation\":{\"packetID\":5,\"uuids\":{\"packetUUID\":\"a423947b-2332-45b3-b32c-c4a28892ace7\"}},\"packetMessage\":{\"responseType\":200,\"aesKey\":\"p6bKI\\/cI2kHa25MFcGxI8OI6v7+01TkBTtxDdc+O3E4=\"}}"
						.length());
	}
}

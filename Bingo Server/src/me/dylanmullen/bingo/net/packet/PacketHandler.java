package me.dylanmullen.bingo.net.packet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.packets.Packet_001_Login;
import me.dylanmullen.bingo.net.packet.packets.Packet_002_Disconnect;
import me.dylanmullen.bingo.net.packet.packets.Packet_003_Request;

public class PacketHandler
{

	private Thread sendThread;
	private DatagramSocket socket;

	public PacketHandler(DatagramSocket socket)
	{
		this.socket = socket;
	}

	public synchronized void handle(DatagramPacket packet)
	{
		String data = decodeData(packet.getData());
		String[] j = data.split("/id/");
		int x = Integer.parseInt(j[1].substring(0, 3));
		PacketType type = PacketType.getPacket(x);

		Packet p = null;
		switch (type)
		{
			case INVALID:
				break;
			case LOGIN:
				p = new Packet_001_Login(this, packet.getAddress(), packet.getPort(), j[1].substring(3));
				break;
			case REQUEST:
				p = new Packet_003_Request(this, packet.getAddress(), packet.getPort(), j[1].substring(3));
				break;
			case DISCONNECT:
				p = new Packet_002_Disconnect(this, packet.getAddress(), packet.getPort(), j[1].substring(3));
				break;
			default:
				break;
		}

		if (p == null)
			return;
		p.handle();
	}

	public void sendPacket(Client c, Packet packet)
	{
		sendThread = new Thread(()->
		{
			DatagramPacket pack = new DatagramPacket(packet.getDataByte(), packet.getDataByte().length, c.getAddress(), c.getPort());
			try
			{
				socket.send(pack);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		});
		sendThread.start();
	}
	public void sendPacket(Packet packet)
	{
		sendThread = new Thread(()->
		{
			DatagramPacket pack = new DatagramPacket(packet.getDataByte(), packet.getDataByte().length, packet.getAddress(), packet.getPort());
			try
			{
				socket.send(pack);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		});
		sendThread.start();
	}
	
	/**
	 * Decodes data from byte[] to String. TO-DO: - Implement End-to-End encryption
	 * - Decode it here.
	 * 
	 * @param data
	 */
	private String decodeData(byte[] data)
	{
		return new String(data).trim();
	}

}

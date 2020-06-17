package me.dylanmullen.bingo.net.runnables;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.ServerStatusManager;
import me.dylanmullen.bingo.net.ServerStatusManager.ServerStatus;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.util.Task;

/**
 * @author Dylan
 * @date 18 Jun 2020
 * @project Bingo Client
 */
public class PingTask extends Task
{

	private boolean response = false;
	private int counter;

	/**
	 * A task to ping the server every few seconds to make sure that the server is
	 * still alive.
	 * 
	 * @param seconds The seconds of how often the task should be repeated.
	 */
	public PingTask(double seconds)
	{
		super(seconds);
	}

	@Override
	public void task()
	{
		if (!this.response)
		{
			if (this.counter >= 2)
			{
				ServerStatusManager.getManager().setStatus(ServerStatus.DISCONNECTED);
			}
			this.counter++;
		} else
		{
			this.counter = 0;
			this.response = false;
		}
		forcePing();
	}

	/**
	 * Forces a ping to the server.
	 */
	public void forcePing()
	{
		PacketHandler.sendPacket(PacketHandler.createPacket(004, "Ping"), new PacketCallback()
		{
			@Override
			public boolean callback()
			{
				ServerStatusManager.getManager().setStatus(ServerStatus.CONNECTED);
				setResponse(true);
				return false;
			}
		});
	}

	public void setResponse(boolean response)
	{
		this.response = response;
	}

}

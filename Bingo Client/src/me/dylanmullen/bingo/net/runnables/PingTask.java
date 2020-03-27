package me.dylanmullen.bingo.net.runnables;

import me.dylanmullen.bingo.net.PacketHandler;
import me.dylanmullen.bingo.net.ServerStatusManager;
import me.dylanmullen.bingo.net.ServerStatusManager.ServerStatus;
import me.dylanmullen.bingo.net.packet.PacketCallback;
import me.dylanmullen.bingo.util.Task;

public class PingTask extends Task
{

	private boolean response = false;
	private int counter;

	/**
	 * Used to make sure the server is still up
	 * 
	 * @param seconds
	 */
	public PingTask(double seconds)
	{
		super(seconds);
	}

	@Override
	public void task()
	{
		if (!response)
		{
			if (counter >= 2)
			{
				ServerStatusManager.getManager().setStatus(ServerStatus.DISCONNECTED);
			}
			counter++;
		} else
		{
			counter = 0;
			response = false;
		}

		PacketHandler.sendPacket(PacketHandler.createPacket(004, "Ping"), new PacketCallback()
		{
			@Override
			public boolean callback()
			{
				ServerStatusManager.getManager().setStatus(ServerStatus.CONNECTED);
				response = true;
				return false;
			}
		});
	}

}

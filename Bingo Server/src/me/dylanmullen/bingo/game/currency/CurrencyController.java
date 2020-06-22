package me.dylanmullen.bingo.game.currency;

import java.util.UUID;

import org.json.simple.JSONObject;

import me.dylanmullen.bingo.game.user.User;
import me.dylanmullen.bingo.mysql.SQLFactory;
import me.dylanmullen.bingo.mysql.sql_util.SQLTicket;
import me.dylanmullen.bingo.net.Client;
import me.dylanmullen.bingo.net.packet.Packet;
import me.dylanmullen.bingo.net.packet.PacketHandler;

public class CurrencyController
{

	private static CurrencyController instance;

	public static CurrencyController getController()
	{
		if (instance == null)
			instance = new CurrencyController();
		return instance;
	}

	public void increment(User u, double credits)
	{
		u.getUserInformation().setCredits(u.getUserInformation().getCredits() + credits);
		sendPacket(u.getClient(), u.getUserInformation().getCredits());
		updateMySQL(u.getUUID(), u.getUserInformation().getCredits());
	}

	public void deduct(User u, double credits) throws InvalidAmountException
	{
		double userCredits = u.getUserInformation().getCredits() - credits;
		if (userCredits < 0)
			throw new InvalidAmountException(u, u.getUserInformation().getCredits(), credits);

		u.getUserInformation().setCredits(userCredits);
		sendPacket(u.getClient(), userCredits);
		updateMySQL(u.getUUID(), userCredits);
	}

	@SuppressWarnings("unchecked")
	public void sendPacket(Client client, double credits)
	{
		Packet packet = PacketHandler.createPacket(client, 15, null);
		
		JSONObject message = new JSONObject();
		message.put("credits", credits);
		packet.setMessageSection(message);
		
		PacketHandler.sendPacket(packet);
	}

	private void updateMySQL(UUID uuid, double credits)
	{
		SQLTicket ticket = SQLFactory.updateData(SQLFactory.getController().getDatabase().getUserInfoTableName(), "uuid=?", new String[] { "credits" },
				new String[] { credits + "", uuid.toString().replace("-", "") }, null);
		SQLFactory.sendTicket(ticket);
	}

}

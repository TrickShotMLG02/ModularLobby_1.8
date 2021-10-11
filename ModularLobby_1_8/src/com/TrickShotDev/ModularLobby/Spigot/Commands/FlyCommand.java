package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class FlyCommand extends BaseCommand {

	public FlyCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			toggleFly(player);
		return true;
	}

	public void toggleFly(Player player) {
		player.setAllowFlight(!player.getAllowFlight());
		
		if (player.getAllowFlight())
		{
			sendPluginMessage(player, lobby.messages.getString("flyEnabled"));
		}
		else {
			sendPluginMessage(player, lobby.messages.getString("flyDisabled"));
		}
	}

}

package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class BuildCommand extends BaseCommand {

	public BuildCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			toggleBuild(player);		
		return true;
	}

	public void toggleBuild(Player player) {
		lobby.getOnlinePlayer(player).setIsInBuildmode(!lobby.getOnlinePlayer(player).getIsInBuildmode());
		lobby.getOnlinePlayer(player).setBuildGameMode(lobby.getOnlinePlayer(player).getIsInBuildmode(),
				lobby.getConfig().getString("defaultGamemode"));
		
		if (lobby.getOnlinePlayer(player).getIsInBuildmode())
		{
			sendPluginMessage(player, lobby.messages.getString("buildEnabled"));
		}
		else {
			sendPluginMessage(player, lobby.messages.getString("buildDisabled"));
		}
	}

}

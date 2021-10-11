package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public abstract class BaseCommand implements CommandExecutor {

	Lobby lobby;
	String permission;
	final String noPermissionMessageKey = "noPermission";

	public BaseCommand(Lobby lobbyInstance, String permission) {
		lobby = lobbyInstance;
		this.permission = permission;
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		return true;
	}

	public boolean playerHasPermission(Player player) {
		if (player.hasPermission(permission)) {
			return true;
		} else {
			sendNoPermsMessage(player);
			return false;
		}
	}

	public void sendNoPermsMessage(Player player) {
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', lobby.messages.getString(noPermissionMessageKey)));
	}
	
	public void sendPluginMessage(Player player, String message)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
}

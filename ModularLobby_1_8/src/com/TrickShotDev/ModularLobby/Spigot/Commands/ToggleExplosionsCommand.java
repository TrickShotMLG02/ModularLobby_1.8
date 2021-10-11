package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class ToggleExplosionsCommand extends BaseCommand {

	public ToggleExplosionsCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			toggleExplosions(player);
		return true;
	}

	public void toggleExplosions(Player player) {
		lobby.config.set("disableExplosions", !lobby.config.getBoolean("disableExplosions"));
		lobby.saveConfig();

		if (lobby.config.getBoolean("disableExplosions")) {
			sendPluginMessage(player, lobby.messages.getString("explosionsDisabled"));
		} else {
			sendPluginMessage(player, lobby.messages.getString("explosionsEnabled"));
		}
	}

}

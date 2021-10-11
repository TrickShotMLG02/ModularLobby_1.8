package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class ReloadCommand extends BaseCommand {

	public ReloadCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
		Player player = (Player) sender;

		if (playerHasPermission(player)) {
			if (lobby.reloadPluginConfigs()) {
				lobby.applyAllValues();
				sendPluginMessage(player, lobby.messages.getString("configReloadSuccess"));
			} else {
				sendPluginMessage(player, lobby.messages.getString("configReloadError"));
			}
		}
		return true;
	}
}

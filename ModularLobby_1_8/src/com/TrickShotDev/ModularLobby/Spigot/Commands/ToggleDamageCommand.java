package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class ToggleDamageCommand extends BaseCommand {

	public ToggleDamageCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			toggleDamage(player);
		return true;
	}

	public void toggleDamage(Player player) {
		lobby.config.set("disableDamage", !lobby.config.getBoolean("disableDamage"));
		lobby.saveConfig();

		if (lobby.config.getBoolean("disableDamage")) {
			sendPluginMessage(player, lobby.messages.getString("damageDisabled"));
		} else {
			sendPluginMessage(player, lobby.messages.getString("damageEnabled"));
		}
	}

}

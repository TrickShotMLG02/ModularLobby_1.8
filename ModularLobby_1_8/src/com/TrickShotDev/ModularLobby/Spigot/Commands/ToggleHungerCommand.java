package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class ToggleHungerCommand extends BaseCommand {

	public ToggleHungerCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			toggleHunger(player);
		return true;
	}

	public void toggleHunger(Player player) {
		lobby.config.set("disableHunger", !lobby.config.getBoolean("disableHunger"));
		lobby.saveConfig();

		if (lobby.config.getBoolean("disableHunger")) {
			sendPluginMessage(player, lobby.messages.getString("hungerDisabled"));
		} else {
			sendPluginMessage(player, lobby.messages.getString("hungerEnabled"));
		}
	}

}

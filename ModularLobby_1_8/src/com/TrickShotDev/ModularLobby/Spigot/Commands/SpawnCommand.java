package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class SpawnCommand extends BaseCommand {

	public SpawnCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			teleportToSpawn(player, true);
		return true;
	}
	
	public void teleportToSpawn(Player player, boolean showMessage) {
		if (!lobby.config.contains("Spawnposition.x") || !lobby.config.contains("Spawnposition.y")
				|| !lobby.config.contains("Spawnposition.z") || !lobby.config.contains("Spawnposition.yaw")
				|| !lobby.config.contains("Spawnposition.pitch")) {
			sendPluginMessage(player, lobby.messages.getString("spawnTpNotSet"));
		} else {
			Location loc = new Location(player.getWorld(), lobby.config.getDouble("Spawnposition.x"),
					lobby.config.getDouble("Spawnposition.y"), lobby.config.getDouble("Spawnposition.z"),
					(float) lobby.config.getDouble("Spawnposition.yaw"), (float) lobby.config.getDouble("Spawnposition.pitch"));

			try {
				player.teleport(loc);
				if (showMessage)
					sendPluginMessage(player, lobby.messages.getString("spawnTpSuccess"));
			} catch (Exception e) {
				if (showMessage)
					sendPluginMessage(player, lobby.messages.getString("spawnTpError"));
				e.printStackTrace();
			}
			
		}
	}

}

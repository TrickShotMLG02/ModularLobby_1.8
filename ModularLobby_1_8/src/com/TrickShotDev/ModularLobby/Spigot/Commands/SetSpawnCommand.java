package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class SetSpawnCommand extends BaseCommand {

	public SetSpawnCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			setSpawn(player);
		return true;
	}

	public void setSpawn(Player player) {
		try {
			Location loc = (Location) player.getLocation();

			lobby.config.set("Spawnposition.x", loc.getX());
			lobby.config.set("Spawnposition.y", loc.getY());
			lobby.config.set("Spawnposition.z", loc.getZ());
			lobby.config.set("Spawnposition.yaw", loc.getYaw());
			lobby.config.set("Spawnposition.pitch", loc.getPitch());
			lobby.saveConfig();
			
			sendPluginMessage(player, lobby.messages.getString("spawnSetSuccess"));
		} catch (Exception e) {
			sendPluginMessage(player, lobby.messages.getString("spawnSetError"));
			e.printStackTrace();
		}		
	}
}

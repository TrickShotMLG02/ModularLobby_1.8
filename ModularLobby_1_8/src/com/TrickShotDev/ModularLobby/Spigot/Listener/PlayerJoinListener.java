package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class PlayerJoinListener implements Listener {

	Lobby lobby;

	public PlayerJoinListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		//Add player to onlineList
		lobby.addOnlinePlayer(event.getPlayer());
		
		//Apply default values to Player
		lobby.setFoodLevel(event.getPlayer());
		lobby.setPlayerHealth(event.getPlayer());	
		event.getPlayer().setGameMode(GameMode.valueOf(lobby.getConfig().getString("defaultGamemode").toUpperCase()));

		//TP To Spawn
		if (lobby.getConfig().getBoolean("spawnAtSpawn")) {
			lobby.spawnCommand.teleportToSpawn(event.getPlayer(), false);
		}
		
		//Update Notification
		if (event.getPlayer().hasPermission("lobby.notifyUpdate") && lobby.updateChecker.getUpdate())
		{
			event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', lobby.updateChecker.getUpdateMessage()));
		}
	}

}

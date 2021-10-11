package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class PlayerQuitListener implements Listener {

	Lobby lobby;

	public PlayerQuitListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		lobby.removeOnlinePlayer(event.getPlayer());
	}

}

package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class BlockPlaceListener implements Listener {

	Lobby lobby;

	public BlockPlaceListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if (lobby.getOnlinePlayer(e.getPlayer()).getIsInBuildmode()) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}
}

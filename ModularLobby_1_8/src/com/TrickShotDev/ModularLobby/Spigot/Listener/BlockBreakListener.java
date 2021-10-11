package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class BlockBreakListener implements Listener {

	Lobby lobby;

	public BlockBreakListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if (lobby.getOnlinePlayer(e.getPlayer()).getIsInBuildmode()) {
			e.setCancelled(false);
		} else {
			e.setCancelled(true);
		}
	}
}

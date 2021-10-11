package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class InventoryCloseListener implements Listener {

	Lobby lobby;

	public InventoryCloseListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler
	public void onInventoryClose(BlockBreakEvent e) {
		
	}
}

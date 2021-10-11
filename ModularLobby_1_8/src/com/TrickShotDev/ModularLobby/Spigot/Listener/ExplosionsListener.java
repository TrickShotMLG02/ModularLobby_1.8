package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class ExplosionsListener implements Listener {

	Lobby lobby;

	public ExplosionsListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler
	public void onEntityExplode(EntityExplodeEvent e) {

		if (lobby.getConfig().getBoolean("disableExplosions")) {
			e.setCancelled(true);
		}

	}
}

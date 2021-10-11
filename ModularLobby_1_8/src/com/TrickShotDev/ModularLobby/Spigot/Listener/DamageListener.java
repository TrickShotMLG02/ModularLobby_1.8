package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class DamageListener implements Listener {

	Lobby lobby;

	public DamageListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler
	public void onHit(EntityDamageEvent e) {
		if (lobby.getConfig().getBoolean("disableDamage")) {
			if (e.getEntity() instanceof Player) {
				e.setCancelled(true);
				lobby.setPlayerHealth((Player) e.getEntity());
			}
		}
	}

}

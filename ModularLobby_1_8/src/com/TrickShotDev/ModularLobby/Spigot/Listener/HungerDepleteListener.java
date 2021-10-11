package com.TrickShotDev.ModularLobby.Spigot.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;

public class HungerDepleteListener implements Listener {

	Lobby lobby;

	public HungerDepleteListener(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	@EventHandler
	public void onHungerDeplete(FoodLevelChangeEvent e) {

		if (lobby.getConfig().getBoolean("disableHunger")) {
			e.setCancelled(true);
			lobby.setFoodLevel(((Player) e.getEntity()));
		}
	}

}

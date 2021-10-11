package com.TrickShotDev.ModularLobby.Spigot;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class LobbyPlayer {

	private Player player;
	private boolean isInBuildmode;

	public LobbyPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean getIsInBuildmode() {
		return isInBuildmode;
	}

	public void setIsInBuildmode(boolean isInBuildmode) {
		this.isInBuildmode = isInBuildmode;
	}

	public void setBuildGameMode(boolean isInBuildmode, String defaultGamemode) {
		if (isInBuildmode) {
			player.setGameMode(GameMode.CREATIVE);
		} else {
			switch (defaultGamemode.toUpperCase()) {
			case "CREATIVE":
				player.setGameMode(GameMode.CREATIVE);
				break;
			case "SURVIVAL":
				player.setGameMode(GameMode.SURVIVAL);
				break;
			case "ADVENTURE":
				player.setGameMode(GameMode.ADVENTURE);
				break;
			default:
				player.setGameMode(GameMode.SPECTATOR);
				break;
			}
		}
	}
}

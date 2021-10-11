package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;
import com.TrickShotDev.ModularLobby.Spigot.Items.ServerSelectorItem;

public class ServerSelectorCommand extends BaseCommand {

	ServerSelectorItem serverSelectorItem;
	
	public ServerSelectorCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
		serverSelectorItem = new ServerSelectorItem(lobby);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			showServerSelector(player);
		return true;
	}
	
	public void showServerSelector(Player player)
	{
		if (lobby.config.getBoolean("Items.ServerSelector.enabled"))
			serverSelectorItem.openGui(player, player.getName());
		else
			sendPluginMessage(player, lobby.messages.getString("serverSelectorDisabledError"));		
	}

}

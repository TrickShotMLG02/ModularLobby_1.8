package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;
import com.TrickShotDev.ModularLobby.Spigot.Items.ServerSelectorItem;

public class EditServerSelectorCommand extends BaseCommand {

	ServerSelectorItem serverSelectorItem;
	
	public EditServerSelectorCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
		serverSelectorItem = new ServerSelectorItem(lobby);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			editServerSelector(player);		
		return true;
	}
	
	public void editServerSelector(Player player)
	{
		serverSelectorItem.openGuiEdit(player, player.getName());	
	}
	
}

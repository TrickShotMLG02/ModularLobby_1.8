package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;
import com.TrickShotDev.ModularLobby.Spigot.Items.NavigatorItem;

public class EditNavigatorCommand extends BaseCommand {

	NavigatorItem navigatorItem;
	
	public EditNavigatorCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
		navigatorItem = new NavigatorItem(lobby);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			editNavigator(player);		
		return true;
	}
	
	public void editNavigator(Player player)
	{
		navigatorItem.openGuiEdit(player, player.getName());	
	}
	
}

package com.TrickShotDev.ModularLobby.Spigot.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;
import com.TrickShotDev.ModularLobby.Spigot.Items.NavigatorItem;

public class NavigatorCommand extends BaseCommand {

	NavigatorItem navigatorItem;
	
	public NavigatorCommand(Lobby lobbyInstance, String permission) {
		super(lobbyInstance, permission);
		navigatorItem = new NavigatorItem(lobby);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (playerHasPermission(player))
			ShowNavigator(player);		
		return true;
	}
	
	public void ShowNavigator(Player player)
	{
		if (lobby.config.getBoolean("Items.Navigator.enabled"))
			navigatorItem.openGui(player, player.getName());
		else
			sendPluginMessage(player, lobby.messages.getString("navigatorDisabledError"));
	}
	
}

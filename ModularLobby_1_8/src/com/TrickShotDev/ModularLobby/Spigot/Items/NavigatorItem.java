package com.TrickShotDev.ModularLobby.Spigot.Items;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;
import com.TrickShotDev.ModularLobby.Spigot.util.*;

public class NavigatorItem extends BaseItem implements Listener {

	Lobby lobby;
	public InventoryManager inventoryManager;

	Inventory navigatorInventory;

	public static final String EDIT_NAVIGATOR_NAME = "&b&lNavigator &4&o(Edit-Mode)";
	String NavigatorName = "&b&lNavigator";

	public NavigatorItem(Lobby lobbyInstance) {
		lobby = lobbyInstance;
		EDIT_INVENTORY_NAME = EDIT_NAVIGATOR_NAME;
	}

	public void openGuiEdit(Player player, String playerName) {
		inventoryManager = new InventoryManager(lobby);
		// navigatorInventory = inventoryManager.loadInventoryFromFile(player,
		// "navigator", "Navigator", this, true);
		navigatorInventory = loadInventoryFromFile(player, "navigator", "Navigator", true);

		if (navigatorInventory == null) {
			player.openInventory(Bukkit.createInventory(player, 27, EDIT_NAVIGATOR_NAME));
		} else {
			player.openInventory(navigatorInventory);
		}
	}

	public void openGui(Player player, String playerName) {
		inventoryManager = new InventoryManager(lobby);
		// navigatorInventory = inventoryManager.loadInventoryFromFile(player,
		// "navigator", "Navigator", this, true);
		navigatorInventory = loadInventoryFromFile(player, "navigator", "Navigator", false);

		if (navigatorInventory == null) {
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', lobby.messages.getString("navigatorNotFound")));
		} else {
			player.openInventory(navigatorInventory);
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		
		if (e.getInventory().getName() == EDIT_NAVIGATOR_NAME) {
			YamlConfiguration c = new YamlConfiguration();
			c.set("navigator" + ".size", e.getInventory().getSize());
			c.set("navigator" + ".name", NavigatorName);
			c.set("navigator" + ".content", e.getInventory().getContents());

			try {
				c.save(new File(lobby.getDataFolder(), "Navigator" + ".yml"));
				e.getPlayer().sendMessage(
						ChatColor.translateAlternateColorCodes('&', lobby.messages.getString("navigatorSavedSuccess")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

//		if (e.getInventory().getName() == EDIT_NAVIGATOR_NAME)
//			inventoryManager.saveInventoryToFile(navigatorInventory, NavigatorName, (Player) e.getPlayer(), "navigator",
//					"Navigator", "navigatorSavedSuccess", "navigatorSavedError");
	}

	public boolean saveInventoryToFile(Inventory inventory, Player player, String keyName, String fileName,
			String successMessageKey, String errorMessageKey) {

		YamlConfiguration c = new YamlConfiguration();
		c.set(keyName + ".size", inventory.getSize());
		c.set(keyName + ".name", NavigatorName);
		c.set(keyName + ".content", inventory.getContents());

		try {
			c.save(new File(lobby.getDataFolder(), fileName + ".yml"));
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', lobby.messages.getString(successMessageKey)));
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', lobby.messages.getString(errorMessageKey)));
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public Inventory loadInventoryFromFile(Player player, String keyName, String fileName, boolean useEditName) {
		try {
			Inventory inventory;
			YamlConfiguration c = YamlConfiguration
					.loadConfiguration(new File(lobby.getDataFolder(), fileName + ".yml"));

			ItemStack[] content = ((List<ItemStack>) c.get(keyName + ".content")).toArray(new ItemStack[0]);

			if (useEditName) {
				inventory = Bukkit.createInventory(player, c.getInt(keyName + ".size"), EDIT_NAVIGATOR_NAME);
			} else {
				inventory = Bukkit.createInventory(player, c.getInt(keyName + ".size"),
						ChatColor.translateAlternateColorCodes('&', c.getString(keyName + ".name")));
			}

			NavigatorName = c.getString(keyName + ".name");

			inventory.setContents(content);
			return inventory;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}

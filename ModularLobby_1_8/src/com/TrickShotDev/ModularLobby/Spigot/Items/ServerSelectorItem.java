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

public class ServerSelectorItem extends BaseItem implements Listener {

	Lobby lobby;
	public InventoryManager inventoryManager;

	Inventory serverSelectorInventory;

	public static final String EDIT_SERVER_SELECTOR_NAME = "&b&lServerSelector &4&o(Edit-Mode)";
	String ServerSelectorName = "&b&lServerSelector";

	public ServerSelectorItem(Lobby lobbyInstance) {
		lobby = lobbyInstance;
		EDIT_INVENTORY_NAME = EDIT_SERVER_SELECTOR_NAME;
	}

	public void openGuiEdit(Player player, String playerName) {
		inventoryManager = new InventoryManager(lobby);
		serverSelectorInventory = loadInventoryFromFile(player, "serverselector", "Serverselector", true);

		if (serverSelectorInventory == null) {
			player.openInventory(Bukkit.createInventory(player, 27, EDIT_SERVER_SELECTOR_NAME));
		} else {
			player.openInventory(serverSelectorInventory);
		}
	}

	public void openGui(Player player, String playerName) {
		inventoryManager = new InventoryManager(lobby);
		serverSelectorInventory = loadInventoryFromFile(player, "serverselector", "Serverselector", false);

		if (serverSelectorInventory == null) {
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', lobby.messages.getString("serverSelectorNotFound")));

		} else {
			player.openInventory(serverSelectorInventory);
		}
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {

		if (e.getInventory().getName() == EDIT_SERVER_SELECTOR_NAME) {
			YamlConfiguration c = new YamlConfiguration();
			c.set("serverselector" + ".size", e.getInventory().getSize());
			c.set("serverselector" + ".name", ServerSelectorName);
			c.set("serverselector" + ".content", e.getInventory().getContents());

			try {
				c.save(new File(lobby.getDataFolder(), "Serverselector" + ".yml"));
				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&',
						lobby.messages.getString("serverSelectorSavedSuccess")));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		// if (e.getInventory().getName() == EDIT_SERVER_SELECTOR_NAME)
		// inventoryManager.saveInventoryToFile(serverSelectorInventory,
		// ServerSelectorName, (Player) e.getPlayer(), "serverselector",
		// "ServerSelector", "serverSelectorSavedSuccess", "serverSelectorSavedError");
	}

	public boolean saveInventoryToFile(Inventory inventory, String keyName, String fileName) {
		try {
			YamlConfiguration c = new YamlConfiguration();
			c.set(keyName + ".size", inventory.getSize());
			c.set(keyName + ".name", ServerSelectorName);
			c.set(keyName + ".content", inventory.getContents());

			c.save(new File(lobby.getDataFolder(), fileName + ".yml"));

			return true;
		} catch (IOException e) {
			e.printStackTrace();

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
				inventory = Bukkit.createInventory(player, c.getInt(keyName + ".size"), EDIT_SERVER_SELECTOR_NAME);
			} else {
				inventory = Bukkit.createInventory(player, c.getInt(keyName + ".size"),
						ChatColor.translateAlternateColorCodes('&', c.getString(keyName + ".name")));
			}

			ServerSelectorName = c.getString(keyName + ".name");

			inventory.setContents(content);
			return inventory;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

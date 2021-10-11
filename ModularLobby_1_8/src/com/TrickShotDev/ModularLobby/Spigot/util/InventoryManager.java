package com.TrickShotDev.ModularLobby.Spigot.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.TrickShotDev.ModularLobby.Spigot.Lobby;
import com.TrickShotDev.ModularLobby.Spigot.Items.BaseItem;

@SuppressWarnings("unchecked")
public class InventoryManager {

	Lobby lobby;

	public InventoryManager(Lobby lobbyInstance) {
		lobby = lobbyInstance;
	}

	/*
	 * public void savePlayerInventoryToFile(Player player, String keyName, String
	 * fileName) throws IOException {
	 * 
	 * YamlConfiguration c = new YamlConfiguration(); c.set(keyName + ".armor",
	 * player.getInventory().getArmorContents()); c.set(keyName + ".content",
	 * player.getInventory().getContents());
	 * 
	 * c.save(new File(lobby.getDataFolder(), fileName + ".yml")); }
	 * 
	 * public void loadPlayerInventoryFromFile(Player player, String fileName)
	 * throws IOException { YamlConfiguration c =
	 * YamlConfiguration.loadConfiguration(new File(lobby.getDataFolder(), fileName
	 * + ".yml")); ItemStack[] content = ((List<ItemStack>)
	 * c.get("inventory.armor")).toArray(new ItemStack[0]);
	 * player.getInventory().setArmorContents(content); content = ((List<ItemStack>)
	 * c.get("inventory.content")).toArray(new ItemStack[0]);
	 * player.getInventory().setContents(content); }
	 */

	
	public boolean saveInventoryToFile(Inventory inventory, String inventoryName, Player player, String keyName, String fileName, String successMessageKey, String errorMessageKey) {

		YamlConfiguration c = new YamlConfiguration();
		c.set(keyName + ".size", inventory.getSize());
		c.set(keyName + ".name", inventoryName);
		c.set(keyName + ".content", inventory.getContents());

		try {
			c.save(new File(lobby.getDataFolder(), fileName + ".yml"));
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', lobby.messages.getString(successMessageKey)));
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', lobby.messages.getString(errorMessageKey)));
			return false;
		}
	}	
	
	public Inventory loadInventoryFromFile(Player player, String keyName, String fileName, BaseItem itemClass, boolean useEditName) {
		try {
			Inventory inventory;
			YamlConfiguration c = YamlConfiguration
					.loadConfiguration(new File(lobby.getDataFolder(), fileName + ".yml"));

			ItemStack[] content = ((List<ItemStack>) c.get(keyName + ".content")).toArray(new ItemStack[0]);

			if (useEditName) {
				inventory = Bukkit.createInventory(player, c.getInt(keyName + ".size"), BaseItem.EDIT_INVENTORY_NAME);
			} else {
				inventory = Bukkit.createInventory(player, c.getInt(keyName + ".size"),
						ChatColor.translateAlternateColorCodes('&', c.getString(keyName + ".name")));
			}

			inventory.setContents(content);
			return inventory;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

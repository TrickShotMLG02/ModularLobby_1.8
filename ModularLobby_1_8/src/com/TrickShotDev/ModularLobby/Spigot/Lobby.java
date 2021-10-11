package com.TrickShotDev.ModularLobby.Spigot;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.TrickShotDev.ModularLobby.Spigot.Commands.BuildCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.EditNavigatorCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.FlyCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.NavigatorCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.ReloadCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.SetSpawnCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.SpawnCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.ToggleDamageCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.ToggleExplosionsCommand;
import com.TrickShotDev.ModularLobby.Spigot.Commands.ToggleHungerCommand;
import com.TrickShotDev.ModularLobby.Spigot.Items.NavigatorItem;
import com.TrickShotDev.ModularLobby.Spigot.Listener.BlockBreakListener;
import com.TrickShotDev.ModularLobby.Spigot.Listener.BlockPlaceListener;
import com.TrickShotDev.ModularLobby.Spigot.Listener.DamageListener;
import com.TrickShotDev.ModularLobby.Spigot.Listener.ExplosionsListener;
import com.TrickShotDev.ModularLobby.Spigot.Listener.HungerDepleteListener;
import com.TrickShotDev.ModularLobby.Spigot.Listener.InventoryCloseListener;
import com.TrickShotDev.ModularLobby.Spigot.Listener.PlayerJoinListener;
import com.TrickShotDev.ModularLobby.Spigot.Listener.PlayerQuitListener;
import com.TrickShotDev.ModularLobby.Spigot.util.*;

@SuppressWarnings("deprecation")
public class Lobby extends JavaPlugin implements Listener {
	
	public static final int ResourceID = 90217;
	public UpdateChecker updateChecker;
	
	File defaultConfigFile;
	File defaultMessagesFile;
	File configFile;
	File messagesFile;
	
	
	public FileConfiguration config = this.getConfig();
	public FileConfiguration messages;
	
	List<LobbyPlayer> onlinePlayers = new ArrayList<>();

	//Commands to access various methods
	public SpawnCommand spawnCommand;
	
	
	@Override
	public void onEnable() {
		createConfigWithDefaults();
		createMessagesFileWithDefaults();
		
		updateChecker = new UpdateChecker(this, ResourceID);
				
		registerCommands();
		registerListeners();
		
		super.onEnable();		
	}

	@Override
	public void onDisable() {
		PlayerChatEvent.getHandlerList().unregister((Plugin) this);
		super.onDisable();
	}
	
	public void registerCommands() {
		
		this.getCommand("lobbyreload").setExecutor((CommandExecutor) new ReloadCommand(this, "lobby.reload"));
		this.getCommand("setspawn").setExecutor((CommandExecutor) new SetSpawnCommand(this, "lobby.setspawn"));
		this.getCommand("spawn").setExecutor((CommandExecutor) (spawnCommand = new SpawnCommand(this, "lobby.spawn")));
		this.getCommand("build").setExecutor((CommandExecutor) new BuildCommand(this, "lobby.build"));		
		this.getCommand("fly").setExecutor((CommandExecutor) new FlyCommand(this, "lobby.fly"));
		this.getCommand("editnavigator").setExecutor((CommandExecutor) new EditNavigatorCommand(this, "lobby.editnavigator"));
		this.getCommand("navigator").setExecutor((CommandExecutor) new NavigatorCommand(this, "lobby.navigator"));
		//this.getCommand("editserverselector").setExecutor((CommandExecutor) new EditServerSelectorCommand(this, "lobby.editserverselector"));
		//this.getCommand("serverselector").setExecutor((CommandExecutor) new ServerSelectorCommand(this, "lobby.serverselector"));
		
		this.getCommand("toggledamage").setExecutor((CommandExecutor) new ToggleDamageCommand(this, "lobby.toggledamage"));
		this.getCommand("togglehunger").setExecutor((CommandExecutor) new ToggleHungerCommand(this, "lobby.togglehunger"));
		this.getCommand("toggleexplosions").setExecutor((CommandExecutor) new ToggleExplosionsCommand(this, "lobby.toggleexplosions"));
	}
	
	public void registerListeners() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);
		Bukkit.getPluginManager().registerEvents(new HungerDepleteListener(this), this);
		Bukkit.getPluginManager().registerEvents(new DamageListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ExplosionsListener(this), this);
		Bukkit.getPluginManager().registerEvents(new BlockBreakListener(this), this);
		Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(this), this);
		Bukkit.getPluginManager().registerEvents(new InventoryCloseListener(this), this);
		
		Bukkit.getPluginManager().registerEvents(new NavigatorItem(this), this);
		//Bukkit.getPluginManager().registerEvents(new ServerSelectorItem(this), this);
	}
	
	public void createConfigWithDefaults()
	{
		config.options().copyDefaults(true);	
		defaultConfigFile = new File(getDataFolder(), "config.yml");
		
		InputStreamReader defConfigStream;
		try {
			defConfigStream = new InputStreamReader(this.getResource("config.yml"), "UTF8");
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);			
			config.setDefaults(defConfig);
			saveConfig();
			
			reloadConfig();
			config = this.getConfig();
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}	
	}
	
	public void createMessagesFileWithDefaults()
	{
		//Create file
		defaultMessagesFile = new File(getDataFolder(), "messages.yml");
        if (!defaultMessagesFile.exists()) {
        	defaultMessagesFile.getParentFile().mkdirs();
            saveResource("messages.yml", false);
         }

        messages = new YamlConfiguration();
        try {
			messages.load(defaultMessagesFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}      
        
        //Fix config
        messages.options().copyDefaults(true);	
        messagesFile = new File(getDataFolder(), "messages.yml");
		
		InputStreamReader defMessagesStream;
		try {
			defMessagesStream = new InputStreamReader(this.getResource("messages.yml"), "UTF8");
			YamlConfiguration defMessages = YamlConfiguration.loadConfiguration(defMessagesStream);			
			messages.setDefaults(defMessages);
			messages.save(messagesFile);
			
			messagesFile = new File(getDataFolder(), "messages.yml");
			messages = YamlConfiguration.loadConfiguration(messagesFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean reloadPluginConfigs() {
		try {
			super.reloadConfig();
			config = getConfig();
			
			messagesFile = new File(getDataFolder(), "messages.yml");
			messages = YamlConfiguration.loadConfiguration(messagesFile);			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!cmd.getName().equalsIgnoreCase("modularlobby")) {
			return false;
		}

		if (args.length == 0) {
			sender.sendMessage("Lobby v" + getDescription().getVersion());
			return true;
		}

		String subCmd = args[0].toUpperCase();

		switch (subCmd) {

		case "LIST":
			getServer().broadcastMessage("Online Players: " + onlinePlayers.size());
			break;

		case "ME":
			getServer()
					.broadcastMessage("My name/uuid is " + getOnlinePlayer((Player) sender).getPlayer().getCustomName()
							+ ", " + getOnlinePlayer((Player) sender).getPlayer().getUniqueId());
			break;

		default:
			sendPlayerMessage((Player)sender, messages.getString("commandNotFound"));
			return false;
		}

		saveConfig();

		return true;
	}

	public void applyAllValues() {

		List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

		for (int i = 0; i < players.size(); i++) {

			//setScoreBoard(players.get(i));
			setFoodLevel(players.get(i));
			setPlayerHealth(players.get(i));

		}
	}

	public void setScoreBoard(Player player) {
		
		if (config.getBoolean("Scoreboard.Show"))
		{
			Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
			Objective objDummy = board.registerNewObjective("serverName", "Dummy");
			objDummy.setDisplaySlot(DisplaySlot.SIDEBAR);
//			Score obj01 = board.registerNewObjective(config.getString("Scoreboard.Line01"), "Line01");
//			Score obj02 = board.registerNewObjective(config.getString("Scoreboard.Line02"), "Line02");
//			Score obj03 = board.registerNewObjective(config.getString("Scoreboard.Line03"), "Line03");
//			Score obj04 = board.registerNewObjective(config.getString("Scoreboard.Line04"), "Line04");
//			Score obj05 = board.registerNewObjective(config.getString("Scoreboard.Line05"), "Line05");
//			Score obj06 = board.registerNewObjective(config.getString("Scoreboard.Line06"), "Line06");
//			Score obj07 = board.registerNewObjective(config.getString("Scoreboard.Line07"), "Line07");
//			Score obj08 = board.registerNewObjective(config.getString("Scoreboard.Line08"), "Line08");
//			Score obj09 = board.registerNewObjective(config.getString("Scoreboard.Line09"), "Line09");
//			Score obj10 = board.registerNewObjective(config.getString("Scoreboard.Line10"), "Line10");
//			Score obj11 = board.registerNewObjective(config.getString("Scoreboard.Line11"), "Line11");
			
//			obj01.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj02.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj03.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj04.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj05.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj06.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj07.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj08.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj09.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj10.setDisplaySlot(DisplaySlot.SIDEBAR);
//			obj11.setDisplaySlot(DisplaySlot.SIDEBAR);
			
//			Score serverNameDisplay = objDummy.getScore(ChatColor.GRAY + config.getString("Scoreboard.Line01"));
//			serverNameDisplay.setScore(0);
//			
//			Score money = objDummy.getScore(ChatColor.GRAY + config.getString("Scoreboard.Line02"));
//	        money.setScore(0);
//	        
//	        Team l1 = board.registerNewTeam("onlineCounter");
	        
			player.setScoreboard(board);
		}		
	}

	public void setFoodLevel(Player player) {
		player.setFoodLevel(config.getInt("FoodLevel"));

	}

	public void setPlayerHealth(Player player) {
		player.setMaxHealth(config.getDouble("PlayerHealth"));
		player.setHealth(config.getDouble("PlayerHealth"));
	}

	public void toggleBuild(Player player) {
		getOnlinePlayer(player).setIsInBuildmode(!getOnlinePlayer(player).getIsInBuildmode());
		getOnlinePlayer(player).setBuildGameMode(getOnlinePlayer(player).getIsInBuildmode(),
				config.getString("defaultGamemode"));
	}

	public void addOnlinePlayer(Player player) {
		onlinePlayers.add(new LobbyPlayer(player));
	}

	public void removeOnlinePlayer(Player player) {
		onlinePlayers.remove(getOnlinePlayer(player));
	}

	public void broadcastColoredMessage(String message)
	{
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public void sendPlayerMessage(Player player, String message)
	{
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public LobbyPlayer getOnlinePlayer(Player player) {
		for (LobbyPlayer lobbyPlayer : onlinePlayers) {
			if (lobbyPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return lobbyPlayer;
			}
		}
		return null;
	}
}

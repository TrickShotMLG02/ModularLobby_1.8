package com.TrickShotDev.ModularLobby.Spigot.util;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class UpdateChecker {

    private JavaPlugin plugin;
    private int project;
    private URL url;
    private String version;
    private boolean setup = true;

    private boolean update;
    private String newVersion;

    public UpdateChecker(JavaPlugin plugin, int project) {
        this.plugin = plugin;
        this.project = project;
        version = plugin.getDescription().getVersion();

        try {
            url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + project);
            check();
        } catch (MalformedURLException exception) {
            setup = false;
            exception.printStackTrace();
        }
    }

    public String getResourceURL() {
        return "https://spigotmc.org/recources/" + project;
    }

    public void check() {
        if (!setup) return;
        new BukkitRunnable() {
            public void run() {
                try {
                    URLConnection connection = url.openConnection();
                    newVersion = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();
                    update = !version.equals(newVersion);
                    
                    plugin.getServer().getLogger().info(ChatColor.translateAlternateColorCodes('&', getUpdateMessage()));
                } catch (Exception exception) {
                    exception.printStackTrace();
                    setup = false;
                }
            }
        }.runTaskAsynchronously(plugin);
    }

    public String getUpdateMessage() {
        if (update)
        {
        	return ChatColor.translateAlternateColorCodes('&', ("&b&oModularLobby: &7New version found! You are currently running version " + version + ", version " + newVersion + " is ready to be downloaded on &6&o" + getResourceURL()));
        }      	
        else
        {
        	return ChatColor.translateAlternateColorCodes('&', "&b&oModularLobby: &7You are up to date!");
        }        	
    }
    
    public boolean getUpdate()
    {
    	return update;
    }
}
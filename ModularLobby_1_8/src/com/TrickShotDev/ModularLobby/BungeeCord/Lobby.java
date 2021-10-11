package com.TrickShotDev.ModularLobby.BungeeCord;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

public class Lobby extends Plugin {

	public Lobby() {
		// TODO Auto-generated constructor stub
	}

	public Lobby(ProxyServer proxy, PluginDescription description) {
		super(proxy, description);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void onEnable()
    {
        getProxy().registerChannel( "ModularLobby:channel" );
        getLogger().info( "Plugin enabled!" );
    }

    @Override
    public void onDisable()
    {
        getLogger().info( "Plugin disabled!" );
    }
    
}

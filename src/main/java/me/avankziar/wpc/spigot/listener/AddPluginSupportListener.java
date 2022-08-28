package main.java.me.avankziar.wpc.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.java.me.avankziar.wpc.spigot.event.AddPluginSupportEvent;
import main.java.me.avankziar.wpc.spigot.handler.ConfigHandler;

public class AddPluginSupportListener implements Listener
{
	
	@EventHandler
	public void onAddPluginSupport(AddPluginSupportEvent event)
	{
		if(!new ConfigHandler().isMainServer())
		{
			return;
		}
		//new WebTaskHandler().addPluginTables(event.getPluginName(), event.getAliasname(), event.getTableMap());
	}

}

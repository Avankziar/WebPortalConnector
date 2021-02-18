package main.java.me.avankziar.wpc.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import main.java.me.avankziar.wpc.spigot.event.AddPluginSupportEvent;
import main.java.me.avankziar.wpc.spigot.handler.WebTaskHandler;
import main.java.me.avankziar.wpc.spigot.objects.PluginSettings;

public class AddPluginSupportListener implements Listener
{	
	public AddPluginSupportListener()
	{}
	
	@EventHandler
	public void onAddPluginSupport(AddPluginSupportEvent event)
	{
		if(!PluginSettings.settings.isMainServer())
		{
			return;
		}
		new WebTaskHandler().addPluginTables(event.getPluginName(), event.getAliasname(), event.getTableMap());
	}

}

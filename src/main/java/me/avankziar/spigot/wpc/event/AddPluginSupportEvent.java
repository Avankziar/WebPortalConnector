package main.java.me.avankziar.spigot.wpc.event;

import java.util.LinkedHashMap;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddPluginSupportEvent extends Event
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean isCancelled;
	private String pluginName;
	private String aliasname;
	private LinkedHashMap<String, String> tableMap = new LinkedHashMap<>();
	
	public AddPluginSupportEvent(String pluginName, String aliasname, LinkedHashMap<String, String> tableMap)
	{
		setCancelled(false);
		setPluginName(pluginName);
		setAliasname(aliasname);
		setTableMap(tableMap);
	}

	public HandlerList getHandlers() 
    {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() 
    {
        return HANDLERS;
    }

	public boolean isCancelled()
	{
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled)
	{
		this.isCancelled = isCancelled;
	}

	public String getPluginName()
	{
		return pluginName;
	}

	public void setPluginName(String pluginName)
	{
		this.pluginName = pluginName;
	}

	public LinkedHashMap<String, String> getTableMap()
	{
		return tableMap;
	}

	public void setTableMap(LinkedHashMap<String, String> tableMap)
	{
		this.tableMap = tableMap;
	}

	public String getAliasname()
	{
		return aliasname;
	}

	public void setAliasname(String aliasname)
	{
		this.aliasname = aliasname;
	}

}

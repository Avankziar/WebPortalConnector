package main.java.me.avankziar.wpc.spigot.event;

import java.util.LinkedHashMap;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JavaPHPTaskEvent extends Event
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean isCancelled;
	private int mySQLID;
	private String pluginName;
	private LinkedHashMap<String, Object> taskMap = new LinkedHashMap<>();
	
	public JavaPHPTaskEvent(int mySQLID, String pluginName, LinkedHashMap<String, Object> taskMap)
	{
		setCancelled(false);
		setMySQLID(mySQLID);
		setPluginName(pluginName);
		setTaskMap(taskMap);
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

	public LinkedHashMap<String, Object> getTaskMap()
	{
		return taskMap;
	}

	public void setTaskMap(LinkedHashMap<String, Object> taskMap)
	{
		this.taskMap = taskMap;
	}

	public int getMySQLID()
	{
		return mySQLID;
	}

	public void setMySQLID(int mySQLID)
	{
		this.mySQLID = mySQLID;
	}

}

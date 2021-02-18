package main.java.me.avankziar.wpc.spigot.objects;

import java.util.ArrayList;

public class PluginObject
{
	private String pluginName;
	private String aliasName;
	private boolean activ;
	private ArrayList<TableObject> pluginTables;
	
	public PluginObject(String pluginName, String aliasName, boolean activ, ArrayList<TableObject> pluginTables)
	{
		setPluginName(pluginName);
		setAliasName(aliasName);
		setActiv(activ);
		setPluginTables(pluginTables);
	}

	public String getPluginName()
	{
		return pluginName;
	}

	public void setPluginName(String pluginName)
	{
		this.pluginName = pluginName;
	}

	public boolean isActiv()
	{
		return activ;
	}

	public void setActiv(boolean activ)
	{
		this.activ = activ;
	}

	public ArrayList<TableObject> getPluginTables()
	{
		return pluginTables;
	}

	public void setPluginTables(ArrayList<TableObject> pluginTables)
	{
		this.pluginTables = pluginTables;
	}

	public String getAliasName()
	{
		return aliasName;
	}

	public void setAliasName(String aliasName)
	{
		this.aliasName = aliasName;
	}
}

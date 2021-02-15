package main.java.me.avankziar.wpc.spigot.database;

import java.io.IOException;
import java.util.ArrayList;

import main.java.me.avankziar.wpc.spigot.WebPortalConnector;
import main.java.me.avankziar.wpc.spigot.database.tables.TableI;

public class MysqlHandler implements TableI
{
	public enum Type
	{
		PLUGINUSER;
	}
	
	private WebPortalConnector plugin;
	public String tablePluginUser;
	
	public MysqlHandler(WebPortalConnector plugin) 
	{
		this.plugin = plugin;
		loadMysqlHandler();
	}
	
	public boolean loadMysqlHandler()
	{
		tablePluginUser = plugin.getYamlHandler().getConfig().getString("Mysql.tablePluginUser");
		if(tablePluginUser == null)
		{
			return false;
		}
		return true;
	}
	
	public boolean exist(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.existI(plugin, whereColumn, whereObject);
		}
		return false;
	}
	
	public boolean create(Type type, Object object)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.createI(plugin, object);
		}
		return false;
	}
	
	public boolean updateData(Type type, Object object, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.updateDataI(plugin, object, whereColumn, whereObject);
		}
		return false;
	}
	
	public Object getData(Type type, String whereColumn, Object... whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getDataI(plugin, whereColumn, whereObject);
		}
		return null;
	}
	
	public boolean deleteData(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.deleteDataI(plugin, whereColumn, whereObject);
		}
		return false;
	}
	
	public int lastID(Type type)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.lastIDI(plugin);
		}
		return 0;
	}
	
	public int countWhereID(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.countWhereIDI(plugin, whereColumn, whereObject);
		}
		return 0;
	}
	
	public ArrayList<?> getList(Type type, String orderByColumn,
			boolean desc, int start, int quantity, String whereColumn, Object...whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getListI(plugin, orderByColumn, start, quantity, whereColumn, whereObject);
		}
		return null;
	}
	
	public ArrayList<?> getTop(Type type, String orderByColumn, boolean desc, int start, int end) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getTopI(plugin, orderByColumn, start, end);
		}
		return null;
	}
	
	public ArrayList<?> getAllListAt(Type type, String orderByColumn,
			boolean desc, String whereColumn, Object...whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getAllListAtI(plugin, orderByColumn, whereColumn, whereObject);
		}
		return null;
	}
}
